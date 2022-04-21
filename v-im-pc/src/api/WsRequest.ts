import ChatUtils from "@/utils/ChatUtils";
import Message from "@/mode/Message";
import Receipt from "@/mode/Receipt";

const ready = '{"code":' + ChatUtils.MessageInfoType.MSG_READY + "}";
const ping = '{"code":' + ChatUtils.MessageInfoType.MSG_PING + "}";

class WsRequest {
  status: string | null;
  lockReconnect: boolean;
  url: string | undefined;
  isConnected: boolean;
  //是否主动关闭
  closeByUser: boolean;
  timeout: number;
  timeoutTask: NodeJS.Timeout | null;
  reconnectTimeoutTask: NodeJS.Timeout | null;
  socket: WebSocket | null;

  constructor() {
    this.status = null; // websocket是否关闭
    this.lockReconnect = false; //避免重复连接
    this.url = "";
    this.isConnected = false;
    //是否主动关闭
    this.closeByUser = false;
    //心跳检测
    this.timeout = 5000; //多少秒执行检测
    //检测服务器端是否还活着
    this.timeoutTask = null;
    //重连之后多久再次重连
    this.reconnectTimeoutTask = null;
    this.socket = null;
  }

  public init(url?: string): void {
    console.log("初始化websocket");
    this.url = url;
    if (this.url) {
      this.socket = new WebSocket(this.url);
      this.socket.onopen = () => {
        //告知服务器准备就绪
        this.send(ready);
        // 清除重连定时器
        if (this.reconnectTimeoutTask) {
          clearTimeout(this.reconnectTimeoutTask);
        }
        // 开启检测
        this.reset();
      };

      // 如果希望websocket连接一直保持，在close或者error上绑定重新连接方法。
      this.socket.onclose = () => {
        if(!this.closeByUser){
          this.isConnected = false;
          this.reconnect();
        }
      };

      this.socket.onerror = () => {
        this.isConnected = false;
        this.reconnect();
      };

      this.socket.onmessage = (event: MessageEvent) => {
        const data = event.data;
        const sendInfo = JSON.parse(data);
        // 真正的消息类型
        if (sendInfo.code === ChatUtils.MessageInfoType.MSG_MESSAGE) {
          this.onmessage(sendInfo.message);
        }
        //接受任何消息都说明当前连接是正常的
        this.reset();
      };
    } else {
      console.error("请提供链接地址...");
    }
  }

  /**
   * 发送状态
   * @param value
   */
  send(value: string): void {
    if (this.socket?.readyState === 1) {
      this.socket.send(value);
    } else {
      this.reset();
      this.reconnect();
    }
  }

  /**
   * 收到消息，这里会被重写覆盖
   * @param message 消息
   */
  onmessage = (message: Message): void => {
    console.log(message.id);
  };

  /**
   * 发送真正的聊天消息
   * @param message 消息
   */
  sendMessage(message: Message): void {
    const sendInfo = {
      code: ChatUtils.MessageInfoType.MSG_MESSAGE,
      message: message,
    };
    this.send(JSON.stringify(sendInfo));
  }

  /**
   * 发送已读取消息
   * @param receipt 消息读取回执
   */
  sendRead(receipt: Receipt): void {
    const sendInfo = {
      code: ChatUtils.MessageInfoType.MSG_READ,
      message: receipt,
    };
    this.send(JSON.stringify(sendInfo));
  }

  /**
   *  reset和start方法主要用来控制心跳的定时。
   */
  reset(): void {
    // 清除定时器重新发送一个心跳信息
    if (this.timeoutTask) {
      clearTimeout(this.timeoutTask);
    }
    this.timeoutTask = setTimeout(() => {
      //这里发送一个心跳，后端收到后，返回一个心跳消息，
      //onmessage拿到返回的心跳就说明连接正常
      this.send(ping);
    }, this.timeout);
  }

  // 重连
  reconnect(): void {
    console.log("lockReconnect", this.lockReconnect);
    // 防止多个方法调用，多处重连
    if (this.lockReconnect) {
      return;
    }
    this.lockReconnect = true;

    //没连接上会一直重连，设置延迟避免请求过多
    this.reconnectTimeoutTask = setTimeout(() => {
      // 重新连接
      this.init(this.url);
      this.lockReconnect = false;
    }, 4000);
  }

  // 手动关闭
  close(): void {
    //主动关闭
    if (this.timeoutTask) {
      clearTimeout(this.timeoutTask);
    }
    if (this.reconnectTimeoutTask) {
      clearTimeout(this.reconnectTimeoutTask);
    }
    this.closeByUser = true;
    if (this.socket) {
      this.socket.close();
    }
  }
}

export default WsRequest;
