<template>
  <div class="main">
    <div class="left">
      <div class="title">
        <el-row>
          <el-col :span="20">
            <div class="text">聊天</div>
          </el-col>
          <el-col :span="4" class="add">
            <!--            <i class="iconfont icon-v-add" title="拉群聊天"></i>-->
          </el-col>
        </el-row>
      </div>
      <el-scrollbar class="list">
        <list-item
          v-for="(chat, index) in store.chats"
          :key="index"
          :index="index"
          :id="chat.id"
          :img="chat.avatar"
          :username="chat.name"
          :unreadCount="chat.unreadCount"
          :text="chat.lastMessage"
          :showDel="true"
          :active="index === store.index"
          @del="delChat"
          @click="showChat(chat)"
        ></list-item>
      </el-scrollbar>
    </div>
    <div class="right">
      <Top></Top>
      <chat-message></chat-message>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useChatStore } from "@/store/chatStore";
import Chat from "@/mode/Chat";
import ChatMessage from "@/views/chat/ChatMessage.vue";
import Top from "@/components/Top.vue";
import ListItem from "@/components/ListItem.vue";
import { nextTick } from "vue";
import ChatUtils from "@/utils/ChatUtils";

const store = useChatStore();

const showChat = (chat: Chat) => {
  store.openChat(chat);
  nextTick(() => {
    ChatUtils.imageLoad("message-box");
  });
};
const delChat = (id: string) => {
  store.delChat(id);
};
</script>

<style lang="scss" scoped></style>
