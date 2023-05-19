<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="主表ID" prop="masterId">
        <el-input
          v-model="queryParams.masterId"
          placeholder="请输入主表ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学工号" prop="studentId">
        <el-input
          v-model="queryParams.studentId"
          placeholder="请输入学工号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名字" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select v-model="queryParams.gender" placeholder="请选择性别" clearable>
          <el-option
            v-for="dict in dict.type.sys_user_sex"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="身份证号码" prop="idCardNumber">
        <el-input
          v-model="queryParams.idCardNumber"
          placeholder="请输入身份证号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目一" prop="project1">
        <el-input
          v-model="queryParams.project1"
          placeholder="请输入项目一"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目二" prop="project2">
        <el-input
          v-model="queryParams.project2"
          placeholder="请输入项目二"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="接力赛" prop="relayRace">
        <el-input
          v-model="queryParams.relayRace"
          placeholder="请输入接力赛"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="拔河比赛" prop="tugOfWar">
        <el-input
          v-model="queryParams.tugOfWar"
          placeholder="请输入拔河比赛"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:RegistrationDetails:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:RegistrationDetails:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:RegistrationDetails:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:RegistrationDetails:export']"
        >导出</el-button>
      </el-col>
      <el-col :span = "1.5">
        <el-button
          type="warning"
          plain
          size = "mini"
          @click="mailSend"
          >发送测试邮件
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="RegistrationDetailsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="标识符" align="center" prop="id" />
      <el-table-column label="主表ID" align="center" prop="masterId" />
      <el-table-column label="学工号" align="center" prop="studentId" />
      <el-table-column label="名字" align="center" prop="name" />
      <el-table-column label="性别" align="center" prop="gender">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.gender"/>
        </template>
      </el-table-column>
      <el-table-column label="身份证号码" align="center" prop="idCardNumber" />
      <el-table-column label="项目一" align="center" prop="project1" />
      <el-table-column label="项目二" align="center" prop="project2" />
      <el-table-column label="接力赛" align="center" prop="relayRace" />
      <el-table-column label="拔河比赛" align="center" prop="tugOfWar" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:RegistrationDetails:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:RegistrationDetails:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改报名子表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="主表ID" prop="masterId">
          <el-input v-model="form.masterId" placeholder="请输入主表ID" />
        </el-form-item>
        <el-form-item label="学工号" prop="studentId">
          <el-input v-model="form.studentId" placeholder="请输入学工号" />
        </el-form-item>
        <el-form-item label="名字" prop="name">
          <el-input v-model="form.name" placeholder="请输入名字" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="身份证号码" prop="idCardNumber">
          <el-input v-model="form.idCardNumber" placeholder="请输入身份证号码" />
        </el-form-item>
        <el-form-item label="项目一" prop="project1">
          <el-input v-model="form.project1" placeholder="请输入项目一" />
        </el-form-item>
        <el-form-item label="项目二" prop="project2">
          <el-input v-model="form.project2" placeholder="请输入项目二" />
        </el-form-item>
        <el-form-item label="接力赛" prop="relayRace">
          <el-input v-model="form.relayRace" placeholder="请输入接力赛" />
        </el-form-item>
        <el-form-item label="拔河比赛" prop="tugOfWar">
          <el-input v-model="form.tugOfWar" placeholder="请输入拔河比赛" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listRegistrationDetails,
  getRegistrationDetails,
  delRegistrationDetails,
  addRegistrationDetails,
  updateRegistrationDetails,
  sendTestMail
} from "@/api/system/RegistrationDetails";

export default {
  name: "RegistrationDetails",
  dicts: ['sys_user_sex'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 报名子表表格数据
      RegistrationDetailsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        masterId: null,
        studentId: null,
        name: null,
        gender: null,
        idCardNumber: null,
        project1: null,
        project2: null,
        relayRace: null,
        tugOfWar: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        masterId: [
          { required: true, message: "主表ID不能为空", trigger: "blur" }
        ],
        studentId: [
          { required: true, message: "学工号不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "名字不能为空", trigger: "blur" }
        ],
        gender: [
          { required: true, message: "性别不能为空", trigger: "change" }
        ],
        idCardNumber: [
          { required: true, message: "身份证号码不能为空", trigger: "blur" }
        ],
        project1: [
          { required: true, message: "项目一不能为空", trigger: "blur" }
        ],
        project2: [
          { required: true, message: "项目二不能为空", trigger: "blur" }
        ],
        relayRace: [
          { required: true, message: "接力赛不能为空", trigger: "blur" }
        ],
        tugOfWar: [
          { required: true, message: "拔河比赛不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询报名子表列表 */
    getList() {
      this.loading = true;
      listRegistrationDetails(this.queryParams).then(response => {
        this.RegistrationDetailsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        masterId: null,
        studentId: null,
        name: null,
        gender: null,
        idCardNumber: null,
        project1: null,
        project2: null,
        relayRace: null,
        tugOfWar: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加报名子表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRegistrationDetails(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报名子表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRegistrationDetails(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRegistrationDetails(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除报名子表编号为"' + ids + '"的数据项？').then(function() {
        return delRegistrationDetails(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/RegistrationDetails/export', {
        ...this.queryParams
      }, `RegistrationDetails_${new Date().getTime()}.xlsx`)
    },
    /** 发送邮件按钮操作*/
    mailSend(){
      return sendTestMail();
    }
  }
};
</script>
