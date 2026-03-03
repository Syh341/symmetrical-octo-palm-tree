@echo off
chcp 65001 >nul
echo ========================================
echo    上传项目到 GitHub
echo ========================================
echo.

echo 请确保你已经：
echo 1. 安装了 Git
echo 2. 在 GitHub 创建了仓库
echo 3. 配置了 Git 用户名和邮箱
echo.
pause

echo.
echo [1/5] 初始化 Git 仓库...
git init
if %errorlevel% neq 0 (
    echo 错误：Git 未安装或初始化失败
    pause
    exit /b 1
)

echo.
echo [2/5] 添加所有文件...
git add .

echo.
echo [3/5] 提交到本地仓库...
git commit -m "初始提交：微信聊天程序"

echo.
echo [4/5] 请输入你的 GitHub 仓库地址
echo 格式：https://github.com/用户名/仓库名.git
set /p repo_url="仓库地址: "

echo.
echo [5/5] 关联远程仓库并推送...
git remote add origin %repo_url%
git branch -M main
git push -u origin main

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo    上传成功！
    echo ========================================
    echo.
    echo 访问你的 GitHub 仓库查看项目
) else (
    echo.
    echo 上传失败，可能需要：
    echo 1. 检查仓库地址是否正确
    echo 2. 检查网络连接
    echo 3. 配置 GitHub 身份验证
)

echo.
pause
