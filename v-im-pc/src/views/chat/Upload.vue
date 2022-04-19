<template>
  <el-upload
    :action="host + '/common/upload'"
    :headers="headers"
    :show-file-list="false"
    :on-success="handleSuccess"
    :before-upload="beforeUpload"
    style="display: inline-block"
  >
    <slot></slot>
  </el-upload>
</template>

<script setup lang="ts">
import { reactive, toRefs, defineEmits, defineProps, PropType } from "vue";
import FetchRequest from "@/api/FetchRequest";
import Auth from "@/api/Auth";
import { ElMessage } from "element-plus";

interface VimData {
  host: string;
  headers: any;
}

const props = defineProps({
  fileTypes: {
    type: Object as PropType<Array<string>>,
    required: true,
    default: null,
  },
  isImg: {
    type: Boolean,
    required: true,
    default: true,
  },
});
const beforeUpload = (file: any) => {
  let suffix = file.name.substring(file.name.lastIndexOf(".") + 1);
  let suffixes = props.fileTypes;
  let len = suffixes.filter((item) => {
    return item === suffix.toLowerCase();
  }).length;
  if (len === 0) {
    ElMessage.error("不支持的文件类型,仅支持：" + suffixes.join(","));
  }
  return len > 0;
};
const vimData = reactive<VimData>({
  host: FetchRequest.getHost(),
  headers: {
    "Access-Control-Allow-Origin": "*",
    Authorization: "Bearer " + Auth.getToken(),
  },
});
const emits = defineEmits(["uploadSuccess"]);
//上传成功回调
const handleSuccess = (res: any) => {
  emits(
    "uploadSuccess",
    props.isImg
      ? "img[" + res.url + "]"
      : "file(" + res.url + ")[" + res.fileName + "]"
  );
};
const { host, headers } = toRefs(vimData);
</script>

<style scoped></style>
