import FaceUtils from "@/utils/FaceUtils";

const ChatUtils = {
  //信息的类型 MSG_PING 心跳 、MSG_READY 链接就绪  MSG_MESSAGE 消息
  MessageInfoType: {
    MSG_PING: "0",
    MSG_READY: "1",
    MSG_MESSAGE: "2",
    //读取消息
    MSG_READ: "3",
  },

  ErrorType: {
    TIMEOUT_ERROR: 9, //超时
    TOKEN_ERROR: 401, //token 失效错误
    PARAM_ERROR: 400, //参数错误
    FLUSH_TOKEN_ERROR: 7, //刷新token错误
    SERVER_ERROR: 500, //服务器错误
    NET_ERROR: "TypeError: Failed to fetch", //网络链接不通
  },

  /**
   * 图片加载完成，聊天对话框scroll拉到最下
   * @param id 容器id
   */
  imageLoad(id: string): void {
    this.scrollBottom(id);
    const messageBox = document.getElementById(id);
    if (messageBox) {
      const images = messageBox.getElementsByTagName("img");
      if (images) {
        const arr: string[] = [];
        for (let i = 0; i < images.length; i++) {
          arr[i] = images[i].src;
        }
        this.preloadImages(arr).finally(() => {
          this.scrollBottom(id);
        });
      }
    }
  },

  /**
   * 加载图片
   * @param arr 图片路径[]
   */
  preloadImages(arr: string[]): Promise<any> {
    let loadedImage = 0;
    return new Promise((resolve) => {
      for (let i = 0; i < arr.length; i++) {
        const image = new Image();
        image.src = arr[i];
        image.onload = () => {
          loadedImage++;
          if (loadedImage === arr.length) {
            resolve("");
          }
        };
        image.onerror = () => {
          loadedImage++;
          if (loadedImage === arr.length) {
            resolve("");
          }
        };
      }
    });
  },
  /**
   * 滚动条到最下方
   * @param id 容器id
   */
  scrollBottom(id: string): void {
    const div = document.getElementById(id);
    if (div) {
      div.scrollTop = div.scrollHeight;
    }
  },
  /**
   * 消息内容转换
   * @param content 要转换的内容
   */
  transform: (content: string) => {
    // 支持的html标签
    const html = (end?: string) => {
      return new RegExp(
        "\\n*\\[" +
          (end || "") +
          "(code|pre|div|span|p|table|thead|th|tbody|tr|td|ul|li|ol|li|dl|dt|dd|h2|h3|h4|h5)([\\s\\S]*?)]\\n*",
        "g"
      );
    };
    const fa = FaceUtils.faces();
    if (content) {
      content = content
        .replace(/&(?!#?[a-zA-Z0-9]+;)/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/'/g, "&#39;")
        .replace(/"/g, "&quot;") // XSS
        .replace(/@(\S+)(\s+?|$)/g, '@<a href="javascript:;">$1</a>$2')

        .replace(/face\[([^\s\\[\]]+?)]/g, function (face: string) {
          // 转义表情
          const alt = face.replace(/^face/g, "");
          return (
            '<img alt="' +
            fa.get(alt) +
            '" title="' +
            fa.get(alt) +
            '" src="' +
            fa.get(alt) +
            '">'
          );
        })
        .replace(/img\[([^\s]+?)]/g, function (img: string) {
          // 转义图片
          const href = img.replace(/(^img\[)|(]$)/g, "");
          return (
            '<img class="message-img" src="' +
            href +
            '" alt="消息图片不能加载">'
          );
        })
        .replace(/file\([\s\S]+?\)\[[\s\S]*?]/g, function (str: string) {
          // 转义文件
          const href = (str.match(/file\(([\s\S]+?)\)\[/) || [])[1];
          const text = (str.match(/\)\[([\s\S]*?)]/) || [])[1];
          if (!href) return str;
          return (
            '<a class="message-file" href="' +
            href +
            '"><i class="cuIcon-file file-icon"></i>' +
            (text || href) +
            "</a>"
          );
        })

        .replace(html(), "<$1 $2>")
        .replace(html("/"), "</$1>") // 转移HTML代码
        .replace(/\n/g, "<br>"); // 转义换行
    }
    return content;
  },
};
export default ChatUtils;
