<template>
  <div class="item" :class="active ? 'active' : ''">
    <el-badge :value="unreadCount" :hidden="unreadCount === 0">
      <Avatar :img="img" class="avatar"></Avatar>
    </el-badge>

    <div class="text">
      <div>{{ username }}</div>
      <div v-if="text">
        <div>{{ text }}</div>
      </div>
    </div>
    <div class="close" v-if="showDel">
      <el-popconfirm title="确定要删除吗?" @confirm="confirmEvent">
        <template #reference>
          <i class="iconfont icon-v-guanbi1"></i>
        </template>
      </el-popconfirm>
    </div>
  </div>
</template>

<script setup lang="ts">
import Avatar from "@/components/Avatar.vue";
import { defineProps, defineEmits } from "vue";
const props = defineProps({
  id: {
    type: String,
    required: true,
    default: "",
  },
  unreadCount: {
    type: Number,
    required: false,
    default: 0,
  },
  img: {
    type: String,
    required: true,
    default: "@/assets/icon.png",
  },
  username: {
    type: String,
    required: true,
    default: "",
  },
  text: {
    type: String,
    required: false,
    default: "",
  },
  showDel: {
    type: Boolean,
    required: true,
    default: false,
  },
  active: {
    type: Boolean,
    required: false,
    default: false,
  },
});

let emit = defineEmits(["del"]);

const confirmEvent = () => {
  emit("del", props.id);
};
</script>

<style scoped lang="scss">
.item {
  height: 5rem;
  display: flex;
  margin-bottom: 10px;
  position: relative;
  padding-left: 15px;
  align-items: center;
  .close {
    position: absolute;
    width: 1.5rem;
    height: 1.5rem;
    right: 10px;
    top: 1.825rem;
    display: none;
  }
  &:hover {
    .close {
      display: block;
    }
  }
  .avatar {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 4.4rem;
    height: 4.4rem;
  }
  .text {
    margin-left: 15px;
    flex: 3;
    display: flex;
    flex-direction: column;
    height: 100%;
    flex-shrink: 0;
    overflow: hidden;
    & > div {
      display: flex;
      justify-content: flex-start;
      align-items: center;
      flex: 1;
      & > div {
        display: block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        color: #999;
        font-size: 12px;
      }
    }
  }
}
.active {
  background-color: $color-gray;
}
</style>
