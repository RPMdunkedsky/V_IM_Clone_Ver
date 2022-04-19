<template>
  <div class="main">
    <div class="left">
      <div class="title">
        <el-row>
          <el-col :span="20">
            <div class="text">设置</div>
          </el-col>
          <el-col :span="4" class="add"></el-col>
        </el-row>
      </div>
      <el-scrollbar class="list">
        <ul class="sys-menu">
          <li>
            <router-link v-bind:to="'/index/system/user'" class="block">
              个人信息
            </router-link>
          </li>
        </ul>
      </el-scrollbar>
    </div>
    <div class="right">
      <Top></Top>
      <router-view v-slot="{ Component }" class="main-view">
        <keep-alive>
          <component :is="Component" />
        </keep-alive>
      </router-view>
    </div>
  </div>
</template>

<script setup lang="ts">
import Top from "@/components/Top.vue";
</script>

<style scoped lang="scss">
.sys-menu {
  list-style: none;
  padding: 0;
  margin: 0;
  li {
    line-height: 30px;
    padding: 10px;
    background-color: #ffffff;
  }
}
.block {
  display: block;
}
</style>
