import FetchRequest from "@/api/FetchRequest";
import vimConfig from "@/config/VimConfig";
import router from "@/router";
import { ElMessage } from "element-plus";

class AccountApi {
  /**
   * 登录
   * @param username 用户名
   * @param password 密码
   */
  static login = (username: string, password: string): Promise<any> => {
    const param: FormData = new FormData();
    param.set("client_id", vimConfig.clientId);
    param.set("client_secret", vimConfig.clientSecret);
    param.set("grant_type", "password");
    param.set("scope", "server");
    param.set("username", username.trim());
    param.set("password", password.trim());
    const config: RequestInit = {
      method: "POST",
      mode: "cors",
      headers: {
        Accept: "application/json",
      },
      body: param,
    };
    return fetch(FetchRequest.getHost() + "/oauth/token", config).then(
      (res) => {
        if (res.status === 200) {
          return res.json();
        } else {
          return Promise.reject("认证失败");
        }
      }
    );
  };

  static toLogin = (): void => {
    router.push("/").catch(() => {
      ElMessage.error("无法跳转到登录界面");
    });
  };
}
export default AccountApi;
