<template>
  <div >
    <el-dialog
      title="搜索"
      :visible.sync="searchFlag"
      width="50%"
      :modal="false"
      center>
      <!-- 输入框 -->
      <div class="search-input-wrapper">
        <i class="el-icon-search"></i>
        <input style="width: 80%;border: none;outline: medium" v-model="input" placeholder="输入文章标题或内容..." />
      </div>
      <div class="search-result-wrapper">
        <hr class="divider" />
        <div v-show="total!==-1">
          查询到{{total}}条与<span style="font-weight: bold">{{input}}</span>相关的结果
        </div>
        <br>
        <br>
        <div v-for="item in blogList" :key="item.id">
          <el-card class="box-card my-shadow">
            <div slot="header" class="clearfix">
              <span style="font-weight: bold;cursor:pointer;" v-html="item.title" @click="toBlog(item.id)"></span>
            </div>
            <div class="eleven wide column">
              <div class="ui mini horizontal link list">
                <div class="item">
                  <!-- <img v-bind:src=item.avatar class="ui avatar image"> -->
                  <div class="content"><a class="header">{{this.$store.state.avatar}}</a></div>
                </div>
                <div class="item">
                  <i class="calendar icon"></i> {{item.time}}
                </div>
                <div class="item">
                  <i class="eye icon"></i> {{item.views}}
                </div>
                <div class="item">
                  <i class="thumbs up outline icon"></i> {{item.likes}}
                </div>
              </div>
            </div>
            <!-- <div class="text item" v-html="item.content" style="cursor:pointer;" @click="toBlog(item.id)"> -->
            <div class="text item" v-html="$options.filters.setContent(item.content)" style="cursor:pointer;" @click="toBlog(item.id)">
            </div>
          </el-card>
          <br>
        </div>
        <!-- 搜索结果不存在提示 -->
        <el-card class="box-card my-shadow"  v-show="total === 0">
          <div style="font-size:0.875rem" >找不到您查询的内容：" <span style="font-weight: bold">{{input}}</span> "
          </div>
        </el-card>
      <span slot="footer" class="dialog-footer">
  </span>
      </div>
    </el-dialog>
  </div>

</template>

<script>
export default {
  data: function () {
    return {
      input: '',
      blogList: [],
      total: -1,
      path: ''
    }
  },
  methods: {
    setDialogVisible () {
      this.searchFlag = true
    },
    toBlog (blogId) {
      this.$store.state.searchFlag = false;
      this.$router.push({ path: "/blog/" + blogId });
    }
  },
  filters: {
    setContent (value) {
      if (!value) return "";
      if (value.length > 125) {
        return value.slice(0, 125) + "...";
      }
      return value;
    }
  },
  mounted () {
    this.path = this.$route.path
  },
  computed: {
    searchFlag: {
      set (value) {
        this.$store.state.searchFlag = value;
      },
      get () {
        return this.$store.state.searchFlag;
      }
    },
  },
  watch: {
    $route (to, from) {
      this.path = to.path
    },
    async input (value) {
      const param = {
        currentPage: 1,
        pageSize: 20,
        queryString: value,
        sort: null,
        categoryId: null,
        labelId: null
      }
      const { data: res } = await this.$http.post('/api/home/search', param)
      if (res.flag) {
        this.blogList = res.data.records
        this.total = res.data.total
      } else { // 执行失败
        this.$message.error(res.message)
      }
      // this.$http.post('/api/home/search', param).then((res) => {
      //   if (res.flag) {
      //     this.blogList = res.data.records
      //     this.total = res.data.total
      //   } else { // 执行失败
      //     this.$message.error(res.message)
      //   }
      // })
    }
  }
}
</script>

<style scoped>
  .box-card{
    width: 80%;
    margin-left: 9%;
  }
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }
  .search-input-wrapper {
    display: flex;
    padding: 5px;
    height: 35px;
    width: 100%;
    border: 2px solid #8e8cd8;
    border-radius: 2rem;
  }
  @media (min-width: 960px) {
    .search-result-wrapper {
      padding-right: 5px;
      height: 450px;
      overflow: auto;
    }
  }
  @media (max-width: 959px) {
    .search-result-wrapper {
      height: calc(100vh - 110px);
      overflow: auto;
    }
  }
  .divider {
    margin: 20px 0;
    border: 2px dashed #d2ebfd;
  }
  .search-reslut-content {
    color: #555;
    cursor: pointer;
    border-bottom: 1px dashed #ccc;
    padding: 5px 0;
    line-height: 2;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
  }
</style>
