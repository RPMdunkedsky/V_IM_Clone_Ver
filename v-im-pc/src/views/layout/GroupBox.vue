<template>
  <div class="main">
    <div class="left">
      <div class="title">
        <el-row>
          <el-col :span="20">
            <div class="text">群</div>
          </el-col>
          <el-col :span="4" class="add">
            <i
              class="iconfont icon-v-add"
              title="新建群"
              @click.stop="newGroup"
            ></i>
          </el-col>
        </el-row>
      </div>
      <el-scrollbar class="list">
        <list-item
          v-for="(group, index) in groupStore.groupList"
          :key="index"
          :id="group.id"
          :img="group.avatar"
          :username="group.name"
          :showDel="false"
          :active="index === groupStore.checkIndex"
          @click="choose(index, group.id)"
        ></list-item>
      </el-scrollbar>
    </div>
    <div class="right">
      <Top></Top>
      <router-view
        v-slot="{ Component }"
        class="content"
        :key="route.params.id"
      >
        <component :is="Component" />
      </router-view>
    </div>
  </div>
</template>

<script setup lang="ts">
import ListItem from "@/components/ListItem.vue";
import Top from "@/components/Top.vue";
import { useRouter, useRoute } from "vue-router";
import { useGroupStore } from "@/store/groupStore";
const groupStore = useGroupStore();

const router = useRouter();
const route = useRoute();

groupStore.reloadGroupList();

const choose = (index: number, id: string) => {
  groupStore.checkIndex = index;
  router.push("/index/group/" + id);
};

const newGroup = () => {
  groupStore.checkIndex = -1;
  router.push("/index/group/new");
};
</script>

<style scoped lang="scss"></style>
