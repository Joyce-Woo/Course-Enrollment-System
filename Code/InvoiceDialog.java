import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class InvoiceDialog extends JDialog {

    private StudentInfo studentInfo;

    public InvoiceDialog(Frame owner, StudentInfo studentInfo, Object[][] courseData, String totalCourseFees,
            String totalOtherFees, String discount, String netPayable) {
        super(owner, "Invoice", true);
        this.studentInfo = studentInfo;

        // Set GridBagLayout for the dialog
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 10, 3, 10); // Insets to provide spacing

        // Panel for student information
        JPanel studentInfoPanel = createStudentInfoPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(studentInfoPanel, gbc);

        // Panel for the table
        JPanel tablePanel = createTablePanel(courseData);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Span across two columns
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(tablePanel, gbc);

        // Panel for total amounts
        JPanel totalPanel = createTotalPanel(totalCourseFees, totalOtherFees, discount, netPayable);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span across two columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        add(totalPanel, gbc);

        // Bottom panel with close button
        JPanel bottomPanel = createBottomPanel();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across two columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(bottomPanel, gbc);

        pack();
        setLocationRelativeTo(owner);
    }

    private JPanel createStudentInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBorder(BorderFactory.createTitledBorder("Student Information"));

        panel.add(new JLabel("Name: " + studentInfo.getName()));
        panel.add(new JLabel("Email: " + studentInfo.getEmail()));
        panel.add(new JLabel("Contact Number: " + studentInfo.getContactNumber()));

        return panel;
    }

    private JPanel createTablePanel(Object[][] courseData) {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = { "Course Level", "Course Name", "Others", "Amount (RM)" };
        JTable courseTable = new JTable(new DefaultTableModel(courseData, columnNames));

        // Set table properties
        courseTable.setFillsViewportHeight(true); // Fill the viewport height
        courseTable.setPreferredScrollableViewportSize(new Dimension(400, 250));

        JScrollPane scrollPane = new JScrollPane(courseTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTotalPanel(String totalCourseFees, String resourceFees, String discount, String netPayable) {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));

        JLabel totalCourseFeeLabel = new JLabel("Total Course Fee (Before Discount): " + totalCourseFees);
        panel.add(totalCourseFeeLabel);

        JLabel totalResourcesFeeLabel = new JLabel("Total Resources Fee: " + resourceFees);
        panel.add(totalResourcesFeeLabel);

        JLabel discountLabel = new JLabel("Discount: " + discount);
        panel.add(discountLabel);

        JLabel netPayableLabel = new JLabel("Net Payable: " + netPayable);
        panel.add(netPayableLabel);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        panel.add(closeButton);

        return panel;
    }
}
