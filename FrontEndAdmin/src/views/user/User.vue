<!-- eslint-disable prettier/prettier -->
<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <!-- 条件筛选
      <div style="margin-left:auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入昵称"
          style="width:200px"
          @keyup.enter.native="searchUsers"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="searchUsers"
        >搜索</el-button>
      </div>-->
    </div>
    <!-- 表格展示 -->
    <el-table border :data="userList" v-loading="loading">
      <!-- 表格列 -->
      <el-table-column prop="linkAvatar" label="头像" align="center" width="100">
        <template>
          <img src="../../assets/images/avatars/3.jpg" width="40" height="40" />
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名" align="center" width="140" />
      <!-- <el-table-column prop="loginType" label="登录方式" align="center" width="80">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.loginType == 1">账号</el-tag>
          <el-tag v-if="scope.row.loginType == 2">QQ</el-tag>
          <el-tag type="danger" v-if="scope.row.loginType == 3">微博</el-tag>
          <el-tag type="info" v-if="scope.row.loginType == 4">人脸</el-tag>
        </template>
      </el-table-column>-->
      <el-table-column prop="roleList" label="用户角色" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.priviledged">管理员</el-tag>
          <el-tag v-else>普通用户</el-tag>
          <!-- <el-tag
            v-for="(item, index) of scope.row.roleList"
            :key="index"
            style="margin-right:4px;margin-top:4px"
          >{{ item.roleName }}</el-tag>-->
        </template>
      </el-table-column>
      <!-- <el-table-column prop="isDisable" label="禁用" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-color="#13ce66"
            inactive-color="#F4F4F5"
            :active-value="1"
            :inactive-value="0"
            @change="changeDisable(scope.row)"
          />
        </template>
      </el-table-column>-->
      <!-- <el-table-column prop="lastIp" label="上次登录ip" align="center" width="140" /> -->
      <!-- <el-table-column prop="ipSource" label="登录地址" align="center" width="140" /> -->
      <!-- <el-table-column prop="createTime" label="创建时间" width="130" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.createTime | date }}
        </template>
      </el-table-column>-->
      <!-- <el-table-column prop="lastLoginTime" label="上次登录时间" width="130" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.lastLoginTime | date }}
        </template>
      </el-table-column>-->
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="100">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="openEditModel(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      class="pagination-container"
      background
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="current"
      :page-size="size"
      :total="count"
      :page-sizes="[10, 20]"
      layout="total, sizes, prev, pager, next, jumper"
    />
    <!-- 修改对话框 -->
    <el-dialog :visible.sync="isEdit" width="30%">
      <div class="dialog-title-container" slot="title">修改用户</div>
      <el-form label-width="60px" size="medium" :model="userForm">
        <el-form-item label="角色">
          <el-radio v-model="roleId" label="0">普通用户</el-radio>
          <el-radio v-model="roleId" label="1">管理员</el-radio>
          <!-- <el-checkbox-group v-model="roleId">
            <el-checkbox
              v-for="item of userRoleList"
              :key="item.rid"
              :label="item.rid"
            >{{ item.roleName }}</el-checkbox>
          </el-checkbox-group>-->
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="isEdit = false">取 消</el-button>
        <el-button type="primary" @click="editUserRole">确 定</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
export default {
  created() {
    this.listUsers();
    // this.listRoles();
  },
  data: function() {
    return {
      loading: true,
      isEdit: false,
      userForm: {
        uid: null
      },
      loginType: null,
      userRoleList: [
        { rid: 0, roleName: "普通用户" },
        { rid: 1, roleName: "管理员" }
      ],
      roleId: null,
      userList: [],
      typeList: [
        {
          type: 1,
          desc: "邮箱"
        },
        {
          type: 2,
          desc: "QQ"
        },
        {
          type: 3,
          desc: "微博"
        }
      ],
      keywords: null,
      current: 1,
      size: 10,
      count: 0
    };
  },
  methods: {
    searchUsers() {
      this.current = 1;
      this.listUsers();
    },
    sizeChange(size) {
      this.size = size;
      this.listUsers();
    },
    currentChange(current) {
      this.current = current;
      this.listUsers();
    },
    changeDisable(user) {
      this.axios
        .put("/api/server/user/admin/disable", {
          uid: user.uid,
          status: user.status
        })
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            });
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            });
          }
          this.isEdit = false;
        });
    },
    openEditModel(user) {
      this.roleId = user.priviledged;
      this.userForm.uid = user.id;
      // this.userForm = JSON.parse(JSON.stringify(user));
      // this.userForm.roleList.forEach(item => {
      //   this.roleIdList.push(item.rid);
      // });
      this.isEdit = true;
    },
    editUserRole() {
      this.userForm.roleId = this.roleId;
      console.log(this.userForm);
      this.axios
        .get(
          `http://101.37.254.247:9001/admin/privilege/${this.userForm.uid}/${this.userForm.roleId}`
        )
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            });
            this.listUsers();
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            });
          }
          this.isEdit = false;
        });
    },
    listUsers() {
      this.axios
        .post("http://101.37.254.247:9001/admin/usersList", {
          currentPage: this.current,
          pageSize: this.size,
          queryString: this.keywords,
          sort: "new",
          categoryId: null,
          labelId: null
        })
        .then(({ data }) => {
          this.userList = data.data.records;
          this.count = data.data.total;
          this.loading = false;
        })
        .catch(err => {
          console.error(err);
        });
    }
    // listRoles() {
    //   this.axios.get("/api/server/role/admin/listAllRoles").then(({ data }) => {
    //     this.userRoleList = data.data;
    //   });
    // }
  },
  watch: {
    loginType() {
      this.current = 1;
      this.listUsers();
    }
  }
};
</script>
