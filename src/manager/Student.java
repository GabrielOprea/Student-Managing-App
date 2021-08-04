package manager;/*
EXERCISE 2 - manager.StudentManager

You need to create a java application that will work as a student manager, with this application we should be able to do:

create new student (insert: FirstName, LastName,
registration number)
show all students (prin: FirstName, LastName, number of courses)
add a course to the student (insert registration number, manager.Course Title, Description)
show student's courses (print: manager.Course title, Description)
All data should be kept in the file, writing and reading should happen via serialization and deserilization operations.


Acceptance Criteria

Create new students - by running this command:

registration number should be unique,  dot'n forget about validation


Show All Students - by running this command:

java -jar myaplication.jar -showAllStudents

Add a course to the student - by running this command:

java -jar myaplication.jar -addCourse -rn=7 -ct='manager.Course Title' -cd='manager.Course Description'

Show students's courses - by running this command:

java -jar myaplication.jar -showCourses -rn=7
 */

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
