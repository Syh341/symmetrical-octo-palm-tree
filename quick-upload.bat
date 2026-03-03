@echo off
chcp 65001 >nul
echo ========================================
echo    快速上传到 GitHub
echo ========================================
echo.

echo 请先在 GitHub 创建仓库：
echo 1. 访问 https://github.com/new
echo 2. 仓库名：wechat-chat
echo 3. 点击 Create repository
echo 4. 复制仓库地址
echo.
echo 示例：https://github.com/Syh341/wechat-chat.git
echo.

set /p repo_url="请粘贴你的仓库地址: "

echo.
echo 开始上传...
echo.

git init
git add .
git commit -m "初始提交：微信聊天程序"
git remote add origin %repo_url%
git branch -M main
git push -u origin main

echo.
echo 如果要求输入密码，请使用 GitHub Token，不是密码！
echo Token 获取：https://github.com/settings/tokens
echo.
pause
