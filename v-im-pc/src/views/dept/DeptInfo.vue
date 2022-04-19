<template>
  <div>
    <div class="users">
      <el-row :gutter="24">
        <el-col
          :span="3"
          v-for="(user, index) in users"
          :key="index"
          class="user"
          @click="openUser(user.id)"
        >
          <Avatar :img="user.avatar"></Avatar>
          <div>{{ user.name }}</div>
        </el-col>
      </el-row>
    </div>
    <user-modal
      :show="show"
      :showSend="true"
      :userId="userId"
      @close="show = false"
    ></user-modal>
  </div>
</template>

<script setup lang="ts">
import { reactive, toRefs } from "vue";
import User from "@/mode/User";
import Avatar from "@/components/Avatar.vue";
import UserModal from "@/components/UserModal.vue";
import { useRoute, useRouter } from "vue-router";
import DeptApi from "@/api/DeptApi";

const router = useRouter();
const route = useRoute();
const data = reactive({
  users: new Array<User>(),
  show: false,
  userId: "",
});
const deptId = route.params.id;
console.log("deptId", deptId);
if (typeof deptId === "string") {
  DeptApi.users(deptId).then((res) => {
    res.data.forEach((item: any) => {
      data.users.push(item);
    });
  });
}

const openUser = (id: string) => {
  if (id !== "") {
    data.show = true;
    data.userId = id;
  }
};
const { users, show, userId } = toRefs(data);
</script>

<style scoped lang="scss">
.users {
  padding: 10px;
  height: 100%;
  width: 100%;
  overflow-x: hidden;
  overflow-y: scroll;

  .user {
    text-align: center;
    cursor: pointer;
    padding: 10px;
  }
}
</style>
