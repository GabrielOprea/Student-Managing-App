import java.io.Serializable;

public class Course implements Serializable {
    //insert registration number, Course Title, Description
    private String courseTitle;
    private String description;

    public Course(String courseTitle, String description) {
        this.courseTitle = courseTitle;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Course of " +  courseTitle + " description: " + description + ".";
    }
}
