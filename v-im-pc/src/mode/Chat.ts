interface Chat {
  id: string;
  name: string;
  avatar: string;
  type: string;
  lastMessage: string;
  unreadCount: number;
  //是否在加载中（历史记录）
  isLoading: boolean;
  //历史记录加载标志，每个chat只加载一次
  loaded: boolean;
}

export default Chat;
