package PZ_8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// абстрактный класс
abstract class Person {
    protected String name;
    protected int id;
    protected String email;

    public Person(String name, int id, String email) {
        this.name = name;
        this.id = id;
        this.email = email;
    }

    // абстрактный метод
    public abstract String getRole();

    // геттеры
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %d, Email: %s, Роль: %s)",
                name, id, email, getRole());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

class Student extends Person {
    private final Map<Course, Double> grades; // Курс -> оценка
    private final Department department;

    public Student(String name, int id, String email, Department department) {
        super(name, id, email);
        this.grades = new HashMap<>();
        this.department = department;
    }

    @Override
    public String getRole() {
        return "Студент";
    }

    public void addGrade(Course course, double grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("Оценка должна быть от 0 до 100");
        }
        grades.put(course, grade);
    }

    public Double getGrade(Course course) {
        return grades.get(course);
    }

    public double calculateAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (double grade : grades.values()) {
            sum += grade;
        }
        return sum / grades.size();
    }

    public Department getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return String.format("%s, Факультет: %s, Средний балл: %.2f",
                super.toString(), department.getName(), calculateAverageGrade());
    }
}

class Teacher extends Person {
    private final String specialization;
    private final List<Course> courses;
    private final Department department;

    public Teacher(String name, int id, String email, String specialization, Department department) {
        super(name, id, email);
        this.specialization = specialization;
        this.courses = new ArrayList<>();
        this.department = department;
    }

    @Override
    public String getRole() {
        return "Преподаватель";
    }

    public void assignToCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            course.setTeacher(this);
        }
    }

    public void removeFromCourse(Course course) {
        courses.remove(course);
        if (course.getTeacher() == this) {
            course.setTeacher(null);
        }
    }

    public String getSpecialization() {
        return specialization;
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public Department getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return String.format("%s, Специализация: %s, Факультет: %s, Курсы: %d",
                super.toString(), specialization, department.getName(), courses.size());
    }
}

class Course {
    private final String courseCode;
    private final String courseName;
    private final int credits;
    private Teacher teacher;
    private final List<Student> enrolledStudents;
    private final Department department;

    public Course(String courseCode, String courseName, int credits, Department department) {
        if (credits <= 0) {
            throw new IllegalArgumentException("Кредиты должны быть положительным числом");
        }
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.department = department;
        this.enrolledStudents = new ArrayList<>();
    }

    public void enrollStudent(Student student) {
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    public void unenrollStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getEnrolledStudents() {
        return new ArrayList<>(enrolledStudents);
    }

    public Department getDepartment() {
        return department;
    }

    public int getEnrollmentCount() {
        return enrolledStudents.size();
    }

    @Override
    public String toString() {
        String teacherInfo = (teacher != null) ? teacher.getName() : "Не назначен";
        return String.format("Курс: %s - %s (Кредиты: %d)\nПреподаватель: %s\nСтудентов: %d\nФакультет: %s",
                courseCode, courseName, credits, teacherInfo, getEnrollmentCount(), department.getName());
    }
}

class Department {
    private String name;
    private String code;
    private List<Course> courses;
    private List<Teacher> teachers;
    private List<Student> students;

    public Department(String name, String code) {
        this.name = name;
        this.code = code;
        this.courses = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public void addTeacher(Teacher teacher) {
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
        }
    }

    public void enrollStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public List<Teacher> getTeachers() {
        return new ArrayList<>(teachers);
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public int getStudentCount() {
        return students.size();
    }

    public int getTeacherCount() {
        return teachers.size();
    }

    public int getCourseCount() {
        return courses.size();
    }

    @Override
    public String toString() {
        return String.format("Факультет: %s (%s)\nСтудентов: %d, Преподавателей: %d, Курсов: %d",
                name, code, getStudentCount(), getTeacherCount(), getCourseCount());
    }
}

public class PZ_8 {
    public static void main(String[] args) {
        try {
            Department csDepartment = new Department("Компьютерные науки", "CS");
            Department mathDepartment = new Department("Математика", "MATH");

            Course javaCourse = new Course("CS101", "Программирование на Java", 4, csDepartment);
            Course algorithmsCourse = new Course("CS102", "Алгоритмы и структуры данных", 3, csDepartment);
            Course calculusCourse = new Course("MATH101", "Математический анализ", 5, mathDepartment);

            // добав курсы на факультеты
            csDepartment.addCourse(javaCourse);
            csDepartment.addCourse(algorithmsCourse);
            mathDepartment.addCourse(calculusCourse);

            Teacher profSmith = new Teacher("Джон Смит", 1001, "smith@university.edu",
                    "Программирование", csDepartment);
            Teacher profJohnson = new Teacher("Анна Джонсон", 1002, "johnson@university.edu",
                    "Математика", mathDepartment);

            csDepartment.addTeacher(profSmith);
            mathDepartment.addTeacher(profJohnson);

            profSmith.assignToCourse(javaCourse);
            profSmith.assignToCourse(algorithmsCourse);
            profJohnson.assignToCourse(calculusCourse);

            Student student1 = new Student("Иван Петров", 2001, "petrov@student.edu", csDepartment);
            Student student2 = new Student("Мария Сидорова", 2002, "sidorova@student.edu", csDepartment);
            Student student3 = new Student("Алексей Козлов", 2003, "kozlov@student.edu", mathDepartment);

            csDepartment.enrollStudent(student1);
            csDepartment.enrollStudent(student2);
            mathDepartment.enrollStudent(student3);

            javaCourse.enrollStudent(student1);
            javaCourse.enrollStudent(student2);
            algorithmsCourse.enrollStudent(student1);
            calculusCourse.enrollStudent(student3);
            calculusCourse.enrollStudent(student1);

            student1.addGrade(javaCourse, 85.5);
            student1.addGrade(algorithmsCourse, 92.0);
            student1.addGrade(calculusCourse, 78.5);

            student2.addGrade(javaCourse, 90.0);
            student3.addGrade(calculusCourse, 95.5);


            System.out.println("Информация об университете");

            System.out.println(csDepartment);
            System.out.println();
            System.out.println(mathDepartment);
            System.out.println();

            System.out.println("курсы");
            List<Course> allCourses = new ArrayList<>();
            allCourses.addAll(csDepartment.getCourses());
            allCourses.addAll(mathDepartment.getCourses());

            for (Course course : allCourses) {
                System.out.println(course);
                System.out.println("---");
            }

            System.out.println("преподаватели");
            List<Teacher> allTeachers = new ArrayList<>();
            allTeachers.addAll(csDepartment.getTeachers());
            allTeachers.addAll(mathDepartment.getTeachers());

            for (Teacher teacher : allTeachers) {
                System.out.println(teacher);
            }

            System.out.println("студенты и успеваемость");
            List<Student> allStudents = new ArrayList<>();
            allStudents.addAll(csDepartment.getStudents());
            allStudents.addAll(mathDepartment.getStudents());

            for (Student student : allStudents) {
                System.out.println(student);
                for (Course course : allCourses) {
                    Double grade = student.getGrade(course);
                    if (grade != null) {
                        System.out.printf("  %s: %.2f\n", course.getCourseName(), grade);
                    }
                }
                System.out.println();
            }

            System.out.println("обработка исключений");
            try {
                student1.addGrade(javaCourse, 150.0); // Некорректная оценка
            } catch (IllegalArgumentException e) {
                System.out.println("ошибка! " + e.getMessage());
            }

            try {
                Course invalidCourse = new Course("TEST", "Тестовый курс", -1, csDepartment);
            } catch (IllegalArgumentException e) {
                System.out.println("ошибка! " + e.getMessage());
            }

            System.out.println("статистика");
            System.out.printf("Всего студентов в университете: %d\n",
                    csDepartment.getStudentCount() + mathDepartment.getStudentCount());
            System.out.printf("Всего преподавателей: %d\n",
                    csDepartment.getTeacherCount() + mathDepartment.getTeacherCount());
            System.out.printf("Всего курсов: %d\n",
                    csDepartment.getCourseCount() + mathDepartment.getCourseCount());

        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}