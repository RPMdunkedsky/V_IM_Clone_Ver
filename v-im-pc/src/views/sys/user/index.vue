<template>
  <el-form ref="userRef" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="头像" prop="avatar">
      <avatar-upload class="avatar"
        :avatar="user.avatar"
        @uploadSuccess="uploadSuccess"
      ></avatar-upload>
    </el-form-item>
    <el-form-item label="名称" prop="name">
      <el-input v-model="user.name" maxlength="30" />
    </el-form-item>
    <el-form-item label="手机" prop="mobile">
      <el-input v-model="user.mobile" maxlength="11" />
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="user.email" maxlength="50" />
    </el-form-item>
    <el-form-item label="性别">
      <el-radio-group v-model="user.sex">
        <el-radio label="0">男</el-radio>
        <el-radio label="1">女</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">保存</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import { useUserStore } from "@/store/userStore.ts";
import User from "@/mode/User";
import UserApi from "@/api/UserApi";
import AvatarUpload from "@/components/AvatarUpload.vue";
import { Stamp } from "@element-plus/icons-vue";

const userStore = useUserStore();
const user = reactive<User>({
  id: "",
  name: "",
  mobile: "",
  email: "",
  avatar: "",
  deptId: "",
  sex: "",
  canAddFriend: "",
});

/** 初始化用户 */
if (userStore.user) {
  UserApi.getUser(userStore.user.id).then((res) => {
    user.id = res.data.id;
    user.name = res.data.name;
    user.deptId = res.data.deptId;
    user.mobile = res.data.mobile;
    user.email = res.data.email;
    user.avatar = res.data.avatar === "" ? null : res.data.avatar;
    user.sex = res.data.sex;
  });
}

/** 验证规则 */
const rules = ref({
  avatar: [{ required: true, message: "头像不能为空", trigger: "blur" }],
  name: [{ required: true, message: "用户名不能为空", trigger: "blur" }],
  email: [
    { required: true, message: "邮箱地址不能为空", trigger: "blur" },
    {
      type: "email",
      message: "'请输入正确的邮箱地址",
      trigger: ["blur", "change"],
    },
  ],
  mobile: [
    { required: true, message: "手机号码不能为空", trigger: "blur" },
    {
      pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
      message: "请输入正确的手机号码",
      trigger: "blur",
    },
  ],
});

/** 上传成功 */
const uploadSuccess = (url: string) => {
  user.avatar = url;
};

/** 提交按钮 */
function submit() {
  UserApi.update(user.id, user)
    .then(() => {
      return UserApi.getUser(user.id);
    })
    .then((res) => {
      userStore.setUser(res.data);
    });
}
</script>
<style scoped lang="scss">
  .avatar{
    border: 1px saddlebrown solid;
    width: 64px;
    height: 64px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>
