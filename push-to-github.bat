@echo off
chcp 65001 >nul
echo ========================================
echo    推送代码到 GitHub
echo ========================================
echo.
echo 仓库：symmetrical-octo-palm-tree
echo 用户：Syh341
echo.
echo 正在推送...
echo.

git push -u origin main

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo    上传成功！
    echo ========================================
    echo.
    echo 访问查看：
    echo https://github.com/Syh341/symmetrical-octo-palm-tree
) else (
    echo.
    echo 上传失败！
    echo.
    echo 可能需要：
    echo 1. 在弹出窗口中登录 GitHub
    echo 2. 或使用 Token 作为密码
    echo.
    echo Token 获取地址：
    echo https://github.com/settings/tokens
)

echo.
pause
