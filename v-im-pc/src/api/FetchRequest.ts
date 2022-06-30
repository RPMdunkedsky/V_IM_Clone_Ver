import Auth from "@/api/Auth";
import { ElMessage } from "element-plus";
import vimConfig from "@/config/VimConfig";
import AccountApi from "@/api/AccountApi";

/**
 * 请求类，支持无感刷新token
 * @author 乐天
 */
class FetchRequest {
  tempRequests: any[];
  isRefreshing: boolean;
  private static instance: FetchRequest;

  private constructor() {
    this.tempRequests = [];
    this.isRefreshing = false;
  }

  /**
   * 单例构造方法，构造一个广为人知的接口，供用户对该类进行实例化
   * @returns {FetchRequest}
   */
  static getInstance() {
    if (!this.instance) {
      this.instance = new FetchRequest();
    }
    return this.instance;
  }

  /**
   * 请求方法
   * @param url 请求路径
   * @param params 参数
   * @param method 方法
   * @param isNeedToken 是否需要token
   */
  request = (
    url: string,
    params: string,
    method: string,
    isNeedToken = false
  ) => {
    const header: HeadersInit = {
      Accept: "application/json",
      "Content-Type": "application/json",
    };

    const token = Auth.getToken();
    if (isNeedToken && token) {
      header.Authorization = "Bearer " + token;
    }

    const config: RequestInit = {
      method: method,
      mode: "cors",
      headers: header,
    };

    if (method !== "GET") {
      config.body = params;
    }

    return fetch(this.getHost() + url, config).then((response) => {
      return this.check(response, url, params, method, isNeedToken);
    });
  };

  /**
   * 检查请求返回值，如果token失效,执行刷新方法
   * @param response 请求响应数据
   * @param url 请求路径
   * @param params 请求参数
   * @param method 方法
   * @param isNeedToken 是否需要token
   */
  check = (
    response: Response,
    url: string,
    params: string,
    method: string,
    isNeedToken = false
  ) => {
    //token 失效
    if (response.status === 200) {
      return response
        .json()
        .then((res) => {
          if (res.code === 401) {
            AccountApi.toLogin();
          } else if (res.code !== 200) {
            ElMessage.error(res.msg);
            return Promise.reject(res);
          } else {
            return Promise.resolve(res);
          }
        })
        .catch((err) => {
          return Promise.reject(err);
        });
    } else {
      ElMessage.error("请求出错，状态码：" + response.status);
      return Promise.reject("请求出错");
    }
  };

  getHost = (): string => {
    return (
      vimConfig.httProtocol + "://" + Auth.getIp() + ":" + vimConfig.httPort
    );
  };

  // 有些 api 并不需要用户授权使用，则无需携带 access_token；默认不携带，需要传则设置第三个参数为 true
  get = (url: string, isNeedToken = false) => {
    return this.request(url, "", "GET", isNeedToken);
  };

  post = (url: string, params: string, isNeedToken = false) => {
    return this.request(url, params, "POST", isNeedToken);
  };

  put = (url: string, params: string, isNeedToken = false) => {
    return this.request(url, params, "PUT", isNeedToken);
  };

  del = (url: string, params: string, isNeedToken = false) => {
    return this.request(url, params, "DELETE", isNeedToken);
  };

  patch = (url: string, params: string, isNeedToken = false) => {
    return this.request(url, params, "PATCH", isNeedToken);
  };
}

export default FetchRequest.getInstance();
