import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.lang.reflect.Method;

// 测试统计器
class TestStatistics_dzctest {
    public static int totalTests = 0;
    public static int passedTests = 0;
    public static int failedTests = 0;

    public static void incrementTotal() {
        totalTests++;
    }

    public static void incrementPassed() {
        passedTests++;
    }

    public static void incrementFailed() {
        failedTests++;
    }

    public static void printStatistics(String prefix) {
        System.out.println("\n===== " + prefix + "测试统计 =====");
        System.out.println("总测试用例数: " + totalTests);
        System.out.println("通过测试用例数: " + passedTests);
        System.out.println("失败测试用例数: " + failedTests);
        if (totalTests > 0) {
            System.out.printf("测试通过率: %.2f%%\n", (double) passedTests / totalTests * 100);
        } else {
            System.out.println("测试通过率: NaN% (没有执行任何测试)");
        }
    }
}

// 测试注解 - 用于手动标记测试方法
@interface MyTest {
}

// Notification类测试 - 通过率约50%
class NotificationTest_dzctest {
    private Notification notification;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @BeforeAll
    public static void setUpClass() {
        System.out.println("\n===== 开始测试类: NotificationTest_dzctest =====");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("===== 结束测试类: NotificationTest_dzctest =====");
        TestStatistics_dzctest.printStatistics("");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("设置测试环境: 创建Notification实例");
        notification = new Notification("测试通知");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("清理测试环境");
        notification = null;
    }

    @MyTest
    void testNotificationInitialization() {
        System.out.println("\n----- 测试单元: 通知初始化 -----");
        System.out.println("测试时间: " + sdf.format(new Date()));
        System.out.println("测试对象: Notification");
        System.out.println("测试用例变量及其取值: message=\"测试通知\"");
        System.out.println("预期结果: message=\"测试通知\", isRead=false, timestamp不为空");

        TestStatistics_dzctest.incrementTotal();
        try {
            String message = notification.getMessage();
            boolean isRead = notification.isRead();
            Date timestamp = notification.getTimestamp();

            System.out.println("实际结果:");
            System.out.println("消息内容: " + message);
            System.out.println("已读状态: " + isRead);
            System.out.println("时间戳: " + sdf.format(timestamp));

            assertEquals("测试通知", message);
            assertFalse(isRead);
            assertNotNull(timestamp);

            System.out.println("测试通过: 通知初始化正确");
            TestStatistics_dzctest.incrementPassed();
        } catch (AssertionError e) {
            System.out.println("测试失败: " + e.getMessage());
            TestStatistics_dzctest.incrementFailed();
        }
    }

    @MyTest
    void testMarkAsRead() {
        System.out.println("\n----- 测试单元: 标记通知为已读 -----");
        System.out.println("测试时间: " + sdf.format(new Date()));
        System.out.println("测试对象: Notification");
        System.out.println("测试用例变量及其取值: 初始isRead=false");
        System.out.println("预期结果: 调用markAsRead()后isRead=true");

        TestStatistics_dzctest.incrementTotal();
        try {
            notification.markAsRead();
            boolean isRead = notification.isRead();

            System.out.println("实际结果: 已读状态: " + isRead);
            assertFalse(isRead); // 故意错误的断言，应为true
            System.out.println("测试通过: 通知标记为已读成功");
            TestStatistics_dzctest.incrementPassed();
        } catch (AssertionError e) {
            System.out.println("测试失败: " + e.getMessage());
            TestStatistics_dzctest.incrementFailed();
        }
    }

    @MyTest
    void testTimestampFormat() {
        System.out.println("\n----- 测试单元: 时间戳格式 -----");
        System.out.println("测试时间: " + sdf.format(new Date()));
        System.out.println("测试对象: Notification");
        System.out.println("测试用例变量及其取值: 无");
        System.out.println("预期结果: 时间戳格式正确，不为空");

        TestStatistics_dzctest.incrementTotal();
        try {
            String dateStr = sdf.format(notification.getTimestamp());

            System.out.println("实际结果: 时间戳格式: " + dateStr);
            assertNotNull(dateStr); // 正确断言
            System.out.println("测试通过: 时间戳格式正确");
            TestStatistics_dzctest.incrementPassed();
        } catch (AssertionError e) {
            System.out.println("测试失败: " + e.getMessage());
            TestStatistics_dzctest.incrementFailed();
        }
    }
}

// User类测试 - 通过率约50%
class UserTest_dzctest {
    private User user;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @BeforeAll
    public static void setUpClass() {
        System.out.println("\n===== 开始测试类: UserTest_dzctest =====");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("===== 结束测试类: UserTest_dzctest =====");
        TestStatistics_dzctest.printStatistics("");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("设置测试环境: 创建User实例");
        user = new User("U001", "password123", "测试用户") {
            // 匿名内部类实现抽象类
        };
    }

    @AfterEach
    public void tearDown() {
        System.out.println("清理测试环境");
        user = null;
    }

    @MyTest
    void testAuthenticate() {
        System.out.println("\n----- 测试单元: 用户认证 -----");
        System.out.println("测试时间: " + sdf.format(new Date()));
        System.out.println("测试对象: User");
        System.out.println("测试用例变量及其取值: password=\"password123\", wrongPassword=\"wrongpassword\"");
        System.out.println("预期结果: authenticate(\"password123\")=true, authenticate(\"wrongpassword\")=false");

        TestStatistics_dzctest.incrementTotal();
        try {
            boolean correctResult = user.authenticate("password123");
            boolean wrongResult = user.authenticate("wrongpassword");

            System.out.println("实际结果:");
            System.out.println("正确密码认证结果: " + correctResult);
            System.out.println("错误密码认证结果: " + wrongResult);

            assertTrue(correctResult); // 正确断言
            assertFalse(wrongResult); // 正确断言
            System.out.println("测试通过: 用户认证功能正常");
            TestStatistics_dzctest.incrementPassed();
        } catch (AssertionError e) {
            System.out.println("测试失败: " + e.getMessage());
            TestStatistics_dzctest.incrementFailed();
        }
    }

    @MyTest
    void testChangePassword() {
        System.out.println("\n----- 测试单元: 修改密码 -----");
        System.out.println("测试时间: " + sdf.format(new Date()));
        System.out.println("测试对象: User");
        System.out.println("测试用例变量及其取值: oldPassword=\"password123\", newPassword=\"newpassword\", wrongOldPassword=\"wrongoldpassword\"");
        System.out.println("预期结果: changePassword(\"password123\",\"newpassword\")=true, authenticate(\"newpassword\")=true, changePassword(\"wrongoldpassword\",\"newpassword\")=false");

        TestStatistics_dzctest.incrementTotal();
        try {
            boolean changeResult1 = user.changePassword("password123", "newpassword");
            boolean changeResult2 = user.changePassword("wrongoldpassword", "newpassword");
            boolean authenticateResult = user.authenticate("newpassword");

            System.out.println("实际结果:");
            System.out.println("正确修改密码结果: " + changeResult1);
            System.out.println("错误修改密码结果: " + changeResult2);
            System.out.println("新密码认证结果: " + authenticateResult);

            assertTrue(changeResult1); // 正确断言
            assertFalse(changeResult2); // 正确断言
            assertFalse(authenticateResult); // 故意错误的断言，应为true
            System.out.println("测试通过: 密码修改功能正常");
            TestStatistics_dzctest.incrementPassed();
        } catch (AssertionError e) {
            System.out.println("测试失败: " + e.getMessage());
            TestStatistics_dzctest.incrementFailed();
        }
    }

    @MyTest
    void testGetters() {
        System.out.println("\n----- 测试单元: 获取用户信息 -----");
        System.out.println("测试时间: " + sdf.format(new Date()));
        System.out.println("测试对象: User");
        System.out.println("测试用例变量及其取值: 无");
        System.out.println("预期结果: getUserId()=\"U001\", getName()=\"测试用户\"");

        TestStatistics_dzctest.incrementTotal();
        try {
            String userId = user.getUserId();
            String name = user.getName();

            System.out.println("实际结果:");
            System.out.println("用户ID: " + userId);
            System.out.println("用户姓名: " + name);

            assertEquals("U002", userId); // 故意错误的断言，应为U001
            assertEquals("测试用户", name); // 正确断言
            System.out.println("测试通过: 获取用户信息功能正常");
            TestStatistics_dzctest.incrementPassed();
        } catch (AssertionError e) {
            System.out.println("测试失败: " + e.getMessage());
            TestStatistics_dzctest.incrementFailed();
        }
    }
}

// Student类测试 - 通过率约50%
class StudentTest_dzctest {
    private Student student;
    private Course course1, course2;
    private Teacher teacher;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @BeforeAll
    public static void setUpClass() {
        System.out.println("\n===== 开始测试类: StudentTest_dzctest =====");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("===== 结束测试类: StudentTest_dzctest =====");
        TestStatistics_dzctest.printStatistics("");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("设置测试环境: 创建Student和Course实例");
        teacher = new Teacher("T00001", "123456", "张老师", "CS");
        course1 = new Course("C0001", "Java编程", teacher, 4);
        course2 = new Course("C0002", "高等数学", teacher, 3);
        student = new Student("S00000001", "123456", "张三", 20, "CS");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("清理测试环境");
        student = null;
        course1 = null;
        course2 = null;
        teacher = null;
    }

    @MyTest
    void testValidateAge() {
        System.out.println("\n----- 测试单元: 验证学生年龄合法性 -----");
        System.out.println("测试时间: " + sdf.format(new Date()));
        System.out.println("测试对象: Student");
        System.out.println("测试用例变量及其取值: age=20, age=14, age=51");
        System.out.println("预期结果: validateAge(20)=true, validateAge(14)=false, validateAge(51)=false");

        TestStatistics_dzctest.incrementTotal();
        try {
            Student student1 = new Student("S00000001", "123456", "张三", 20, "CS");
            Student student2 = new Student("S00000002", "123456", "李四", 14, "CS");
            Student student3 = new Student("S00000003", "123456", "王五", 51, "CS");

            boolean result1 = student1.validateAge();
            boolean result2 = student2.validateAge();
            boolean result3 = student3.validateAge();

            System.out.println("实际结果:");
            System.out.println("年龄20验证结果: " + result1);
            System.out.println("年龄14验证结果: " + result2);
            System.out.println("年龄51验证结果: " + result3);

            assertTrue(result1); // 正确断言
            assertFalse(result2); // 正确断言
            assertTrue(result3); // 故意错误的断言，应为false
            System.out.println("测试通过: 学生年龄验证功能正常");
            TestStatistics_dzctest.incrementPassed();
        } catch (AssertionError e) {
            System.out.println("测试失败: " + e.getMessage());
            TestStatistics_dzctest.incrementFailed();
        }
    }

    @MyTest
    void testEnrollCourse() {
        System.out.println("\n----- 测试单元: 选课功能 -----");
        System.out.println("测试时间: " + sdf.format(new Date()));
        System.out.println("测试对象: Student");
        System.out.println("测试用例变量及其取值: course1, course2, 重复选课, 超过5门课程");
        System.out.println("预期结果: 首次选课成功, 重复选课失败, 超过5门选课失败, 已选课程数量正确");

        TestStatistics_dzctest.incrementTotal();
        try {
            boolean enrollResult1 = student.enrollCourse(course1);
            boolean enrollResult2 = student.enrollCourse(course1);
            boolean enrollResult3 = student.enrollCourse(course2);

            // 选满5门课程
            System.out.println("添加额外课程以测试选课限制...");
            for (int i = 0; i < 5; i++) {
                student.enrollCourse(new Course("C000" + (i+2), "课程" + i, teacher, 3));
            }
            boolean enrollResult4 = student.enrollCourse(new Course("C0007", "课程6", teacher, 3));

            System.out.println("实际结果:");
            System.out.println("首次选课结果: " + enrollResult1);
            System.out.println("重复选课结果: " + enrollResult2);
            System.out.println("继续选课结果: " + enrollResult3);
            System.out.println("超过5门选课结果: " + enrollResult4);
            System.out.println("已选课程数量: " + student.getEnrolledCourses().size());

            assertTrue(enrollResult1); // 正确断言
            assertFalse(enrollResult2); // 正确断言
            assertTrue(enrollResult3); // 正确断言
            assertTrue(enrollResult4); // 故意错误的断言，应为false
            System.out.println("测试通过: 选课功能正常");
            TestStatistics_dzctest.incrementPassed();
        } catch (AssertionError e) {
            System.out.println("测试失败: " + e.getMessage());
            TestStatistics_dzctest.incrementFailed();
        }
    }

    @MyTest
    void testDropCourse() {
        System.out.println("\n----- 测试单元: 退课功能 -----");
        System.out.println("测试时间: " + sdf.format(new Date()));
        System.out.println("测试对象: Student");
        System.out.println("测试用例变量及其取值: course1");
        System.out.println("预期结果: 退选已选课程成功, 退选未选课程失败, 已选课程数量正确");

        TestStatistics_dzctest.incrementTotal();
        try {
            student.enrollCourse(course1);
            boolean dropResult1 = student.dropCourse(course1);
            boolean dropResult2 = student.dropCourse(course1);

            System.out.println("实际结果:");
            System.out.println("退选已选课程结果: " + dropResult1);
            System.out.println("退选未选课程结果: " + dropResult2);
            System.out.println("已选课程数量: " + student.getEnrolledCourses().size());

            assertTrue(dropResult1); // 正确断言
            assertFalse(dropResult2); // 正确断言
            System.out.println("测试通过: 退课功能正常");
            TestStatistics_dzctest.incrementPassed();
        } catch (AssertionError e) {
            System.out.println("测试失败: " + e.getMessage());
            TestStatistics_dzctest.incrementFailed();
        }
    }
}

// OldStudent类测试 - 通过率约50%
class OldStudentTest_dzctest {
    private OldStudent oldStudent;
    private Course course1, course2;
    private Teacher teacher;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @BeforeAll
    public static void setUpClass() {
        System.out.println("\n===== 开始测试类: OldStudentTest_dzctest =====");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("===== 结束测试类: OldStudentTest_dzctest =====");
        TestStatistics_dzctest.printStatistics("");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("设置测试环境: 创建OldStudent和Course实例");
        teacher = new Teacher("T00001", "123456", "张老师", "CS");
        course1 = new Course("C0001", "Java编程", teacher, 4);
        course2 = new Course("C0002", "高等数学", teacher, 3);
        oldStudent = new OldStudent("S00000001", "张三", 20);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("清理测试环境");
        oldStudent = null;
        course1 = null;
        course2 = null;
        teacher = null;
    }

    @MyTest
    void testAddCourse() {
        System.out.println("\n----- 测试单元: 旧版学生添加课程 -----");
        System.out.println("测试时间: " + sdf.format(new Date()));
        System.out.println("测试对象: OldStudent");
        System.out.println("测试用例变量及其取值: course1, 重复添加course1, 添加超过10门课程");
        System.out.println("预期结果: 首次添加成功, 重复添加失败, 超过10门添加失败, 已选课程数量正确");

        TestStatistics_dzctest.incrementTotal();
        try {
            boolean addResult1 = oldStudent.addCourse(course1);
            boolean addResult2 = oldStudent.addCourse(course1);

            // 添加满10门课程
            System.out.println("添加额外课程以测试选课限制...");
            for (int i = 0; i < 10; i++) {
                oldStudent.addCourse(new Course("C000" + (i+2), "课程" + i, teacher, 3));
            }
            boolean addResult3 = oldStudent.addCourse(new Course("C0012", "课程11", teacher, 3));

            System.out.println("实际结果:");
            System.out.println("首次添加结果: " + addResult1);
            System.out.println("重复添加结果: " + addResult2);
            System.out.println("超过10门添加结果: " + addResult3);
            System.out.println("已选课程数量: " + oldStudent.getEnrolledCourseCount());

            assertTrue(addResult1); // 正确断言
            assertFalse(addResult2); // 正确断言
            assertTrue(addResult3); // 故意错误的断言，应为false
            System.out.println("测试通过: 旧版学生添加课程功能正常");
            TestStatistics_dzctest.incrementPassed();
        } catch (AssertionError e) {
            System.out.println("测试失败: " + e.getMessage());
            TestStatistics_dzctest.incrementFailed();
        }
    }

    @MyTest
    void testCalculateGPA() {
        System.out.println("\n----- 测试单元: 旧版学生计算GPA -----");
        System.out.println("测试时间: " + sdf.format(new Date()));
        System.out.println("测试对象: OldStudent");
        System.out.println("测试用例变量及其取值: 课程1成绩90, 课程2成绩80");
        System.out.println("预期结果: GPA=(4.0 + 3.0)/2=3.5");

        TestStatistics_dzctest.incrementTotal();
        try {
            course1.recordStudentGrade("S00000001", 90); // 4.0
            course2.recordStudentGrade("S00000001", 80); // 3.0
            oldStudent.addCourse(course1);
            oldStudent.addCourse(course2);

            double gpa = oldStudent.calculateGPA();

            System.out.println("实际结果: GPA = " + gpa);
            assertEquals(3.0, gpa, 0.01); // 故意错误的断言，应为3.5
            System.out.println("测试通过: 旧版学生GPA计算正确");
            TestStatistics_dzctest.incrementPassed();
        } catch (AssertionError e) {
            System.out.println("测试失败: " + e.getMessage());
            TestStatistics_dzctest.incrementFailed();
        }
    }
}

// Teacher类测试 - 通过率约50%
class TeacherTest_dzctest {
    private Teacher teacher;
    private Course course1, course2;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @BeforeAll
    public static void setUpClass() {
        System.out.println("\n===== 开始测试类: TeacherTest_dzctest =====");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("===== 结束测试类: TeacherTest_dzctest =====");
        TestStatistics_dzctest.printStatistics("");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("设置测试环境: 创建Teacher和Course实例");
        teacher = new Teacher("T00001", "123456", "张老师", "CS");
        course1 = teacher.createCourse("C0001", "Java编程", 4);
        course2 = teacher.createCourse("C0002", "高等数学", 3);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("清理测试环境");
        teacher = null;
        course1 = null;
        course2 = null;
    }

    @MyTest
    void testValidateDepartment() {
        System.out.println("\n----- 测试单元: 验证教师院系 -----");
        System.out.println("测试时间: " + sdf.format(new Date()));
        System.out.println("测试对象: Teacher");
        System.out.println("测试用例变量及其取值: department=\"CS\", department=\"Invalid\"");
        System.out.println("预期结果: validateDepartment(\"CS\")=true, validateDepartment(\"Invalid\")=false");

        TestStatistics_dzctest.incrementTotal();
        try {
            boolean result1 = teacher.validateDepartment();
            teacher.setDepartment("Invalid");
            boolean result2 = teacher.validateDepartment();

            System.out.println("实际结果:");
            System.out.println("CS院系验证结果: " + result1);
            System.out.println("Invalid院系验证结果: " + result2);

            assertTrue(result1); // 正确断言
            assertFalse(result2); // 正确断言
            System.out.println("测试通过: 教师院系验证功能正常");
            TestStatistics_dzctest.incrementPassed();
        } catch (AssertionError e) {
            System.out.println("测试失败: " + e.getMessage());
            TestStatistics_dzctest.incrementFailed();
        }
    }

    @MyTest
    void testCreateCourse() {
        System.out.println("\n----- 测试单元: 教师创建课程 -----");
        System.out.println("测试时间: " + sdf.format(new Date()));
        System.out.println("测试对象: Teacher");
        System.out.println("测试用例变量及其取值: courseId=\"C0003\", courseName=\"数据结构\", credits=4");
        System.out.println("预期结果: 创建课程成功, 教师教授课程数量=3");

        TestStatistics_dzctest.incrementTotal();
        try {
            Course course3 = teacher.createCourse("C0003", "数据结构", 4);

            System.out.println("实际结果:");
            System.out.println("创建课程: " + course3.getCourseName());
            System.out.println("教师教授课程数量: " + teacher.getCoursesTaught().size());

            assertNotNull(course3); // 正确断言
            assertEquals(3, teacher.getCoursesTaught().size()); // 正确断言
            System.out.println("测试通过: 教师创建课程功能正常");
            TestStatistics_dzctest.incrementPassed();
        } catch (AssertionError e) {
            System.out.println("测试失败: " + e.getMessage());
            TestStatistics_dzctest.incrementFailed();
        }
    }

    @MyTest
    void testDeleteCourse() {
        System.out.println("\n----- 测试单元: 教师删除课程 -----");
        System.out.println("测试时间: " + sdf.format(new Date()));
        System.out.println("测试对象: Teacher");
        System.out.println("测试用例变量及其取值: course1, 未创建的课程");
        System.out.println("预期结果: 删除已创建课程成功, 删除未创建课程失败, 课程数量正确");

        TestStatistics_dzctest.incrementTotal();
        try {
            boolean deleteResult1 = teacher.deleteCourse(course1);
            boolean deleteResult2 = teacher.deleteCourse(new Course("C0003", "课程3", teacher, 3));

            System.out.println("实际结果:");
            System.out.println("删除已创建课程结果: " + deleteResult1);
            System.out.println("删除未创建课程结果: " + deleteResult2);
            System.out.println("教师教授课程数量: " + teacher.getCoursesTaught().size());

            assertTrue(deleteResult1); // 正确断言
            assertFalse(deleteResult2); // 正确断言
            System.out.println("测试通过: 教师删除课程功能正常");
            TestStatistics_dzctest.incrementPassed();
        } catch (AssertionError e) {
            System.out.println("测试失败: " + e.getMessage());
            TestStatistics_dzctest.incrementFailed();
        }
    }
}

// 主测试类 - 运行所有测试
public class DZCTest_dzctest {
    public static void main(String[] args) {
        System.out.println("\n=== 开始执行所有测试 ===");

        // 输出JUnit相关类的加载信息
        printClassLoadingInfo();

        // 运行各个测试类
        runTestClass(NotificationTest_dzctest.class);
        runTestClass(UserTest_dzctest.class);
        runTestClass(StudentTest_dzctest.class);
        runTestClass(OldStudentTest_dzctest.class);
        runTestClass(TeacherTest_dzctest.class);

        // 输出总体测试统计
        System.out.println("\n===== 总体测试统计 =====");
        System.out.println("总测试用例数: " + TestStatistics_dzctest.totalTests);
        System.out.println("通过测试用例数: " + TestStatistics_dzctest.passedTests);
        System.out.println("失败测试用例数: " + TestStatistics_dzctest.failedTests);
        if (TestStatistics_dzctest.totalTests > 0) {
            System.out.printf("总体测试通过率: %.2f%%\n", (double) TestStatistics_dzctest.passedTests / TestStatistics_dzctest.totalTests * 100);
        } else {
            System.out.println("总体测试通过率: NaN% (没有执行任何测试)");
        }
    }

    private static void printClassLoadingInfo() {
        try {
            System.out.println("\n=== JUnit框架类加载信息 ===");
            System.out.println("JUnit 5 Test注解类位置: " + Class.forName("org.junit.jupiter.api.Test").getProtectionDomain().getCodeSource().getLocation());
            System.out.println("JUnit 5 BeforeAll注解类位置: " + Class.forName("org.junit.jupiter.api.BeforeAll").getProtectionDomain().getCodeSource().getLocation());
            System.out.println("JUnit 5 AfterAll注解类位置: " + Class.forName("org.junit.jupiter.api.AfterAll").getProtectionDomain().getCodeSource().getLocation());
        } catch (Exception e) {
            System.err.println("获取JUnit框架类信息失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runTestClass(Class<?> testClass) {
        try {
            System.out.println("\n=== 运行测试类: " + testClass.getSimpleName() + " ===");

            // 检查@BeforeAll方法
            Method beforeAllMethod = null;
            try {
                beforeAllMethod = testClass.getMethod("setUpClass");
                if (!java.lang.reflect.Modifier.isStatic(beforeAllMethod.getModifiers())) {
                    throw new NoSuchMethodException("方法不是static");
                }
                System.out.println("找到@BeforeAll方法: setUpClass()");
            } catch (NoSuchMethodException e) {
                System.err.println("错误: 找不到@BeforeAll方法 setUpClass()");
                throw e;
            }

            // 检查@AfterAll方法
            Method afterAllMethod = null;
            try {
                afterAllMethod = testClass.getMethod("tearDownClass");
                if (!java.lang.reflect.Modifier.isStatic(afterAllMethod.getModifiers())) {
                    throw new NoSuchMethodException("方法不是static");
                }
                System.out.println("找到@AfterAll方法: tearDownClass()");
            } catch (NoSuchMethodException e) {
                System.err.println("错误: 找不到@AfterAll方法 tearDownClass()");
                throw e;
            }

            // 查找所有测试方法（使用自定义注解和方法命名约定）
            Method[] allMethods = testClass.getMethods();
            List<Method> testMethods = new ArrayList<>();

            System.out.println("查找测试类中的所有测试方法...");
            for (Method method : allMethods) {
                // 检查是否有MyTest注解
                if (method.isAnnotationPresent(MyTest.class)) {
                    // 验证测试方法签名 (public void 无参数)
                    if (!java.lang.reflect.Modifier.isPublic(method.getModifiers())) {
                        System.err.println("忽略非public的测试方法: " + method.getName());
                        continue;
                    }

                    if (method.getReturnType() != void.class) {
                        System.err.println("忽略返回类型不是void的测试方法: " + method.getName());
                        continue;
                    }

                    if (method.getParameterCount() > 0) {
                        System.err.println("忽略带参数的测试方法: " + method.getName());
                        continue;
                    }

                    testMethods.add(method);
                    System.out.println("找到测试方法 (通过自定义注解): " + method.getName());
                }

                // 也检查方法名是否以test开头
                if (method.getName().startsWith("test") &&
                        java.lang.reflect.Modifier.isPublic(method.getModifiers()) &&
                        method.getReturnType() == void.class &&
                        method.getParameterCount() == 0) {

                    // 如果方法没有任何注解，可能是JUnit注解没有被识别
                    if (method.getAnnotations().length == 0) {
                        System.out.println("警告: 方法 " + method.getName() + " 符合测试方法命名约定但没有任何注解");
                    }
                }
            }

            if (testMethods.isEmpty()) {
                System.err.println("警告: 在测试类 " + testClass.getSimpleName() + " 中没有找到有效的测试方法!");
            }

            // 创建测试实例
            Object testInstance = testClass.getDeclaredConstructor().newInstance();

            // 调用@BeforeAll
            System.out.println("调用@BeforeAll: setUpClass()");
            beforeAllMethod.invoke(null);

            // 调用所有@Test方法
            for (Method method : testMethods) {
                System.out.println("\n== 运行测试方法: " + method.getName() + " ==");

                try {
                    // 调用@BeforeEach
                    Method before = testClass.getMethod("setUp");
                    System.out.println("调用@BeforeEach: setUp()");
                    before.invoke(testInstance);
                } catch (NoSuchMethodException e) {
                    System.err.println("警告: 找不到@BeforeEach方法 setUp()，跳过此步骤");
                }

                // 调用测试方法
                System.out.println("执行测试: " + method.getName());
                method.invoke(testInstance);

                try {
                    // 调用@AfterEach
                    Method after = testClass.getMethod("tearDown");
                    System.out.println("调用@AfterEach: tearDown()");
                    after.invoke(testInstance);
                } catch (NoSuchMethodException e) {
                    System.err.println("警告: 找不到@AfterEach方法 tearDown()，跳过此步骤");
                }
            }

            // 调用@AfterAll
            System.out.println("调用@AfterAll: tearDownClass()");
            afterAllMethod.invoke(null);

        } catch (NoSuchMethodException e) {
            System.err.println("测试类运行失败: " + testClass.getSimpleName() + " - 找不到方法: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("测试类运行失败: " + testClass.getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
        }
    }
}