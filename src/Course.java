import java.util.*;

// Course.java - 课程类
    public class Course {
        private String courseId;
        private String courseName;
        private Teacher teacher;
        private int credits;
        private Map<String, Double> studentGrades;
        private List<Student> students;

        public Course(String courseId, String courseName, Teacher teacher, int credits) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.teacher = teacher;
            this.credits = credits;
            this.studentGrades = new HashMap<>();
            this.students = new ArrayList<>();
        }

        // 测试单元40：添加学生
        public boolean addStudent(Student student) {
            if (students.size() >= 30) {
                return false; // 课程容量上限30人
            }
            if (students.contains(student)) {
                return false;
            }
            students.add(student);
            return true;
        }

        // 测试单元41：移除学生
        public boolean removeStudent(Student student) {
            if (!students.contains(student)) {
                return false;
            }
            students.remove(student);
            studentGrades.remove(student.getUserId());
            return true;
        }

        // 测试单元42：录入学生成绩
        public boolean recordStudentGrade(String studentId, double grade) {
            if (grade < 0 || grade > 100) {
                return false;
            }
            // 检查学生是否在课程中
            for (Student student : students) {
                if (student.getUserId().equals(studentId)) {
                    studentGrades.put(studentId, grade);
                    return true;
                }
            }
            return false;
        }

        // 测试单元43：获取学生成绩
        public double getStudentGrade(String studentId) {
            return studentGrades.getOrDefault(studentId, -1.0);
        }

        // 测试单元44：计算课程平均分
        public double calculateAverageGrade() {
            if (studentGrades.isEmpty()) {
                return 0.0;
            }
            double sum = 0;
            for (double grade : studentGrades.values()) {
                sum += grade;
            }
            return sum / studentGrades.size();
        }

        // 测试单元45：获取成绩分布
        public Map<String, Integer> getGradeDistribution() {
            Map<String, Integer> distribution = new LinkedHashMap<>();
            distribution.put("A (90-100)", 0);
            distribution.put("B (80-89)", 0);
            distribution.put("C (70-79)", 0);
            distribution.put("D (60-69)", 0);
            distribution.put("F (<60)", 0);

            for (double grade : studentGrades.values()) {
                if (grade >= 90) distribution.put("A (90-100)", distribution.get("A (90-100)") + 1);
                else if (grade >= 80) distribution.put("B (80-89)", distribution.get("B (80-89)") + 1);
                else if (grade >= 70) distribution.put("C (70-79)", distribution.get("C (70-79)") + 1);
                else if (grade >= 60) distribution.put("D (60-69)", distribution.get("D (60-69)") + 1);
                else distribution.put("F (<60)", distribution.get("F (<60)") + 1);
            }
            return distribution;
        }

        // 测试单元10：获取高分学生（成绩≥90）数量（旧版功能）
        public int getHighAchievingStudentsCountOld() {
            int count = 0;
            for (double grade : studentGrades.values()) {
                if (grade >= 90) {
                    count++;
                }
            }
            return count;
        }

        // 测试单元6：getter方法（旧版功能）
        public String getCourseId() { return courseId; }
        public String getCourseName() { return courseName; }
        public Teacher getTeacher() { return teacher; }
        public int getCredits() { return credits; }
        public List<Student> getStudents() { return new ArrayList<>(students); }
    }
