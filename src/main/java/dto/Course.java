package dto;

import annotations.EmailField;
import email.EmailHandler;
import annotations.EmailText;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@EmailText("Course {title} {description} was associated to student with ID: {studentId}")
@Table(name = "studentmanaging.courses")
public class Course implements Serializable {

    @Id
    @EmailField("title")
    @Column (name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "description")
    @EmailField("description")
    private String description;

    @EmailField("studentId")
    private transient int registrationNumber;

    @ManyToMany(mappedBy = "courseSet")
    private Set<Student> studentSet = new HashSet<Student>();

    public Course() {

    }

    public Course(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Course(String title, String description, int registrationNumber) {
        this(title, description);
        this.registrationNumber = registrationNumber;
        try {
            EmailHandler.method(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Course of " + title + ", description: " + description + ".";
    }
}
