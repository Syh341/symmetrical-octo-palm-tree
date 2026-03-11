# 微信聊天程序

一个基于 Java Socket 的多人在线聊天室，带有美观的登录界面。

## 功能特性

- ✅ 多人在线聊天
- ✅ 实时显示在线用户列表
- ✅ 用户加入/离开提醒
- ✅ 消息时间戳
- ✅ 美观的登录界面（带风景画）
- ✅ 类微信绿色主题



## 技术栈

- Java SE
- Socket 网络编程
- Swing GUI
- 多线程
- 对象序列化

## 快速开始

### 环境要求

- Java 8 或更高版本

### 运行步骤

1. 克隆项目
```bash
git clone https://github.com/你的用户名/wechat-chat.git
cd wechat-chat
```

2. 编译代码
```bash
javac -encoding UTF-8 -d bin src/*.java
```

3. 启动服务器
```bash
java -cp bin src.ChatServer
```

4. 启动客户端（新开终端）
```bash
java -cp bin src.ChatUI
```

### 使用批处理文件（Windows）

双击 `restart.bat` 即可自动编译并启动服务器和客户端。

## 项目结构

```
wechat-chat/
├── src/
│   ├── ChatUI.java          # 聊天界面（包含登录界面）
│   ├── ChatClient.java      # 客户端逻辑
│   ├── ChatServer.java      # 服务器端
│   └── Message.java         # 消息实体类
├── restart.bat              # 重启脚本
├── start-client.bat         # 启动新客户端
└── README.md
```

## 使用说明

1. 先启动服务器（默认端口 8888）
2. 启动一个或多个客户端
3. 在登录界面输入用户名
4. 开始聊天！

## 功能演示

- 输入消息后按回车或点击"发送"
- 左侧显示当前在线用户
- 所有用户都能实时收到消息
- 用户加入/离开时有系统提示


