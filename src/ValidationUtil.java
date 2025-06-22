// ValidationUtil.java - 数据验证工具类
    public class ValidationUtil {
        // 测试单元58：验证学生ID格式（示例：S+8位数字）
        public static boolean validateStudentId(String studentId) {
            if (studentId == null || studentId.length() != 9) {
                return false;
            }
            if (studentId.charAt(0) != 'S') {
                return false;
            }
            for (int i = 1; i < 9; i++) {
                if (!Character.isDigit(studentId.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

        // 测试单元59：验证教师ID格式（示例：T+6位数字）
        public static boolean validateTeacherId(String teacherId) {
            if (teacherId == null || teacherId.length() != 7) {
                return false;
            }
            if (teacherId.charAt(0) != 'T') {
                return false;
            }
            for (int i = 1; i < 7; i++) {
                if (!Character.isDigit(teacherId.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

        // 测试单元60：验证课程ID格式（示例：C+4位数字）
        public static boolean validateCourseId(String courseId) {
            if (courseId == null || courseId.length() != 5) {
                return false;
            }
            if (courseId.charAt(0) != 'C') {
                return false;
            }
            for (int i = 1; i < 5; i++) {
                if (!Character.isDigit(courseId.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

        // 测试单元61：验证姓名格式（非空且只包含字母和中文）
        public static boolean validateName(String name) {
            if (name == null || name.trim().isEmpty()) {
                return false;
            }
            String pattern = "^[\\u4e00-\\u9fa5a-zA-Z]+$";
            return name.matches(pattern);
        }

        // 测试单元62：验证邮箱格式
        public static boolean validateEmail(String email) {
            if (email == null || email.trim().isEmpty()) {
                return false;
            }
            String pattern = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            return email.matches(pattern);
        }

        // 测试单元63：验证密码强度（至少8位，包含字母和数字）
        public static boolean validatePassword(String password) {
            if (password == null || password.length() < 8) {
                return false;
            }
            boolean hasLetter = false;
            boolean hasDigit = false;
            for (char c : password.toCharArray()) {
                if (Character.isLetter(c)) hasLetter = true;
                if (Character.isDigit(c)) hasDigit = true;
            }
            return hasLetter && hasDigit;
        }

        // 测试单元21-25：旧版验证功能（保留编号连续性）
        public static boolean validateStudentIdOld(String studentId) { return validateStudentId(studentId); }
        public static boolean validateTeacherIdOld(String teacherId) { return validateTeacherId(teacherId); }
        public static boolean validateCourseIdOld(String courseId) { return validateCourseId(courseId); }
        public static boolean validateNameOld(String name) { return validateName(name); }
        public static boolean validateEmailOld(String email) { return validateEmail(email); }
    }
