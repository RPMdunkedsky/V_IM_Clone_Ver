"use strict";

import { app, protocol, BrowserWindow, Tray, Menu, ipcMain } from "electron";
import { createProtocol } from "vue-cli-plugin-electron-builder/lib";
import installExtension, { VUEJS3_DEVTOOLS } from "electron-devtools-installer";
import path from "path";

const isDevelopment = process.env.NODE_ENV !== "production";
//刷新托盘定时器
let flashIconTimer: any;

// Scheme must be registered before the app is ready
protocol.registerSchemesAsPrivileged([
  { scheme: "app", privileges: { secure: true, standard: true } },
]);

async function createWindow() {
  // Create the browser window.
  const win = new BrowserWindow({
    useContentSize: true,
    width: 1000,
    height: 600,
    frame: false,
    webPreferences: {
      contextIsolation: !process.env.ELECTRON_NODE_INTEGRATION,
      webSecurity: false,
      nodeIntegration: true, // 解决require is not defined问题
      webviewTag: true, // 解决webview无法显示问题
    },
  });

  if (process.env.WEBPACK_DEV_SERVER_URL) {
    await win.loadURL(process.env.WEBPACK_DEV_SERVER_URL as string);
    if (!process.env.IS_TEST) win.webContents.openDevTools();
  } else {
    createProtocol("app");
    // Load the index.html when not in development
    await win.loadURL("app://./index.html");
  }

  ipcMain.on("min", () => {
    win.minimize();
  });

  ipcMain.on("max", () => {
    if (win.isMaximized()) {
      win.unmaximize();
    } else {
      win.maximize();
    }
  });

  // 只是隐藏任务栏
  ipcMain.on("close", () => {
    hideMain(win);
  });

  // 闪烁任务栏
  ipcMain.on("flashFrame", () => {
    win.flashFrame(true);
  });

  let iconPath = "/static/icon.ico";
  let emptyIconPath = "/static/empty.ico";
  if (process.env.NODE_ENV === "development") {
    iconPath = "../public/static/icon.ico";
    emptyIconPath = "../public/static/empty.ico";
  }
  const appIcon = createTray(win, iconPath);
  // 闪烁任务栏
  ipcMain.on("flashIcon", () => {
    if (!win.isVisible()) {
      clearFlashIconTimer();
      let count = 0;
      flashIconTimer = setInterval(function () {
        count++;
        if (count % 2 === 0) {
          appIcon.setImage(path.join(__dirname, emptyIconPath));
        } else {
          appIcon.setImage(path.join(__dirname, iconPath));
        }
      }, 500);
    }
  });

  ipcMain.on("clearFlashIcon", () => {
    clearFlashIconTimer();
    appIcon.setImage(path.join(__dirname, iconPath));
  });
}

/**
 * 创建托盘图标
 * @param win
 * @param iconPath
 */
function createTray(win: BrowserWindow, iconPath: string) {
  // 托盘
  const appIcon = new Tray(path.join(__dirname, iconPath));
  const contextMenu = Menu.buildFromTemplate([
    {
      label: "显示",
      click: function () {
        showMain(win, appIcon, iconPath);
      },
    },
    {
      label: "退出",
      click: function () {
        app.quit();
      },
    },
  ]);
  appIcon.setToolTip("v-im");
  appIcon.setContextMenu(contextMenu);
  appIcon.on("click", function () {
    showMain(win, appIcon, iconPath);
  });

  return appIcon;
}

/**
 * 展示窗口，打开任务栏
 */
function showMain(win: BrowserWindow, appIcon: Tray, iconPath: string) {
  console.log("showMain");
  console.log("flashIconTimer", flashIconTimer);
  win.setSkipTaskbar(false);
  win.show();
  clearFlashIconTimer();
  appIcon.setImage(path.join(__dirname, iconPath));
}

/**
 * 清除图片闪烁的定时器
 */
function clearFlashIconTimer() {
  if (flashIconTimer) {
    clearInterval(flashIconTimer);
  }
}

/**
 * 隐藏窗口，隐藏任务栏
 */
function hideMain(win: BrowserWindow) {
  win.setSkipTaskbar(true);
  win.hide();
}

app.on("window-all-closed", () => {
  // On macOS it is common for applications and their menu bar
  // to stay active until the user quits explicitly with Cmd + Q
  if (process.platform !== "darwin") {
    app.quit();
  }
});

app.on("activate", () => {
  if (BrowserWindow.getAllWindows().length === 0) createWindow();
});

app.on("ready", async () => {
  if (isDevelopment && !process.env.IS_TEST) {
    try {
      await installExtension(VUEJS3_DEVTOOLS);
    } catch (e) {
      console.error("Vue Devtools failed to install:", e.toString());
    }
  }
  await createWindow();
});

if (isDevelopment) {
  if (process.platform === "win32") {
    process.on("message", (data) => {
      if (data === "graceful-exit") {
        app.quit();
      }
    });
  } else {
    process.on("SIGTERM", () => {
      app.quit();
    });
  }
}
