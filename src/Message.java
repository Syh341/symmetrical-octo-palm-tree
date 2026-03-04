package src;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sender;
    private String content;
    private String timestamp;
    private MessageType type;
    
    public enum MessageType {
        TEXT, LOGIN, LOGOUT, USER_LIST, REGISTER, LOGIN_FAILED, REGISTER_SUCCESS, REGISTER_FAILED
    }
    
    public Message(String sender, String content, MessageType type) {
        this.sender = sender;
        this.content = content;
        this.type = type;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    
    public String getSender() { return sender; }
    public String getContent() { return content; }
    public String getTimestamp() { return timestamp; }
    public MessageType getType() { return type; }
    
    @Override
    public String toString() {
        return String.format("[%s] %s: %s", timestamp, sender, content);
    }
}
