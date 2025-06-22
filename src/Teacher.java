import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

// Teacher.java - 教师类（继承自User）
    public class Teacher extends User {
        private String department;
        private List<Course> coursesTaught;

        public Teacher(String teacherId, String password, String name, String department) {
            super(teacherId, password, name);
            this.department = department;
            this.coursesTaught = new ArrayList<>();
        }

        // 测试单元35：验证教师所属学院合法性
        public boolean validateDepartment() {
            List<String> validDepartments = Arrays.asList("CS", "Math", "Physics", "Chemistry", "Biology");
            return validDepartments.contains(department);
        }

        // 测试单元36：创建课程
        public Course createCourse(String courseId, String courseName, int credits) {
            Course course = new Course(courseId, courseName, this, credits);
            coursesTaught.add(course);
            return course;
        }

        // 测试单元37：删除课程
        public boolean deleteCourse(Course course) {
            if (!coursesTaught.contains(course)) {
                return false;
            }
            coursesTaught.remove(course);
            return true;
        }

        // 测试单元38：批量录入成绩
        public int batchRecordGrades(Course course, Map<String, Double> studentGrades) {
            if (!coursesTaught.contains(course)) {
                return 0;
            }
            int count = 0;
            for (Map.Entry<String, Double> entry : studentGrades.entrySet()) {
                if (course.recordStudentGrade(entry.getKey(), entry.getValue())) {
                    count++;
                }
            }
            return count;
        }

        // 测试单元39：分析课程难度系数
        public double analyzeCourseDifficulty(Course course) {
            if (!coursesTaught.contains(course)) {
                return -1;
            }
            double avgGrade = course.calculateAverageGrade();
            return 100 - avgGrade; // 平均分越低，难度系数越高
        }

        // 测试单元13：检查是否教授指定课程（旧版功能）
        public boolean teachesCourse(Course course) {
            return course.getTeacher() == this;
        }

        // 测试单元14：批量录入学生成绩（旧版功能）
        public int batchRecordGradesOld(Course course, String[] studentIds, double[] grades) {
            if (studentIds.length != grades.length) {
                return 0;
            }
            int successfulCount = 0;
            for (int i = 0; i < studentIds.length; i++) {
                if (course.recordStudentGrade(studentIds[i], grades[i])) {
                    successfulCount++;
                }
            }
            return successfulCount;
        }

        // 测试单元15：生成课程成绩报告（旧版功能）
        public String generateGradeReport(Course course) {
            if (!teachesCourse(course)) {
                return "无权生成报告";
            }

            double avgGrade = course.calculateAverageGrade();
            int highAchievers = course.getHighAchievingStudentsCountOld();

            return String.format("课程 %s 成绩报告：\n" +
                            "平均分：%.2f\n" +
                            "高分学生（≥90）数量：%d",
                    course.getCourseName(), avgGrade, highAchievers);
        }

        public String getDepartment() { return department; }
        public List<Course> getCoursesTaught() { return new ArrayList<>(coursesTaught); }

    // 在 Teacher.java 中添加以下方法：
    public void setDepartment(String department) {
        this.department = department;
    }

}
