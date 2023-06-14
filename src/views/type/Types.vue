<template>
  <div class="types">    <!-- banner -->
    <div class="types-banner banner">
      <h1 class="banner-title">分类</h1>
    </div>
    <!--中间内容-->
    <div class="m-container-small m-types">
      <div class="ui container">
        <!--header-->
        <div class="ui top attached segment my-shadow">
          <div class="ui middle aligned two column grid">
            <div class="column">
              <h3 class="ui my-blue header">分类</h3>
            </div>
            <div class="right aligned column">
              共 <h2 class="ui orange header m-inline-block m-text-thin"> {{categoryList.length}} </h2> 个
            </div>
          </div>
        </div>

        <div class="ui attached segment m-padded-tb-large my-shadow" >
          <template v-for="item in categoryList">
            <div class="ui labeled button m-margin-tb-tiny" @click="saveNavState(item)" v-if="activeId===item.id" :key="item.id">
              <a class="ui basic blue button">{{item.categoryname}}</a>
              <div class="ui basic blue left pointing label">{{item.categoryCount}}</div>
            </div>
            <div class="ui labeled button m-margin-tb-tiny" @click="saveNavState(item)" v-else :key="item.id">
              <a class="ui basic button">{{item.categoryname}}</a>
              <div class="ui basic left pointing label">{{item.categoryCount}}</div>
            </div>
          </template>
        </div>

        <!--header-->
        <div class="ui top attached segment my-shadow">
          <div class="ui middle aligned two column grid">
            <div class="column">
              <h3 class="ui my-blue header">博客</h3>
            </div>
            <div class="right aligned column">
              共 <h2 class="ui orange header m-inline-block m-text-thin">{{pagination.total}}</h2> 篇
            </div>
          </div>
        </div>

        <!--content-->
        <div class="ui attached segment my-blog-shadow">

          <div class="ui padded vertical segment m-padded-tb-large" v-for="item in dataList" :key="item.id">
            <div class="ui middle aligned mobile reversed stackable grid">
              <div class="eleven wide column" style="cursor:pointer;">
                <h3 class="ui header" @click="toBlog(item.id)">{{item.title}}</h3>
                <p class="m-text" @click="toBlog(item.id)">{{item.descript}}</p>
                <div class="ui grid">
                  <div class="eleven wide column">
                    <div class="ui mini horizontal link list">
                      <div class="item">
                        <!-- <img v-bind:src=item.avatar class="ui avatar image"> -->
                        <div class="content" @click="toUser(item.authorid)"><a class="header">{{item.username}}</a></div>
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
                  <div class="right aligned five wide column">
                    <a target="_blank" class="ui my-blue basic label m-padded-tiny m-text-thin">{{item.categoryName}}</a>
                  </div>
                </div>
              </div>
              <!-- <div class="five wide column">
                <a target="_blank">
                  <img v-bind:src=item.firstPicture @click="toBlog(item.blogId)" alt="" class="ui rounded image">
                </a>
              </div> -->

            </div>
          </div>
        </div>

        <!--footer-->
        <div class="ui bottom attached segment my-blog-shadow">
          <div class="pagination-container">
            <el-pagination
              class="pagiantion"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pagination.currentPage"
              :page-sizes="[2,6,10,15]"
              :page-size="pagination.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="pagination.total">
            </el-pagination>
          </div>
        </div>

      </div>
    </div>

    <br>
    <br>
  </div>
</template>

<script>
export default {
  data () {
    return {
      activeId: '', // 被激活的标签id
      dataList: [], // 当前页要展示的博客分页列表数据
      categoryList: [], // 分类列表的数据
      tagList: [], // 标签列表的数据
      latestList: [], // 最新发布的博客列表的数据
      pagination: { // 分页相关模型数据
        currentPage: 1, // 当前页码
        pageSize: 6, // 每页显示的记录数
        total: 0, // 总记录数
      },
      user: {},
      username: '',
      // 被激活的链接地址
      avatar: ''
    }
  },
  created () {
    this.findPage(this.activeId)
    this.getcategoryList()
  },
  methods: {
    toBlog (blogId) {
      this.$store.state.blogId = blogId;
      this.$router.push({ path: "/blog/" + blogId });
    },
    toUser (userId) {
      this.$router.push({ path: "/userinfo/" + userId });
    },
    async saveNavState (item) {
      this.activeId = item.id
      if (item.typeCount === 0) {
        this.$message.error('查询失败，当前所在分类的博客数为0')
      } else {
        await this.findPage(this.activeId)
      }
    },
    async getcategoryList () {
      const { data: res } = await this.$http.get('/api/home/getCategoryCount')
      this.categoryList = res.data
    },
    // 分页查询
    async findPage (categoryId) {
      var id = this.$route.path.split('/types/')[1]
      if (categoryId === '') {
        categoryId = id
      }
      this.activeId = categoryId
      this.$store.state.categoryId = null
      const param = {
        currentPage: this.pagination.currentPage,
        pageSize: this.pagination.pageSize,
        queryString: null,
        sort: null,
        categoryId: categoryId,
        labelId: null
      }
      const { data: res } = await this.$http.post('/api/articles/category', param)
      if (!res.flag) {
        return this.$message.error('获取首页列表失败！')
      }
      this.pagination.total = res.data.total
      this.dataList = res.data.records
    },
    handleCurrentChange (currentPage) {
      // 设置最新的页码
      this.pagination.currentPage = currentPage
      // 重新调用findPage方法进行分页查询
      this.findPage()
    },
    handleSizeChange (newSize) {
      this.pagination.pageSize = newSize
      this.findPage()
    }
  },
  mounted () {
    $('.ui.dropdown').dropdown({
      on: 'hover'
    })
    $('.menu.toggle').click(function () {
      $('.m-item').toggleClass('m-mobile-hide')
    })
  }
}
</script>

<style scoped>
  .container{
    animation: main 1.0s;
  }
  .m-types {
    padding-top: 69vh !important;
    padding-bottom: 0px !important;
  }
  .types-banner {
    height: 67vh;
    background: url("../../assets/images/category.jpg") center
    center / cover no-repeat;
    background-color: #49b1f5;
  }
</style>
