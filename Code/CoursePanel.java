import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

public class CoursePanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable courseTable;
    private JTextField courseNameField;
    private JComboBox<String> facultyCombo;
    private JComboBox<String> courseLevelCombo;
    private JComboBox<String> remedialCombo;
    private JLabel feeLabel;
    private JCheckBox hostelCheckBox;
    private Image backgroundImage;

    private Map<String, Double> feeMap;
    private StudentInfo studentInfo;

    public CoursePanel(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;

        // Load MMU logo as background image
        backgroundImage = new ImageIcon("Background Images/coursePage.jpg").getImage();

        setLayout(new BorderLayout());

        JPanel middlePanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JPanel whiteContainer = new JPanel(new GridBagLayout());
        whiteContainer.setBackground(Color.WHITE);

        // Create and add components
        addLogoAndTitle(whiteContainer);
        JPanel inputPanel = createInputPanel();
        JScrollPane tablePanel = createTablePanel();
        JPanel bottomPanel = createBottomPanel();

        GridBagConstraints gbc = createGridBagConstraints(0, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        whiteContainer.add(inputPanel, gbc);

        gbc = createGridBagConstraints(0, 3);
        gbc.fill = GridBagConstraints.BOTH;
        whiteContainer.add(tablePanel, gbc);

        gbc = createGridBagConstraints(0, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        whiteContainer.add(bottomPanel, gbc);

        GridBagConstraints gbcWhiteContainer = createGridBagConstraints(0, 0);
        gbcWhiteContainer.fill = GridBagConstraints.BOTH;
        middlePanel.add(whiteContainer, gbcWhiteContainer);

        add(middlePanel, BorderLayout.CENTER);

        // Initialize fee map based on course level
        feeMap = new HashMap<>();
        feeMap.put("Remedial", 900.0);
        feeMap.put("Matriculation", 1200.0);
        feeMap.put("Undergraduate", 1500.0);
        feeMap.put("Postgraduate", 1900.0);
    }

    private void addLogoAndTitle(JPanel panel) {
        // MMU logo at the top
        ImageIcon logoIcon = new ImageIcon("Background Images/logo.jpg");
        Image scaledLogoImage = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledLogoImage);
        JLabel logoLabel = new JLabel(logoIcon, JLabel.CENTER);
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(Color.WHITE);
        logoPanel.add(logoLabel);

        GridBagConstraints gbcLogo = createGridBagConstraints(0, 0);
        gbcLogo.fill = GridBagConstraints.HORIZONTAL;
        panel.add(logoPanel, gbcLogo);

        // Title label
        JLabel titleLabel = new JLabel("COURSE ENROLLMENT", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        GridBagConstraints gbcTitle = createGridBagConstraints(0, 1);
        gbcTitle.fill = GridBagConstraints.HORIZONTAL;
        panel.add(titleLabel, gbcTitle);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        JLabel facultyLabel = new JLabel("Faculty:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(facultyLabel, gbc);

        facultyCombo = new JComboBox<>(new String[]{"Information Technology", "Creative Multimedia", "Management", "Engineering"});
        facultyCombo.addActionListener(new FacultyComboListener());
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(facultyCombo, gbc);

        JLabel remedialLabel = new JLabel("Remedial?");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(remedialLabel, gbc);

        remedialCombo = new JComboBox<>(new String[]{"No", "English", "Malay", "Mandarin"});
        remedialCombo.addActionListener(new RemedialComboListener());
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(remedialCombo, gbc);

        JLabel courseLevelLabel = new JLabel("Course Level:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(courseLevelLabel, gbc);

        courseLevelCombo = new JComboBox<>(new String[]{"Matriculation", "Undergraduate", "Postgraduate"});
        courseLevelCombo.addActionListener(new CourseLevelComboListener());
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(courseLevelCombo, gbc);

        JLabel courseNameLabel = new JLabel("Course Name:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(courseNameLabel, gbc);

        courseNameField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(courseNameField, gbc);

        JLabel feeTextLabel = new JLabel("Fee:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(feeTextLabel, gbc);

        feeLabel = new JLabel("RM 0.00");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(feeLabel, gbc);

        JButton addButton = new JButton("Add Course");
        addButton.addActionListener(new AddCourseButtonListener());
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(addButton, gbc);

        JButton deleteButton = new JButton("Delete Selected Course");
        deleteButton.addActionListener(new DeleteCourseButtonListener());
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(deleteButton, gbc);

        return panel;
    }

    private JScrollPane createTablePanel() {
        tableModel = new DefaultTableModel(new Object[]{"Course Name", "Course Level", "Course Fee"}, 0);
        courseTable = new JTable(tableModel);

        // Set table properties
        courseTable.setFillsViewportHeight(true);
        courseTable.setPreferredScrollableViewportSize(new Dimension(400, 150));

        JScrollPane scrollPane = new JScrollPane(courseTable);
        return scrollPane;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        hostelCheckBox = new JCheckBox("Apply for Hostel (RM 500)");
        topPanel.add(hostelCheckBox);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(CoursePanel.this);
            frame.setContentPane(new StudentInfo());
            frame.revalidate();
        });
        bottomPanel.add(goBackButton);

        JButton studentInfoButton = new JButton("Student Info");
        studentInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        CoursePanel.this,
                        "Name: " + studentInfo.getName() +
                                "\nFather's Name: " + studentInfo.getFathersName() +
                                "\nMother's Name: " + studentInfo.getMothersName() +
                                "\nAddress: " + studentInfo.getAddress() +
                                "\nAge: " + studentInfo.getAge() +
                                "\nGender: " + studentInfo.getGender() +
                                "\nContact Number: " + studentInfo.getContactNumber() +
                                "\nEmail: " + studentInfo.getEmail(),
                        "Student Information",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        bottomPanel.add(studentInfoButton);

        JButton generateButton = new JButton("Generate Invoice");
        generateButton.addActionListener(new GenerateInvoiceButtonListener());
        bottomPanel.add(generateButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private GridBagConstraints createGridBagConstraints(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(3, 10, 3, 10);
        return gbc;
    }

    private void updateFeeLabel(String level) {
        double fee = feeMap.get(level);
        feeLabel.setText(String.format("RM %.2f", fee));
    }

    private class FacultyComboListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            courseNameField.setEnabled(true);
        }
    }

    private class CourseLevelComboListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedLevel = (String) courseLevelCombo.getSelectedItem();
            updateFeeLabel(selectedLevel);
        }
    }

    private class RemedialComboListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedRemedial = (String) remedialCombo.getSelectedItem();
            if (selectedRemedial.equals("No")) {
                courseLevelCombo.setEnabled(true);
                courseNameField.setEnabled(true);
                String selectedLevel = (String) courseLevelCombo.getSelectedItem();
                updateFeeLabel(selectedLevel);
            } else {
                courseLevelCombo.setEnabled(false);
                courseNameField.setEnabled(false);
                updateFeeLabel("Remedial");
            }
        }
    }

    private class AddCourseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String courseName;
            String courseLevel;
            double fee;

            if (remedialCombo.getSelectedItem().equals("No")) {
                courseName = courseNameField.getText();
                courseLevel = (String) courseLevelCombo.getSelectedItem();
                fee = feeMap.get(courseLevel);
            } else {
                courseName = (String) remedialCombo.getSelectedItem();
                courseLevel = "Remedial";
                fee = feeMap.get(courseLevel);
            }

            if (courseName.isEmpty()) {
                JOptionPane.showMessageDialog(CoursePanel.this, "Please enter a course name.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add the course to the table
            tableModel.addRow(new Object[]{courseName, courseLevel, String.format("RM %.2f", fee)});
            courseNameField.setText("");
        }
    }

    private class GenerateInvoiceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double totalCourseFees = 0;
            double hostelFee = 0;
            double discount = 0;
            double netPayable = 0;
            int discountCount = 0;

            // Prepare course data
            Object[][] courseData = new Object[tableModel.getRowCount() + 4][4];
            
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String courseName = (String) tableModel.getValueAt(i, 0);
                String courseLevel = (String) tableModel.getValueAt(i, 1);
                String feeString = (String) tableModel.getValueAt(i, 2);
                double fee = Double.parseDouble(feeString.replace("RM ", ""));
                totalCourseFees += fee;
    
                courseData[i][0] = courseLevel;
                courseData[i][1] = courseName;
                courseData[i][2] = "";
                courseData[i][3] = feeString;
            }
    
            // Add fixed resource fees
            int currentIndex = tableModel.getRowCount();
            courseData[currentIndex][0] = "";
            courseData[currentIndex][1] = "";
            courseData[currentIndex][2] = "Network Fee";
            courseData[currentIndex][3] = "RM 100.00";
            
            currentIndex++;
            courseData[currentIndex][0] = "";
            courseData[currentIndex][1] = "";
            courseData[currentIndex][2] = "Library Fee";
            courseData[currentIndex][3] = "RM 100.00";
    
            currentIndex++;
            courseData[currentIndex][0] = "";
            courseData[currentIndex][1] = "";
            courseData[currentIndex][2] = "Club Fee";
            courseData[currentIndex][3] = "RM 100.00";
    
            double resourceFees = 300.0; // Total of network, library, and club fees
    
            if (hostelCheckBox.isSelected()) {
                hostelFee = 500.0;
                currentIndex++;
                courseData[currentIndex][0] = "";
                courseData[currentIndex][1] = "";
                courseData[currentIndex][2] = "Hostel Fee";
                courseData[currentIndex][3] = "RM 500.00";
            }
    
            double totalOtherFees = resourceFees + hostelFee;
    
            // Apply discount if moving from one level to another
            int previousLevel = -1;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String level = (String) tableModel.getValueAt(i, 1);
                int currentLevel = getLevel(level);
                if (previousLevel != -1 && currentLevel > previousLevel) {
                    discountCount++;
                }
                previousLevel = currentLevel;
            }
    
            discount = discountCount * 0.10 * totalCourseFees; // 10% discount for each level transition
            netPayable = totalCourseFees + totalOtherFees - discount;
    
            // Display invoice
            InvoiceDialog invoiceDialog = new InvoiceDialog((Frame) SwingUtilities.getWindowAncestor(CoursePanel.this),
                    studentInfo,
                    courseData,
                    String.format("RM %.2f", totalCourseFees),
                    String.format("RM %.2f", totalOtherFees),
                    String.format("RM %.2f", discount),
                    String.format("RM %.2f", netPayable));
            invoiceDialog.setVisible(true);
        }
        private int getLevel(String courseLevel) {
            switch (courseLevel) {
                case "Remedial":
                case "Matriculation":
                    return 1;
                case "Undergraduate":
                    return 2;
                case "Postgraduate":
                    return 3;
                default:
                    return -1;
            }
        }
    }

    private class DeleteCourseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(CoursePanel.this, "Please select a course to delete.", "No Course Selected", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}