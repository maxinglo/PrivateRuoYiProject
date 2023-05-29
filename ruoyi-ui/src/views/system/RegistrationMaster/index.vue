<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="组别" prop="teamGroup">
        <el-input
          v-model="queryParams.teamGroup"
          placeholder="请输入组别"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参赛队名" prop="teamName">
        <el-input
          v-model="queryParams.teamName"
          placeholder="请输入参赛队名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="团长" prop="teamLeader">
        <el-input
          v-model="queryParams.teamLeader"
          placeholder="请输入团长"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="领队" prop="teamCaptain">
        <el-input
          v-model="queryParams.teamCaptain"
          placeholder="请输入领队"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教练" prop="coach">
        <el-input
          v-model="queryParams.coach"
          placeholder="请输入教练"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工作人员" prop="staff">
        <el-input
          v-model="queryParams.staff"
          placeholder="请输入工作人员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="队医" prop="teamDoctor">
        <el-input
          v-model="queryParams.teamDoctor"
          placeholder="请输入队医"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系方式" prop="contactInfo">
        <el-input
          v-model="queryParams.contactInfo"
          placeholder="请输入联系方式"
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
          v-hasPermi="['system:RegistrationMaster:add']"
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
          v-hasPermi="['system:RegistrationMaster:edit']"
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
          v-hasPermi="['system:RegistrationMaster:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:RegistrationMaster:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="RegistrationMasterList" @selection-change="handleSelectionChange" highlight-row :row-key ="row => row.id">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="标识符" align="center" prop="id" />
      <el-table-column label="组别" align="center" prop="teamGroup" />
      <el-table-column label="参赛队名" align="center" prop="teamName" />
      <el-table-column label="团长" align="center" prop="teamLeader" />
      <el-table-column label="领队" align="center" prop="teamCaptain" />
      <el-table-column label="教练" align="center" prop="coach" />
      <el-table-column label="工作人员" align="center" prop="staff" />
      <el-table-column label="队医" align="center" prop="teamDoctor" />
      <el-table-column label="联系方式" align="center" prop="contactInfo" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:RegistrationMaster:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:RegistrationMaster:remove']"
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

    <!-- 添加或修改报名主表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="组别" prop="teamGroup">
          <el-input v-model="form.teamGroup" placeholder="请输入组别" />
        </el-form-item>
        <el-form-item label="参赛队名" prop="teamName">
          <el-input v-model="form.teamName" placeholder="请输入参赛队名" />
        </el-form-item>
        <el-form-item label="团长" prop="teamLeader">
          <el-input v-model="form.teamLeader" placeholder="请输入团长" />
        </el-form-item>
        <el-form-item label="领队" prop="teamCaptain">
          <el-input v-model="form.teamCaptain" placeholder="请输入领队" />
        </el-form-item>
        <el-form-item label="教练" prop="coach">
          <el-input v-model="form.coach" placeholder="请输入教练" />
        </el-form-item>
        <el-form-item label="工作人员" prop="staff">
          <el-input v-model="form.staff" placeholder="请输入工作人员" />
        </el-form-item>
        <el-form-item label="队医" prop="teamDoctor">
          <el-input v-model="form.teamDoctor" placeholder="请输入队医" />
        </el-form-item>
        <el-form-item label="联系方式" prop="contactInfo">
          <el-input v-model="form.contactInfo" placeholder="请输入联系方式" />
        </el-form-item>
        <el-divider content-position="center">报名子表信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddRegistrationDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteRegistrationDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="registrationDetailList" :row-class-name="rowRegistrationDetailIndex" @selection-change="handleRegistrationDetailSelectionChange" ref="registrationDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="主表ID" prop="masterId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.masterId" placeholder="请输入主表ID" />
            </template>
          </el-table-column>
          <el-table-column label="学工号" prop="studentId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.studentId" placeholder="请输入学工号" />
            </template>
          </el-table-column>
          <el-table-column label="名字" prop="name" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.name" placeholder="请输入名字" />
            </template>
          </el-table-column>
          <el-table-column label="性别" prop="gender" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.gender" placeholder="请选择性别">
                <el-option label="请选择字典生成" value="" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="身份证号码" prop="idCardNumber" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.idCardNumber" placeholder="请输入身份证号码" />
            </template>
          </el-table-column>
          <el-table-column label="项目一" prop="project1" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.project1" placeholder="请输入项目一" />
            </template>
          </el-table-column>
          <el-table-column label="项目二" prop="project2" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.project2" placeholder="请输入项目二" />
            </template>
          </el-table-column>
          <el-table-column label="接力赛" prop="relayRace" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.relayRace" placeholder="请输入接力赛" />
            </template>
          </el-table-column>
          <el-table-column label="拔河比赛" prop="tugOfWar" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.tugOfWar" placeholder="请输入拔河比赛" />
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="remark" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remark" placeholder="请输入备注" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRegistrationMaster, getRegistrationMaster, delRegistrationMaster, addRegistrationMaster, updateRegistrationMaster } from "@/api/system/RegistrationMaster";

export default {
  name: "RegistrationMaster",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedRegistrationDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 报名主表表格数据
      RegistrationMasterList: [],
      // 报名子表表格数据
      registrationDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        teamGroup: null,
        teamName: null,
        teamLeader: null,
        teamCaptain: null,
        coach: null,
        staff: null,
        teamDoctor: null,
        contactInfo: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        teamGroup: [
          { required: true, message: "组别不能为空", trigger: "blur" }
        ],
        teamName: [
          { required: true, message: "参赛队名不能为空", trigger: "blur" }
        ],
        teamLeader: [
          { required: true, message: "团长不能为空", trigger: "blur" }
        ],
        teamCaptain: [
          { required: true, message: "领队不能为空", trigger: "blur" }
        ],
        coach: [
          { required: true, message: "教练不能为空", trigger: "blur" }
        ],
        staff: [
          { required: true, message: "工作人员不能为空", trigger: "blur" }
        ],
        teamDoctor: [
          { required: true, message: "队医不能为空", trigger: "blur" }
        ],
        contactInfo: [
          { required: true, message: "联系方式不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询报名主表列表 */
    getList() {
      this.loading = true;
      listRegistrationMaster(this.queryParams).then(response => {
        this.RegistrationMasterList = response.rows;
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
        teamGroup: null,
        teamName: null,
        teamLeader: null,
        teamCaptain: null,
        coach: null,
        staff: null,
        teamDoctor: null,
        contactInfo: null
      };
      this.registrationDetailList = [];
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
      this.title = "添加报名主表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRegistrationMaster(id).then(response => {
        this.form = response.data;
        this.registrationDetailList = response.data.registrationDetailList;
        this.open = true;
        this.title = "修改报名主表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.registrationDetailList = this.registrationDetailList;
          if (this.form.id != null) {
            updateRegistrationMaster(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRegistrationMaster(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除报名主表编号为"' + ids + '"的数据项？').then(function() {
        return delRegistrationMaster(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 报名子表序号 */
    rowRegistrationDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 报名子表添加按钮操作 */
    handleAddRegistrationDetail() {
      let obj = {};
      obj.masterId = "";
      obj.studentId = "";
      obj.name = "";
      obj.gender = "";
      obj.idCardNumber = "";
      obj.project1 = "";
      obj.project2 = "";
      obj.relayRace = "";
      obj.tugOfWar = "";
      obj.remark = "";
      this.registrationDetailList.push(obj);
    },
    /** 报名子表删除按钮操作 */
    handleDeleteRegistrationDetail() {
      if (this.checkedRegistrationDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的报名子表数据");
      } else {
        const registrationDetailList = this.registrationDetailList;
        const checkedRegistrationDetail = this.checkedRegistrationDetail;
        this.registrationDetailList = registrationDetailList.filter(function(item) {
          return checkedRegistrationDetail.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleRegistrationDetailSelectionChange(selection) {
      this.checkedRegistrationDetail = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/RegistrationMaster/export', {
        ...this.queryParams
      }, `RegistrationMaster_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
