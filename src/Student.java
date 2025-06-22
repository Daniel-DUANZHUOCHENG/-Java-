import java.util.*;


// User.java - 用户基类
    abstract class User {
        private String userId;
        private String password;
        private String name;

        public User(String userId, String password, String name) {
            this.userId = userId;
            this.password = password;
            this.name = name;
        }

        // 测试单元26：用户认证
        public boolean authenticate(String password) {
            return this.password.equals(password);
        }

        // 测试单元27：修改密码
        public boolean changePassword(String oldPassword, String newPassword) {
            if (!authenticate(oldPassword)) {
                return false;
            }
            this.password = newPassword;
            return true;
        }

        public String getUserId() { return userId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getPassword() {
            return password;
        }
    }

    // Student.java - 学生类（继承自User）
    public class Student extends User {
        private int age;
        private String major;
        private List<Course> enrolledCourses;
        private List<Notification> notifications;

        public Student(String studentId, String password, String name, int age, String major) {
            super(studentId, password, name);
            this.age = age;
            this.major = major;
            this.enrolledCourses = new ArrayList<>();
            this.notifications = new ArrayList<>();
        }

        // 测试单元28：验证学生年龄合法性
        public boolean validateAge() {
            return age >= 15 && age <= 50;
        }

        // 测试单元29：选课
        public boolean enrollCourse(Course course) {
            if (enrolledCourses.contains(course)) {
                return false;
            }
            if (enrolledCourses.size() >= 5) {
                return false; // 最多选5门课
            }
            if (course.addStudent(this)) {
                enrolledCourses.add(course);
                return true;
            }
            return false;
        }

        // 测试单元30：退课
        public boolean dropCourse(Course course) {
            if (!enrolledCourses.contains(course)) {
                return false;
            }
            if (course.removeStudent(this)) {
                enrolledCourses.remove(course);
                return true;
            }
            return false;
        }

        // 测试单元31：计算GPA
        public double calculateGPA() {
            if (enrolledCourses.isEmpty()) {
                return 0.0;
            }
            double totalGradePoints = 0;
            int totalCredits = 0;

            for (Course course : enrolledCourses) {
                double grade = course.getStudentGrade(getUserId());
                if (grade >= 0) {
                    totalGradePoints += convertToGradePoint(grade) * course.getCredits();
                    totalCredits += course.getCredits();
                }
            }
            return totalCredits > 0 ? totalGradePoints / totalCredits : 0.0;
        }

        private double convertToGradePoint(double score) {
            if (score >= 90) return 4.0;
            else if (score >= 85) return 3.7;
            else if (score >= 80) return 3.3;
            else if (score >= 75) return 3.0;
            else if (score >= 70) return 2.7;
            else if (score >= 65) return 2.3;
            else if (score >= 60) return 1.0;
            else return 0.0;
        }

        // 测试单元32：获取通知
        public List<Notification> getNotifications() {
            return new ArrayList<>(notifications);
        }

        // 测试单元33：添加通知
        public void addNotification(Notification notification) {
            notifications.add(notification);
        }

        // 测试单元34：获取未读通知数量
        public int getUnreadNotificationCount() {
            int count = 0;
            for (Notification notification : notifications) {
                if (!notification.isRead()) {
                    count++;
                }
            }
            return count;
        }

        public int getAge() { return age; }
        public String getMajor() { return major; }
        public List<Course> getEnrolledCourses() { return new ArrayList<>(enrolledCourses); }
    }

    // OldStudent.java - 旧版Student类（保留测试单元1-5）
    class OldStudent {
        private String studentId;
        private String name;
        private int age;
        private Course[] enrolledCourses;
        private int courseCount;

        public OldStudent(String studentId, String name, int age) {
            this.studentId = studentId;
            this.name = name;
            this.age = age;
            this.enrolledCourses = new Course[10];
            this.courseCount = 0;
        }

        // 测试单元1：getter/setter方法
        public String getStudentId() { return studentId; }
        public void setStudentId(String studentId) { this.studentId = studentId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }

        // 测试单元2：验证学生年龄合法性
        public boolean validateAge() {
            return age >= 15 && age <= 50;
        }

        // 测试单元3：添加课程
        public boolean addCourse(Course course) {
            if (courseCount >= enrolledCourses.length) {
                return false;
            }
            enrolledCourses[courseCount++] = course;
            return true;
        }

        // 测试单元4：获取已选课程数量
        public int getEnrolledCourseCount() {
            return courseCount;
        }

        // 测试单元5：计算GPA（平均绩点）
        public double calculateGPA() {
            if (courseCount == 0) {
                return 0.0;
            }

            double totalGradePoints = 0;
            for (int i = 0; i < courseCount; i++) {
                double grade = enrolledCourses[i].getStudentGrade(studentId);
                if (grade >= 90) totalGradePoints += 4.0;
                else if (grade >= 80) totalGradePoints += 3.0;
                else if (grade >= 70) totalGradePoints += 2.0;
                else if (grade >= 60) totalGradePoints += 1.0;
            }
            return totalGradePoints / courseCount;
        }
    }

// OldCourse.java - 旧版Course类（保留测试单元6-10）
    class OldCourse {
        private String courseId;
        private String courseName;
        private Teacher teacher;
        private StudentGrade[] studentGrades;
        private int gradeCount;

        public OldCourse(String courseId, String courseName, Teacher teacher) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.teacher = teacher;
            this.studentGrades = new StudentGrade[50];
            this.gradeCount = 0;
        }

        // 测试单元6：getter方法
        public String getCourseId() { return courseId; }
        public String getCourseName() { return courseName; }
        public Teacher getTeacher() { return teacher; }

        // 测试单元7：录入学生成绩
        public boolean recordStudentGrade(String studentId, double grade) {
            if (grade < 0 || grade > 100) {
                return false;
            }
            for (int i = 0; i < gradeCount; i++) {
                if (studentGrades[i].getStudentId().equals(studentId)) {
                    studentGrades[i].setGrade(grade);
                    return true;
                }
            }
            if (gradeCount >= studentGrades.length) {
                return false;
            }
            studentGrades[gradeCount++] = new StudentGrade(studentId, grade);
            return true;
        }

        // 测试单元8：获取学生成绩
        public double getStudentGrade(String studentId) {
            for (int i = 0; i < gradeCount; i++) {
                if (studentGrades[i].getStudentId().equals(studentId)) {
                    return studentGrades[i].getGrade();
                }
            }
            return -1; // 未找到成绩
        }

        // 测试单元9：计算课程平均分
        public double calculateAverageGrade() {
            if (gradeCount == 0) {
                return 0.0;
            }
            double totalGrade = 0;
            for (int i = 0; i < gradeCount; i++) {
                totalGrade += studentGrades[i].getGrade();
            }
            return totalGrade / gradeCount;
        }

        // 测试单元10：获取高分学生（成绩≥90）数量
        public int getHighAchievingStudentsCount() {
            int count = 0;
            for (int i = 0; i < gradeCount; i++) {
                if (studentGrades[i].getGrade() >= 90) {
                    count++;
                }
            }
            return count;
        }

        // 内部类：学生成绩记录
        private static class StudentGrade {
            private String studentId;
            private double grade;

            public StudentGrade(String studentId, double grade) {
                this.studentId = studentId;
                this.grade = grade;
            }

            public String getStudentId() { return studentId; }
            public void setStudentId(String studentId) { this.studentId = studentId; }
            public double getGrade() { return grade; }
            public void setGrade(double grade) { this.grade = grade; }
        }
    }

