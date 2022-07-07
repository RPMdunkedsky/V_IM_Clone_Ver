<template>
  <teleport to="#modal">
    <el-dialog
      v-model="show_"
      width="40rem"
      center
      :show-close="false"
      :close-on-click-modal="false"
    >
      <div class="info" v-if="user">
        <Avatar :img="user.avatar"></Avatar>
        <el-descriptions class="description" :column="2">
          <el-descriptions-item label="姓名">{{
            user.name
          }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{
            user.sex === "0" ? "男" : "女"
          }}</el-descriptions-item>
          <el-descriptions-item label="电话">{{
            user.mobile
          }}</el-descriptions-item>
          <el-descriptions-item label="邮箱">{{
            user.email
          }}</el-descriptions-item>
          <el-descriptions-item label="部门" role="2">
            <span v-for="(item, index) in depts" :key="index"
              >{{ item.name }},</span
            >
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
import Avatar from "@/components/Avatar.vue";
import { useChatStore } from "@/store/chatStore";
import { useRouter } from "vue-router";
import UserApi from "@/api/UserApi";
import DeptApi from "@/api/DeptApi";
import Dept from "@/mode/Dept";
import User from "@/mode/User";
import ChatType from "@/config/ChatType";

const router = useRouter();
const store = useChatStore();
const emit = defineEmits(["close"]);

const props = defineProps({
  userId: {
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

const user = ref<User>();
const depts = ref(new Array<Dept>());

function getUser() {
  UserApi.getUser(props.userId)
    .then((res) => {
      user.value = res.data;
      return DeptApi.parent(res.data.deptId);
    })
    .then((res) => {
      depts.value.splice(0, depts.value.length);
      res.data.forEach((item: Dept) => {
        depts.value.push(item);
      });
    });
}

onMounted(() => {
  if (props.userId) {
    getUser();
  }
});

watch(
  () => {
    return props.show;
  },
  (n) => {
    if (n) {
      getUser();
    }
  },
  {
    immediate: true,
  }
);
watch(
  () => {
    return props.userId;
  },
  (n) => {
    if (n) {
      getUser();
    }
  },
  {
    immediate: true,
  }
);

const send = () => {
  emit("close");
  if (user.value) {
    store.openChat({
      id: user.value.id,
      name: user.value.name,
      avatar: user.value.avatar,
      type: ChatType.FRIEND,
      lastMessage: "",
      unreadCount: 0,
      isLoading: false,
      loaded: true,
    });
  }
  router.push("/index/chat");
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
