<template>
  <div id="app">
    <!-- 导航栏 -->
    <TopNavBar v-show="( path==='/home' || path.includes('/types') || path==='/archives' || path==='/link' || path.includes('/blog/') || path.includes('/tags') || path==='/message' || path==='/login' || path.includes('/userinfo') )"></TopNavBar>
    <!-- 侧边导航栏 -->
    <SideNavBar></SideNavBar>
    <!--    <AdminTopNavBar v-show="( path==='/blogs' || path==='/tags2' || path==='/types2' || path==='/postblogs')"></AdminTopNavBar>-->
    <AdminTopNavBar v-show="false"></AdminTopNavBar>
<!--        <Footer v-show="( path==='/home' || path==='/types' || path==='/archives' || path==='/link' || path==='/blog' || path==='/tags' || path==='/about')"></Footer>-->
    <router-view/>
  </div>
</template>
<script>
import Vue from 'vue'
import TopNavBar from './components/layout/TopNavBar'
import AdminTopNavBar from './components/layout/AdminTopNavBar'
import SideNavBar from './components/layout/SideNavBar'
import router from './router'
import { generaMenu } from './assets/js/menu'

export default {
  data () {
    return {
      path: '',
      myApp: new Vue() // 为了非父子组件共享值
    }
  },
  components: {
    TopNavBar,
    SideNavBar,
    AdminTopNavBar
  },
  // 判断路由
  mounted () {
    this.path = this.$route.path
  },
  watch: {
    $route (to, from) {
      this.path = to.path
      router.addRoutes(this.$store.state.asyncRoutes);
    }
  },
  created () {
    this.getBlogInfo()
    // 在页面加载时读取sessionStorage里的状态信息
    if (sessionStorage.getItem("store")) {
      this.$store.replaceState(Object.assign({}, this.$store.state, JSON.parse(sessionStorage.getItem("store"))))
    }
    if (this.$store.state.token !== '') {
      generaMenu()
    }
    // 在页面刷新时将vuex里的信息保存到sessionStorage里
    window.addEventListener("beforeunload", () => {
      sessionStorage.setItem("store", JSON.stringify(this.$store.state))
    })
  },
  methods: {
    getBlogInfo () {
      this.$http.get("/api/server/blog/blogInfo").then(({ data }) => {
        this.$store.commit("setBlogInfo", data.data);
      });
    }
  }
}
</script>
<style>
</style>
