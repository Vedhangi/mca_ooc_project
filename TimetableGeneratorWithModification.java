import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    public String getClass(int day, int classIndex) {
        return schedule[day][classIndex];
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

public class TimetableGeneratorWithModification {
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

        Random random = new Random();

        // Assign classes randomly without repeating patterns
        int subjectIndex = 0;
        for (int day = 0; day < days; day++) {
            for (int classIndex = 0; classIndex < classTimings.size(); classIndex++) {
                Subject subject = subjects.get(subjectIndex);
                if (subject.credits > 0) {
                    timetable.addClass(day, classIndex, subject.name);
                    subject.credits--;
                }
                subjectIndex = (subjectIndex + 1) % numSubjects;
            }
        }

        // Display the initial timetable
        System.out.println("Generated Timetable:");
        timetable.display();

        // Allow for modification
        while (true) {
            System.out.print("Do you want to modify the timetable (y/n): ");
            String modifyChoice = scanner.next().toLowerCase();
            if (modifyChoice.equals("n")) {
                break;
            } else if (modifyChoice.equals("y")) {
                System.out.print("Enter the day to modify (1-" + days + "): ");
                int dayToModify = scanner.nextInt();
                dayToModify--; // Convert to 0-based index
                System.out.print("Enter the class index to modify (1-" + classTimings.size() + "): ");
                int classIndexToModify = scanner.nextInt();
                classIndexToModify--; // Convert to 0-based index

                if (timetable.getClass(dayToModify, classIndexToModify) == null) {
                    System.out.print("Enter the subject name to assign: ");
                    String subjectNameToAssign = scanner.next();
                    timetable.addClass(dayToModify, classIndexToModify, subjectNameToAssign);
                    System.out.println("Timetable updated.");
                    timetable.display();
                } else {
                    System.out.println("Cannot modify a non-null slot. Try again.");
                }
            }
        }

        scanner.close();
    }
}
