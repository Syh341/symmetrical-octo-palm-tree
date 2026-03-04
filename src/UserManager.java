package src;

import java.util.HashSet;
import java.util.Set;

public class UserManager {
    private static Set<String> registeredUsers = new HashSet<>();
    
    static {
        // 添加初始用户
        registeredUsers.add("123");
        registeredUsers.add("321");
        registeredUsers.add("213");
        registeredUsers.add("231");
        registeredUsers.add("132");
        registeredUsers.add("312");
    }
    
    public static boolean isRegistered(String username) {
        return registeredUsers.contains(username);
    }
    
    public static boolean register(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (registeredUsers.contains(username)) {
            return false; // 用户名已存在
        }
        registeredUsers.add(username);
        return true;
    }
    
    public static Set<String> getAllUsers() {
        return new HashSet<>(registeredUsers);
    }
}
