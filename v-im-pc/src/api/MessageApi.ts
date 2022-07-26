import FetchRequest from "@/api/FetchRequest";

class MessageApi {
  static url = "/api/sys/messages";

  static list(
    chatId: string,
    fromId: string,
    type: string,
    pageNum: number,
    pageSize: number
  ) {
    const param =
      "?chatId=" +
      chatId +
      "&fromId=" +
      fromId +
      "&type=" +
      type +
      "&pageNum=" +
      pageNum +
      "&pageSize=" +
      pageSize;
    return FetchRequest.get(this.url + param, true);
  }
}

export default MessageApi;
