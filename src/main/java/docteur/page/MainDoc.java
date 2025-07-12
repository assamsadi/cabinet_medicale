package docteur.page;

import Ordonnance.page.OrdonnanceListByDoc;
import RendezVous.page.rendezVousList;
import consultation.page.ConsultationAdd;
import consultation.page.ConsultationListByDoc;

import javax.swing.*;
import java.awt.*;

public class MainDoc extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    public MainDoc(String nameD) {
        setTitle("Doctor Dashboard - Medical Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);

        // Set application icon


        // Create main panel with better styling
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 248, 255)); // AliceBlue background

        // Create button panel with improved styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(70, 130, 180)),
                BorderFactory.createEmptyBorder(5, 5, 10, 5)));

        // Create styled buttons
        JButton btnCard1 = createModernButton("Diagnostic", new Color(70, 130, 180)); // SteelBlue
        JButton btnCard2 = createModernButton("rendez-vous", new Color(60, 179, 113)); // MediumSeaGreen
        JButton btnCard3 = createModernButton("Ordonnance", new Color(218, 165, 32)); // GoldenRod

        buttonPanel.add(btnCard1);
        buttonPanel.add(btnCard2);
        buttonPanel.add(btnCard3);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add cards (though they're not being used in your current implementation)
        cardPanel.add(createCardWithBoxes("Diagnostic "), "card1");
        cardPanel.add(createCardWithBoxes("Rendez-vous "), "card2");
        cardPanel.add(createCardWithBoxes("Ordonnance"), "card3");

        // Add action listeners
        btnCard1.addActionListener(e -> new rendezVousList(nameD));
        btnCard2.addActionListener(e -> new ConsultationListByDoc(nameD));
        btnCard3.addActionListener(e -> new OrdonnanceListByDoc(nameD));

        // Add header label
        JLabel headerLabel = new JLabel("Doctor : " +nameD, SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setForeground(new Color(25, 25, 112)); // MidnightBlue
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Add components to main panel
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(new JLabel("Select an option above to manage patient records", SwingConstants.CENTER),
                BorderLayout.SOUTH);

        // Set content pane
        setContentPane(mainPanel);
        setVisible(true);
    }

    private JButton createModernButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(color);


        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));




        return button;
    }
    private JPanel createCardWithBoxes(String labelPrefix) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        Dimension fixedSize = new Dimension(250, 35);

        for (int i = 1; i <= 3; i++) {
            JTextField textField = new JTextField(labelPrefix + i);
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            textField.setMaximumSize(fixedSize);
            textField.setPreferredSize(fixedSize);
            textField.setMinimumSize(fixedSize);
            textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)));

            panel.add(textField);
            panel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        return panel;
    }

    public static void main(String[] args) {
//         Use SwingUtilities.invokeLater for thread safety
//
    }
}