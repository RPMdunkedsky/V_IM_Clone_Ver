<template>
  <teleport to="#modal">
    <el-dialog
      v-model="show_"
      width="40rem"
      center
      :show-close="false"
      :close-on-click-modal="false"
    >
      <div class="info" v-if="group">
        <Avatar :img="group.avatar"></Avatar>
        <el-descriptions class="description" :column="2">
          <el-descriptions-item label="群名"
            >{{ group.name }}
          </el-descriptions-item>
          <el-descriptions-item label="审核"
            >{{ group.needCheck === "0" ? "否" : "是" }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="close">关闭</el-button>
          <el-button v-if="showSend" type="primary" @click="send()"
            >聊天</el-button
          >
        </span>
      </template>
    </el-dialog>
  </teleport>
</template>

<script setup lang="ts">
import { computed, defineEmits, defineProps, onMounted, ref, watch } from "vue";
import { useChatStore } from "@/store/chatStore";
import { useRouter } from "vue-router";
import Avatar from "@/components/Avatar.vue";
import GroupApi from "@/api/GroupApi";
import ChatType from "@/config/ChatType";
import Group from "@/mode/Group";

const router = useRouter();
const store = useChatStore();
const emit = defineEmits(["close"]);

const props = defineProps({
  groupId: {
    type: String,
    required: true,
    default: null,
  },
  showSend: {
    type: Boolean,
    required: false,
    default: false,
  },
  show: {
    type: Boolean,
    required: true,
    default: false,
  },
});

const show_ = computed(() => {
  return props.show;
});

const group = ref<Group>();
function getGroup(groupId: string) {
  GroupApi.get(groupId).then((res) => {
    group.value = res.data;
  });
}

watch(
  () => {
    return props.groupId;
  },
  (n) => {
    getGroup(n);
  },
  { immediate: true }
);

const send = () => {
  if (group.value) {
    emit("close");
    store.openChat({
      id: group.value.id,
      name: group.value.name,
      avatar: group.value.avatar,
      type: ChatType.GROUP,
      lastMessage: "",
      unreadCount: 0,
      isLoading: false,
      loaded: true,
    });
    router.push("/index/chat");
  }
};

const close = () => {
  emit("close");
};
</script>

<style scoped lang="scss">
.info {
  text-align: center;
  line-height: 200%;
}

.description {
  padding: 20px 20px 0px 20px;
  background-color: #ffffff;
}
</style>
