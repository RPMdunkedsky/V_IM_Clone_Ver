import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ElementPlus from "element-plus";
import "@/assets/styles/g.css";
import "element-plus/dist/index.css";
import "@/assets/font/iconfont.css";
import WsRequest from "@/api/WsRequest";
const ws = new WsRequest();
const app = createApp(App);
app.config.globalProperties.$ws = ws;
if (process.env.VUE_APP_MODE === "web") {
  app.config.globalProperties.$winControl = require("./hooks/webControl");
} else {
  app.config.globalProperties.$winControl = require("./hooks/windowControl");
}
app.use(store).use(router).use(ElementPlus).mount("#app");
