@echo off
chcp 65001 >nul
echo ====================================
echo   微信聊天系统 - 完整测试
echo ====================================
echo.
echo [1/3] 编译代码...
javac -encoding UTF-8 -d bin src/*.java
if %errorlevel% neq 0 (
    echo [错误] 编译失败！
    pause
    exit /b 1
)
echo [成功] 编译完成！
echo.

echo [2/3] 检查服务器...
netstat -ano | findstr ":8888" >nul
if %errorlevel% equ 0 (
    echo [提示] 服务器已在运行
) else (
    echo [启动] 正在启动服务器...
    start "聊天服务器" java -cp bin src.ChatServer
    timeout /t 2 /nobreak >nul
    echo [成功] 服务器已启动
)
echo.

echo [3/3] 启动客户端...
echo.
echo ====================================
echo   使用说明：
echo   - 初始用户：123, 321, 213, 231, 132, 312
echo   - 可以点击"注册新用户"注册新账号
echo   - 可以多次运行此脚本打开多个客户端
echo ====================================
echo.
java -cp bin src.ChatUI
