import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Main.java - 主程序入口
// Main.java - 主程序入口
public class Main {
    public static void main(String[] args) {
        GradeSystem gradeSystem = new GradeSystem("gradesystem.dat");
        Scanner scanner = new Scanner(System.in);

        // 初始化示例数据
        initializeSampleData(gradeSystem);

        while (true) {
            System.out.println("\n===== 校园成绩管理系统 =====");
            System.out.println("1. 登录");
            System.out.println("2. 退出");
            System.out.print("请选择操作: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    loginMenu(gradeSystem, scanner);
                    break;
                case 2:
                    System.out.println("感谢使用系统，再见！");
                    return;
                default:
                    System.out.println("无效选择，请重新输入。");
            }
        }
    }

    private static void initializeSampleData(GradeSystem gradeSystem) {
        // 创建教师
        Teacher teacher1 = new Teacher("T00001", "123456", "张教授", "CS");
        Teacher teacher2 = new Teacher("T00002", "123456", "李老师", "Math");

        // 创建课程
        Course course1 = teacher1.createCourse("C0001", "Java编程", 4);
        Course course2 = teacher2.createCourse("C0002", "高等数学", 3);

        // 创建学生
        Student student1 = new Student("S00000001", "123456", "张三", 20, "CS");
        Student student2 = new Student("S00000002", "123456", "李四", 21, "Math");
        Student student3 = new Student("S00000003", "123456", "王五", 19, "CS");

        // 注册用户
        gradeSystem.registerUser(teacher1);
        gradeSystem.registerUser(teacher2);
        gradeSystem.registerUser(student1);
        gradeSystem.registerUser(student2);
        gradeSystem.registerUser(student3);

        // 添加课程到系统
        gradeSystem.addCourse(course1);
        gradeSystem.addCourse(course2);

        // 学生选课
        student1.enrollCourse(course1);
        student1.enrollCourse(course2);
        student2.enrollCourse(course2);
        student3.enrollCourse(course1);

        // 录入成绩
        course1.recordStudentGrade("S00000001", 85);
        course1.recordStudentGrade("S00000003", 92);
        course2.recordStudentGrade("S00000001", 78);
        course2.recordStudentGrade("S00000002", 95);

        // 添加成绩通知
        student1.addNotification(new Notification("您的Java编程成绩已更新：85分"));
        student1.addNotification(new Notification("您的高等数学成绩已更新：78分"));
        student2.addNotification(new Notification("您的高等数学成绩已更新：95分"));
        student3.addNotification(new Notification("您的Java编程成绩已更新：92分"));
    }

    private static void loginMenu(GradeSystem gradeSystem, Scanner scanner) {
        System.out.print("请输入用户ID: ");
        String userId = scanner.nextLine();
        System.out.print("请输入密码: ");
        String password = scanner.nextLine();

        User user = gradeSystem.login(userId, password);
        if (user == null) {
            System.out.println("登录失败，用户名或密码错误！");
            return;
        }

        System.out.println("登录成功，欢迎 " + user.getName() + "！");

        if (user instanceof Teacher) {
            teacherMenu((Teacher) user, gradeSystem, scanner);
        } else if (user instanceof Student) {
            studentMenu((Student) user, gradeSystem, scanner);
        }
    }

    private static void teacherMenu(Teacher teacher, GradeSystem gradeSystem, Scanner scanner) {
        while (true) {
            System.out.println("\n===== 教师菜单 =====");
            System.out.println("1. 查看所授课程");
            System.out.println("2. 创建新课程");
            System.out.println("3. 录入课程成绩");
            System.out.println("4. 分析课程难度");
            System.out.println("5. 查看课程成绩分布");
            System.out.println("6. 退出登录");
            System.out.print("请选择操作: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    viewCoursesTaught(teacher, scanner);
                    break;
                case 2:
                    createNewCourse(teacher, gradeSystem, scanner);
                    break;
                case 3:
                    recordGrades(teacher, gradeSystem, scanner);
                    break;
                case 4:
                    analyzeCourseDifficulty(teacher, scanner);
                    break;
                case 5:
                    viewGradeDistribution(teacher, scanner);
                    break;
                case 6:
                    System.out.println("已退出登录。");
                    return;
                default:
                    System.out.println("无效选择，请重新输入。");
            }
        }
    }

    private static void studentMenu(Student student, GradeSystem gradeSystem, Scanner scanner) {
        while (true) {
            System.out.println("\n===== 学生菜单 =====");
            System.out.println("1. 查看已选课程");
            System.out.println("2. 选课");
            System.out.println("3. 退课");
            System.out.println("4. 查看成绩");
            System.out.println("5. 计算GPA");
            System.out.println("6. 查看通知");
            System.out.println("7. 退出登录");
            System.out.print("请选择操作: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    viewEnrolledCourses(student, scanner);
                    break;
                case 2:
                    enrollInCourse(student, gradeSystem, scanner);
                    break;
                case 3:
                    dropCourse(student, scanner);
                    break;
                case 4:
                    viewGrades(student, scanner);
                    break;
                case 5:
                    viewGPA(student, scanner);
                    break;
                case 6:
                    viewNotifications(student, scanner);
                    break;
                case 7:
                    System.out.println("已退出登录。");
                    return;
                default:
                    System.out.println("无效选择，请重新输入。");
            }
        }
    }

    // 教师功能辅助方法
    private static void viewCoursesTaught(Teacher teacher, Scanner scanner) {
        System.out.println("\n您所教授的课程：");
        List<Course> courses = teacher.getCoursesTaught();
        if (courses.isEmpty()) {
            System.out.println("暂无课程。");
            return;
        }
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            System.out.printf("%d. %s (%s) - %d学分\n",
                    i + 1, course.getCourseName(), course.getCourseId(), course.getCredits());
        }
        System.out.println("\n按Enter继续...");
        scanner.nextLine();
    }

    private static void createNewCourse(Teacher teacher, GradeSystem gradeSystem, Scanner scanner) {
        System.out.println("\n===== 创建新课程 =====");
        System.out.print("请输入课程ID: ");
        String courseId = scanner.nextLine();
        System.out.print("请输入课程名称: ");
        String courseName = scanner.nextLine();
        System.out.print("请输入课程学分: ");
        int credits = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符

        Course newCourse = teacher.createCourse(courseId, courseName, credits);
        if (gradeSystem.addCourse(newCourse)) {
            System.out.println("课程创建成功！");
        } else {
            System.out.println("课程创建失败，可能ID已存在。");
        }
    }

    private static void recordGrades(Teacher teacher, GradeSystem gradeSystem, Scanner scanner) {
        System.out.println("\n===== 录入课程成绩 =====");
        List<Course> courses = teacher.getCoursesTaught();
        if (courses.isEmpty()) {
            System.out.println("您尚未教授任何课程。");
            return;
        }

        System.out.println("请选择课程：");
        for (int i = 0; i < courses.size(); i++) {
            System.out.printf("%d. %s (%s)\n", i + 1, courses.get(i).getCourseName(), courses.get(i).getCourseId());
        }
        int courseIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // 消耗换行符

        if (courseIndex < 0 || courseIndex >= courses.size()) {
            System.out.println("无效选择。");
            return;
        }

        Course course = courses.get(courseIndex);
        List<Student> students = course.getStudents();

        System.out.println("\n课程 " + course.getCourseName() + " 的学生列表：");
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            double grade = course.getStudentGrade(student.getUserId());
            System.out.printf("%d. %s (%s) - 当前成绩: %s\n",
                    i + 1, student.getName(), student.getUserId(),
                    grade >= 0 ? String.valueOf(grade) : "未录入");
        }

        System.out.println("\n请输入要录入成绩的学生ID（输入q返回）：");
        String studentId = scanner.nextLine();
        if ("q".equalsIgnoreCase(studentId)) {
            return;
        }

        Student student = gradeSystem.findStudentById(studentId);
        if (student == null || !students.contains(student)) {
            System.out.println("学生不存在或未选修此课程。");
            return;
        }

        System.out.print("请输入成绩: ");
        double grade = scanner.nextDouble();
        scanner.nextLine(); // 消耗换行符

        if (course.recordStudentGrade(studentId, grade)) {
            System.out.println("成绩录入成功！");
            // 发送通知
            student.addNotification(new Notification("您的" + course.getCourseName() + "成绩已更新：" + grade + "分"));
        } else {
            System.out.println("成绩录入失败，可能成绩无效。");
        }
    }

    private static void analyzeCourseDifficulty(Teacher teacher, Scanner scanner) {
        System.out.println("\n===== 分析课程难度 =====");
        List<Course> courses = teacher.getCoursesTaught();
        if (courses.isEmpty()) {
            System.out.println("您尚未教授任何课程。");
            return;
        }

        System.out.println("请选择课程：");
        for (int i = 0; i < courses.size(); i++) {
            System.out.printf("%d. %s (%s)\n", i + 1, courses.get(i).getCourseName(), courses.get(i).getCourseId());
        }
        int courseIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // 消耗换行符

        if (courseIndex < 0 || courseIndex >= courses.size()) {
            System.out.println("无效选择。");
            return;
        }

        Course course = courses.get(courseIndex);
        double difficulty = teacher.analyzeCourseDifficulty(course);
        System.out.printf("\n课程 %s 的难度系数为: %.2f\n", course.getCourseName(), difficulty);
        System.out.println("难度评级: " + getDifficultyRating(difficulty));
    }

    private static String getDifficultyRating(double difficulty) {
        if (difficulty < 20) return "简单";
        else if (difficulty < 40) return "中等";
        else if (difficulty < 60) return "较难";
        else return "困难";
    }

    private static void viewGradeDistribution(Teacher teacher, Scanner scanner) {
        System.out.println("\n===== 查看成绩分布 =====");
        List<Course> courses = teacher.getCoursesTaught();
        if (courses.isEmpty()) {
            System.out.println("您尚未教授任何课程。");
            return;
        }

        System.out.println("请选择课程：");
        for (int i = 0; i < courses.size(); i++) {
            System.out.printf("%d. %s (%s)\n", i + 1, courses.get(i).getCourseName(), courses.get(i).getCourseId());
        }
        int courseIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // 消耗换行符

        if (courseIndex < 0 || courseIndex >= courses.size()) {
            System.out.println("无效选择。");
            return;
        }

        Course course = courses.get(courseIndex);
        Map<String, Integer> distribution = course.getGradeDistribution();

        System.out.println("\n课程 " + course.getCourseName() + " 的成绩分布：");
        for (Map.Entry<String, Integer> entry : distribution.entrySet()) {
            System.out.printf("%-12s: %d人\n", entry.getKey(), entry.getValue());
        }
    }

    // 学生功能辅助方法
    private static void viewEnrolledCourses(Student student, Scanner scanner) {
        System.out.println("\n您已选修的课程：");
        List<Course> courses = student.getEnrolledCourses();
        if (courses.isEmpty()) {
            System.out.println("您尚未选修任何课程。");
            return;
        }
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            double grade = course.getStudentGrade(student.getUserId());
            System.out.printf("%d. %s (%s) - 授课教师: %s - 成绩: %s\n",
                    i + 1, course.getCourseName(), course.getCourseId(),
                    course.getTeacher().getName(), grade >= 0 ? String.valueOf(grade) : "未公布");
        }
        System.out.println("\n按Enter继续...");
        scanner.nextLine();
    }

    private static void enrollInCourse(Student student, GradeSystem gradeSystem, Scanner scanner) {
        System.out.println("\n===== 选课 =====");
        System.out.println("可选课程列表：");
        List<Course> availableCourses = new ArrayList<>();
        for (Course course : gradeSystem.getCourses()) {
            if (!student.getEnrolledCourses().contains(course) &&
                    student.getEnrolledCourses().size() < 5) {
                availableCourses.add(course);
            }
        }

        if (availableCourses.isEmpty()) {
            System.out.println("没有可选的课程（可能已选满5门课程）。");
            return;
        }

        for (int i = 0; i < availableCourses.size(); i++) {
            Course course = availableCourses.get(i);
            System.out.printf("%d. %s (%s) - 授课教师: %s - 学分: %d\n",
                    i + 1, course.getCourseName(), course.getCourseId(),
                    course.getTeacher().getName(), course.getCredits());
        }

        System.out.println("\n请选择要选修的课程（输入编号，输入0返回）：");
        int courseIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // 消耗换行符

        if (courseIndex == -1) {
            return;
        }

        if (courseIndex < 0 || courseIndex >= availableCourses.size()) {
            System.out.println("无效选择。");
            return;
        }

        Course course = availableCourses.get(courseIndex);
        if (student.enrollCourse(course)) {
            System.out.println("选课成功！");
        } else {
            System.out.println("选课失败，可能课程已满或您已选满5门课程。");
        }
    }

    private static void dropCourse(Student student, Scanner scanner) {
        System.out.println("\n===== 退课 =====");
        List<Course> enrolledCourses = student.getEnrolledCourses();
        if (enrolledCourses.isEmpty()) {
            System.out.println("您尚未选修任何课程。");
            return;
        }

        for (int i = 0; i < enrolledCourses.size(); i++) {
            Course course = enrolledCourses.get(i);
            System.out.printf("%d. %s (%s) - 授课教师: %s\n",
                    i + 1, course.getCourseName(), course.getCourseId(), course.getTeacher().getName());
        }

        System.out.println("\n请选择要退选的课程（输入编号，输入0返回）：");
        int courseIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // 消耗换行符

        if (courseIndex == -1) {
            return;
        }

        if (courseIndex < 0 || courseIndex >= enrolledCourses.size()) {
            System.out.println("无效选择。");
            return;
        }

        Course course = enrolledCourses.get(courseIndex);
        if (student.dropCourse(course)) {
            System.out.println("退课成功！");
        } else {
            System.out.println("退课失败。");
        }
    }

    private static void viewGrades(Student student, Scanner scanner) {
        System.out.println("\n===== 查看成绩 =====");
        List<Course> courses = student.getEnrolledCourses();
        if (courses.isEmpty()) {
            System.out.println("您尚未选修任何课程。");
            return;
        }

        System.out.println("您的成绩：");
        for (Course course : courses) {
            double grade = course.getStudentGrade(student.getUserId());
            System.out.printf("%-15s: %s\n", course.getCourseName(),
                    grade >= 0 ? String.valueOf(grade) : "未公布");
        }
    }

    private static void viewGPA(Student student, Scanner scanner) {
        double gpa = student.calculateGPA();
        System.out.printf("\n您的GPA为: %.2f\n", gpa);
        System.out.println("GPA等级: " + getGPARating(gpa));
    }

    private static String getGPARating(double gpa) {
        if (gpa >= 3.7) return "优秀";
        else if (gpa >= 3.0) return "良好";
        else if (gpa >= 2.0) return "中等";
        else if (gpa >= 1.0) return "及格";
        else return "不及格";
    }

    private static void viewNotifications(Student student, Scanner scanner) {
        List<Notification> notifications = student.getNotifications();
        if (notifications.isEmpty()) {
            System.out.println("\n您没有任何通知。");
            return;
        }

        System.out.println("\n您的通知：");
        for (int i = 0; i < notifications.size(); i++) {
            Notification notification = notifications.get(i);
            System.out.printf("%d. [%s] %s - %s\n",
                    i + 1,
                    notification.isRead() ? "已读" : "未读",
                    notification.getTimestamp(),
                    notification.getMessage());
        }

        // 标记所有通知为已读
        for (Notification notification : notifications) {
            if (!notification.isRead()) {
                notification.markAsRead();
            }
        }
    }
}
