import { defineStore } from "pinia";
import Group from "@/mode/Group";
import GroupApi from "@/api/GroupApi";

export interface State {
  groupList: Array<Group>;
  checkIndex: number;
}

export const useGroupStore = defineStore({
  id: "group_store",
  state: (): State => ({
    groupList: [],
    checkIndex: -1,
  }),
  actions: {
    getGroupList(): Array<Group> {
      return this.groupList;
    },
    setGroupList(groupList: Array<Group>): Array<Group> {
      return (this.groupList = groupList);
    },
    reloadGroupList(): Promise<any> {
      return GroupApi.list().then((res) => {
        this.groupList = res.data;
        return Promise.resolve(1);
      });
    },
  },
});
