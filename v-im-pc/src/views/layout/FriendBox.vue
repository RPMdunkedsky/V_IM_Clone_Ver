<template>
  <div class="main">
    <div class="left">
      <div class="title">
        <el-row>
          <el-col :span="20">
            <div class="text">好友</div>
          </el-col>
          <el-col :span="4" class="add">
            <i
              class="iconfont icon-v-add"
              title="加好友"
              @click.stop="showAddFriend = !showAddFriend"
            ></i>
          </el-col>
        </el-row>
        <add-friend :dialogVisible="showAddFriend" @close="close"></add-friend>
      </div>
      <el-scrollbar class="list">
        <div v-for="(user, index) in friends" :key="user.id">
          <list-item
            :id="user.id"
            :img="user.avatar"
            :username="user.name"
            :active="index === checkIndex"
            :showDel="true"
            @del="delFriend(user.id)"
            @click="choose(index, user.id)"
          ></list-item>
        </div>
      </el-scrollbar>
    </div>
    <div class="right">
      <Top></Top>
      <div class="main-view">
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
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import User from "@/mode/User";
import Top from "@/components/Top.vue";
import ListItem from "@/components/ListItem.vue";
import AddFriend from "@/components/AddFriend.vue";
import FriendApi from "@/api/FriendApi";
import AccountApi from "@/api/AccountApi";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "@/store/userStore";

const router = useRouter();
const route = useRoute();
const store = useUserStore();
const friends = ref(new Array<User>());
const checkIndex = ref(-1);
const showAddFriend = ref(false);

const id = store.getUser()?.id;
if (!id) {
  AccountApi.toLogin();
}

const choose = (index: number, id: string) => {
  checkIndex.value = index;
  router.push("/index/friend/" + id);
};

const close = () => {
  showAddFriend.value = false;
  loadFriends();
};

const loadFriends = () => {
  friends.value.splice(0, friends.value.length);
  if (typeof id !== "undefined") {
    FriendApi.friends().then((res: any) => {
      res.data.forEach((item: User) => {
        friends.value.push(item);
      });
    });
  }
};

const delFriend = (friendId: string) => {
  if (typeof id !== "undefined") {
    FriendApi.delete(friendId).then(() => {
      loadFriends();
    });
  }
};

loadFriends();
</script>

<style scoped lang="scss"></style>
