package timetable;


import java.util.ArrayList;
import java.util.List;

public class Instructor {
    private String email;
    private String name;
    private List<Course> courses = new ArrayList<>();

    public Instructor(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public List<Course> getCourses() {
        return courses;
    }

    // Additional methods
    public void addCourse(Course course) {
        courses.add(course);
    }
}

