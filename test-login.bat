@echo off
chcp 65001 >nul
echo ====================================
echo   微信聊天系统 - 测试登录
echo ====================================
echo.
echo 编译代码...
javac -encoding UTF-8 -d bin src/*.java
if %errorlevel% neq 0 (
    echo [错误] 编译失败！
    pause
    exit /b 1
)
echo [成功] 编译完成！
echo.
echo 启动客户端...
echo 提示：使用初始用户 123, 321, 213, 231, 132, 312 登录
echo       或点击"注册新用户"按钮注册新账号
echo.
java -cp bin src.ChatUI
pause
