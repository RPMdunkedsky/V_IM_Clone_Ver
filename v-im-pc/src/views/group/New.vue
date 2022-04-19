<template>
  <div class="header">
    <avatar-upload
      :avatar="dataForm.avatar"
      @uploadSuccess="uploadSuccess"
    ></avatar-upload>
  </div>
  <el-divider />
  <div class="text-center">
    <el-form :inline="true" ref="ruleFormRef" :model="dataForm" :rules="rules">
      <el-form-item label="群名称:" prop="name">
        <el-input v-model="dataForm.name"></el-input>
      </el-form-item>
      <el-form-item label="进群审核:">
        <el-switch v-model="dataForm.needCheck" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveGroup">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import FetchRequest from "@/api/FetchRequest";
import Auth from "@/api/Auth";
import AvatarUpload from "@/components/AvatarUpload.vue";
import { useGroupStore } from "@/store/groupStore";
import { useRouter } from "vue-router";
import { reactive, ref, toRefs } from "vue";
import GroupApi from "@/api/GroupApi";
import { ElMessage } from "element-plus";
import type { ElForm } from "element-plus";

const router = useRouter();
const groupStore = useGroupStore();
const ruleFormRef = ref<InstanceType<typeof ElForm>>();

const dataForm = reactive({
  name: "",
  needCheck: false,
  avatar: "/img/default-group.png",
});

const rules = reactive({
  name: [
    {
      required: true,
      message: "必填",
      trigger: "blur",
    },
    {
      min: 3,
      max: 10,
      message: "长度介于3-10",
      trigger: "blur",
    },
  ],
});

interface VimData {
  host: string;
  headers: any;
  data: any;
}

const uploadSuccess = (url: string) => {
  dataForm.avatar = url;
};

const vimData = reactive<VimData>({
  host: FetchRequest.getHost(),
  headers: {
    "Access-Control-Allow-Origin": "*",
  },
  data: {
    access_token: Auth.getToken(),
    type: "file",
  },
});

const saveGroup = () => {
  ruleFormRef.value?.validate((f) => {
    if (f) {
      GroupApi.save(
        dataForm.name,
        dataForm.avatar,
        dataForm.needCheck ? "1" : "0"
      )
        .then((res) => {
          groupStore.checkIndex = groupStore.groupList.length;
          groupStore.reloadGroupList().then(() => {
            router.push("/index/group/" + res.data.id);
          });
          ElMessage.success("保存成功");
        })
        .catch(() => {
          ElMessage.error("保存错误");
        });
    }
  });
};

const { host, headers, data } = toRefs(vimData);
</script>

<style scoped lang="scss">
.header {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.add-user {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  font-size: 6rem;
  border: 1px #ccc solid;
  color: #ccc;

  i {
    font-size: 6rem;
  }
}

.user {
  position: relative;

  .close {
    position: absolute;
    top: 0;
    right: 0;
    background-color: #ff0000;
    width: 16px;
    height: 16px;
    color: #ffffff;
    border-radius: 8px;

    .icon-v-close {
      font-size: 12px;
    }
  }
}
</style>
