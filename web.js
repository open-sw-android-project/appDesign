const express = require('express');
const app = express();
var path = require('path');
const port = 3000;

const fs = require("fs");
const WEBPATH = "./";

//서버 접속
const http = require('http');
const hostname = '192.168.56.1';//ip 바꾸기 (조수현) 192.168.0.184/index.php

app.use(express.static(__dirname + '/public'));
app.use(express.static(__dirname + '/views'));

//const __dirname = "C:\\Users\\82104\\Desktop\\qr_attend";


app.listen(port, () => {
    console.log(`server is listening at localhost:${port}`);
});

app.get("/", (req, res) => {
    fs.readFile(`${WEBPATH}/index.html`, (error, data) => {
        if (error) {
            console.log(error);
            return res.status(500).send("<h1>500 Error</h1>");
        }
        res.writeHead(200, { "Content-Type": "text/html" });
        res.end(data);
    });
});

app.get("/qr.html", (req, res) => {
    fs.readFile(`${WEBPATH}/qr.html`, (error, data) => {
        if (error) {
            console.log(error);
            return res.status(500).send("<h1>500 Error</h1>");
        }
        res.writeHead(200, { "Content-Type": "text/html" });
        res.end(data);
    });
});

// app.get('/', (req, res) => {
//     res.readFile("../views/index.html")
// });

