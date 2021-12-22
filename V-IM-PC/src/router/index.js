import Vue from "vue";
import Router from "vue-router";
import UserBox from "../views/im/layout/userBox.vue";
import ChatBox from "../views/im/layout/chatBox.vue";
import ChatGroupBox from "../views/im/layout/chatGroupBox.vue";
import DeptBox from "../views/im/layout/deptBox.vue";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/index",
      name: "landing-page",
      component: require("@/views/index.vue").default,
      children: [
        {
          path: "chatBox",
          name: "chatBox",
          component: ChatBox
        },
        {
          path: "/",
          name: "userIndex",
          component: UserBox
        },
        {
          path: "userBox",
          name: "userBox",
          component: UserBox
        },
        {
          path: "dept",
          name: "dept",
          component: DeptBox
        },
        {
          path: "chatGroupBox",
          name: "chatGroupBox",
          component: ChatGroupBox
        }
      ]
    },
    {
      path: "/",
      name: "login-page",
      component: require("@/views/login.vue").default
    }
  ]
});

//解决重复路由报错问题
const routerPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(error=> error)
}