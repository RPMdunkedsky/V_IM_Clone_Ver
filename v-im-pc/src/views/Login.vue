<template>
  <el-form class="login" ref="formRef" :model="form" label-width="80px">
    <el-form-item label="用户名">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="密码">
      <el-input type="password" v-model="form.password"></el-input>
    </el-form-item>
    <el-form-item label="验证码" prop="code" v-if="captchaOnOff">
      <el-input
        v-model="form.code"
        auto-complete="off"
        placeholder="验证码"
        style="width: 63%"
      >
      </el-input>
      <div class="login-code">
        <img :src="codeUrl" @click="getCode" class="login-code-img" />
      </div>
    </el-form-item>
    <el-form-item label="主机">
      <el-input v-model="form.host"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">登录</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import Auth from "@/api/Auth";
import { useUserStore } from "@/store/userStore";
import UserApi from "@/api/UserApi";
import { login, getCodeImg } from "@/api/Login.ts";

const router = useRouter();
const store = useUserStore();

const form = reactive({
  name: "",
  password: "",
  code: "",
  uuid: "",
  host: "127.0.0.1",
});

const submit = () => {
  const reg =
    /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
  if (!reg.test(form.host)) {
    ElMessage.error("主机地址不对");
    return;
  }
  Auth.setIp(form.host);
  login(form.name, form.password, form.code, form.uuid)
    .then((res: any) => {
      Auth.setToken(res.token);
      return UserApi.currentUser();
    })
    .then((res) => {
      store.setUser(res.data);
      router.push("/index/chat");
    });
};

// 验证码开关
const captchaOnOff = ref(true);
const codeUrl = ref("");

const getCode = () => {
  getCodeImg().then((res: any) => {
    captchaOnOff.value =
      res.captchaOnOff === undefined ? true : res.captchaOnOff;
    if (captchaOnOff.value) {
      codeUrl.value = "data:image/gif;base64," + res.img;
      form.uuid = res.uuid;
    }
  });
};
onMounted(() => {
  getCode();
});
</script>

<style scoped lang="scss">
.login {
  width: 50rem;
  margin: 20rem auto;
}
.login-code {
  flex: 1;
  height: 32px;
  img {
    width: 100%;
    height: 100%;
  }
}
</style>
