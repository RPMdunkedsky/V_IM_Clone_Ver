<template>
  <div>
    <ul class="faces">
      <li v-for="(item, index) in faceList" :key="index">
        <img
          :src="faceMap.get(item)"
          :alt="item"
          :title="item"
          @click="insertFace(item)"
        />
      </li>
    </ul>
    <div class="clear"></div>
  </div>
</template>

<script setup lang="ts">
import FaceUtils from "@/utils/FaceUtils.ts";
import { defineEmits, ref } from "vue";
const faceList = ref(FaceUtils.alt);
const faceMap = ref(FaceUtils.faces());
let emit = defineEmits(["insertFace"]);
const insertFace = (item: string) => {
  emit("insertFace", item);
};
</script>

<style scoped lang="scss">
.faces {
  width: 30.5rem;
  list-style: none;
  background-color: #ffffff;
  border: 1px solid #f0f5ff;
  display: block;
  height: 25rem;
  & > li {
    width: 3rem !important;
    height: 3rem !important;
    display: inline-block;
    padding: 4px;
    float: left;
    cursor: pointer;
    & > img {
      width: 100%;
      height: 100%;
    }
  }
}
</style>
