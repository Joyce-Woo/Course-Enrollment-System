import javax.swing.*;
import java.awt.*;

public class StudentInfo extends JPanel {
    private JTextField nameField, fathersNameField, mothersNameField, addressField, ageField, contactNumberField, emailField;
    private JRadioButton maleButton, femaleButton;
    private Image backgroundImage;

    public StudentInfo() {
        // Load MMU logo as background image
        backgroundImage = new ImageIcon("Background Images/studentPage.jpg").getImage();

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

        // MMU logo at the top
        ImageIcon logoIcon = new ImageIcon("Background Images/logo.jpg");
        Image scaledLogoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledLogoImage);
        JLabel logoLabel = new JLabel(logoIcon, JLabel.CENTER);
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(Color.WHITE);
        logoPanel.add(logoLabel);
        
        // Center logo panel
        GridBagConstraints gbcLogo = createGridBagConstraints(0, 0);
        gbcLogo.fill = GridBagConstraints.HORIZONTAL;
        whiteContainer.add(logoPanel, gbcLogo);

        // Title label
        addTitleWithUnderline(whiteContainer);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbcForm = createGridBagConstraints(0, 3);
        gbcForm.fill = GridBagConstraints.HORIZONTAL;
        addFormField(formPanel, gbcForm, "Name:", nameField = new JTextField(20), 0);
        addFormField(formPanel, gbcForm, "Father's Name:", fathersNameField = new JTextField(20), 1);
        addFormField(formPanel, gbcForm, "Mother's Name:", mothersNameField = new JTextField(20), 2);
        addFormField(formPanel, gbcForm, "Address:", addressField = new JTextField(20), 3);
        addFormField(formPanel, gbcForm, "Age:", ageField = new JTextField(5), 4);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setForeground(Color.BLACK);
        gbcForm.gridx = 0;
        gbcForm.gridy = 5;
        gbcForm.anchor = GridBagConstraints.WEST;
        formPanel.add(genderLabel, gbcForm);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        genderPanel.setOpaque(false);

        gbcForm.gridx = 1;
        gbcForm.gridy = 5;
        gbcForm.anchor = GridBagConstraints.WEST;
        formPanel.add(genderPanel, gbcForm);

        addFormField(formPanel, gbcForm, "Contact Number:", contactNumberField = new JTextField(15), 6);
        addFormField(formPanel, gbcForm, "Email:", emailField = new JTextField(20), 7);

        // Center form panel
        GridBagConstraints gbcFormPanel = createGridBagConstraints(0, 4);
        gbcFormPanel.fill = GridBagConstraints.HORIZONTAL;
        whiteContainer.add(formPanel, gbcFormPanel);

        // Reset and Proceed buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton resetButton = new JButton("Reset");
        styleMenuButton(resetButton);
        resetButton.addActionListener(e -> resetFormFields());

        JButton proceedButton = new JButton("Proceed");
        styleMenuButton(proceedButton);
        proceedButton.addActionListener(e -> {
            if (validateFields()) {
                switchToCoursePage();
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });


        buttonPanel.add(resetButton);
        buttonPanel.add(proceedButton);

        // Center button panel
        GridBagConstraints gbcButtonPanel = createGridBagConstraints(0, 5);
        gbcButtonPanel.fill = GridBagConstraints.HORIZONTAL;
        whiteContainer.add(buttonPanel, gbcButtonPanel);

        // Add white container to the center of the middle panel
        GridBagConstraints gbcWhiteContainer = createGridBagConstraints(0, 0);
        gbcWhiteContainer.fill = GridBagConstraints.BOTH;
        middlePanel.add(whiteContainer, gbcWhiteContainer);

        // Add main panel to the center of the frame
        add(middlePanel, BorderLayout.CENTER);
    }

    private void addTitleWithUnderline(JPanel panel) {
        JLabel titleLabel = new JLabel("STUDENT INFORMATION", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        GridBagConstraints gbcTitle = createGridBagConstraints(0, 1);
        gbcTitle.fill = GridBagConstraints.HORIZONTAL;
        panel.add(titleLabel, gbcTitle);

        JSeparator titleSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        titleSeparator.setBackground(Color.BLACK);
        titleSeparator.setForeground(Color.BLACK);
        titleSeparator.setPreferredSize(new Dimension(titleLabel.getPreferredSize().width, 5));
        GridBagConstraints gbcSeparator = createGridBagConstraints(0, 2);
        gbcSeparator.fill = GridBagConstraints.HORIZONTAL;
        panel.add(titleSeparator, gbcSeparator);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JTextField textField, int yPos) {
        JLabel jLabel = new JLabel(label);
        jLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(textField, gbc);
    }

    private GridBagConstraints createGridBagConstraints(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(5, 10, 5, 10);
        return gbc;
    }

    private void styleMenuButton(JButton button) {
        button.setBackground(Color.RED);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(100, 40));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void resetFormFields() {
        nameField.setText("");
        fathersNameField.setText("");
        mothersNameField.setText("");
        addressField.setText("");
        ageField.setText("");
        contactNumberField.setText("");
        emailField.setText("");
        maleButton.setSelected(false);
        femaleButton.setSelected(false);
    }

    private boolean validateFields() {
        return !nameField.getText().isEmpty() &&
               !fathersNameField.getText().isEmpty() &&
               !mothersNameField.getText().isEmpty() &&
               !addressField.getText().isEmpty() &&
               !ageField.getText().isEmpty() &&
               !contactNumberField.getText().isEmpty() &&
               !emailField.getText().isEmpty() &&
               (maleButton.isSelected() || femaleButton.isSelected());
    }

    private void switchToCoursePage() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(StudentInfo.this);
        frame.setContentPane(new CoursePanel(this));
        frame.revalidate();
    }

    public String getName() {
        return nameField.getText();
    }

    public String getFathersName() {
        return fathersNameField.getText();
    }

    public String getMothersName() {
        return mothersNameField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }

    public String getAge() {
        return ageField.getText();
    }

    public String getContactNumber() {
        return contactNumberField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getGender() {
        return maleButton.isSelected() ? "Male" : "Female";
    }

}