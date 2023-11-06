package timetable;

public class InstructorController {
    private Instructor loggedInInstructor;

    // Method to authenticate and log in an instructor
    public boolean login(String email) {
        // Assume there is a list of instructors with their emails
        // Replace the sample logic below with your actual authentication mechanism
        if (email.equals("instructor@example.com")) {
            loggedInInstructor = new Instructor(email, "John Doe");
            return true;
        }
        return false;
    }

    // Method to log out the instructor
    public void logout() {
        loggedInInstructor = null;
    }

    // Method to handle entering course details
    public void enterCourseDetails(String courseName, int numberOfStudents) {
        // Check if an instructor is logged in
        if (loggedInInstructor == null) {
            System.out.println("Please log in first.");
            return;
        }

        // Create a new course and associate it with the logged-in instructor
        Course newCourse = new Course(courseName, numberOfStudents);
        loggedInInstructor.addCourse(newCourse);

        System.out.println("Course details added successfully.");
    }

    // Method to generate timetable based on instructor's course details
    public void generateTimetable() {
        // Check if an instructor is logged in
        if (loggedInInstructor == null) {
            System.out.println("Please log in first.");
            return;
        }

        // Get the list of courses associated with the logged-in instructor
        for (Course course : loggedInInstructor.getCourses()) {
            System.out.println("Generating timetable for course: " + course.getCourseName());

            // Assume there is a list of available time slots
            // Replace with your actual logic for generating timetables
            for (TimeSlot timeSlot : TimeSlot.getAvailableTimeSlots()) {
                // Check if the time slot fits the course's duration
                if (course.getDuration() <= timeSlot.calculateDurationInMinutes()) {
                    // Allocate the time slot to the course
                    course.allocateTimeSlot(timeSlot);
                    System.out.println("Time slot allocated: " + timeSlot.getStartTime() + " - " + timeSlot.getEndTime());
                    break; // Break out of the loop after allocating a time slot
                }
            }
        }
    }
}
