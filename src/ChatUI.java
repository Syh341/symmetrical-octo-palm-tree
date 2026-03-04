package src;

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
                if (client != null) client.disconnect();
            }
        });
    }
    
    private void initComponents() {
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        
        inputField = new JTextField();
        inputField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        inputField.addActionListener(e -> sendMessage());
        
        sendButton = new JButton("发送");
        sendButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        sendButton.setBackground(new Color(7, 193, 96));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.addActionListener(e -> sendMessage());
        
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        userList.setBorder(new EmptyBorder(5, 5, 5, 5));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(5, 5));
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(150, 0));
        leftPanel.setBorder(BorderFactory.createTitledBorder("在线用户"));
        leftPanel.add(new JScrollPane(userList), BorderLayout.CENTER);
        
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane chatScroll = new JScrollPane(chatArea);
        chatScroll.setBorder(BorderFactory.createTitledBorder("聊天记录"));
        centerPanel.add(chatScroll, BorderLayout.CENTER);
        
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
        // 设置窗口标题显示用户名
        setTitle("微信聊天 - " + client.getUsername());
    }
    
    // 显示登录对话框
    private static String showLoginDialog() {
        JDialog dialog = new JDialog((Frame)null, "欢迎登录", true);
        dialog.setSize(450, 550);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        
        // 风景画面板
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // 渐变背景
                GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 206, 250), 0, getHeight(), new Color(255, 228, 181));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // 太阳
                g2d.setColor(new Color(255, 223, 0));
                g2d.fillOval(320, 30, 60, 60);
                g2d.setColor(new Color(255, 223, 0, 50));
                g2d.fillOval(310, 20, 80, 80);
                
                // 云朵
                g2d.setColor(Color.WHITE);
                int[][] clouds = {{50,40}, {200,60}, {300,30}};
                for (int[] cloud : clouds) {
                    g2d.fillOval(cloud[0], cloud[1], 40, 25);
                    g2d.fillOval(cloud[0]+20, cloud[1]-5, 35, 25);
                    g2d.fillOval(cloud[0]+40, cloud[1], 30, 25);
                }
                
                // 山脉
                g2d.setColor(new Color(34, 139, 34));
                g2d.fillPolygon(new int[]{0,100,200,300,450}, new int[]{200,120,160,100,200}, 5);
                g2d.setColor(new Color(60, 179, 113));
                g2d.fillPolygon(new int[]{0,150,250,450}, new int[]{200,140,130,200}, 4);
                
                // 树木
                int[][] trees = {{80,140}, {350,150}, {120,155}};
                for (int[] tree : trees) {
                    g2d.setColor(new Color(139, 69, 19));
                    g2d.fillRect(tree[0], tree[1], 15, 40);
                    g2d.setColor(new Color(0, 128, 0));
                    g2d.fillOval(tree[0]-15, tree[1]-20, 45, 45);
                }
            }
        };
        imagePanel.setPreferredSize(new Dimension(450, 200));
        imagePanel.setMaximumSize(new Dimension(450, 200));
        mainPanel.add(imagePanel);
        
        // 表单面板
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        JLabel titleLabel = new JLabel("微信聊天");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 28));
        titleLabel.setForeground(new Color(50, 50, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(10));
        
        JLabel subtitleLabel = new JLabel("请输入用户名登录");
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(120, 120, 120));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(subtitleLabel);
        formPanel.add(Box.createVerticalStrut(30));
        
        JLabel usernameLabel = new JLabel("用户名");
        usernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(usernameLabel);
        formPanel.add(Box.createVerticalStrut(8));
        
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        usernameField.setMaximumSize(new Dimension(300, 40));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(usernameField);
        formPanel.add(Box.createVerticalStrut(25));
        
        JButton loginButton = new JButton("登录");
        loginButton.setFont(new Font("微软雅黑", Font.BOLD, 15));
        loginButton.setMaximumSize(new Dimension(300, 45));
        loginButton.setBackground(new Color(7, 193, 96));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        final String[] result = {null};
        final boolean[] isRegister = {false};
        
        ActionListener loginAction = e -> {
            String username = usernameField.getText().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "请输入用户名", "提示", JOptionPane.WARNING_MESSAGE);
            } else {
                result[0] = username;
                isRegister[0] = false;
                dialog.dispose();
            }
        };
        
        loginButton.addActionListener(loginAction);
        usernameField.addActionListener(loginAction);
        formPanel.add(loginButton);
        
        formPanel.add(Box.createVerticalStrut(15));
        
        // 注册按钮
        JButton registerButton = new JButton("注册新用户");
        registerButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        registerButton.setMaximumSize(new Dimension(300, 45));
        registerButton.setBackground(new Color(240, 240, 240));
        registerButton.setForeground(new Color(100, 100, 100));
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "请输入用户名", "提示", JOptionPane.WARNING_MESSAGE);
            } else {
                result[0] = username;
                isRegister[0] = true;
                dialog.dispose();
            }
        });
        formPanel.add(registerButton);
        
        mainPanel.add(formPanel);
        dialog.add(mainPanel);
        dialog.setVisible(true);
        
        if (result[0] != null && isRegister[0]) {
            result[0] = "REGISTER:" + result[0];
        }
        
        return result[0];
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String input = showLoginDialog();
            if (input == null) {
                System.exit(0);
            }
            
            boolean isRegister = input.startsWith("REGISTER:");
            String username = isRegister ? input.substring(9) : input;
            
            try {
                if (isRegister) {
                    // 处理注册
                    ChatClient registerClient = new ChatClient("localhost", 8888, username, true);
                    Thread.sleep(500);
                    if (!registerClient.isConnected()) {
                        System.exit(0);
                        return;
                    }
                    // 注册成功，提示用户重新登录
                    input = showLoginDialog();
                    if (input == null) {
                        System.exit(0);
                        return;
                    }
                    username = input;
                }
                
                // 正常登录
                ChatClient client = new ChatClient("localhost", 8888, username, false);
                ChatUI ui = new ChatUI();
                ui.setClient(client);
                client.setUI(ui);
                client.start();
                ui.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "无法连接到服务器！", "连接错误", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}
