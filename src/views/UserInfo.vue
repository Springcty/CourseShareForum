<template>
  <div class="home">
    <div class="blog-banner banner">
      <h1 class="banner-title"></h1>
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
                <a target="_blank" class="ui my-blue basic label m-padded-tiny m-text-thin">{{item.categoryname}}</a>
              </div>
            </div>
          </div>
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

    <!-- <div class="ui toc-container flowing popup transition hidden" style="width: 250px!important;">
      <ol style="overflow: auto" class="js-toc">
      </ol>
    </div> -->
  </div>
</template>

<script>
export default {
  data () {
    return {
      formData: {
        blogId: '',
        content: '请输入评论内容'
      },
      uid: '',
      username: '',
      avatar: '',
      dataList: [],
      dataList2: [],
      likeFlag: false,
      starFlag: false
    }
  },
  created () {
    this.uid = this.$store.state.id
    this.formData.blogId = this.$route.path.split('/blog/')[1]
  },
  destroyed () {
  },
  methods: {
    toLogin () {
      const tokenStr = this.$store.state.token
      // 后端指定接口验证了token的正确性
      if (!tokenStr) {
        this.$confirm('登录后才能发表评论或者点赞，请问是否先登录？', '提示', { // 确认框
          type: 'info'
        }).then(() => {
          this.$router.push('/login')
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '你选择不登录'
          })
          return false
        })
      }
      return !!tokenStr
    },
  },
  mounted () {
    $('.ui.dropdown').dropdown({
      on: 'hover'
    })
    $('.menu.toggle').click(function () {
      $('.m-item').toggleClass('m-mobile-hide')
    })
    $('.toc.button').popup({
      popup: $('.toc-container.popup'),
      on: 'click',
      position: 'left center'
    })
    $('#toTop-button').click(function () {
      $(window).scrollTo(0, 500)
    })
    $('#toBottom-button').click(function () {
      $(window).scrollTo(999999999, 1000)
    })
  },
  watch: {
    $route (to, from) {
      if ((this.path !== to.path) && (to.path.indexOf('#') === -1)) {
        this.formData.uid = to.path.split('/userinfo/')[1]
      }
    }
  }
}
</script>

<style scoped>
  @import "../assets/css/typo.css";
  @import "../assets/css/animate.css";
  .container{
    animation: main 1s;
  }
  .m-blog {
    padding-top: 25vh !important;
    padding-bottom: 0px !important;
  }
  .blog-banner {
    height: 25vh;
    /* background: url(https://r.photo.store.qq.com/psc?/V53KcXfb1umonn4HbITu3rINxs43TczD/45NBuzDIW489QBoVep5mcSPhM*LqG*bYzP4o2JVbahf8eHd4Yd9FO1B4n8UbWJRaQANKT1EBypIfvSNRueI1LSu6d212FUoavpm63ZrUI3E!/r) center
    center / cover no-repeat; */
    background-color: #49b1f5;
  }
</style>
