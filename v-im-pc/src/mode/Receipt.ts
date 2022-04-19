/**
 * 接受到的消息回执
 */
interface Receipt {
  chatId: string;
  userId: string;
  timestamp: number;
  type: string;
}
export default Receipt;
