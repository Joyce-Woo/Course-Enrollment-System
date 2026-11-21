import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnrollSystem extends JPanel {
    private JButton welcomeButton;
    private Image backgroundImage;

    public EnrollSystem() {
        // Load MMU logo as background image
        backgroundImage = new ImageIcon("Code/Background Images/MMU_Logo.png").getImage();
        
        // Set layout and background color
        setLayout(new BorderLayout());
        setBackground(new Color(240,238,233));

        // Create and configure Welcome button
        welcomeButton = new JButton("Welcome");
        welcomeButton.setBackground(new Color(49,64,146));
        welcomeButton.setForeground(Color.WHITE);
        welcomeButton.setFont(new Font("Arial", Font.BOLD, 24)); 
        welcomeButton.setPreferredSize(new Dimension(200, 60)); 
        welcomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(EnrollSystem.this);
                frame.setContentPane(new StudentInfo());
                frame.revalidate();
            }
        });

        // Add bottom panel to the main panel
        add(welcomeButton, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Enroll System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new EnrollSystem());
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}