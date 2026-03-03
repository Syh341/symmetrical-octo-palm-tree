package src;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 8888;
    private static Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();
    private static Map<String, ClientHandler> userMap = new ConcurrentHashMap<>();
    
    public static void main(String[] args) {
        System.out.println("服务器启动，端口: " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static class ClientHandler implements Runnable {
        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private String username;
        
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                
                while (true) {
                    Message message = (Message) in.readObject();
                    switch (message.getType()) {
                        case LOGIN:
                            handleLogin(message);
                            break;
                        case TEXT:
                            broadcastMessage(message);
                            break;
                        case LOGOUT:
                            handleLogout();
                            return;
                    }
                }
            } catch (Exception e) {
                handleLogout();
            }
        }
        
        private void handleLogin(Message message) {
            username = message.getSender();
            userMap.put(username, this);
            System.out.println(username + " 已连接");
            Message welcomeMsg = new Message("系统", username + " 加入了聊天室", Message.MessageType.TEXT);
            broadcastMessage(welcomeMsg);
            sendUserList();
        }
        
        private void handleLogout() {
            if (username != null) {
                userMap.remove(username);
                clients.remove(this);
                System.out.println(username + " 已断开");
                Message logoutMsg = new Message("系统", username + " 离开了聊天室", Message.MessageType.TEXT);
                broadcastMessage(logoutMsg);
                sendUserList();
            }
            try { socket.close(); } catch (IOException e) {}
        }
        
        private void broadcastMessage(Message message) {
            System.out.println(message);
            for (ClientHandler client : clients) {
                try {
                    client.out.writeObject(message);
                    client.out.flush();
                } catch (IOException e) {}
            }
        }
        
        private void sendUserList() {
            String userList = String.join(",", userMap.keySet());
            Message userListMsg = new Message("系统", userList, Message.MessageType.USER_LIST);
            for (ClientHandler client : clients) {
                try {
                    client.out.writeObject(userListMsg);
                    client.out.flush();
                } catch (IOException e) {}
            }
        }
    }
}
