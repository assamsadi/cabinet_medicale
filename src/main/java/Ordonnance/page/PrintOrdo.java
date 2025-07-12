package Ordonnance.page;

import javax.swing.*;
import java.awt.*;


public class PrintOrdo extends JFrame {

    // UI Constants
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 400;
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 252);
    private static final Color ITEM_BACKGROUND = new Color(245, 245, 245);
    private static final Font ITEM_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final int PADDING = 15;
    private static final int ITEM_SPACING = 8;


    public PrintOrdo(String ordo) {
        initializeFrame();
        setupUI(ordo);
    }


    private void initializeFrame() {
        setTitle("Ordonnance Viewer");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setBackground(BACKGROUND_COLOR);
    }


    private void setupUI(String ordo) {
        JPanel mainPanel = createMainPanel();
        addPrescriptionItems(mainPanel, ordo);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        styleScrollPane(scrollPane);
        add(scrollPane);
    }


    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        panel.setBackground(BACKGROUND_COLOR);
        return panel;
    }

    /**
     * Styles the scroll pane that contains the prescription items.
     */
    private void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1)
        ));
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
    }

    /**
     * Parses and adds prescription items to the main panel.
     */
    private void addPrescriptionItems(JPanel mainPanel, String ordo) {
        String[] lines = ordo.split("\\n");

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                JPanel itemPanel = createItemPanel(parts[0].trim(), parts[1].trim(), parts[2].trim());
                mainPanel.add(itemPanel);
                mainPanel.add(Box.createVerticalStrut(ITEM_SPACING));
            }
        }
    }

    /**
     * Creates a styled panel for a single prescription item.
     */
    private JPanel createItemPanel(String name, String duration, String mode) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        panel.setBackground(ITEM_BACKGROUND);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(formatPrescriptionText(name, duration, mode));
        label.setFont(ITEM_FONT);
        panel.add(label);

        return panel;
    }

    /**
     * Formats the prescription information into display text.
     */
    private String formatPrescriptionText(String name, String duration, String mode) {
        return String.format("<html><b>Name:</b> %s &nbsp;&nbsp; <b>Durée:</b> %s j &nbsp;&nbsp; <b>Mode:</b> %s</html>",
                name, duration, mode);
    }

    /**
     * Entry point for demonstration purposes.
     */
    public static void main(String[] args) {
        String samplePrescription = "Paracétamol, 5, 3x/j\nIbuprofène, 3, 2x/j\nVitamine C, 10, 1x/j";

        SwingUtilities.invokeLater(() -> {
            PrintOrdo viewer = new PrintOrdo(samplePrescription);
            viewer.setVisible(true);
        });
    }
}