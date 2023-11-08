import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TimetableGeneratorSwing extends JFrame {
    private int days = 0;
    private int numSubjects = 0;
    private List<Subject> subjects = new ArrayList<>();
    private List<String> classTimings = new ArrayList<>();
    private Timetable timetable = null;

    public TimetableGeneratorSwing() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Timetable Generator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);

        JMenuItem generateMenuItem = new JMenuItem("Generate Timetable");
        JMenuItem modifyMenuItem = new JMenuItem("Modify Timetable");
        JMenuItem displayMenuItem = new JMenuItem("Display Timetable");
        JMenuItem extraClassMenuItem = new JMenuItem("Add Extra Class");
        JMenuItem commentclassMenuItem = new JMenuItem("Add comment ");

        JMenuItem exitMenuItem = new JMenuItem("Exit");

        optionsMenu.add(generateMenuItem);
        optionsMenu.add(modifyMenuItem);
        optionsMenu.add(displayMenuItem);
        optionsMenu.add(extraClassMenuItem);
        optionsMenu.add(commentclassMenuItem);
        optionsMenu.add(exitMenuItem);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // Make the text area read-only
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        generateMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateTimetable();
                displayTimetable(textArea);
            }
        });

        modifyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyTimetable(textArea);
            }
        });

        displayMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTimetable(textArea);
            }
        });

        extraClassMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExtraClass(textArea);
            }
        });
        commentclassMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                comment(textArea);
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
    }
    
    

    private void generateTimetable() {
        
        
            JTextField daysField = new JTextField();
            JTextField numSubjectsField = new JTextField();
        
            JPanel inputPanel = new JPanel(new GridLayout(0, 2));
            inputPanel.add(new JLabel("Enter the number of days in the timetable:"));
            inputPanel.add(daysField);
            inputPanel.add(new JLabel("Enter the number of subjects:"));
            inputPanel.add(numSubjectsField);
        
            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Generate Timetable", JOptionPane.OK_CANCEL_OPTION);
        
            if (result == JOptionPane.OK_OPTION) {
                try {
                    days = Integer.parseInt(daysField.getText());
                    numSubjects = Integer.parseInt(numSubjectsField.getText());
        
                    if (days <= 0 || numSubjects <= 0) {
                        JOptionPane.showMessageDialog(this, "Please enter valid numbers for days and subjects.");
                        return;
                    }
        
                    subjects.clear(); // Clear any existing subjects
                    classTimings.clear(); // Clear any existing class timings
        
                    for (int i = 0; i < numSubjects; i++) {
                        JTextField nameField = new JTextField();
                        JTextField creditsField = new JTextField();
        
                        JPanel subjectPanel = new JPanel(new GridLayout(0, 2));
                        subjectPanel.add(new JLabel("Enter subject name for Subject " + (i + 1) + ":"));
                        subjectPanel.add(nameField);
                        subjectPanel.add(new JLabel("Enter subject credits for Subject " + (i + 1) + ":"));
                        subjectPanel.add(creditsField);
        
                        int subjectResult = JOptionPane.showConfirmDialog(this, subjectPanel, "Subject Details", JOptionPane.OK_CANCEL_OPTION);
        
                        if (subjectResult != JOptionPane.OK_OPTION) {
                            JOptionPane.showMessageDialog(this, "Subject details for Subject " + (i + 1) +"were not entered.");
                            return;
                        }
        
                        String subjectName = nameField.getText();
                        int subjectCredits = Integer.parseInt(creditsField.getText());
        
                        if (subjectCredits <= 0) {
                            JOptionPane.showMessageDialog(this, "Please enter a valid positive number for subject credits.");
                            return;
                        }
        
                        subjects.add(new Subject(subjectName, subjectCredits));
                    }
        
                    classTimings.add("9:30 AM - 10:30 AM");
                    classTimings.add("10:30 AM - 11:30 AM");
                    classTimings.add("11:30 AM - 12:30 PM");
                    classTimings.add("01:30 PM - 02:30 PM");
                    classTimings.add("02:30 PM - 03:30 PM");
                    classTimings.add("03:30 PM - 04:30 PM");
                    classTimings.add("04:30 PM - 05:30 PM");
        
                    timetable = new Timetable(days, classTimings.size());
        
                    Random random = new Random();
                    int subjectIndex = 0;
        
                    for (int day = 0; day < days; day++) {
                        List<String> assignedSubjects = new ArrayList<>(); // To keep track of assigned subjects
        
                        for (int classIndex = 0; classIndex < classTimings.size(); classIndex++) {
                            Subject subject = subjects.get(subjectIndex);
        
                            if (subject.credits > 0) {
                                // Check if the slot is available and before 12:30
                                if (classIndex < 3 && timetable.getClass(day, classIndex) == null) {
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
        
                    JOptionPane.showMessageDialog(this, "Timetable generated successfully.");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numbers for days and subjects.");
                }
            }
        
        
        
        
    }

    private void modifyTimetable(JTextArea textArea) {
        // Implement your code for modifying the timetable here
        // You can use the 'timetable' object and update it as needed.
        // After modifications, update the text area using 'textArea.setText()' and 'displayTimetable()'.
        
    if (timetable == null) {
        JOptionPane.showMessageDialog(this, "Please generate the timetable first.");
    } else {
        int modifyOption = JOptionPane.showOptionDialog(
            this,
            "1. Modify existing class\n2. Cancel a class (set to null)",
            "Modify Timetable",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[] { "Modify", "Cancel" },
            "Modify"
        );

        if (modifyOption == 0) {
            String dayToModifyStr = JOptionPane.showInputDialog(this, "Enter the day to modify (1-" + days + "):");
            String classIndexToModifyStr = JOptionPane.showInputDialog(this, "Enter the class index to modify (1-" + classTimings.size() + "):");

            try {
                int dayToModify = Integer.parseInt(dayToModifyStr);
                int classIndexToModify = Integer.parseInt(classIndexToModifyStr);

                dayToModify--; // Convert to 0-based index
                classIndexToModify--; // Convert to 0-based index

                if (dayToModify < 0 || dayToModify >= days || classIndexToModify < 0 || classIndexToModify >= classTimings.size()) {
                    JOptionPane.showMessageDialog(this, "Invalid day or class index. Please try again.");
                } else {
                    String currentClass = timetable.getClass(dayToModify, classIndexToModify);
                    if (currentClass == null) {
                        JOptionPane.showMessageDialog(this, "Cannot modify a null slot. Please try again.");
                    } else {
                        String newSubjectName = JOptionPane.showInputDialog(this, "Enter the new subject name:");
                        timetable.addClass(dayToModify, classIndexToModify, newSubjectName);
                        JOptionPane.showMessageDialog(this, "Class modified.");
                        displayTimetable(textArea);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers for day and class index.");
            }
        } else if (modifyOption == 1) {
            String dayToCancelStr = JOptionPane.showInputDialog(this, "Enter the day to cancel a class (1-" + days + "):");
            String classIndexToCancelStr = JOptionPane.showInputDialog(this, "Enter the class index to cancel (1-" + classTimings.size() + "):");

            try {
                int dayToCancel = Integer.parseInt(dayToCancelStr);
                int classIndexToCancel = Integer.parseInt(classIndexToCancelStr);

                dayToCancel--; // Convert to 0-based index
                classIndexToCancel--; // Convert to 0-based index

                if (dayToCancel < 0 || dayToCancel >= days || classIndexToCancel < 0 || classIndexToCancel >= classTimings.size()) {
                    JOptionPane.showMessageDialog(this, "Invalid day or class index. Please try again.");
                } else {
                    String currentClass = timetable.getClass(dayToCancel, classIndexToCancel);
                    if (currentClass == null) {
                        JOptionPane.showMessageDialog(this, "The selected slot is already null.");
                    } else {
                        timetable.addClass(dayToCancel, classIndexToCancel, null);
                        JOptionPane.showMessageDialog(this, "Class canceled.");
                        displayTimetable(textArea);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers for day and class index.");
            }
        }
    }
   

            
        
    }

    private void displayTimetable(JTextArea textArea) {
        // Implement your code for displaying the timetable here
        // You can use the 'timetable' object to get the timetable information.
        // Update the text area using 'textArea.setText()' and 'displayTimetable()'.
        
            if (timetable == null) {
                textArea.setText("Please generate the timetable first.");
            } else {
                textArea.setText("Timetable:\n");
                String timetableStr = generateTimetableString(timetable, classTimings);
                textArea.append(timetableStr);
            }
        }
        
        // This method generates the timetable string based on your existing logic.
        private String generateTimetableString(Timetable timetable, List<String> classTimings) {
            StringBuilder timetableStr = new StringBuilder();
            for (int day = 0; day < timetable.schedule.length; day++) {
                timetableStr.append("Day ").append(day + 1).append(": ");
                for (int classIndex = 0; classIndex < timetable.schedule[day].length; classIndex++) {
                    String className = timetable.getClass(day, classIndex);
                    if (className != null) {
                        timetableStr.append(classTimings.get(classIndex)).append(" - ").append(className).append(" | ");
                    } else {
                        timetableStr.append("FREE | ");
                    }
                }
                timetableStr.append("\n");
            }
            return timetableStr.toString();
        
        
    }

    private void addExtraClass(JTextArea textArea) {
        // Implement your code for adding an extra class here
        // You can use the 'timetable' object and update it as needed.
        // After adding an extra class, update the text area using 'textArea.setText()' and 'displayTimetable()'.
        
    if (timetable == null) {
        JOptionPane.showMessageDialog(this, "Please generate the timetable first.");
    } else {
        String dayToAddClassStr = JOptionPane.showInputDialog(this, "Enter the day to add an extra class (1-" + days + "):");
        
        try {
            int dayToAddClass = Integer.parseInt(dayToAddClassStr);
            dayToAddClass--; // Convert to 0-based index

            if (dayToAddClass < 0 || dayToAddClass >= days) {
                JOptionPane.showMessageDialog(this, "Invalid day. Please try again.");
            } else {
                String hoursToAddStr = JOptionPane.showInputDialog(this, "Enter the number of hours for the extra class:");
                int hoursToAdd = Integer.parseInt(hoursToAddStr);

                boolean canAddClass = true;
                int classIndexToAdd = -1;

                for (int i = 0; i < classTimings.size(); i++) {
                    System.out.println((i + 1) + ". " + classTimings.get(i));
                }

                String selectedTimeStr = JOptionPane.showInputDialog(this, "Select the starting time for the extra class (1-" + classTimings.size() + "):");
                int selectedTime = Integer.parseInt(selectedTimeStr);

                if (selectedTime < 1 || selectedTime > classTimings.size()) {
                    JOptionPane.showMessageDialog(this, "Invalid starting time selection. Please try again.");
                } else {
                    classIndexToAdd = selectedTime - 1;

                    if (classIndexToAdd + hoursToAdd > classTimings.size()) {
                        JOptionPane.showMessageDialog(this, "Not enough time slots available for the specified duration.");
                    } else {
                        for (int i = classIndexToAdd; i < classIndexToAdd + hoursToAdd; i++) {
                            if (timetable.getClass(dayToAddClass, i) != null) {
                                canAddClass = false;
                                break;
                            }
                        }

                        if (canAddClass) {
                            String subjectNameToAdd = JOptionPane.showInputDialog(this, "Enter the subject name for the extra class:");

                            for (int i = classIndexToAdd; i < classIndexToAdd + hoursToAdd; i++) {
                                timetable.addClass(dayToAddClass, i, subjectNameToAdd);
                            }

                            JOptionPane.showMessageDialog(this, "Extra class added to the timetable.");
                            displayTimetable(textArea);
                        } else {
                            JOptionPane.showMessageDialog(this, "Cannot add the class at the provided time. The slots are not available.");
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers for day, hours, and class index.");
        }
    }
    

    }
    public void comment(JTextArea textArea)
    {
        
        
        if (timetable == null) {
            JOptionPane.showMessageDialog(this, "Please generate the timetable first.");
        } else {
            String dayToAddClassStr = JOptionPane.showInputDialog(this, "Enter the day to add a comment (1-" + days + "):");
    
            try {
                int dayToAddComment = Integer.parseInt(dayToAddClassStr);
                dayToAddComment--; // Convert to 0-based index
    
                if (dayToAddComment < 0 || dayToAddComment >= days) {
                    JOptionPane.showMessageDialog(this, "Invalid day. Please try again.");
                } else {
                    String indexToAddCommentStr = JOptionPane.showInputDialog(this, "Enter the time slot index to add a comment (1-" + classTimings.size() + "):");
    
                    try {
                        int indexToAddComment = Integer.parseInt(indexToAddCommentStr);
                        indexToAddComment--; // Convert to 0-based index
    
                        if (indexToAddComment < 0 || indexToAddComment >= classTimings.size()) {
                            JOptionPane.showMessageDialog(this, "Invalid time slot index. Please try again.");
                        } else {
                            String className = timetable.getClass(dayToAddComment, indexToAddComment);
                            String comment = JOptionPane.showInputDialog(this, "Enter a comment for Day " + (dayToAddComment + 1) + " at " + classTimings.get(indexToAddComment) + ":");
    
                            if (className != null) {
                                timetable.addClass(dayToAddComment, indexToAddComment, className + " (" + comment + ")");
                            } else {
                                timetable.addClass(dayToAddComment, indexToAddComment, comment);
                            }
    
                            JOptionPane.showMessageDialog(this, "Comment added to the timetable.");
                            displayTimetable(textArea);
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid time slot index.");
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid day number.");
            }
        }
    

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TimetableGeneratorSwing app = new TimetableGeneratorSwing();
                app.setVisible(true);
            }
        });
    }

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

        public Timetable(int days, int classTimings) {
            schedule = new String[days][classTimings];
        }

        public void addClass(int day, int classIndex, String className) {
            schedule[day][classIndex] = className;
        }

        public String getClass(int day, int classIndex) {
            return schedule[day][classIndex];
        }

        public void displayTimetable(JTextArea textArea) {
            // Implement your code for displaying the timetable in the text area
        }
    }
}
