import vimConfig from "@/config/VimConfig";

class Auth {
  static getToken = (): string => {
    return localStorage.getItem("access_token") ?? "";
  };

  static setToken = (token: string): void => {
    localStorage.setItem("access_token", token);
  };

  static setRefreshToken = (token: string): void => {
    localStorage.setItem("refresh_token", token);
  };

  static getRefreshToken = (): string => {
    return localStorage.getItem("refresh_token") ?? "";
  };

  static clearToken = (): void => {
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
  };

  static setIp = (ip: string): void => {
    localStorage.setItem("ip", ip);
  };

  static getIp = (): string => {
    return localStorage.getItem("ip") ?? vimConfig.host;
  };
}
export default Auth;
