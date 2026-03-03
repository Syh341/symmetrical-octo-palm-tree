@echo off
chcp 65001 >nul
echo ========================================
echo    自动推送到 GitHub
echo ========================================
echo.

echo 正在推送到：
echo https://github.com/Syh341/symmetrical-octo-palm-tree
echo.

echo 如果弹出登录窗口，请在浏览器中登录 GitHub
echo.

git push -u origin main --force

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo    ✅ 上传成功！
    echo ========================================
    echo.
    echo 访问查看你的代码：
    echo https://github.com/Syh341/symmetrical-octo-palm-tree
    echo.
) else (
    echo.
    echo ❌ 上传失败
    echo.
    echo 可能的原因：
    echo 1. 需要在浏览器中登录 GitHub
    echo 2. 需要使用 Token 作为密码
    echo.
    echo 获取 Token：
    echo https://github.com/settings/tokens
    echo.
)

pause
