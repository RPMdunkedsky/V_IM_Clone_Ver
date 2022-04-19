import FetchRequest from "@/api/FetchRequest";

class DeptApi {
  static url = "/api/sys/depts";

  /**
   * 获取父部门
   * @param deptId 部门ID
   */
  static parent(deptId: string): Promise<any> {
    return FetchRequest.get(this.url + "/parent?deptId=" + deptId, true);
  }

  /**
   * 获取所有部门
   */
  static list(): Promise<any> {
    return FetchRequest.get(this.url, true);
  }

  /**
   * 获取部门
   * @param id 部门ID
   */
  static get(id: string): Promise<any> {
    return FetchRequest.get(this.url + "/" + id, true);
  }

  /**
   * 获取部门用户
   * @param deptId 部门ID
   */
  static users(deptId: string): Promise<any> {
    return FetchRequest.get(this.url + "/" + deptId + "/users", true);
  }

  /**
   * 获取部门人数
   */
  static count(): Promise<any> {
    return FetchRequest.get(this.url + "/count", true);
  }
}

export default DeptApi;
