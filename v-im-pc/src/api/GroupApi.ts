import FetchRequest from "@/api/FetchRequest";

class GroupApi {
  //基础url
  static url = "/api/sys/groups";

  /**
   * 添加群组
   * @param name 群名称
   * @param avatar 群头像
   * @param needCheck 加群是否需要审核
   */
  static save(name: string, avatar: string, needCheck: string): Promise<any> {
    const data = {
      name: name,
      avatar: avatar,
      needCheck: needCheck,
    };
    return FetchRequest.post(this.url, JSON.stringify(data), true);
  }

  /**
   * 更新群组
   * @param id 群id
   * @param name 群名称
   * @param avatar 群头像
   * @param check 加群是否需要审核
   */
  static update(
    id: string,
    name: string,
    avatar: string,
    needCheck: string
  ): Promise<any> {
    const data = {
      name: name,
      avatar: avatar,
      needCheck: needCheck,
    };
    return FetchRequest.patch(this.url + "/" + id, JSON.stringify(data), true);
  }

  /**
   * 获取一个群的信息
   * @param id 群id
   */
  static get(id: string): Promise<any> {
    return FetchRequest.get(this.url + "/" + id, true);
  }

  /**
   * 查询当前用户的群组
   */
  static list(): Promise<any> {
    return FetchRequest.get(this.url, true);
  }

  /**
   * 获取一个群的所有用户
   * @param id 群id
   */
  static users(id: string): Promise<any> {
    return FetchRequest.get(this.url + "/" + id + "/users", true);
  }

  /**
   * 删除群
   * @param id 用户ID
   */
  static delete(id: string): Promise<any> {
    return FetchRequest.del(this.url + "/" + id, "", true);
  }

  /**
   * 添加群成员
   * @param id 群id
   * @param userId userId
   */
  static addUsers(id: string, userId: string[]): Promise<any> {
    return FetchRequest.post(
      this.url + "/" + id + "/users",
      JSON.stringify(userId),
      true
    );
  }

  /**
   * 删除群
   * @param id 用户ID
   * @param userId 用户ID
   */
  static deleteUser(id: string, userId: string): Promise<any> {
    return FetchRequest.del(this.url + "/" + id + "/users/" + userId, "", true);
  }
}

export default GroupApi;
