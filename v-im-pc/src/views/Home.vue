<template>
  <div class="v-im" v-if="currentUser">
    <div class="left-bar" style="-webkit-app-region: drag">
      <ul>
        <li class="userPhoto">
          <Avatar
            :img="currentUser.avatar"
            @click="userConf.show = true"
          ></Avatar>
        </li>
        <li title="会话">
          <router-link v-bind:to="'/index/chat'">
            <el-badge :value="unreadCount" :hidden="unreadCount === 0">
              <i class="iconfont icon-v-liaotian"></i>
            </el-badge>
          </router-link>
        </li>
        <li title="好友">
          <router-link v-bind:to="'/index/friend'">
            <i class="iconfont icon-v-haoyou"></i>
          </router-link>
        </li>
        <li title="组织">
          <router-link v-bind:to="'/index/dept'">
            <i class="iconfont icon-v-bumen"></i>
          </router-link>
        </li>
        <li title="群">
          <router-link v-bind:to="'/index/group'">
            <i class="iconfont icon-v-qunzhong"></i>
          </router-link>
        </li>
        <li title="设置">
          <router-link v-bind:to="'/index/system'">
            <i class="iconfont icon-v-shezhi"></i>
          </router-link>
        </li>
        <li title="退出" class="logout" @click="vimLogout">
          <i class="iconfont icon-v-cuowutishi"></i>
        </li>
      </ul>
    </div>
    <router-view v-slot="{ Component }" class="content">
      <keep-alive>
        <component :is="Component" />
      </keep-alive>
    </router-view>
  </div>
  <user-modal
    :show="userConf.show"
    :showSend="true"
    :userId="userConf.userId"
    @close="userConf.show = false"
  ></user-modal>
</template>

<script setup lang="ts">
import UserModal from "@/components/UserModal.vue";
import Avatar from "@/components/Avatar.vue";
import { useUserStore } from "@/store/userStore";
import { useChatStore } from "@/store/chatStore";
import AccountApi from "@/api/AccountApi";
import { logout } from "@/api/Login";
import { ElMessageBox } from "element-plus";
import {
  computed,
  getCurrentInstance,
  nextTick,
  onMounted,
  reactive,
} from "vue";
import Auth from "@/api/Auth";
import Message from "@/mode/Message";
import ChatType from "@/config/ChatType";
import ChatUtils from "@/utils/ChatUtils";

const { proxy } = getCurrentInstance();
const userStore = useUserStore();
const chatStore = useChatStore();
const currentUser = userStore.getUser();
if (!currentUser) {
  AccountApi.toLogin();
}
const userConf = reactive({
  show: false,
  userId: "",
});
const unreadCount = computed(() => {
  let i = 0;
  chatStore.chats.forEach((item) => {
    i = i + item.unreadCount;
  });
  return i;
});

const vimLogout = () => {
  ElMessageBox.confirm("确定注销并退出系统吗？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    logout().then(() => {
      proxy.$ws.close();
      location.href = "/#/";
    });
  });
};
onMounted(() => {
  if (currentUser) {
    //初始化websocket
    proxy.$ws.init("ws://127.0.0.1:9326?token=" + Auth.getToken());
    //重写 onmessage 方法，收到的消息都在这里进行分发
    proxy.$ws.onmessage = (message: Message) => {
      //群聊里面，自己发的消息不再显示
      if (currentUser.id === message.fromId) {
        message.mine = true;
      }
      //友聊换chatId,chatId 不一样
      if (
        ChatType.FRIEND === message.type &&
        currentUser.id !== message.fromId
      ) {
        message.chatId = message.fromId;
      }
      chatStore.pushMessage(message);

      proxy.$winControl.default.flashIcon();
      nextTick(() => {
        ChatUtils.imageLoad("message-box");
      });
    };
    userConf.userId = currentUser.id;
  }
});
</script>
<style lang="scss" scoped>
.v-im {
  display: flex;
  flex-direction: row;

  .left-bar {
    background-color: #1c2438;
    width: 6rem;
    height: 100%;

    ul {
      padding: 3rem 1.2rem 1.2rem 1.2rem;
      list-style: none;
      height: 100%;
      position: relative;

      li {
        -webkit-app-region: no-drag;
        display: block;
        width: 3.6rem;
        height: 3.6rem;
        text-align: center;
        margin-bottom: 2rem;
        cursor: pointer;

        a {
          display: block;
          text-decoration: none;
        }

        .iconfont {
          font-size: 2.4rem !important;
          color: $color-default;
          margin: 0.3rem;
          cursor: pointer;

          &:hover {
            color: $color-write;
          }
        }

        .router-link-active {
          .iconfont {
            color: $color-write;
          }
        }
      }

      .logout {
        bottom: 0;
        position: absolute;
      }

      .userPhoto {
        margin-bottom: 3rem;

        img {
          width: 3.6rem;
          height: 3.6rem;
        }
      }
    }
  }

  .content {
    flex: 1;
  }
}
</style>
