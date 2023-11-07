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

public class TimetableGeneratorWithSwitch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int days = 0;
        int numSubjects = 0;
        List<Subject> subjects = new ArrayList<>();
        List<String> classTimings = new ArrayList<>();
        Timetable timetable = null;

        while (true) {
            System.out.println("1. Generate Timetable");
            System.out.println("2. Modify Timetable");
            System.out.println("3. Display Timetable");
            System.out.println("4. Extra class");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter the number of days in the timetable: ");
                    days = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    System.out.print("Enter the number of subjects: ");
                    numSubjects = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    for (int i = 0; i < numSubjects; i++) {
                        System.out.print("Enter subject name: ");
                        String subjectName = scanner.next();
                        System.out.print("Enter subject credits: ");
                        int subjectCredits = scanner.nextInt();
                        subjects.add(new Subject(subjectName, subjectCredits));
                    }

                    classTimings.add("9:30 AM - 10:30 AM");
                    classTimings.add("10:30 AM - 11:30 AM");
                    classTimings.add("11:30 AM - 12:30 PM");
                    classTimings.add("01:30 PM - 02:30 PM");
                    classTimings.add("02:30 PM - 03:30 PM");
                    classTimings.add("03:30 PM - 04:30 PM");
                    classTimings.add("04:30 PM - 05:30 PM");

                    timetable = new Timetable(days, classTimings);

                    Random random = new Random();
                    int subjectIndex = 0;
                    for (int day = 0; day < days; day++) {
                        List<String> assignedSubjects = new ArrayList<>(); // To keep track of assigned subjects

                        for (int classIndex = 0; classIndex < classTimings.size(); classIndex++) {
                            Subject subject = subjects.get(subjectIndex);

                            if (subject.credits > 0) {
                                // Check if the slot is available and before 12:30
                                if (classIndex < classTimings.size() - 1 && timetable.getClass(day, classIndex) == null) {
                                    // Check if the subject is not already assigned on the same day
                                    if (!assignedSubjects.contains(subject.name)) {
                                        timetable.addClass(day, classIndex, subject.name);
                                        subject.credits--;
                                        assignedSubjects.add(subject.name);
                                    }
                                }
                            }

                            subjectIndex = (subjectIndex + 1) % numSubjects;
                        }
                    }
                    System.out.println("Timetable generated.");
                    break;

                case 2:
                    if (timetable == null) {
                        System.out.println("Please generate the timetable first.");
                    } else {
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
                    break;

                case 3:
                    if (timetable == null) {
                        System.out.println("Please generate the timetable first.");
                    } else {
                        System.out.println("Timetable:");
                        timetable.display();
                    }
                    break;

                    case 4:
                    if (timetable == null) {
                        System.out.println("Please generate the timetable first.");
                    } else {
                        System.out.print("Enter the day to add an extra class (1-" + days + "): ");
                        int dayToAddClass = scanner.nextInt();
                        dayToAddClass--; // Convert to 0-based index
                
                        // Check if the day is valid
                        if (dayToAddClass < 0 || dayToAddClass >= days) {
                            System.out.println("Invalid day. Please try again.");
                            break;
                        }
                
                        System.out.print("Enter the number of hours for the extra class: ");
                        int hoursToAdd = scanner.nextInt();
                
                        // Check if the class slots are available
                        boolean canAddClass = true;
                        int classIndexToAdd = -1;
                
                        for (int i = 0; i < classTimings.size(); i++) {
                            System.out.println((i + 1) + ". " + classTimings.get(i));
                        }
                
                        System.out.print("Select the starting time for the extra class (1-" + classTimings.size() + "): ");
                        int selectedTime = scanner.nextInt();
                
                        if (selectedTime < 1 || selectedTime > classTimings.size()) {
                            System.out.println("Invalid starting time selection. Please try again.");
                        } else {
                            classIndexToAdd = selectedTime - 1;
                
                            if (classIndexToAdd + hoursToAdd > classTimings.size()) {
                                System.out.println("Not enough time slots available for the specified duration.");
                            } else {
                                for (int i = classIndexToAdd; i < classIndexToAdd + hoursToAdd; i++) {
                                    if (timetable.getClass(dayToAddClass, i) != null) {
                                        canAddClass = false;
                                        break;
                                    }
                                }
                
                                if (canAddClass) {
                                    System.out.print("Enter the subject name for the extra class: ");
                                    String subjectNameToAdd = scanner.next();
                
                                    // Add the extra class to the timetable
                                    for (int i = classIndexToAdd; i < classIndexToAdd + hoursToAdd; i++) {
                                        timetable.addClass(dayToAddClass, i, subjectNameToAdd);
                                    }
                
                                    System.out.println("Extra class added to the timetable.");
                                    timetable.display();
                                } else {
                                    System.out.println("Cannot add the class at the provided time. The slots are not available.");
                                }
                            }
                        }
                    }
                    break;
                

                case 5:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
