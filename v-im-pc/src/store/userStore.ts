import { defineStore } from "pinia";
import User from "@/mode/User";

export interface State {
  user: User | null;
}

// defineStore 调用后返回一个函数，调用该函数获得 Store 实体
export const useUserStore = defineStore({
  // id: 必须的，在所有 Store 中唯一
  id: "user_store",
  // state: 返回对象的函数
  state: (): State => ({
    user: null,
  }),
  // 开启数据缓存
  persist: {
    enabled: true,
    strategies: [
      {
        key: "user",
        storage: localStorage,
        paths: ["user"],
      },
    ],
  },
  actions: {
    setUser(user: User): void {
      this.user = user;
    },
    getUser(): User | null {
      return this.user;
    },
  },
});
