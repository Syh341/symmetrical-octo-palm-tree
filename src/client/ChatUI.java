package client;

import common.Message;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class ChatUI extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JList<String> userList;
    private DefaultListModel<String> userListModel;
    private ChatClient client;
    
    public ChatUI() {
        setTitle("微信聊天");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        setupLayout();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (client != null) {
                    client.disconnect();
                }
            }
        });
    }
    
    private void initComponents() {
        // 聊天区域
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        
        // 输入框
        inputField = new JTextField();
        inputField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        inputField.addActionListener(e -> sendMessage());
        
        // 发送按钮
        sendButton = new JButton("发送");
        sendButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        sendButton.setBackground(new Color(7, 193, 96));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.addActionListener(e -> sendMessage());
        
        // 在线用户列表
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        userList.setBorder(new EmptyBorder(5, 5, 5, 5));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(5, 5));
        
        // 左侧：在线用户
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(150, 0));
        leftPanel.setBorder(BorderFactory.createTitledBorder("在线用户"));
        leftPanel.add(new JScrollPane(userList), BorderLayout.CENTER);
        
        // 中间：聊天区域
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JScrollPane chatScroll = new JScrollPane(chatArea);
        chatScroll.setBorder(BorderFactory.createTitledBorder("聊天记录"));
        centerPanel.add(chatScroll, BorderLayout.CENTER);
        
        // 底部：输入区域
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        
        centerPanel.add(inputPanel, BorderLayout.SOUTH);
        
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void sendMessage() {
        String text = inputField.getText().trim();
        if (!text.isEmpty() && client != null) {
            Message message = new Message(client.getUsername(), text, Message.MessageType.TEXT);
            client.sendMessage(message);
            inputField.setText("");
        }
    }
    
    public void appendMessage(Message message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message.toString() + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }
    
    public void updateUserList(String users) {
        SwingUtilities.invokeLater(() -> {
            userListModel.clear();
            for (String user : users.split(",")) {
                if (!user.isEmpty()) {
                    userListModel.addElement("👤 " + user);
                }
            }
        });
    }
    
    public void setClient(ChatClient client) {
        this.client = client;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 显示自定义登录对话框
            String username = LoginDialog.showLoginDialog();
            
            if (username == null || username.trim().isEmpty()) {
                System.exit(0);
            }
            
            try {
                ChatClient client = new ChatClient("localhost", 8888, username.trim());
                ChatUI ui = new ChatUI();
                ui.setClient(client);
                client.setUI(ui);
                client.start();
                ui.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "无法连接到服务器！\n请确保服务器已启动。", "连接错误", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}
