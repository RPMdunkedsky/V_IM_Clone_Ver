let i = 1;
const winControl = {
  openURL: (url: string): void => {
    console.log("openURL");
    window.open(url);
  },
  flashFrame: (): void => {
    console.log("do nothing");
  },
  min: (): void => {
    alert("方法需要自定义");
  },
  max: (): void => {
    if (i % 2 === 0) {
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // @ts-ignore
      document.getElementById("app").style.cssText =
        "width:1000px;height:600px;margin:30px auto";
    } else {
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // @ts-ignore
      document.getElementById("app").style.cssText =
        "width:100%;height:100%;margin:0";
    }
    i++;
  },
  flashIcon: (): void => {
    console.log("flashIcon");
  },
  clearFlashIcon: (): void => {
    console.log("clearFlashIcon");
  },
  close: (): void => {
    alert("方法需要自定义");
  },
};
export default winControl;
