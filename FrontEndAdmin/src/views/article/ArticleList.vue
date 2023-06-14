<!-- eslint-disable no-unused-vars -->
<!-- eslint-disable prettier/prettier -->
<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <br />
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-button
        v-if="isDelete == 0"
        type="danger"
        size="small"
        icon="el-icon-delete"
        :disabled="articleIdList.length == 0"
        @click="updateIsDelete = true"
      >批量删除</el-button>
      <el-button
        v-else
        type="danger"
        size="small"
        icon="el-icon-delete"
        :disabled="articleIdList.length == 0"
        @click="remove = true"
      >批量删除</el-button>
      <!-- 条件筛选 -->
      <div style="margin-left:auto">
        <!-- 分类 -->
        <el-select
          clearable
          size="small"
          v-model="categoryId"
          filterable
          placeholder="请选择分类"
          style="margin-right:1rem"
        >
          <el-option
            v-for="item in typeList"
            :key="item.id"
            :label="item.categoryname"
            :value="item.id"
          />
        </el-select>
        <!-- 标签
        <el-select
          clearable
          size="small"
          v-model="labelId"
          filterable
          placeholder="请选择标签"
          style="margin-right:1rem"
        >
          <el-option
            v-for="item in tagList"
            :key="item.id"
            :label="item.labelname"
            :value="item.id"
          />
        </el-select> -->
        <!-- 文章名
        <el-input
          clearable
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入文章名"
          style="width:200px"
          @keyup.enter.native="searchArticles"
        />-->
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="searchArticles"
        >搜索</el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table border :data="articleList" @selection-change="selectionChange" v-loading="loading">
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <!-- 文章标题 -->
      <el-table-column prop="title" label="标题" align="center" />
      <!-- 文章分类 -->
      <el-table-column prop="categoryName" label="分类" width="110" align="center" />
      <!-- 文章浏览量 -->
      <el-table-column prop="viewsCount" label="浏览量" width="70" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.views">{{ scope.row.views }}</span>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <!-- 文章点赞量 -->
      <el-table-column prop="likes" label="点赞量" width="70" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.likes">{{ scope.row.likes }}</span>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <!-- 文章收藏量 -->
      <el-table-column prop="stars" label="收藏量" width="70" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.stars">{{ scope.row.stars }}</span>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <!-- 文章评论量 -->
      <el-table-column prop="comments" label="评论量" width="70" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.comments">{{ scope.row.comments }}</span>
          <span v-else>0</span>
        </template>
      </el-table-column>

      <!-- 文章类型
      <el-table-column prop="type" label="类型" width="80" align="center">
        <template slot-scope="scope">
          <el-tag :type="articleType(scope.row.copyright).tagType">
            {{ articleType(scope.row.copyright).name }}
          </el-tag>
        </template>
      </el-table-column>-->

      <!-- 文章发表时间
      <el-table-column
        prop="createTime"
        label="发表时间"
        width="130"
        align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.createTime | date }}
        </template>
      </el-table-column>-->

      <el-table-column label="操作" align="center" width="230">
        <template slot-scope="scope">
          <el-button
            v-if="!scope.row.verified"
            type="primary"
            size="mini"
            @click="verifyArticle(scope.row.id)"
          >审核</el-button>

          <el-popconfirm
            title="确定删除吗？"
            style="margin-left:10px"
            @confirm="updateArticleDelete(scope.row.id)"
            v-if="scope.row.isDelete == 0"
          >
            <el-button size="mini" type="danger" slot="reference">删除</el-button>
          </el-popconfirm>

          <el-popconfirm
            title="确定恢复吗？"
            v-if="scope.row.isDelete == 1"
            @confirm="updateArticleDelete(scope.row.id)"
          >
            <el-button size="mini" type="success" slot="reference">恢复</el-button>
          </el-popconfirm>

          <el-popconfirm
            style="margin-left:10px"
            title="确定彻底删除吗？"
            @confirm="deleteArticles(scope.row.id)"
          >
            <el-button size="mini" type="danger" slot="reference">删除</el-button>
          </el-popconfirm>
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
    <!-- 批量逻辑删除对话框 -->
    <el-dialog :visible.sync="updateIsDelete" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color:#ff9900" />提示
      </div>
      <div style="font-size:1rem">是否删除选中项？</div>
      <div slot="footer">
        <el-button @click="updateIsDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteArticles(null)">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 批量彻底删除对话框 -->
    <el-dialog :visible.sync="remove" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color:#ff9900" />提示
      </div>
      <div style="font-size:1rem">是否彻底删除选中项？</div>
      <div slot="footer">
        <el-button @click="remove = false">取 消</el-button>
        <el-button type="primary" @click="deleteArticles(null)">确 定</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
// import qs from "qs";
export default {
  created() {
    this.listArticles();
    this.listCategories();
    this.listTags();
  },
  data: function() {
    return {
      loading: true,
      updateIsDelete: false,
      remove: false,
      activeStatus: "all",
      articleList: [],
      articleIdList: [],
      typeList: [],
      tagList: [],
      keywords: null,
      categoryId: null,
      labelId: null,
      isDelete: 0,
      status: null,
      current: 1,
      size: 10,
      count: 0
    };
  },
  methods: {
    selectionChange(articleList) {
      this.articleIdList = [];
      articleList.forEach(item => {
        this.articleIdList.push(item.id);
      });
    },
    searchArticles() {
      this.current = 1;
      this.listArticles();
    },
    verifyArticle(id) {
      this.axios
        .get(`http://101.37.254.247:9001/admin/articles/verified/${id}`)
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "审核成功",
              message: data.message
            });
            console.log(data.message);
            this.listArticles();
          } else {
            this.$notify.error({
              title: "审核失败",
              message: data.message
            });
          }
        });
      window.location.href = `http://localhost:8081/#/blog/${id}`;
    },
    updateArticleDelete(id) {
      let param = {};
      if (id != null) {
        param.idList = [id];
      } else {
        param.idList = this.articleIdList;
      }
      param.isDelete = this.isDelete == 0 ? 1 : 0;
      this.axios
        .put("http://101.37.254.247:9001/server/admin/articles", param)
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            });
            this.listArticles();
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            });
          }
          this.updateIsDelete = false;
        });
    },
    deleteArticles(id) {
      this.updateIsDelete = false;
      if (id != null) {
        this.axios
          .delete(`http://101.37.254.247:9001/admin/articles/deleteId/${id}`)
          .then(({ data }) => {
            if (data.flag) {
              this.$notify.success({
                title: "成功",
                message: data.message
              });
              this.listArticles();
            } else {
              this.$notify.error({
                title: "失败",
                message: data.message
              });
            }
            this.remove = false;
          });
      } else {
        console.log(this.articleIdList);
        // this.axios({
        //   url: "http://101.37.254.247:9001/admin/articles/deleteIds",
        //   methods: "post",
        //   params: this.articleIdList,
        //   paramsSerializer: params => {
        //     return qs.stringify(params, { indices: false });
        //   }
        // }).then(({ data }) => {
        // var param = {};
        // param = { ids: this.articleIdList };
        this.axios
          .post(
            "http://101.37.254.247:9001/admin/articles/deleteIds",
            this.articleIdList
          )
          .then(({ data }) => {
            if (data.flag) {
              this.$notify.success({
                title: "成功",
                message: data.message
              });
              this.listArticles();
            } else {
              this.$notify.error({
                title: "失败",
                message: data.message
              });
            }
            this.remove = false;
          });
      }
    },
    sizeChange(size) {
      this.size = size;
      this.listArticles();
    },
    currentChange(current) {
      this.current = current;
      this.listArticles();
    },
    changeStauts(status) {
      switch (status) {
        case "all":
          this.isDelete = 0;
          this.status = null;
          break;
        case "public":
          this.isDelete = 0;
          this.status = 1;
          break;
        case "secret":
          this.isDelete = 0;
          this.status = 2;
          break;
        case "draft":
          this.isDelete = 0;
          this.status = 3;
          break;
        case "delete":
          this.isDelete = 1;
          this.status = null;
          break;
      }
      this.activeStatus = status;
    },
    changeTop(article) {
      this.axios
        .put("http://101.37.254.247:9001/server/admin/articles/top", {
          id: article.id,
          isTop: article.isTop
        })
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: "置顶成功"
            });
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            });
          }
          this.remove = false;
        });
    },
    listArticles() {
      console.log(this.categoryId);
      this.axios
        .post("http://101.37.254.247:9001/admin/articles/list", {
          currentPage: this.current,
          pageSize: this.size,
          queryString: this.keywords,
          sort: "new",
          categoryId: this.categoryId,
          labelId: this.labelId
        })
        .then(({ data }) => {
          this.articleList = data.data.records;
          this.count = data.data.total;
          this.loading = false;
        })
        .catch(err => {
          console.error(err);
        });
    },
    listCategories() {
      this.axios
        .get("http://101.37.254.247:9001/home/getCategoryCount")
        .then(({ data }) => {
          this.typeList = data.data;
        });
    },
    listTags() {
      this.axios
        .get("http://101.37.254.247:9001/home/getLabelCount")
        .then(({ data }) => {
          this.tagList = data.data;
        });
    }
  },
  watch: {
    categoryId() {
      this.current = 1;
      this.listArticles();
    },
    labelId() {
      this.current = 1;
      this.listArticles();
    },
    status() {
      this.current = 1;
      this.listArticles();
    },
    isDelete() {
      this.current = 1;
      this.listArticles();
    }
  },
  computed: {
    // articleType() {
    //   return function(type) {
    //     var tagType = "";
    //     var name = "";
    //     switch (type) {
    //       case 1:
    //         tagType = "danger";
    //         name = "原创";
    //         break;
    //       case 2:
    //         tagType = "success";
    //         name = "转载";
    //         break;
    //       case 3:
    //         tagType = "primary";
    //         name = "翻译";
    //         break;
    //     }
    //     return {
    //       tagType: tagType,
    //       name: name
    //     };
    //   };
    // },
    isActive() {
      return function(status) {
        return this.activeStatus == status ? "active-status" : "status";
      };
    }
  }
};
</script>

<style scoped>
.operation-container {
  margin-top: 1.5rem;
}
.article-status-menu {
  font-size: 14px;
  margin-top: 40px;
  color: #999;
}
.article-status-menu span {
  margin-right: 24px;
}
.status {
  cursor: pointer;
}
.active-status {
  cursor: pointer;
  color: #333;
  font-weight: bold;
}
.article-cover {
  position: relative;
  width: 100%;
  height: 90px;
  border-radius: 4px;
}
.article-cover::after {
  content: "";
  background: rgba(0, 0, 0, 0.3);
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}
.article-status-icon {
  color: #fff;
  font-size: 1.5rem;
  position: absolute;
  right: 1rem;
  bottom: 1.4rem;
}
</style>
