package manager;

import java.io.Serializable;

public class Course implements Serializable {

    private final String courseTitle;
    private final String description;

    public Course(String courseTitle, String description) {
        this.courseTitle = courseTitle;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Course of " +  courseTitle + ", description: " + description + ".";
    }
}
