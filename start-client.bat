@echo off
chcp 65001 >nul
echo 启动新客户端...
start "客户端" java -cp bin src.ChatUI
echo 客户端已启动！
timeout /t 2
