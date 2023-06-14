<template>
  <div class="home">
    <div class="blog-banner banner">
      <h1 class="banner-title"></h1>
    </div>
    <!--中间内容-->
    <div id="waypoint" class="m-container-small m-blog animated fadeInLeft">
      <div class="ui container">
        <div class="ui top attached segment">
          <div class="ui horizontal link list">
            <div class="item">
              <img src="../assets/images/avatars/2.jpg" class="ui avatar image">
              <div class="content" @click="toUser(dataList.authorid)"><a class="header">{{dataList.username}}</a></div>
            </div>
            <div class="item">
              <div button class="ui blue button" @click="follow">
                <i v-if="followFlag" >取消关注</i>
                <i v-else type="primary">关注</i>
              </div>
            </div>
            <div class="item">
              <i class="calendar icon"></i> {{dataList.time}}
            </div>
            <div class="item">
              <i class="eye icon"></i> {{dataList.views}}
            </div>
            <div class="item">
              <i class="thumbs up outline icon"></i> {{dataList.likes}}
            </div>
          </div>
        </div>
        <div class="ui  attached padded segment">
          <h2 class="ui center aligned header" v-text="dataList.title"></h2>
          <br>
          <div id="content" class="typo  typo-selection js-toc-content m-padded-lr-responsive m-padded-tb-large" v-html="dataList.content" style="width: 800px">
          </div>

          <center><button class="ui blue icon button" @click="like">
            <i v-if="likeFlag" class="thumbs up icon"></i>
            <i v-else class="thumbs up outline icon"></i>
          </button>
          <button class="ui yellow icon button" @click="star">
            <i v-if="starFlag" class="star icon"></i>
            <i v-else class="star outline icon"></i>
          </button></center>
        </div>

        <div id="comment-container" class="ui bottom attached segment">
          <!--留言区域列表-->
          <div class="ui blue segment">
            <div class="ui threaded comments" style="max-width: 100%">
              <h3 class="ui dividing header">评论列表</h3>
              <div class="comment" v-for="item in dataList2" :key="item.id">
                <a class="avatar">
                  <img v-bind:src=item.avatar>
                </a>
                <div class="content">
                  <a class="author" @click="toUser(item.userid)">{{item.username}}</a>
                  <div class="metadata">
                    <span class="date">{{item.time}}</span>
                  </div>
                  <div class="text" v-html="item.content">
                  </div>
                  <div class="actions" >
                    <a class="reply" @click="deleteComment(item)" v-show="item.userid==uid">删除</a>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="ui form">
            <el-form ref="addForm" :model="formData">
            <div class="field">
              <textarea name="content" v-model="formData.content"></textarea>
            </div>
            </el-form>
            <div class="fields">
              <div class="field  m-margin-bottom-small m-mobile-wide">
                <button class="ui blue button m-mobile-wide" @click="addComment"><i class="edit icon"></i>发布</button>
              </div>
            </div>

          </div>
        </div>
      </div>
    </div>

    <div class="ui toc-container flowing popup transition hidden" style="width: 250px!important;">
      <ol style="overflow: auto" class="js-toc">
      </ol>
    </div>

    <br>
    <br>
  </div>

</template>
<script>
import Prism from '../assets/lib/prism/prism'
import tocbot from "tocbot";
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
      likeFlag: '',
      starFlag: '',
      followFlag: ''
    }
  },
  created () {
    this.uid = this.$store.state.id
    this.formData.blogId = this.$route.path.split('/blog/')[1]
    this.getOneBlog()
    this.getCommentList()
  },
  destroyed () {
    tocbot.destroy();
  },
  methods: {
    toUser (userId) {
      this.$router.push({ path: "/userinfo/" + userId });
    },
    async star () {
      if (this.toLogin()) {
        const blogId = this.$store.state.blogId
        if (this.starFlag === false) {
          const { data: res } = await this.$http.get(`/api/stars/${blogId}`)
          if (res.flag) {
            this.$message.success("收藏成功")
            this.starFlag = true
          }
        } else {
          const { data: res } = await this.$http.post(`/api/stars/${blogId}`)
          if (res.flag) {
            this.$message.success("取消收藏成功")
            this.starFlag = false
          }
        }
      }
    },
    async like () {
      if (this.toLogin()) {
        const blogId = this.$store.state.blogId
        if (this.likeFlag === false) {
          const { data: res } = await this.$http.get(`/api/likes/${blogId}`)
          if (res.flag) {
            this.$message.success("点赞成功")
            this.likeFlag = true
          }
        } else {
          const { data: res } = await this.$http.delete(`/api/likes/${blogId}`)
          if (res.flag) {
            this.$message.success("取消点赞成功")
            this.likeFlag = false
          }
        }
        this.getOneBlog()
      }
    },
    async follow () {
      if (this.toLogin()) {
        const authorId = this.dataList.authorid
        if (this.followFlag === false) {
          const { data: res } = await this.$http.get(`/api/follow/${authorId}`)
          if (res.flag) {
            this.$message.success("关注成功")
            this.followFlag = true
          }
        } else {
          const { data: res } = await this.$http.delete(`/api/follow/${authorId}`)
          if (res.flag) {
            this.$message.success("取消关注成功")
            this.followFlag = false
          }
        }
      }
    },
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
    async deleteComment (item) {
      this.$confirm('是否删除该评论？', '提示', { // 确认框
        type: 'warning'
      }).then(() => {
        const commentId = item.id
        const blogId = this.$store.state.blogId
        // 表单校验通过，发ajax请求，把数据录入至后台处理
        this.$http.delete(`/api/comment/delete/${blogId}/${commentId}`).then((res) => {
          this.getCommentList()
          this.formData.content = '请输入评论内容'
          this.$message({
            message: '删除评论成功',
            type: 'success'
          })
          // if (res.flag) {
          //   this.getCommentList()
          //   this.formData.content = '请输入评论内容'
          //   this.$message({
          //     message: '删除评论成功',
          //     type: 'success'
          //   })
          // } else { // 执行失败
          //   this.$message.error(res.message)
          // }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '删除操作已取消'
        })
      })
    },
    getCurrentTime () {
      const year = new Date().getFullYear()
      const month = new Date().getMonth() + 1 < 10 ? '0' + (new Date().getMonth() + 1) : new Date().getMonth() + 1
      const date = new Date().getDate()
      const hour = new Date().getHours() < 10 ? '0' + new Date().getHours() : new Date().getHours()
      const minute = new Date().getMinutes() < 10 ? '0' + new Date().getMinutes() : new Date().getMinutes()
      const second = new Date().getSeconds() < 10 ? '0' + new Date().getSeconds() : new Date().getSeconds()
      return year + '-' + month + '-' + date + ' ' + hour + ':' + minute + ':' + second
    },
    async addComment () {
      if (this.toLogin()) {
        this.formData.blogId = this.$store.state.blogId
        // 表单校验通过，发ajax请求，把数据录入至后台处理
        const param = {
          id: null,
          articleid: this.$store.state.blogId,
          userid: this.uid,
          time: this.getCurrentTime(),
          content: this.formData.content
        }
        this.$http.post('/api/comment/reply', param).then((res) => {
          this.getCommentList()
          this.formData.content = '请输入评论内容'
          this.$message({
            message: '回复评论成功',
            type: 'success'
          })
          setTimeout(() => {
            this.reloadPrism()
          }, 1000)
        })
      }
    },
    async getCommentList () {
      const { data: res } = await this.$http.get(`/api/comment/${this.formData.blogId}`)
      if (!res.flag) {
        return this.$message.error('获取评论列表信息失败！')
      }
      this.dataList2 = res.data
    },
    reloadPrism () {
      process.browser && document.querySelectorAll('pre code').forEach(block => Prism.highlightElement(block))
    },
    // 获取所有的菜单
    async getOneBlog () {
      // const { data: res } = await this.$http.get(`/api/articles/${this.formData.blogId}`)
      // const param = { user: this.$store.state.token ? this.$store.state.uid : null }
      const param = {
        id: this.$store.state.id,
        username: null,
        password: null,
        avatar: null,
        priviledged: false
      }
      const { data: res } = await this.$http.post(`/api/articles/${this.formData.blogId}`, param)
      if (!res.flag) {
        return this.$message.error(res.message)
      }
      this.dataList = res.data
      this.likeFlag = res.data.liked
      this.starFlag = res.data.stared
      this.followFlag = res.data.followed
      setTimeout(() => {
        // eslint-disable-next-line no-undef
        tocbot.init({
          // Where to render the table of contents.
          tocSelector: '.js-toc',
          // Where to grab the headings to build the table of contents.
          contentSelector: '.js-toc-content',
          // Which headings to grab inside of the contentSelector element.
          headingSelector: 'h1, h2, h3'
        })
      }, 1000)
    }
  },
  mounted () {
    // 有效
    setTimeout(() => {
      this.reloadPrism()
    }, 1000)
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
        this.formData.blogId = to.path.split('/blog/')[1]
        this.getCommentList()
        this.getOneBlog()
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
    background: url("../assets/images/blog.jpg") center
    center / cover no-repeat;
    background-color: #49b1f5;
  }
</style>
