<template>
  <div class="home">
    <div class="userinfo-banner banner">
      <h1 class="banner-title"></h1>
    </div>

    <div class="m-container-small m-userinfo">
      <div class="ui container">

        <div class="ui top attached segment my-shadow">
          <div class="ui horizontal link list">
            <div class="item">
              <img v-bind:src="this.formData.avatar" class="ui avatar image">
              <div class="content"><a class="header">{{this.formData.username}}</a></div>
            </div>
            <div class="item">
              <div button class="ui blue button" @click="follow">
                <i v-if="followFlag" >取消关注</i>
                <i v-else type="primary">关注</i>
              </div>
            </div>
          </div>
          <br>
          <br>
          <div class="ui horizontal link list">
            <div class="item">
              {{this.formData.follows}} 关注
            </div>
            <div class="item">
              {{this.formData.followers}} 粉丝
            </div>
          </div>
        </div>

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
            </div>
          </div>
        </div>

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
      formData: {
        userId: '', // 主页用户的id
        username: '',
        avatar: '',
        follows: '',
        followers: '',
      },
      pagination: { // 分页相关模型数据
        currentPage: 1,
        pageSize: 6,
        total: 0,
        queryString: null,
        sort: "new"
      },
      uid: '', // 登录用户的id
      username: '',
      dataList: [],
      followFlag: '',
    }
  },
  created () {
    this.uid = this.$store.state.id
    this.formData.userId = this.$route.path.split('/userinfo/')[1]
    this.getUser()
    this.getUserArticles()
  },
  methods: {
    toUser (userId) {
      this.$router.push({ path: "/userinfo/" + userId });
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
    async follow () {
      if (this.toLogin()) {
        const followUserId = this.formData.userId
        if (this.followFlag === false) {
          const { data: res } = await this.$http.get(`/api/follow/${followUserId}`)
          if (res.flag) {
            this.$message.success("关注成功")
            this.followFlag = true
          }
        } else {
          const { data: res } = await this.$http.delete(`/api/follow/${followUserId}`)
          if (res.flag) {
            this.$message.success("取消关注成功")
            this.followFlag = false
          }
        }
        this.getUser()
      }
    },
    async getUserArticles () {
      const param = {
        currentPage: this.pagination.currentPage,
        pageSize: this.pagination.pageSize,
        queryString: this.pagination.queryString,
        sort: this.pagination.sort,
        categoryId: null,
        labelId: null
      }
      const { data: res } = await this.$http.post(`/api/users/articles/${this.formData.userId}`, param)
      if (!res.flag) {
        return this.$message.error('获取文章列表失败！')
      }
      this.pagination.total = res.data.total
      this.dataList = res.data.records
    },
    handleCurrentChange (currentPage) {
      this.pagination.currentPage = currentPage
      this.getUserArticles()
    },
    handleSizeChange (newSize) {
      this.pagination.pageSize = newSize
      this.getUserArticles()
    },
    async getUser () {
      const param = {
        id: this.uid,
        username: null,
        password: null,
        avatar: null,
        priviledged: false
      }
      const { data: res } = await this.$http.post(`/api/users/userInfo/${this.formData.userId}`, param)
      if (!res.flag) {
        return this.$message.error(res.message)
      }
      this.formData.username = res.data.username
      this.formData.avatar = res.data.avatar
      this.formData.follows = res.data.follows
      this.formData.followers = res.data.fans
      this.followFlag = res.data.followed
    },
  },
  mounted () {
    $('.ui.dropdown').dropdown({
      on: 'hover'
    })
    $('.menu.toggle').click(function () {
      $('.m-item').toggleClass('m-mobile-hide')
    })
  },
  watch: {
    $route (to, from) {
      if ((this.path !== to.path) && (to.path.indexOf('#') === -1)) {
        this.formData.userId = to.path.split('/userinfo/')[1]
        this.getUser()
        this.getUserArticles()
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
  .m-userinfo {
    padding-top: 25vh !important;
    padding-bottom: 0px !important;
  }
  .userinfo-banner {
    height: 25vh;
    /* background: url(https://r.photo.store.qq.com/psc?/V53KcXfb1umonn4HbITu3rINxs43TczD/45NBuzDIW489QBoVep5mcSPhM*LqG*bYzP4o2JVbahf8eHd4Yd9FO1B4n8UbWJRaQANKT1EBypIfvSNRueI1LSu6d212FUoavpm63ZrUI3E!/r) center
    center / cover no-repeat; */
    background-color: #49b1f5;
  }
</style>
