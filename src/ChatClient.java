package src;

import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String username;
    private ChatUI ui;
    
    public ChatClient(String host, int port, String username) throws IOException {
        this.username = username;
        this.socket = new Socket(host, port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
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
