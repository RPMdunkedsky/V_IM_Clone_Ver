<template>
  <div v-if="user">
    <div class="text-center">
      <Avatar :img="user.avatar" size="large"></Avatar>
    </div>
    <el-descriptions title="用户信息" class="description" :column="2">
      <el-descriptions-item label="姓名">{{ user.name }}</el-descriptions-item>
      <el-descriptions-item label="性别">{{
        user.sex === "0" ? "男" : "女"
      }}</el-descriptions-item>
      <el-descriptions-item label="电话">{{
        user.mobile
      }}</el-descriptions-item>
      <el-descriptions-item label="邮箱">{{ user.email }}</el-descriptions-item>
      <el-descriptions-item label="部门" role="2">
        <span v-for="(item, index) in depts" :key="index"
          >{{ item.name }},</span
        >
      </el-descriptions-item>
    </el-descriptions>
    <el-button class="send-btn" @click="send()">发送消息</el-button>
  </div>
</template>

<script setup lang="ts">
import { reactive, toRefs } from "vue";
import { useRoute, useRouter } from "vue-router";
import UserApi from "@/api/UserApi";
import DeptApi from "@/api/DeptApi";
import Dept from "@/mode/Dept";
import { useChatStore } from "@/store/chatStore";
import User from "@/mode/User";
import Avatar from "@/components/Avatar.vue";
import ChatType from "@/config/ChatType";

const store = useChatStore();
const router = useRouter();
const route = useRoute();

const userId = route.params.id;

interface IData {
  user: User | null;
  depts: Array<Dept>;
}

const data = reactive<IData>({
  user: null,
  depts: new Array<Dept>(),
});
if (typeof userId === "string") {
  UserApi.getUser(userId)
    .then((res) => {
      data.user = res.data;
      return DeptApi.parent(res.data.deptId);
    })
    .then((res) => {
      res.data.forEach((item: Dept) => {
        data.depts.push(item);
      });
    });
}
const send = () => {
  if (data.user) {
    store.openChat({
      id: data.user.id,
      name: data.user.name,
      avatar: data.user.avatar,
      type: ChatType.FRIEND,
      lastMessage: "",
      unreadCount: 0,
      isLoading: false,
      loaded: true,
    });
    router.push("/index/chat");
  }
};
const { user, depts } = toRefs(data);
</script>

<style scoped lang="scss">
.description {
  padding: 20px;
  background-color: #ffffff;
}

.send-btn {
  float: right;
  margin-top: 15px;
}
</style>
