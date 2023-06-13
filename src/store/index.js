import Vue from "vue";
import Vuex from "vuex";
Vue.use(Vuex);
Vue.config.devtools = true
export default new Vuex.Store({
  state: {
    count: 0,
    token: '', // 用户token
    id: null,
    avatar: null,
    username: null,
    nickname: null,
    loginType: null,
    email: null,
    forgetFlag: false,
    // blogInfo: {},
    blogId: '',
    searchFlag: false, // 搜索模态框
    payFlag: false, // 微信支付模态框
    messageFlag: null,
    queryString: null,
    loginUrl: "",
    music: false,
    songId: null,
    parentCommentId: null,
    categoryId: null,
    tagId: null,
    activePath: null,
    userMenuList: [],
    asyncRoutes: [],
    adminBlogId: null,
    activeIndex: null,
    drawer: false, // 首页侧边栏
    faceRegisterFormVisible: false, // 人脸注册表格
    faceLoginFormVisible: false, // 人脸登录表格
    facePhotoFlag: false, // 打开摄像头
    faceRegisterFlag: false, // 拍照页面是注册
    isCollapse: false, // 个人后台侧边栏展开状态
    blogInfo: {
      websiteConfig: {
        websiteAvatar: 'aaa',
        websiteAuthor: ''
      },
      articleCount: 100,
      categoryCount: 10,
      tagCount: 2,
    }
  },
  mutations: {
    setBlogInfo (state, blogInfo) {
      state.blogInfo = blogInfo;
    },
    saveLoginUrl (state, url) {
      state.loginUrl = url;
    },
    saveUserMenuList (state, userMenuList) {
      state.userMenuList = userMenuList;
    },
    saveAsyncRoutes (state, asyncRoutes) {
      state.asyncRoutes = asyncRoutes;
    },
    login (state, user) {
      state.id = user.id;
      state.avatar = user.avatar;
      state.username = user.username;
      state.nickname = null; // 暂不支持nickname
      state.email = null; // 暂不支持email
      state.loginType = 1; // 固定loginType伪1
    },
    logout (state) {
      state.id = null
      state.avatar = null
      state.username = null
      state.nickname = null
      state.email = null
      state.loginType = null
      state.token = ''
    },
  },
  actions: {},
  modules: {}
});
