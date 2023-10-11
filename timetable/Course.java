package timetable;


public class Course {
    private String courseName;
    private int numberOfStudents;

    // Constructor
    public Course(String courseName, int numberOfStudents) {
        this.courseName = courseName;
        this.numberOfStudents = numberOfStudents;
    }

    // Getters and Setters
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
    

    private TimeSlot allocatedTimeSlot;

    public TimeSlot getAllocatedTimeSlot() {
        return allocatedTimeSlot;
    }


	public void allocateTimeSlot(TimeSlot timeSlot) {
		// TODO Auto-generated method stub
		allocatedTimeSlot = timeSlot;
	}

    public int getDuration() {
        // Assuming you have a predefined duration for each course
        // Modify this to match your actual course durations
        if (courseName.equals("Math")) {
            return 60; // Assuming Math class lasts for 60 minutes
        } else if (courseName.equals("Science")) {
            return 45; // Assuming Science class lasts for 45 minutes
        } else {
            return 0; // Default case, handle it as per your requirements
        }
    }
	
}


