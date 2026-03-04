import java.net.Socket;

public class TestConnection {
    public static void main(String[] args) {
        try {
            System.out.println("尝试连接服务器 localhost:8888...");
            Socket socket = new Socket("localhost", 8888);
            System.out.println("连接成功！");
            socket.close();
        } catch (Exception e) {
            System.out.println("连接失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
