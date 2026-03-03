@echo off
chcp 65001 >nul
echo ========================================
echo    重启聊天程序
echo ========================================
echo.

echo [1/5] 停止所有 Java 进程...
taskkill /F /IM java.exe >nul 2>&1
if %errorlevel% equ 0 (
    echo      已停止运行中的 Java 程序
) else (
    echo      没有运行中的 Java 程序
)
echo.

echo [2/5] 清理旧的编译文件...
if exist bin (
    rmdir /S /Q bin
    echo      已删除 bin 目录
)
mkdir bin
echo      已创建新的 bin 目录
echo.

echo [3/5] 重新编译代码...
javac -encoding UTF-8 -d bin src/*.java
if %errorlevel% neq 0 (
    echo      编译失败！请检查代码错误
    pause
    exit /b 1
)
echo      编译成功！
echo.

echo [4/5] 启动服务器...
start "聊天服务器" java -cp bin src.ChatServer
echo      服务器已启动
echo.

echo [5/5] 启动客户端...
timeout /t 2 /nobreak >nul
start "客户端" java -cp bin src.ChatUI
echo      客户端已启动
echo.

echo ========================================
echo    程序启动完成！
echo ========================================
echo.
echo 提示：
echo - 可以多次运行此脚本启动更多客户端
echo - 关闭所有窗口后再次运行此脚本重启
echo.
pause
