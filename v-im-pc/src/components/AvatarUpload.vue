<template>
  <el-upload
    :action="host + '/common/upload'"
    :headers="headers"
    :data="data"
    :show-file-list="false"
    :on-success="handleSuccess"
  >
    <Avatar v-if="avatar" size="large" :img="avatar"></Avatar>
    <i v-if="!avatar" class="iconfont icon-v-add"></i>
  </el-upload>
</template>

<script setup lang="ts">
import { reactive, toRefs, defineProps, computed, defineEmits } from "vue";
import FetchRequest from "@/api/FetchRequest";
import Auth from "@/api/Auth";
import Avatar from "@/components/Avatar.vue";

interface VimData {
  host: string;
  headers: any;
  data: any;
}
const props = defineProps({
  avatar: {
    type: String,
    required: true,
    default: "/img/default-group.png",
  },
});

let avatar = computed(() => {
  return props.avatar;
});
const token = Auth.getToken();

const vimData = reactive<VimData>({
  host: FetchRequest.getHost(),
  headers: {
    "Access-Control-Allow-Origin": "*",
    Authorization: "Bearer " + token,
  },
  data: {
    access_token: Auth.getToken(),
    type: "file",
  },
});
const emits = defineEmits(["uploadSuccess"]);
//上传成功回调
const handleSuccess = (response: any) => {
  emits("uploadSuccess", response.url);
};
const { host, headers, data } = toRefs(vimData);
</script>

<style scoped></style>
