import FetchRequest from "@/api/FetchRequest";

class FriendApi {
  static url = "/api/sys/friends";

  /**
   * 获取用户的所有好友
   * @param id 用户ID
   */
  static friends(): Promise<any> {
    return FetchRequest.get(this.url + "?state=0", true);
  }

  /**
   * 添加好友
   * @param friendId 好友ID
   */
  static add(friendId: string): Promise<any> {
    return FetchRequest.post(this.url + "/add", friendId, true);
  }

  /**
   * 审核好友
   * @param userId 用户ID
   * @param friendId 好友
   * @param state 状态
   */
  static check(userId: string, friendId: string, state: string): Promise<any> {
    const data = {
      friendId: friendId.toString(),
      state: state.toString(),
    };
    return FetchRequest.patch(
      this.url + "/" + userId,
      JSON.stringify(data),
      true
    );
  }

  /**
   * 删除好友
   * @param userId 用户ID
   * @param friendId 好友ID
   */
  static delete(friendId: string): Promise<any> {
    return FetchRequest.del(this.url + "/delete", friendId, true);
  }
}

export default FriendApi;
