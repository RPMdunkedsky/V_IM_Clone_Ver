import { shell, ipcRenderer } from "electron";
const winControl = {
  openURL: (url: string): void => {
    shell.openExternal(url).catch((err) => {
      console.error(err);
    });
  },
  flashFrame: (): void => {
    ipcRenderer.send("flashFrame");
  },
  min: (): void => {
    ipcRenderer.send("min");
  },
  max: (): void => {
    ipcRenderer.send("max");
  },
  close: (): void => {
    ipcRenderer.send("close");
  },
  flashIcon: (): void => {
    ipcRenderer.send("flashIcon");
  },
  clearFlashIcon: (): void => {
    ipcRenderer.send("clearFlashIcon");
  },
};

export default winControl;
