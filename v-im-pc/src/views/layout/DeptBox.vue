<template>
  <div class="main">
    <div class="left">
      <div class="title">
        <el-row>
          <el-col :span="20">
            <div class="text">组织</div>
          </el-col>
          <el-col :span="4"></el-col>
        </el-row>
      </div>
      <el-scrollbar class="list">
        <el-tree
          :default-expand-all="true"
          :data="tree"
          :props="defaultProps"
          @node-click="handleNodeClick"
        />
      </el-scrollbar>
    </div>
    <div class="right">
      <Top></Top>
      <router-view
        v-slot="{ Component }"
        class="content"
        :key="route.params.id"
      >
        <keep-alive>
          <component :is="Component" />
        </keep-alive>
      </router-view>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import Top from "@/components/Top.vue";
import DeptApi from "@/api/DeptApi";

const router = useRouter();
const route = useRoute();

interface Tree {
  id: string;
  parentId: string;
  label: string;
  count: number;
  children?: Tree[];
}

const defaultProps = {
  children: "children",
  label: "label",
};

const handleNodeClick = (data: Tree) => {
  if (data.children?.length === 0) {
    router.push("/index/dept/" + data.id);
  }
};

const tree = ref<Array<Tree>>([]);
DeptApi.list().then((res) => {
  tree.value.splice(0, tree.value.length);
  res.data.forEach((item: any) => {
    tree.value.push(item);
  });
});
</script>

<style lang="scss" scoped>
.el-tree {
  background-color: transparent !important;
}
</style>
