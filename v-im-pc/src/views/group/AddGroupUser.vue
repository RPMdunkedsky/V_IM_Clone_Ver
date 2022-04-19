<template>
  <el-row class="d-row" :gutter="20">
    <el-col :span="12">
      <el-scrollbar class="list">
        <div v-for="(item, index) in friends" :key="index" class="user">
          <div class="avatar">
            <Avatar :img="item.user.avatar"></Avatar>
          </div>
          <div class="name">{{ item.user.name }}</div>
          <div class="state">
            <el-checkbox v-model="item.isCheck" size="large"></el-checkbox>
          </div>
        </div>
      </el-scrollbar>
    </el-col>
    <el-col :span="12">
      <el-scrollbar class="list">
        <div v-for="(item, index) in checkedFriends" :key="index" class="user">
          <div class="avatar">
            <Avatar :img="item.user.avatar"></Avatar>
          </div>
          <div class="name">{{ item.user.name }}</div>
          <div class="state"></div>
        </div>
      </el-scrollbar>
    </el-col>
  </el-row>
  <div class="footer text-right">
    <el-button type="primary" @click.stop="addToGroup" :disabled="canSave"
      >确定
    </el-button>
  </div>
</template>

<script setup lang="ts">
import Avatar from "@/components/Avatar.vue";
import FriendApi from "@/api/FriendApi";
import User from "@/mode/User";
import AccountApi from "@/api/AccountApi";
import { useUserStore } from "@/store/userStore";
import {
  computed,
  ref,
  defineProps,
  defineEmits,
  onMounted,
  getCurrentInstance,
  PropType,
} from "vue";
import GroupApi from "@/api/GroupApi";
import Message from "@/mode/Message";
import ChatType from "@/config/ChatType";
import Group from "@/mode/Group";

const { proxy } = getCurrentInstance();

interface UserCheck {
  user: User;
  isCheck: boolean;
}

let props = defineProps({
  group: {
    type: Object as PropType<Group>,
    required: true,
    default: null,
  },
});
const userStore = useUserStore();
const friends = ref(new Array<UserCheck>());
const user = userStore.getUser();
const userId = user?.id;
if (!userId) {
  AccountApi.toLogin();
}

let tempList = new Array<UserCheck>();
//过滤掉已经存在的用户
if (typeof userId !== "undefined") {
  FriendApi.friends()
    .then((res: any) => {
      res.data.forEach((item: User) => {
        tempList.push({ user: item, isCheck: false });
      });
      return GroupApi.users(props.group.id);
    })
    .then((res: any) => {
      const ids = res.data.map((item: User) => item.id);
      friends.value = tempList.filter(
        (item) => ids.indexOf(item.user.id) === -1
      );
    });
}

const checkedFriends = computed((): Array<UserCheck> => {
  return friends.value.filter((item) => item.isCheck);
});
const userIds = computed((): string[] => {
  return checkedFriends.value.map((item) => item.user.id);
});
const canSave = computed((): boolean => {
  return userIds.value.length === 0;
});

const emit = defineEmits(["reload"]);

//执行添加到数据库
const addToGroup = () => {
  GroupApi.addUsers(props.group.id, userIds.value).then((res) => {
    if (res) {
      emit("reload");
      if (user) {
        const content = user.name + "把你加入新的群";
        let msg: Message = {
          chatId: props.group.id,
          fromId: user.id,
          avatar: user.avatar,
          name: user.name,
          mine: true,
          content: content,
          timestamp: new Date().getTime(),
          type: ChatType.GROUP,
        };
        proxy.$ws.sendMessage(msg);
      }
    }
  });
};
</script>

<style scoped lang="scss">
.d-row {
  height: 90%;
}

.user {
  display: flex;
  background-color: #eeeeee;
  padding: 5px 10px;
  margin-bottom: 5px;

  .avatar {
    width: 6rem;
    display: flex;
    align-items: center;
    justify-content: flex-start;
  }

  .name {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: flex-start;
  }

  .state {
    width: 6rem;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}

.footer {
  position: fixed;
  right: 15px;
  bottom: 15px;
}
</style>
