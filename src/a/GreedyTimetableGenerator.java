
package a;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Subject {
    String name;
    int credits;

    public Subject(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }
}

class Timetable {
    String[][] schedule;
    List<String> classTimings;

    public Timetable(int days, List<String> classTimings) {
        schedule = new String[days][classTimings.size()];
        this.classTimings = classTimings;
    }

    public void addClass(int day, int classIndex, String className) {
        schedule[day][classIndex] = className;
    }

    public void display() {
        for (int day = 0; day < schedule.length; day++) {
            System.out.print("Day " + (day + 1) + ": ");
            for (int classIndex = 0; classIndex < schedule[day].length; classIndex++) {
                if (schedule[day][classIndex] != null) {
                    System.out.print(classTimings.get(classIndex) + " - " + schedule[day][classIndex] + " | ");
                } else {
                    System.out.print("FREE | ");
                }
            }
            System.out.println();
        }
    }
}

public class GreedyTimetableGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the number of days
        System.out.print("Enter the number of days in the timetable: ");
        int days = scanner.nextInt();

        // Input subjects and their credits
        System.out.print("Enter the number of subjects: ");
        int numSubjects = scanner.nextInt();
        List<Subject> subjects = new ArrayList<>();

        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter subject name: ");
            String subjectName = scanner.next();
            System.out.print("Enter subject credits: ");
            int subjectCredits = scanner.nextInt();
            subjects.add(new Subject(subjectName, subjectCredits));
        }

        // Predefined class timings
        List<String> classTimings = new ArrayList<>();
        classTimings.add("9:00 AM - 10:00 AM");
        classTimings.add("10:15 AM - 11:15 AM");
        classTimings.add("11:30 AM - 12:30 PM");

        // Create a timetable
        Timetable timetable = new Timetable(days, classTimings);

        // Schedule classes based on subject credits
        int subjectIndex = 0;
        for (int day = 0; day < days; day++) {
            for (String timing : classTimings) {
                Subject subject = subjects.get(subjectIndex);
                if (subject.credits > 0) {
                    timetable.addClass(day, classTimings.indexOf(timing), subject.name);
                    subject.credits--;
                }
                subjectIndex = (subjectIndex + 1) % numSubjects; // Move to the next subject
            }
        }

        // Display the timetable
        System.out.println("Generated Timetable:");
        timetable.display();

        scanner.close();
    }
}