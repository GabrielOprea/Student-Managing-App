package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student implements Serializable {
    private final String firstName;
    private final String lastName;
    private final int registrationNum;
    private final List<Course> courses;

    public Student(String firstName, String lastName, int registrationNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationNum = registrationNum;
        courses = new ArrayList<>();
    }

    public int getRegistrationNum() {
        return registrationNum;
    }

    public void addCourse(String courseTitle, String description) {
        courses.add(new Course(courseTitle, description));

    }

    public void printCourses() {
        courses.forEach(System.out::println);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return registrationNum == student.registrationNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNum);
    }

    @Override
    public String toString() {
        return "Student " + firstName + " " + lastName + " with ID: " + registrationNum;
    }
}
