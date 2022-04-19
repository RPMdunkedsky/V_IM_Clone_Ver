import FetchRequest from "@/api/FetchRequest";
import * as stream from "stream";

interface loginBody {
  username: string;
  password: string;
  code: string;
  uuid: string;
}
// 登录方法
export const login = (
  username: string,
  password: string,
  code: string,
  uuid: string
) => {
  const data: loginBody = {
    username,
    password,
    code,
    uuid,
  };
  return FetchRequest.post("/login", JSON.stringify(data), false);
};

// 注册方法
export const register = (data: loginBody) =>
  FetchRequest.post("/request", JSON.stringify(data), false);

// 获取用户详细信息
export const getInfo = () => {
  return FetchRequest.get("/getInfo", false);
};

// 退出方法
export const logout = () => FetchRequest.post("/logout", "", true);

// 获取验证码
export const getCodeImg = () => FetchRequest.get("/captchaImage", false);
