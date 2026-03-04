@echo off
chcp 65001
echo 编译中...
javac -encoding UTF-8 -d bin src/*.java
if %errorlevel% neq 0 (
    echo 编译失败！
    pause
    exit /b 1
)

echo.
echo 启动服务器...
start "聊天服务器" java -cp bin src.ChatServer

timeout /t 2 /nobreak >nul

echo 启动客户端...
start "客户端1" java -cp bin src.ChatUI

echo.
echo 程序