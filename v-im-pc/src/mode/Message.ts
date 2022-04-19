interface Message {
  //消息id,雪花id,有序增长
  id?: string;
  //聊天室id
  chatId: string;
  //消息发送人
  fromId: string;
  //发送人头像
  avatar: string;
  //发送人名称
  name: string;
  //是否是本人
  mine: boolean;
  //消息内容
  content: string;
  //消息时间
  timestamp: number;
  //消息类型：私聊|群聊
  type: string;
}
export default Message;
