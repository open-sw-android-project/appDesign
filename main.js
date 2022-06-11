const {app, BrowserWindow} = require("electron");

const createWindow = () => {
    //윈도우 화면 생성
    const window = new BrowserWindow({
        width:1200,
        height: 800,
    });
    window.loadFile("index.html");

    //메뉴바 생성
}

app.whenReady().then(() => {
    createWindow();
});