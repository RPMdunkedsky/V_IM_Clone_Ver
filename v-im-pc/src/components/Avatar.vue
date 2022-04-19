<template>
  <el-avatar shape="square" :size="size" :src="url" fit="cover"></el-avatar>
</template>

<script setup lang="ts">
import { computed, defineProps, ref } from "vue";
import FetchRequest from "@/api/FetchRequest";

const host = ref(FetchRequest.getHost());
let props = defineProps({
  size: {
    type: String,
    required: false,
    default: "default",
  },
  img: {
    type: String,
    required: true,
    default: "@/assets/icon.png",
  },
});
let url = computed(() => {
  if (props.img?.indexOf("http") > -1) {
    return props.img;
  } else {
    return host.value + props.img;
  }
});
</script>
<style lang="scss" scoped>
.avatar {
  width: 6rem;
  height: 6rem;
}
</style>
