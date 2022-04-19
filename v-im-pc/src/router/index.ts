import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/index",
    name: "Home",
    component: () => import("../views/Home.vue"),
    children: [
      {
        path: "chat",
        name: "chatBox",
        component: () => import("../views/layout/ChatBox.vue"),
      },
      {
        path: "friend",
        name: "friendBox",
        component: () => import("../views/layout/FriendBox.vue"),
        children: [
          {
            path: ":id",
            name: "user",
            component: () => import("../views/friend/UserInfo.vue"),
          },
        ],
      },
      {
        path: "dept",
        name: "deptBox",
        component: () => import("../views/layout/DeptBox.vue"),
        children: [
          {
            path: "new",
            name: "new",
            component: () => import("../views/dept/New.vue"),
          },
          {
            path: ":id",
            name: "dept",
            component: () => import("../views/dept/DeptInfo.vue"),
          },
        ],
      },
      {
        path: "group",
        name: "groupBox",
        component: () => import("../views/layout/GroupBox.vue"),
        children: [
          {
            path: "new",
            name: "new",
            component: () => import("../views/group/New.vue"),
          },
          {
            path: ":id",
            name: "group",
            component: () => import("../views/group/Info.vue"),
          },
        ],
      },
      {
        path: "system",
        name: "systemBox",
        component: () => import("../views/layout/SystemBox.vue"),
        children: [
          {
            path: "user",
            name: "sys-user",
            component: () => import("../views/sys/user/index.vue"),
          },
        ],
      },
    ],
  },
  {
    path: "/",
    name: "login",
    component: () => import("../views/Login.vue"),
  },
];
// 这里必须是createWebHashHistory模式，也就是 hash 模式，否则打包完成是白板。
const router = createRouter({
  history: createWebHashHistory(process.env.BASE_URL),
  routes,
});

export default router;
