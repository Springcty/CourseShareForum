<!-- eslint-disable prettier/prettier -->
<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-button type="primary" size="small" icon="el-icon-plus" @click="openModel(null)">新增</el-button>
      <!-- <el-button
        type="danger"
        size="small"
        icon="el-icon-delete"
        :disabled="tagIdList.length == 0"
        @click="isDelete = true"
        >批量删除</el-button
      >-->
      <!-- <div style="margin-left:auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入标签名"
          style="width:200px"
          @keyup.enter.native="searchTags"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="searchTags"
          >搜索</el-button
        >
      </div>-->
    </div>
    <!-- 表格展示 -->
    <el-table border :data="tagList" v-loading="loading" @selection-change="selectionChange">
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <!-- 标签名 -->
      <el-table-column prop="labelname" label="标签名" align="center">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.labelname }}</el-tag>
        </template>
      </el-table-column>
      <!-- 文章量 -->
      <el-table-column prop="labelCount" label="文章量" align="center" />
      <!-- 标签创建时间
      <el-table-column prop="createTime" label="创建时间" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.createTime | date }}
        </template>
      </el-table-column>-->
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="openModel(scope.row)">编辑</el-button>
          <!-- <el-popconfirm
            title="确定删除吗？"
            style="margin-left:1rem"
            @confirm="deleteTag(scope.row.tagId)"
          >
            <el-button size="mini" type="danger" slot="reference">删除</el-button>
          </el-popconfirm>-->
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页
    <el-pagination
      class="pagination-container"
      background
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="current"
      :page-size="size"
      :total="count"
      :page-sizes="[5, 10, 20]"
      layout="total, sizes, prev, pager, next, jumper"
    />-->
    <!-- 批量删除对话框 -->
    <el-dialog :visible.sync="isDelete" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color:#ff9900" />提示
      </div>
      <div style="font-size:1rem">是否删除选中项？</div>
      <div slot="footer">
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteTag(null)">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 编辑对话框 -->
    <el-dialog :visible.sync="addOrEdit" width="30%">
      <div class="dialog-title-container" slot="title" ref="tagTitle" />
      <el-form label-width="80px" size="medium" :model="tagForm">
        <el-form-item label="标签名">
          <el-input style="width:220px" v-model="tagForm.labelname" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="addOrEditTag">确 定</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
export default {
  created() {
    this.listTags();
  },
  data: function() {
    return {
      isDelete: false,
      loading: true,
      addOrEdit: false,
      keywords: null,
      tagList: [],
      tagIdList: [],
      tagForm: {
        id: null,
        labelname: ""
      },
      current: 1,
      size: 10,
      count: 0
    };
  },
  methods: {
    selectionChange(tagList) {
      this.tagIdList = [];
      tagList.forEach(item => {
        this.tagIdList.push(item.id);
      });
    },
    searchTags() {
      this.current = 1;
      this.listTags();
    },
    sizeChange(size) {
      this.size = size;
      this.listTags();
    },
    currentChange(current) {
      this.current = current;
      this.listTags();
    },
    deleteTag(id) {
      var param = {};
      if (id == null) {
        param = { data: this.tagIdList };
      } else {
        param = { data: [id] };
      }
      this.axios
        .delete("/api/server/tag/admin/delete", param)
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            });
            this.listTags();
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            });
          }
        });
      this.isDelete = false;
    },
    listTags() {
      // const param = {
      //   currentPage: this.current,
      //   pageSize: this.size,
      //   queryString: this.keywords
      // };
      // this.axios
      //   .post("/api/server/tag/admin/tagList", param)
      //   .then(({ data }) => {
      //     this.tagList = data.data.records;
      //     this.count = data.data.total;
      //     this.loading = false;
      //   });
      this.axios
        .get("http://101.37.254.247:9001/home/getLabelCount")
        .then(({ data }) => {
          this.tagList = data.data;
          this.loading = false;
        });
    },
    openModel(tag) {
      if (tag != null) {
        this.tagForm = JSON.parse(JSON.stringify(tag));
        delete this.tagForm.labelCount;
        this.$refs.tagTitle.innerHTML = "修改标签";
      } else {
        this.tagForm.id = null;
        this.tagForm.labelname = "";
        this.$refs.tagTitle.innerHTML = "添加标签";
      }
      this.addOrEdit = true;
    },
    addOrEditTag() {
      if (this.tagForm.labelname.trim() == "") {
        this.$message.error("标签名不能为空");
        return false;
      }
      this.axios
        .post(
          "http://101.37.254.247:9001/labels/admin/updateOrInsert",
          this.tagForm
        )
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            });
            this.listTags();
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            });
          }
        });
      this.addOrEdit = false;
    }
  }
};
</script>
