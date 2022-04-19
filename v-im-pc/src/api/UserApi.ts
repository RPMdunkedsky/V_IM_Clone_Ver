import FetchRequest from "@/api/FetchRequest";
import User from "@/mode/User";

/**
 * 用户接口
 */
class UserApi {
  static url = "/api/sys/users";

  static getUser(id: string): Promise<any> {
    return FetchRequest.get(this.url + "/" + id, true);
  }

  static currentUser(): Promise<any> {
    return FetchRequest.get(this.url + "/" + "my", true);
  }

  static update(id: string, user: User): Promise<any> {
    user.id = id;
    return FetchRequest.put(this.url + "/" + id, JSON.stringify(user), true);
  }

  /**
   * search好友
   * @param mobile mobile
   */
  static search(mobile: string): Promise<any> {
    return FetchRequest.get(this.url + "/search?mobile=" + mobile, true);
  }
}

export default UserApi;
