<template>
  <div id="app" class="home" >
    <!-- banner -->
    <div class="home-banner">
      <div class="banner-container">
        <h1 class="blog-title animated zoomIn">
          <!-- 首页 -->
        </h1>
        <!-- 向下滚动 -->
        <!-- <div class="scroll-down" @click="scrollDown">
          <h4>向下滚动<i class="el-icon-arrow-down"></i></h4>
        </div> -->
      </div>
      <!--中间内容,如果太窄了可放到container    <div  class="m-container m-padded-tb-big">-->
    </div>
    <div  class="m-home">
      <div class="ui container">
        <div class="ui stackable grid">
          <!--左边博客列表-->
          <div class="eleven wide column">
            <!--header-->
            <div class="ui top attached segment">
              <div class="ui middle aligned two column grid my-shadow">
                <div class="column">
                  <h3 class="ui my-blue header" >文章</h3>
                </div>
                  <div class="right aligned column"><h4 class="ui header m-inline-block m-text" v-if="pagination.queryString!=='' && pagination.queryString!==null" style="height: 1px !important;">根据"{{pagination.queryString}}"的搜索结果</h4>
                  共 <h2 class="ui orange header m-inline-block m-text-thin">{{pagination.total}}</h2> 篇
                </div>
                  <el-row>
                    <el-button type = "text" @click = "handleSortChange('new')">最新</el-button>
                    <el-button type = "text" @click = "handleSortChange('hot')">最热</el-button>
                  </el-row>
              </div>
            </div>

            <!--content-->
            <div class="ui attached  segment my-blog-shadow">

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
                  :current-page="pagination.currentPaege"
                  :page-sizes="[2,6,10,15]"
                  :page-size="pagination.pageSize"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="pagination.total">
                </el-pagination>
              </div>
            </div>

          </div>

          <!--右边的top-->
          <div class="five wide column blog-recommend" style="left: 34px !important;">

            <!--分类-->
            <div class="ui segments my-shadow">
              <div class="ui secondary segment">
                <div class="ui two column grid">
                  <div class="column">
                    <i class="idea icon"></i>分类
                  </div>
                  <div class="right aligned column">
                    <router-link to="/types">
                    more <i class="angle double right icon"></i>
                    </router-link>
                  </div>
                </div>
              </div>
              <div class="ui my-blue segment">
                <div class="ui fluid vertical menu">
                  <template v-for="(item, index) in categoryList">
                    <a class="item" v-if='index<6' :key="item.id" @click="toType(item.id)">
                      {{ item.categoryname }}
                      <div class="ui blue basic left pointing label">{{item.categoryCount}}</div>
                    </a>
                  </template>
                </div>
              </div>
            </div>
              <!--标签-->
              <div class="ui segments m-margin-top-large my-shadow">
                <div class="ui secondary segment">
                  <div class="ui two column grid">
                    <div class="column">
                      <i class="tags icon"></i>标签
                    </div>
                    <!-- <div class="right aligned column">
                      <a href="/tags" target="_blank">more <i class="angle double right icon"></i></a>
                    </div> -->
                    <div class="right aligned column">
                      <router-link to="/tags">
                      more <i class="angle double right icon"></i>
                      </router-link>
                    </div>
                  </div>
                </div>
                <div class="ui my-blue segment">
                  <template v-for="(item, index) in labelList">
                    <a target="_blank" class="ui my-blue basic left pointing label m-margin-tb-tiny" v-if='index<15' :key="item.id" @click="toTag(item.id)">
                      {{ item.labelname }}
                      <div class="detail">{{item.labelCount}}</div>
                    </a>
                  </template>
                </div>
              </div>

              <!--最新推荐-->
              <div class="ui segments m-margin-top-large my-shadow">
                <div class="ui secondary segment ">
                  <i class="bookmark icon"></i>最新推荐
                </div>
                <div class="ui segment" v-for="item in latestList" :key="item.id">
                  <a  target="_blank" class="m-black m-text-thin" style="cursor:pointer;" v-text="item.title" @click="toBlog(item.id)"></a>
                </div>
              </div>

          </div>

        </div>
      </div>
    </div>
    <br>
    <br>
    <Footer></Footer>
<!--    &lt;!&ndash; 主体组件 &ndash;&gt;-->
<!--    <index style="width: 100%"></index>-->
  </div>

</template>

<script>
// import { Notification } from 'element-ui'
// import Footer from '../components/layout/Footer'
export default {
  // 注册组件
  components: {
    // Footer
  },
  data () {
    return {
      dataList: [], // 当前页要展示的博客分页列表数据
      categoryList: [], // 分类列表的数据
      labelList: [], // 标签列表的数据
      latestList: [], // 最新发布的博客列表的数据
      pagination: { // 分页相关模型数据
        currentPage: 1, // 当前页码
        pageSize: 6, // 每页显示的记录数
        total: 0, // 总记录数
        queryString: null, // 查询条件
        sort: "new"
      },
      tip: false,
      obj: {
        isEnd: false,
        speed: 300,
        singleBack: false,
        sleep: 0,
        type: 'rollback',
        backSpeed: 40,
        sentencePause: true
      },
      articleList: [],
      blogInfo: {},
      current: 1
    }
  },
  created () {
    this.findPage()
    this.getcategoryList()
    this.getlabelList()
    this.getLatestList()
  },
  methods: {
    scrollDown () {
      window.scrollTo({
        behavior: 'smooth',
        top: document.documentElement.clientHeight
      })
    },
    search () {
      this.findPage()
      this.pagination.queryString = null
    },
    toTag (tagId) {
      this.$router.push({ path: "/tags/" + tagId });
    },
    toType (categoryId) {
      this.$router.push({ path: "/types/" + categoryId });
    },
    toBlog (blogId) {
      this.$store.state.searchFlag = false;
      this.$store.state.blogId = blogId;
      this.$router.push({ path: "/blog/" + blogId });
    },
    toUser (userId) {
      this.$router.push({ path: "/userinfo/" + userId });
    },
    async getLatestList () {
      const { data: res } = await this.$http.get('/api/home/latestList')
      this.latestList = res.data
    },
    async getcategoryList () {
      const { data: res } = await this.$http.get('/api/home/getCategoryCount')
      this.categoryList = res.data
    },
    async getlabelList () {
      const { data: res } = await this.$http.get('/api/home/getLabelCount')
      this.labelList = res.data
    },
    // 分页查询
    async findPage () {
      const str = this.$store.state.queryString
      if (str !== null) {
        this.pagination.queryString = str
        this.$store.state.queryString = null
        this.$message.info('搜索结果已经显示在页面下方')
      }
      // 发送ajax，提交分页请求
      const param = {
        currentPage: this.pagination.currentPage,
        pageSize: this.pagination.pageSize,
        queryString: this.pagination.queryString,
        sort: this.pagination.sort,
        categoryId: null,
        labelId: null
      }
      const { data: res } = await this.$http.post('/api/home/articles', param)
      // 解析controller响应回的数据
      if (!res.flag) {
        return this.$message.error('获取文章列表失败！')
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
    },
    handleSortChange (newSort) {
      this.pagination.sort = newSort
      this.findPage()
    }
  },
  mounted () {
    $('.menu.toggle').click(function () {
      $('.m-item').toggleClass('m-mobile-hide')
    })
    $('.ui.dropdown').dropdown({
      on: 'hover'
    })
    $('#toTop-button').click(function () {
      $(window).scrollTo(0, 500)
    })
  }
}
</script>
<style scoped>
  .m-home {
    padding-top: 25vh !important;
    padding-bottom: 0px !important;
  }
  .home-banner {
    position: absolute;
    top: 0px;
    left: 0;
    right: 0;
    height: 25vh;
    background: url("../assets/images/home.jpg") center center /
    cover no-repeat;
    background-color: #49b1f5;
    background-attachment: fixed;
    text-align: center;
    /*color: #fff !important;*/
    animation: header-effect 1s !important;
  }
  .banner-container {
    margin-top: 43vh;
    line-height: 1.5;
    color: #eee;
  }
  .blog-contact a {
    color: #fff !important;
  }
  .card-info-social {
    line-height: 40px;
    text-align: center;
    font-size: 1.5rem;
    margin: 6px 0 -6px;
  }
  .left-radius {
    border-radius: 8px 0 0 8px !important;
    order: 0;
  }
  .right-radius {
    border-radius: 0 8px 8px 0 !important;
    order: 1;
  }
  .article-wrapper {
    font-size: 14px;
  }
  @media (min-width: 760px) {
    .blog-title {
      font-size: 2.5rem;
    }
    .blog-intro {
      font-size: 1.5rem;
    }
    .blog-contact {
      line-height: 40px;
      text-align: center;
      font-size: 1.5rem;
      margin: 6px 0 -6px;
    }
    .home-container {
      max-width: 1200px;
      margin: calc(100vh - 50px) auto 0 auto;
      padding: 0 3px;
    }
    .article-card {
      display: flex;
      align-items: center;
      height: 280px;
      width: 100%;
      margin-top: 20px;
    }
    .article-cover {
      overflow: hidden;
      height: 100%;
      width: 45%;
    }
    .on-hover {
      transition: all 0.6s;
    }
    .article-card:hover .on-hover {
      transform: scale(1.1);
    }
    .article-wrapper {
      padding: 0 2.5rem;
      width: 55%;
    }
    .article-wrapper a {
      font-size: 1.5rem;
      transition: all 0.3s;
    }
  }
  @media (max-width: 759px) {
    .blog-title {
      font-size: 24px;
    }
    .blog-contact {
      font-size: 1.25rem;
      line-height: 2;
    }
    .home-container {
      width: 100%;
      margin: calc(100vh - 66px) auto 0 auto;
    }
    .blog-recommend {
      display: none !important;
    }
    .article-card {
      margin-top: 1rem;
    }
    .article-cover {
      border-radius: 8px 8px 0 0 !important;
      height: 230px !important;
      width: 100%;
    }
    .article-cover div {
      border-radius: 8px 8px 0 0 !important;
    }
    .article-wrapper {
      padding: 1.25rem 1.25rem 1.875rem;
    }
    .article-wrapper a {
      font-size: 1.25rem;
      transition: all 0.3s;
    }
  }
  .scroll-down {
    cursor: pointer;
    position: absolute;
    bottom: 0;
    width: 100%;
  }
  .scroll-down i {
    font-size: 2rem;
  }
  .article-wrapper a:hover {
    color: #8e8cd8;
  }
  .article-info {
    font-size: 95%;
    color: #858585;
    line-height: 2;
    margin: 0.375rem 0;
  }
  .article-info a {
    font-size: 95%;
    color: #858585 !important;
  }
  .article-content {
    line-height: 2;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
  }
  .blog-wrapper {
    position: sticky;
    top: 10px;
  }
  .blog-card {
    line-height: 2;
    padding: 1.25rem 1.5rem;
  }
  .author-wrapper {
    text-align: center;
  }
  .blog-info-wrapper {
    display: flex;
    justify-self: center;
    padding: 0.875rem 0;
  }
  .blog-info-data {
    flex: 1;
    text-align: center;
  }
  .blog-info-data a {
    text-decoration: none;
  }
  .collection-btn {
    text-align: center;
    z-index: 1;
    font-size: 14px;
    position: relative;
    display: block;
    background-color: #49b1f5;
    color: #fff !important;
    height: 32px;
    line-height: 32px;
    transition-duration: 1s;
    transition-property: color;
  }
  .collection-btn:before {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: -1;
    background: #ff7242;
    content: "";
    transition-timing-function: ease-out;
    transition-duration: 0.5s;
    transition-property: transform;
    transform: scaleX(0);
    transform-origin: 0 50%;
  }
  .collection-btn:hover:before {
    transition-timing-function: cubic-bezier(0.45, 1.64, 0.47, 0.66);
    transform: scaleX(1);
  }
  .author-avatar {
    transition: all 0.5s;
  }
  .author-avatar:hover {
    transform: rotate(360deg);
  }
  .web-info {
    padding: 0.25rem;
    font-size: 0.875rem;
  }
  .scroll-down-effects {
    color: #eee !important;
    text-align: center;
    text-shadow: 0.1rem 0.1rem 0.2rem rgba(0, 0, 0, 0.15);
    line-height: 1.5;
    display: inline-block;
    text-rendering: auto;
    -webkit-font-smoothing: antialiased;
    animation: scroll-down-effect 1.5s infinite;
  }
  @keyframes scroll-down-effect {
    0% {
      top: 0;
      opacity: 0.4;
      filter: alpha(opacity=40);
    }
    50% {
      top: -16px;
      opacity: 1;
      filter: none;
    }
    100% {
      top: 0;
      opacity: 0.4;
      filter: alpha(opacity=40);
    }
  }
  .big i {
    color: #f00;
    animation: big 0.8s linear infinite;
  }
  @keyframes big {
    0%,
    100% {
      transform: scale(1);
    }
    50% {
      transform: scale(1.2);
    }
  }
</style>
