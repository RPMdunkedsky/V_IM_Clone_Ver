<template>
  <div class="im-chat-main" style="background-color: #f8f8f8">
    <div class="messages" id="his-chat-message" style="height: 100%">
      <ul>
        <li
          v-for="(item, index) in hisMessageList"
          :key="index"
          :class="{ 'im-chat-mine': item.mine }"
        >
          <div class="im-chat-user" id="historyMessageBox">
            <avatar :img="item.avatar" />
            <div class="message-info right" v-if="item.mine">
              <i> <Time :time="item.timestamp" /></i>
              <span>{{ item.username }}</span>
            </div>
            <div class="message-info" v-if="!item.mine">
              <span>{{ item.username }}</span>
              <i> <Time :time="item.timestamp" /></i>
            </div>
          </div>
          <div class="im-chat-text">
           <pre
               v-html="ChatUtils.transform(item.content)"
               v-on:click="openImageProxy($event)"
           ></pre>
          </div>
        </li>
      </ul>
    </div>
  </div>
  <el-pagination
    background
    v-model:currentPage="pageNum"
    :page-size="pageSize"
    layout="prev, pager, next"
    :total="total"
    @current-change="change"
  >
  </el-pagination>
</template>

<script setup lang="ts">
import { defineProps, getCurrentInstance, nextTick, reactive, ref, toRefs, watch } from "vue";
import MessageApi from "@/api/MessageApi";
import Avatar from "@/components/Avatar.vue";
import Time from "@/components/Time.vue";
import ChatUtils from "@/utils/ChatUtils";

const { proxy } = getCurrentInstance();
const pageSize = ref(10);
const props = defineProps({
  chatId: {
    type: String,
    required: true,
    default: "",
  },
  fromId: {
    type: String,
    required: true,
    default: "",
  },
  type: {
    type: String,
    required: true,
    default: "",
  },
  showHistory: {
    type: Boolean,
    required: true,
    default: false,
  },
});

const data = reactive({
  hisMessageList: [],
  pageNum: 1,
  total: 0,
});

// 附件和图片点击展开
const openImageProxy = (event: any) => {
  event.preventDefault();
  if (event.target.nodeName === "IMG") {
    proxy.$winControl.default.openURL(event.target.src);
  } else if (
    event.target.className === "message-file" ||
    event.target.nodeName === "A"
  ) {
    proxy.$winControl.default.openURL(event.target.href);
  }
};

const change = (pageNum: number) => {
  MessageApi.list(props.chatId, props.fromId, props.type, pageNum, pageSize.value).then(
    (res) => {
      data.hisMessageList = res.data.messageList;
      data.total = res.data.count;
      data.pageNum = pageNum;
      nextTick(() => {
        ChatUtils.imageLoad("his-chat-message");
      });
    }
  );
};

watch(
  () => {
    return props.showHistory;
  },
  (n) => {
    if (n) {
      change(1);
    }
  },
  {
    immediate: true,
  }
);
const { hisMessageList, pageNum, total } = toRefs(data);
</script>

<style scoped></style>
