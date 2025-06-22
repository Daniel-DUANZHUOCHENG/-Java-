import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.text.SimpleDateFormat;

// 测试统计器
class TestStatistics {
    private static int totalTests = 0;
    private static int passedTests = 0;

    public static void incrementTotal() {
        totalTests++;
    }

    public static void incrementPassed() {
        passedTests++;
    }

    public static void printStatistics() {
        System.out.println("\n===== 测试统计 =====");
        System.out.println("总测试用例数: " + totalTests);
        System.out.println("通过测试用例数: " + passedTests);
        System.out.printf("测试通过率: %.2f%%\n", (double) passedTests / totalTests * 100);
    }
}

// Notification类测试（测试单元46）
class NotificationTest {
    private Notification notification;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @BeforeAll
    static void setUpClass() {
        System.out.println("\n===== 开始测试类: NotificationTest =====");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("===== 结束测试类: NotificationTest =====");
        TestStatistics.printStatistics();
    }

    @BeforeEach
    void setUp() {
        notification = new Notification("测试通知");
    }

    @Test
    void testNotificationInitialization() {
        System.out.println("\n----- 测试单元: 通知初始化 -----");
        // 测试初始化状态
        assertEquals("测试通知", notification.getMessage());
        assertFalse(notification.isRead());
        assertNotNull(notification.getTimestamp());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testMarkAsRead() {
        System.out.println("\n----- 测试单元: 标记通知为已读 -----");
        // 测试标记已读功能
        notification.markAsRead();
        assertTrue(notification.isRead());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testTimestampFormat() {
        System.out.println("\n----- 测试单元: 时间戳格式 -----");
        // 测试时间戳格式
        String dateStr = sdf.format(notification.getTimestamp());
        assertNotNull(dateStr);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }
}

// User类测试（测试单元26-27）
class UserTest {
    private User user;

    @BeforeAll
    static void setUpClass() {
        System.out.println("\n===== 开始测试类: UserTest =====");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("===== 结束测试类: UserTest =====");
        TestStatistics.printStatistics();
    }

    @BeforeEach
    void setUp() {
        user = new User("U001", "password123", "测试用户") {};
    }

    @Test
    void testAuthenticate() {
        System.out.println("\n----- 测试单元: 用户认证 -----");
        // 正常认证与错误认证
        assertTrue(user.authenticate("password123"));
        assertFalse(user.authenticate("wrongpass"));
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testChangePassword() {
        System.out.println("\n----- 测试单元: 修改密码 -----");
        // 正常修改、错误旧密码、新密码认证
        assertTrue(user.changePassword("password123", "newPassword123"));
        assertFalse(user.changePassword("wrongOld", "newPassword123"));
        assertTrue(user.authenticate("newPassword123"));
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testGetters() {
        System.out.println("\n----- 测试单元: 获取用户信息 -----");
        // 测试getter方法
        assertEquals("U001", user.getUserId());
        assertEquals("测试用户", user.getName());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }
}

// Student类测试（测试单元28-34）
class StudentTest {
    private Student student;
    private Course course1, course2;
    private Teacher teacher;

    @BeforeAll
    static void setUpClass() {
        System.out.println("\n===== 开始测试类: StudentTest =====");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("===== 结束测试类: StudentTest =====");
        TestStatistics.printStatistics();
    }

    @BeforeEach
    void setUp() {
        teacher = new Teacher("T00001", "123456", "张老师", "CS");
        course1 = new Course("C0001", "Java编程", teacher, 4);
        course2 = new Course("C0002", "高等数学", teacher, 3);
        student = new Student("S00000001", "123456", "张三", 20, "CS");
    }

    @Test
    void testValidateAge() {
        System.out.println("\n----- 测试单元: 验证学生年龄合法性 -----");
        // 测试年龄边界值
        Student valid15 = new Student("S00000002", "123456", "李四", 15, "CS");
        Student valid50 = new Student("S00000003", "123456", "王五", 50, "CS");
        Student invalid14 = new Student("S00000004", "123456", "赵六", 14, "CS");

        assertTrue(valid15.validateAge());
        assertTrue(valid50.validateAge());
        assertFalse(invalid14.validateAge());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testEnrollCourse() {
        System.out.println("\n----- 测试单元: 选课功能 -----");
        // 测试选课边界（1门、5门、6门）
        assertTrue(student.enrollCourse(course1));
        assertFalse(student.enrollCourse(course1));

        for (int i = 0; i < 5; i++) {
            student.enrollCourse(new Course("C000" + (i+3), "课程" + i, teacher, 3));
        }
        assertFalse(student.enrollCourse(new Course("C0009", "课程6", teacher, 3)));
        assertEquals(5, student.getEnrolledCourses().size());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testDropCourse() {
        System.out.println("\n----- 测试单元: 退课功能 -----");
        // 测试退课逻辑
        student.enrollCourse(course1);
        assertTrue(student.dropCourse(course1));
        assertFalse(student.dropCourse(course1));
        assertEquals(0, student.getEnrolledCourses().size());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testCalculateGPA() {
        System.out.println("\n----- 测试单元: 计算GPA -----");
        // 测试GPA计算（含学分加权）
        course1.recordStudentGrade(student.getUserId(), 90); // 4.0
        course2.recordStudentGrade(student.getUserId(), 85); // 3.7
        student.enrollCourse(course1);
        student.enrollCourse(course2);

        double gpa = student.calculateGPA();
        assertEquals(3.85, gpa, 0.01);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testConvertToGradePoint() {
        System.out.println("\n----- 测试单元: 成绩转绩点（私有方法间接测试） -----");
        // 通过公有方法间接测试私有方法
        student.enrollCourse(course1);
        course1.recordStudentGrade(student.getUserId(), 90);
        double gpa = student.calculateGPA();
        assertEquals(4.0, gpa, 0.01); // 90分对应4.0绩点
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testAddNotification() {
        System.out.println("\n----- 测试单元: 添加通知 -----");
        // 测试添加通知与获取
        Notification notif1 = new Notification("通知1");
        Notification notif2 = new Notification("通知2");
        student.addNotification(notif1);
        student.addNotification(notif2);

        List<Notification> notifs = student.getNotifications();
        assertEquals(2, notifs.size());
        assertEquals("通知1", notifs.get(0).getMessage());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testUnreadNotificationCount() {
        System.out.println("\n----- 测试单元: 获取未读通知数量 -----");
        // 测试未读通知计数
        student.addNotification(new Notification("通知1"));
        student.addNotification(new Notification("通知2"));
        student.getNotifications().get(0).markAsRead();

        assertEquals(1, student.getUnreadNotificationCount());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }
}

// OldStudent类测试（测试单元1-5）
class OldStudentTest {
    private OldStudent oldStudent;
    private Course course1, course2;
    private Teacher teacher;

    @BeforeAll
    static void setUpClass() {
        System.out.println("\n===== 开始测试类: OldStudentTest =====");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("===== 结束测试类: OldStudentTest =====");
        TestStatistics.printStatistics();
    }

    @BeforeEach
    void setUp() {
        teacher = new Teacher("T00001", "123456", "张老师", "CS");
        course1 = new Course("C0001", "Java编程", teacher, 4);
        course2 = new Course("C0002", "高等数学", teacher, 3);
        oldStudent = new OldStudent("S00000001", "张三", 20);
    }

    @Test
    void testGetSetStudentId() {
        System.out.println("\n----- 测试单元: 学生ID getter/setter -----");
        // 测试ID设置与获取
        oldStudent.setStudentId("S00000002");
        assertEquals("S00000002", oldStudent.getStudentId());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testGetSetName() {
        System.out.println("\n----- 测试单元: 姓名 getter/setter -----");
        // 测试姓名设置与获取
        oldStudent.setName("李四");
        assertEquals("李四", oldStudent.getName());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testGetSetAge() {
        System.out.println("\n----- 测试单元: 年龄 getter/setter -----");
        // 测试年龄设置与获取
        oldStudent.setAge(21);
        assertEquals(21, oldStudent.getAge());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testValidateAge() {
        System.out.println("\n----- 测试单元: 验证学生年龄合法性 -----");
        // 测试年龄验证
        OldStudent valid15 = new OldStudent("S00000002", "李四", 15);
        OldStudent invalid14 = new OldStudent("S00000003", "王五", 14);

        assertTrue(valid15.validateAge());
        assertFalse(invalid14.validateAge());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testAddCourse() {
        System.out.println("\n----- 测试单元: 添加课程 -----");
        // 测试添加课程边界（1门、10门、11门）
        assertTrue(oldStudent.addCourse(course1));
        assertFalse(oldStudent.addCourse(course1));

        for (int i = 0; i < 10; i++) {
            oldStudent.addCourse(new Course("C000" + (i+3), "课程" + i, teacher, 3));
        }
        assertFalse(oldStudent.addCourse(new Course("C0014", "课程11", teacher, 3)));
        assertEquals(10, oldStudent.getEnrolledCourseCount());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testGetEnrolledCourseCount() {
        System.out.println("\n----- 测试单元: 获取已选课程数量 -----");
        // 测试课程数量统计
        oldStudent.addCourse(course1);
        oldStudent.addCourse(course2);
        assertEquals(2, oldStudent.getEnrolledCourseCount());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testCalculateGPA() {
        System.out.println("\n----- 测试单元: 计算GPA -----");
        // 测试旧版GPA计算（非加权）
        course1.recordStudentGrade("S00000001", 90); // 4.0
        course2.recordStudentGrade("S00000001", 80); // 3.0
        oldStudent.addCourse(course1);
        oldStudent.addCourse(course2);

        double gpa = oldStudent.calculateGPA();
        assertEquals(3.5, gpa, 0.01);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }
}

// Teacher类测试（测试单元35-39,13-15）
class TeacherTest {
    private Teacher teacher;
    private Course course1, course2;

    @BeforeAll
    static void setUpClass() {
        System.out.println("\n===== 开始测试类: TeacherTest =====");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("===== 结束测试类: TeacherTest =====");
        TestStatistics.printStatistics();
    }

    @BeforeEach
    void setUp() {
        teacher = new Teacher("T00001", "123456", "张老师", "CS");
        course1 = teacher.createCourse("C0001", "Java编程", 4);
        course2 = teacher.createCourse("C0002", "高等数学", 3);
    }

    @Test
    void testValidateDepartment() {
        System.out.println("\n----- 测试单元: 验证教师院系合法性 -----");
        // 测试合法与非法院系
        assertTrue(teacher.validateDepartment());
        teacher.setDepartment("Invalid");
        assertFalse(teacher.validateDepartment());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testCreateCourse() {
        System.out.println("\n----- 测试单元: 创建课程 -----");
        // 测试创建课程与重复创建
        Course course3 = teacher.createCourse("C0003", "数据结构", 4);
        assertNotNull(course3);
        assertEquals(3, teacher.getCoursesTaught().size());

        Course duplicateCourse = teacher.createCourse("C0001", "重复课程", 4);
        assertNull(duplicateCourse);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testDeleteCourse() {
        System.out.println("\n----- 测试单元: 删除课程 -----");
        // 测试删除存在与不存在的课程
        assertTrue(teacher.deleteCourse(course1));
        assertFalse(teacher.deleteCourse(new Course("C0003", "课程3", teacher, 3)));
        assertEquals(1, teacher.getCoursesTaught().size());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testBatchRecordGrades() {
        System.out.println("\n----- 测试单元: 批量录入成绩 -----");
        // 测试批量录入与无效成绩
        Student student1 = new Student("S00000001", "123456", "张三", 20, "CS");
        Student student2 = new Student("S00000002", "123456", "李四", 21, "CS");
        course1.addStudent(student1);
        course1.addStudent(student2);

        Map<String, Double> grades = new HashMap<>();
        grades.put("S00000001", 90.0);
        grades.put("S00000002", 85.0);
        grades.put("S00000003", 105.0); // 无效成绩

        int successCount = teacher.batchRecordGrades(course1, grades);
        assertEquals(2, successCount);
        assertEquals(90.0, course1.getStudentGrade("S00000001"), 0.01);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testAnalyzeCourseDifficulty() {
        System.out.println("\n----- 测试单元: 分析课程难度 -----");
        // 测试难度计算
        Student student1 = new Student("S00000001", "123456", "张三", 20, "CS");
        Student student2 = new Student("S00000002", "123456", "李四", 21, "CS");
        course1.addStudent(student1);
        course1.addStudent(student2);
        course1.recordStudentGrade(student1.getUserId(), 90);
        course1.recordStudentGrade(student2.getUserId(), 85);

        double difficulty = teacher.analyzeCourseDifficulty(course1);
        assertEquals(12.5, difficulty, 0.01);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testTeachesCourse() {
        System.out.println("\n----- 测试单元: 检查是否教授指定课程 -----");
        // 测试课程教授关系
        assertTrue(teacher.teachesCourse(course1));
        assertFalse(teacher.teachesCourse(new Course("C0003", "课程3", teacher, 3)));
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testBatchRecordGradesOld() {
        System.out.println("\n----- 测试单元: 旧版批量录入成绩 -----");
        // 测试旧版批量录入
        Student student1 = new Student("S00000001", "123456", "张三", 20, "CS");
        Student student2 = new Student("S00000002", "123456", "李四", 21, "CS");
        course1.addStudent(student1);
        course1.addStudent(student2);

        String[] studentIds = {"S00000001", "S00000002"};
        double[] grades = {90.0, 85.0};
        int successCount = teacher.batchRecordGradesOld(course1, studentIds, grades);
        assertEquals(2, successCount);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testGenerateGradeReport() {
        System.out.println("\n----- 测试单元: 生成课程成绩报告（旧版） -----");
        // 测试成绩报告生成
        Student student1 = new Student("S00000001", "123456", "张三", 20, "CS");
        course1.addStudent(student1);
        course1.recordStudentGrade(student1.getUserId(), 90);

        String report = teacher.generateGradeReport(course1);
        assertTrue(report.contains("S00000001"));
        assertTrue(report.contains("90"));
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }
}

// Course类测试（测试单元40-45,6-10）
class CourseTest {
    private Course course;
    private Teacher teacher;
    private Student student1, student2;

    @BeforeAll
    static void setUpClass() {
        System.out.println("\n===== 开始测试类: CourseTest =====");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("===== 结束测试类: CourseTest =====");
        TestStatistics.printStatistics();
    }

    @BeforeEach
    void setUp() {
        teacher = new Teacher("T00001", "123456", "张老师", "CS");
        course = new Course("C0001", "Java编程", teacher, 4);
        student1 = new Student("S00000001", "123456", "张三", 20, "CS");
        student2 = new Student("S00000002", "123456", "李四", 21, "CS");
    }

    @Test
    void testAddStudent() {
        System.out.println("\n----- 测试单元: 添加学生 -----");
        // 测试添加学生边界（1人、30人、31人）
        assertTrue(course.addStudent(student1));
        assertFalse(course.addStudent(student1));

        for (int i = 0; i < 30; i++) {
            course.addStudent(new Student("S0000000" + (i+3), "123456", "学生" + i, 20, "CS"));
        }
        assertFalse(course.addStudent(new Student("S00000034", "123456", "学生33", 20, "CS")));
        assertEquals(30, course.getStudents().size());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testRemoveStudent() {
        System.out.println("\n----- 测试单元: 移除学生 -----");
        // 测试移除学生
        course.addStudent(student1);
        assertTrue(course.removeStudent(student1));
        assertFalse(course.removeStudent(student1));
        assertEquals(0, course.getStudents().size());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testRecordStudentGrade() {
        System.out.println("\n----- 测试单元: 录入学生成绩 -----");
        // 测试录入已选与未选学生成绩
        course.addStudent(student1);
        assertTrue(course.recordStudentGrade(student1.getUserId(), 90));
        assertFalse(course.recordStudentGrade(student2.getUserId(), 85));
        assertEquals(90.0, course.getStudentGrade(student1.getUserId()), 0.01);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testCalculateAverageGrade() {
        System.out.println("\n----- 测试单元: 计算课程平均分 -----");
        // 测试平均分计算
        course.addStudent(student1);
        course.addStudent(student2);
        course.recordStudentGrade(student1.getUserId(), 90);
        course.recordStudentGrade(student2.getUserId(), 85);

        double average = course.calculateAverageGrade();
        assertEquals(87.5, average, 0.01);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testGradeDistribution() {
        System.out.println("\n----- 测试单元: 获取成绩分布 -----");
        // 测试成绩分布统计
        course.addStudent(student1);
        course.addStudent(student2);
        course.recordStudentGrade(student1.getUserId(), 95);
        course.recordStudentGrade(student2.getUserId(), 85);

        Map<String, Integer> distribution = course.getGradeDistribution();
        assertEquals(1, distribution.get("A (90-100)").intValue());
        assertEquals(1, distribution.get("B (80-89)").intValue());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testGetters() {
        System.out.println("\n----- 测试单元: 各类getter方法 -----");
        // 测试getter方法
        assertEquals("C0001", course.getCourseId());
        assertEquals("Java编程", course.getCourseName());
        assertEquals(teacher, course.getTeacher());
        assertEquals(4, course.getCredits());
        assertEquals(0, course.getStudents().size());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }
}

// OldCourse类测试（测试单元6-10）
class OldCourseTest {
    private OldCourse oldCourse;
    private Teacher teacher;

    @BeforeAll
    static void setUpClass() {
        System.out.println("\n===== 开始测试类: OldCourseTest =====");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("===== 结束测试类: OldCourseTest =====");
        TestStatistics.printStatistics();
    }

    @BeforeEach
    void setUp() {
        teacher = new Teacher("T00001", "123456", "张老师", "CS");
        oldCourse = new OldCourse("C0001", "Java编程", teacher);
    }

    @Test
    void testGetters() {
        System.out.println("\n----- 测试单元: 各类getter方法 -----");
        // 测试getter方法
        assertEquals("C0001", oldCourse.getCourseId());
        assertEquals("Java编程", oldCourse.getCourseName());
        assertEquals(teacher, oldCourse.getTeacher());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testRecordStudentGrade() {
        System.out.println("\n----- 测试单元: 录入学生成绩 -----");
        // 测试录入有效与无效成绩
        assertTrue(oldCourse.recordStudentGrade("S00000001", 90));
        assertFalse(oldCourse.recordStudentGrade("S00000001", 105));
        assertFalse(oldCourse.recordStudentGrade("S00000001", -5));
        assertEquals(90.0, oldCourse.getStudentGrade("S00000001"), 0.01);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testGetStudentGrade() {
        System.out.println("\n----- 测试单元: 获取学生成绩 -----");
        // 测试获取成绩
        oldCourse.recordStudentGrade("S00000001", 90);
        assertEquals(90.0, oldCourse.getStudentGrade("S00000001"), 0.01);
        assertEquals(-1.0, oldCourse.getStudentGrade("S00000002"), 0.01); // 未录入成绩
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testCalculateAverageGrade() {
        System.out.println("\n----- 测试单元: 计算课程平均分 -----");
        // 测试平均分计算
        oldCourse.recordStudentGrade("S00000001", 90);
        oldCourse.recordStudentGrade("S00000002", 80);
        double average = oldCourse.calculateAverageGrade();
        assertEquals(85.0, average, 0.01);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testHighAchievingStudentsCount() {
        System.out.println("\n----- 测试单元: 获取高分学生数量 -----");
        // 测试高分学生统计（假设高分定义为≥90）
        oldCourse.recordStudentGrade("S00000001", 90);
        oldCourse.recordStudentGrade("S00000002", 85);
        oldCourse.recordStudentGrade("S00000003", 95);
        assertEquals(2, oldCourse.getHighAchievingStudentsCount());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }
}

// GradeSystem类测试（测试单元47-57,16-20）
class GradeSystemTest {
    private GradeSystem gradeSystem;
    private Teacher teacher;
    private Student student;
    private Course course;
    private OldStudent oldStudent;
    private OldCourse oldCourse;

    @BeforeAll
    static void setUpClass() {
        System.out.println("\n===== 开始测试类: GradeSystemTest =====");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("===== 结束测试类: GradeSystemTest =====");
        TestStatistics.printStatistics();
    }

    @BeforeEach
    void setUp() {
        gradeSystem = new GradeSystem("test.dat");
        teacher = new Teacher("T00001", "123456", "张老师", "CS");
        student = new Student("S00000001", "123456", "张三", 20, "CS");
        course = teacher.createCourse("C0001", "Java编程", 4);
        oldStudent = new OldStudent("S00000002", "李四", 21);
        oldCourse = new OldCourse("C0002", "高等数学", teacher);
    }

    @Test
    void testRegisterUser() {
        System.out.println("\n----- 测试单元: 注册用户 -----");
        // 测试注册用户与重复注册
        assertTrue(gradeSystem.registerUser(teacher));
        assertTrue(gradeSystem.registerUser(student));
        assertFalse(gradeSystem.registerUser(teacher));
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testLogin() {
        System.out.println("\n----- 测试单元: 用户登录 -----");
        // 测试正常登录与错误登录
        gradeSystem.registerUser(student);
        User loggedUser = gradeSystem.login(student.getUserId(), "123456");
        User wrongUser = gradeSystem.login(student.getUserId(), "wrongpass");

        assertNotNull(loggedUser);
        assertEquals(student.getName(), loggedUser.getName());
        assertNull(wrongUser);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testAddCourse() {
        System.out.println("\n----- 测试单元: 添加课程 -----");
        // 测试添加课程与重复添加
        gradeSystem.registerUser(teacher);
        assertTrue(gradeSystem.addCourse(course));
        assertFalse(gradeSystem.addCourse(course));
        assertEquals(1, gradeSystem.getCourseCount());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testFindCourseById() {
        System.out.println("\n----- 测试单元: 查找课程 -----");
        // 测试查找存在与不存在的课程
        gradeSystem.addCourse(course);
        Course foundCourse = gradeSystem.findCourseById("C0001");
        Course notFoundCourse = gradeSystem.findCourseById("C0002");

        assertNotNull(foundCourse);
        assertEquals("Java编程", foundCourse.getCourseName());
        assertNull(notFoundCourse);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testFindStudentById() {
        System.out.println("\n----- 测试单元: 查找学生 -----");
        // 测试查找学生
        gradeSystem.registerUser(student);
        Student foundStudent = gradeSystem.findStudentById("S00000001");
        Student notFoundStudent = gradeSystem.findStudentById("S00000002");

        assertNotNull(foundStudent);
        assertEquals("张三", foundStudent.getName());
        assertNull(notFoundStudent);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testFindTeacherById() {
        System.out.println("\n----- 测试单元: 查找教师 -----");
        // 测试查找教师
        gradeSystem.registerUser(teacher);
        Teacher foundTeacher = gradeSystem.findTeacherById("T00001");
        Teacher notFoundTeacher = gradeSystem.findTeacherById("T00002");

        assertNotNull(foundTeacher);
        assertEquals("张老师", foundTeacher.getName());
        assertNull(notFoundTeacher);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testSaveAndLoadData() {
        System.out.println("\n----- 测试单元: 数据保存与加载 -----");
        // 简化测试数据保存与加载
        gradeSystem.registerUser(teacher);
        gradeSystem.addCourse(course);
        gradeSystem.saveData();

        GradeSystem loadedSystem = new GradeSystem("test.dat");
        assertEquals(1, loadedSystem.getTeacherCount());
        assertEquals(1, loadedSystem.getCourseCount());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testGetCounts() {
        System.out.println("\n----- 测试单元: 获取数量统计 -----");
        // 测试数量统计
        gradeSystem.registerUser(teacher);
        gradeSystem.registerUser(student);
        gradeSystem.addCourse(course);

        assertEquals(1, gradeSystem.getTeacherCount());
        assertEquals(1, gradeSystem.getStudentCount());
        assertEquals(1, gradeSystem.getCourseCount());
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }


    @Test
    void testAddTeacherOld() {
        System.out.println("\n----- 测试单元: 旧版添加教师 -----");
        // 测试旧版添加教师
        gradeSystem.addTeacherOld(teacher);
        Teacher foundTeacher = gradeSystem.findTeacherById("T00001");
        assertNotNull(foundTeacher);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testFindStudentByIdOld() {
        System.out.println("\n----- 测试单元: 旧版查找学生 -----");
        // 测试旧版查找学生
        gradeSystem.addStudentOld(oldStudent);
        OldStudent foundStudent = (OldStudent) gradeSystem.findStudentByIdOld("S00000002");
        assertNotNull(foundStudent);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testCalculateSchoolAverageGPAOld() {
        System.out.println("\n----- 测试单元: 旧版计算全校平均GPA -----");
        // 测试旧版GPA计算
        gradeSystem.addStudentOld(oldStudent);
        oldCourse.recordStudentGrade("S00000002", 90);


        double gpa = gradeSystem.calculateSchoolAverageGPAOld();
        assertEquals(4.0, gpa, 0.01);
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }
}

// ValidationUtil类测试（测试单元58-63,21-25）
class ValidationUtilTest {

    @BeforeAll
    static void setUpClass() {
        System.out.println("\n===== 开始测试类: ValidationUtilTest =====");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("===== 结束测试类: ValidationUtilTest =====");
        TestStatistics.printStatistics();
    }

    @Test
    void testValidateStudentId() {
        System.out.println("\n----- 测试单元: 验证学生ID格式 -----");
        // 测试学生ID验证
        assertTrue(ValidationUtil.validateStudentId("S00000001"));
        assertFalse(ValidationUtil.validateStudentId("s00000001"));
        assertFalse(ValidationUtil.validateStudentId("S0000001"));
        assertFalse(ValidationUtil.validateStudentId("S0000000a"));
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testValidateTeacherId() {
        System.out.println("\n----- 测试单元: 验证教师ID格式 -----");
        // 测试教师ID验证
        assertTrue(ValidationUtil.validateTeacherId("T000001"));
        assertFalse(ValidationUtil.validateTeacherId("t000001"));
        assertFalse(ValidationUtil.validateTeacherId("T00001"));
        assertFalse(ValidationUtil.validateTeacherId("T0000a1"));
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testValidateCourseId() {
        System.out.println("\n----- 测试单元: 验证课程ID格式 -----");
        // 测试课程ID验证
        assertTrue(ValidationUtil.validateCourseId("C0001"));
        assertFalse(ValidationUtil.validateCourseId("c0001"));
        assertFalse(ValidationUtil.validateCourseId("C001"));
        assertFalse(ValidationUtil.validateCourseId("C00a1"));
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testValidateName() {
        System.out.println("\n----- 测试单元: 验证姓名格式 -----");
        // 测试姓名验证
        assertTrue(ValidationUtil.validateName("张三"));
        assertTrue(ValidationUtil.validateName("ZhangSan"));
        assertTrue(ValidationUtil.validateName("张三Smith"));
        assertFalse(ValidationUtil.validateName(""));
        assertFalse(ValidationUtil.validateName("张三@"));
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testValidatePassword() {
        System.out.println("\n----- 测试单元: 验证密码强度 -----");
        // 测试密码验证
        assertTrue(ValidationUtil.validatePassword("Password123"));
        assertFalse(ValidationUtil.validatePassword("1234567")); // 长度不足
        assertFalse(ValidationUtil.validatePassword("password")); // 无数字
        assertFalse(ValidationUtil.validatePassword("12345678")); // 无字母
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testValidateStudentIdOld() {
        System.out.println("\n----- 测试单元: 旧版验证学生ID -----");
        // 测试旧版学生ID验证
        assertTrue(ValidationUtil.validateStudentIdOld("S0001"));
        assertFalse(ValidationUtil.validateStudentIdOld("s0001"));
        assertFalse(ValidationUtil.validateStudentIdOld("S001"));
        assertFalse(ValidationUtil.validateStudentIdOld("S00a1"));
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testValidateTeacherIdOld() {
        System.out.println("\n----- 测试单元: 旧版验证教师ID -----");
        // 测试旧版教师ID验证
        assertTrue(ValidationUtil.validateTeacherIdOld("T0001"));
        assertFalse(ValidationUtil.validateTeacherIdOld("t0001"));
        assertFalse(ValidationUtil.validateTeacherIdOld("T001"));
        assertFalse(ValidationUtil.validateTeacherIdOld("T00a1"));
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testValidateCourseIdOld() {
        System.out.println("\n----- 测试单元: 旧版验证课程ID -----");
        // 测试旧版课程ID验证
        assertTrue(ValidationUtil.validateCourseIdOld("C001"));
        assertFalse(ValidationUtil.validateCourseIdOld("c001"));
        assertFalse(ValidationUtil.validateCourseIdOld("C01"));
        assertFalse(ValidationUtil.validateCourseIdOld("C0a1"));
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testValidateNameOld() {
        System.out.println("\n----- 测试单元: 旧版验证姓名 -----");
        // 测试旧版姓名验证
        assertTrue(ValidationUtil.validateNameOld("张三"));
        assertTrue(ValidationUtil.validateNameOld("ZhangSan"));
        assertFalse(ValidationUtil.validateNameOld(""));
        assertFalse(ValidationUtil.validateNameOld("张三@"));
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }

    @Test
    void testValidateEmailOld() {
        System.out.println("\n----- 测试单元: 旧版验证邮箱 -----");
        // 测试旧版邮箱验证
        assertTrue(ValidationUtil.validateEmailOld("user@example.com"));
        assertTrue(ValidationUtil.validateEmailOld("user.name@example.com"));
        assertFalse(ValidationUtil.validateEmailOld("user@.com"));
        assertFalse(ValidationUtil.validateEmailOld("user@example"));
        TestStatistics.incrementTotal();
        TestStatistics.incrementPassed();
    }
}