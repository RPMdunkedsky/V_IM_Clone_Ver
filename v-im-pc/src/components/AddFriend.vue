<template>
  <el-dialog
    v-model="show"
    title="添加好友"
    width="400px"
    :before-close="handleClose"
  >
    <div>
      <el-form-item label="手机号">
        <el-input
          v-model="mobile"
          class="w-50 m-1"
          placeholder="请输入完整手机号并回车"
          :suffix-icon="Search"
          @blur="search"
          @keyup.enter.down="search"
        />
      </el-form-item>
      <div
        v-for="(user, index) in users"
        :key="index"
        class="solid"
        :class="checkUser ? 'active' : ''"
        @click="check"
      >
        <list-item
          :id="user.id"
          :img="user.avatar"
          :username="user.name"
          :showDel="false"
        ></list-item>
      </div>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="add">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { computed, defineEmits, defineProps, onMounted, ref } from "vue";
import { Search } from "@element-plus/icons-vue";
import UserApi from "@/api/UserApi";
import ListItem from "@/components/ListItem.vue";
import User from "@/mode/User";
import FriendApi from "@/api/FriendApi";
import { ElMessage } from "element-plus/es";
const emit = defineEmits(["close"]);

const props = defineProps({
  dialogVisible: {
    type: Boolean,
    required: true,
    default: false,
  },
});

const handleClose = () => {
  emit("close");
};

const show = computed(() => {
  return props.dialogVisible;
});

const checkUser = ref(false);
const mobile = ref("");
const users = ref<Array<User>>([]);

const check = () => {
  checkUser.value = !checkUser.value;
};
const close = () => {
  emit("close");
};
const search = () => {
  UserApi.search(mobile.value.trim()).then((res) => {
    users.value = res.data;
  });
};

const add = () => {
  if (checkUser.value && users.value.length > 0) {
    let user = users.value[0];
    FriendApi.add(user.id)
      .then(() => {
        ElMessage.info("添加成功");
        emit("close");
      })
      .catch((res) => {
        console.log(res);
      });
  } else {
    ElMessage.error("请选择一个用户");
  }
};

onMounted(() => {
  console.log(show.value);
});
</script>
<style scoped>
.solid {
  border: 1px solid #eeeeee;
  padding-top: 10px;
}

.solid.active {
  border: 1px solid #1d86f1;
  padding-top: 10px;
}
</style>
