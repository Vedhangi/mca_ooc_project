package timetable;





import java.time.Duration;
import java.time.LocalTime;

public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;

    // Constructor
    public TimeSlot(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    // Additional methods
    public int calculateDurationInMinutes() {
        Duration duration = Duration.between(startTime, endTime);
        return (int) duration.toMinutes();
    }

    public boolean overlapsWith(TimeSlot otherTimeSlot) {
        return this.startTime.isBefore(otherTimeSlot.endTime) && otherTimeSlot.startTime.isBefore(this.endTime);
    }

    // Comparison for sorting
    public int compareTo(TimeSlot otherTimeSlot) {
        return this.startTime.compareTo(otherTimeSlot.startTime);
    }

	public static TimeSlot[] getAvailableTimeSlots() {
		// TODO Auto-generated method stub
        TimeSlot[] availableTimeSlots = new TimeSlot[3];
        availableTimeSlots[0] = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(10, 30));
        availableTimeSlots[1] = new TimeSlot(LocalTime.of(11, 0), LocalTime.of(12, 30));
        availableTimeSlots[2] = new TimeSlot(LocalTime.of(13, 0), LocalTime.of(14, 30));
        return availableTimeSlots;
		
	}
}
