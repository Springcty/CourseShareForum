/* eslint-disable prettier/prettier */
import Layout from "@/layout/index.vue";
import router from "../../router";
import store from "../../store";
import axios from "axios";
import Vue from "vue";

export function generaMenu() {
  // 查询用户菜单
  axios.get("http://101.37.254.247:9001/menu/admin/listAdminMenus").then(({ data }) => {
    if (data.flag) {
      var userMenuList = data.data;
      userMenuList.forEach(item => {
        if (item.icon != null) {
          item.icon = "iconfont " + item.icon;
        }
        if (item.component == "Layout") {
          item.component = Layout;
        }
        if (item.children && item.children.length > 0) {
          item.children.forEach(route => {
            route.icon = "iconfont " + route.icon;
            route.component = loadView(route.component);
          });
        }
      });
      // 添加侧边栏菜单
      store.commit("saveUserMenuList", userMenuList);
      // 添加菜单到路由
      router.addRoutes(userMenuList);
    } else {
      Vue.prototype.$message.error("请先登录");
      router.push({ path: "/login" });
    }
  });
}

export const loadView = view => {
  // 路由懒加载
  return resolve => require([`@/views${view}`], resolve);
};
