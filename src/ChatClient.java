package src;

import javax.swing.JOptionPane;
import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String username;
    private ChatUI ui;
    private boolean connected = true;
    
    public ChatClient(String host, int port, String username, boolean isRegister) throws IOException {
        this.username = username;
        this.socket = new Socket(host, port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        
        if (isRegister) {
            handleRegister();
        }
    }
    
    public ChatClient(String host, int port, String username) throws IOException {
        this(host, port, username, false);
    }
    
    private void handleRegister() {
        try {
            sendMessage(new Message(username, "register", Message.MessageType.REGISTER));
            Message response = (Message) in.readObject();
            
            if (response.getType() == Message.MessageType.REGISTER_SUCCESS) {
                JOptionPane.showMessageDialog(null, "注册成功！请登录", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, response.getContent(), "注册失败", JOptionPane.ERROR_MESSAGE);
                connected = false;
            }
            socket.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "注册失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            connected = false;
        }
    }
    
    public boolean isConnected() {
        return connected;
    }
    
    public void setUI(ChatUI ui) {
        this.ui = ui;
    }
    
    public void start() {
        sendMessage(new Message(username, "login", Message.MessageType.LOGIN));
        new Thread(() -> {
            try {
                while (true) {
                    Message message = (Message) in.readObject();
                    
                    if (message.getType() == Message.MessageType.LOGIN_FAILED) {
                        JOptionPane.showMessageDialog(null, message.getContent(), "登录失败", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                        return;
                    }
                    
                    if (message.getType() == Message.MessageType.USER_LIST) {
                        ui.updateUserList(message.getContent());
                    } else {
                        ui.appendMessage(message);
                    }
                }
            } catch (Exception e) {
                System.err.println("连接已断开");
            }
        }).start();
    }
    
    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void disconnect() {
        try {
            sendMessage(new Message(username, "logout", Message.MessageType.LOGOUT));
            socket.close();
        } catch (IOException e) {}
    }
    
    public String getUsername() {
        return username;
    }
}
