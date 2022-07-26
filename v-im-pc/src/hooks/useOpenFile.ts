import { getCurrentInstance } from "vue";

const { proxy } = getCurrentInstance();
function openImageProxy(event: any) {
  event.preventDefault();
  if (event.target.nodeName === "IMG") {
    proxy.$winControl.default.openURL(event.target.src);
  } else if (
    event.target.className === "message-file" || event.target.nodeName.toUpperCase() === "A"
  ) {
    proxy.$winControl.default.openURL(event.target.href);
  }
}
export default openImageProxy;
