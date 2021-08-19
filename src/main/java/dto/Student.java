package dto;

import annotations.EmailField;
import email.EmailHandler;
import annotations.EmailText;
import java.util.logging.Level;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

@Entity
@EmailText("Student {name}/{surname} identified by id: {studentId} has been created.")
@Table(name = "studentmanaging.students")
public class Student implements Serializable {
    private static Logger logger = Logger.getLogger(Student.class.getName());

    @Id
    @EmailField("studentId")
    @Column(name = "registrationNumber", unique = true)
    private int registrationNumber;

    @EmailField("name")
    @Column(name = "firstName")
    private String firstName;

    @EmailField("surname")
    @Column(name = "lastName")
    private String lastName;

    @ElementCollection
    private List<Course> courses;

    @ManyToMany
    @JoinTable(
            name = "studentmanaging.studentstocourses",
            joinColumns = {@JoinColumn(name = "registrationNumber")},
            inverseJoinColumns = {@JoinColumn(name = "title")}
    )
    private Set<Course> courseSet = new HashSet<>();
    
    public Student() {
    }

    public Student(String firstName, String lastName, int registrationNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationNumber = registrationNumber;
        courses = new ArrayList<>();
        try {
            EmailHandler.method(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void addCourse(Course course) {
        courseSet.add(course);
    }

    public Set<Course> getCourseSet() {
        return courseSet;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addCourse(String courseTitle, String description) {
        courses.add(new Course(courseTitle, description, registrationNumber));

    }

    public void printCourses() {
        courses.forEach(course -> logger.log(Level.INFO, course.toString()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return registrationNumber == student.registrationNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber);
    }

    @Override
    public String toString() {
        return "Student " + firstName + " " + lastName + " with ID: " + registrationNumber;
    }
}
