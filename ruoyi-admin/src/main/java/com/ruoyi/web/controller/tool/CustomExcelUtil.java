package com.ruoyi.web.controller.tool;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.utils.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.DecimalFormat;
import java.util.*;

public class CustomExcelUtil<T, U> {
    private static final Logger log = LoggerFactory.getLogger(CustomExcelUtil.class);

    public static final String FORMULA_REGEX_STR = "=|-|\\+|@";

    public static final String[] FORMULA_STR = {"=", "-", "+", "@"};

    /**
     * 用于dictType属性数据存储，避免重复查缓存
     */

    public Map<String, String> sysDictMap = new HashMap<String, String>();

    /**
     * Excel sheet最大行数，默认65536
     */
    public static final int sheetSize = 65536;

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    /**
     * 导入导出数据列表
     */
    private List<T> list;

    /**
     * 统计列表
     */
    private Map<Integer, Double> statistics = new HashMap<Integer, Double>();

    /**
     * 数字格式
     */
    private static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("######0.00");

    /**
     * 需要排除列属性
     */
    public String[] excludeFields;
    /**
     * 主子表数据列表
     */
    private Map<T, U> map;

    /**
     * 主表实体对象
     */
    private Class<T> masterClazz;

    /**
     * 子表实体对象
     */
    private Class<U> detailClazz;

    /**
     * 主表注解
     */
    private List<Object[]> masterFields;
    /**
     * 子表注解
     */
    private List<Object[]> detailFields;

    /**
     * sheet名称
     */
    private String[] sheetName;

    /**
     * 工作表对象
     */
    private Sheet[] sheet;

    /**
     * 主表对象的子列表方法
     */
    private Method masterSubMethod;
    /**
     * 主表对象的子列表属性
     */
    private List<Field> masterSubFields;
    /**
     * 子表对象的子列表方法
     */
    private Method detailSubMethod;
    /**
     * 子表对象的子列表属性
     */
    private List<Field> detailSubFields;
    private short maxHeight;

    /**
     * 构造函数
     *
     * @param masterClazz 主表实体对象
     * @param detailClazz 子表实体对象
     */
    public CustomExcelUtil(Class<T> masterClazz, Class<U> detailClazz) {
        this.masterClazz = masterClazz;
        this.detailClazz = detailClazz;
    }

    public void init(Map<T, U> map, String[] sheetName) {
        if (map == null || map.isEmpty()) {
            map = new HashMap<T, U>();
        }
        this.map = map;
        this.sheetName = sheetName;
        createExcelField();
    }

    private void createExcelField() {
        this.masterFields = getFields(masterClazz, true);
        this.detailFields = getFields(detailClazz, false);
        this.maxHeight = getRowHeight();
    }

    public <T> List<Object[]> getFields(Class<T> clazz, boolean type) {
        List<Object[]> fields = new ArrayList<Object[]>();
        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        for (Field field : tempFields) {
            // 单注解
            if (field.isAnnotationPresent(Excel.class)) {
                Excel attr = field.getAnnotation(Excel.class);
                if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == Excel.Type.EXPORT)) {
                    field.setAccessible(true);
                    fields.add(new Object[]{field, attr});
                }
                if (Collection.class.isAssignableFrom(field.getType())) {
                    if (type) {
                        masterSubMethod = getSubMethod(field.getName(), clazz);
                        ParameterizedType pt = (ParameterizedType) field.getGenericType();
                        Class<?> subClass = (Class<?>) pt.getActualTypeArguments()[0];
                        this.masterSubFields = FieldUtils.getFieldsListWithAnnotation(subClass, Excel.class);
                    } else {
                        detailSubMethod = getSubMethod(field.getName(), clazz);
                        ParameterizedType pt = (ParameterizedType) field.getGenericType();
                        Class<?> subClass = (Class<?>) pt.getActualTypeArguments()[0];
                        this.detailSubFields = FieldUtils.getFieldsListWithAnnotation(subClass, Excel.class);
                    }
                }
            }

            // 多注解
            if (field.isAnnotationPresent(Excels.class)) {
                Excels attrs = field.getAnnotation(Excels.class);
                Excel[] excels = attrs.value();
                for (Excel attr : excels) {
                    if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == Excel.Type.EXPORT)) {
                        field.setAccessible(true);
                        fields.add(new Object[]{field, attr});
                    }
                }
            }
        }
        return fields;
    }

    public Method getSubMethod(String name, Class<?> pojoClass) {
        StringBuffer getMethodName = new StringBuffer("get");
        getMethodName.append(name.substring(0, 1).toUpperCase());
        getMethodName.append(name.substring(1));
        Method method = null;
        try {
            method = pojoClass.getMethod(getMethodName.toString(), new Class[]{});
        } catch (Exception e) {
            log.error("获取对象异常{}", e.getMessage());
        }
        return method;
    }

    /**
     * 根据注解获取最大行高
     */
    public short getRowHeight() {
        double maxHeight = 0;
        for (Object[] os : this.detailFields) {
            Excel excel = (Excel) os[1];
            maxHeight = Math.max(maxHeight, excel.height());
        }
        return (short) (maxHeight * 20);
    }

    /**
     * 创建一个工作簿
     */
    public void createWorkbook() {
        this.wb = new SXSSFWorkbook(500);
        int sheetNum = map.keySet().size();
        this.sheet = new Sheet[sheetNum];
        int cnt = 0;
        for (Object object : map.keySet()) {
            for (Object[] os : this.masterFields) {
                Excel excel = (Excel) os[1];
                if (excel.isSheetName()) {
                    Field field = (Field) os[0];
                    this.sheet[cnt] = wb.createSheet();
                    //获取被注解的属性值
                    field.setAccessible(true);
                    try {
                        Object value = field.get(object);
                        if (value != null) {
                            wb.setSheetName(cnt, value.toString());
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            cnt++;
        }
        this.styles = createStyles(wb);
    }


    private Map<String, CellStyle> createStyles(Workbook wb) {
        // 写入各条记录,每条记录对应excel表中的一行
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        style.setFont(titleFont);
        styles.put("title", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font totalFont = wb.createFont();
        totalFont.setFontName("Arial");
        totalFont.setFontHeightInPoints((short) 10);
        style.setFont(totalFont);
        styles.put("total", style);


        return styles;
    }




}
