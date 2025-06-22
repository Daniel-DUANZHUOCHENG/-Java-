import java.util.Date;

// Notification.java - 通知类
    public class Notification {
        private String message;
        private boolean isRead;
        private Date timestamp;

        public Notification(String message) {
            this.message = message;
            this.isRead = false;
            this.timestamp = new Date();
        }

        // 测试单元46：标记通知为已读
        public void markAsRead() {
            this.isRead = true;
        }

        public String getMessage() { return message; }
        public boolean isRead() { return isRead; }
        public Date getTimestamp() { return timestamp; }

    public String getContent() {
            return message;
    }
}
