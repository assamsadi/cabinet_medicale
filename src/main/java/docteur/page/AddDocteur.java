package docteur.page;

import docteur.page.DocteurFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddDocteur extends JFrame {
    private DocteurFunction F = new DocteurFunction();

    public AddDocteur() {
        setTitle("Add New Docteur");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        // Modern UI styling
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 120, 215)); // MS blue
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("Add New Docteur");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form fields
        JTextField nameInput = createTextField();
        JTextField phoneInput = createTextField();
        JTextField spInput = createTextField();
        JTextField passwordInput = createTextField();



        // Error labels
        JLabel nameError = createErrorLabel();
        JLabel phoneError = createErrorLabel();
        JLabel cinError = createErrorLabel();
        JLabel roleError = createErrorLabel();

        int y = 0;

        // Name field
        addFormRow(formPanel, gbc, y++, "Name:", nameInput, nameError);
        // Phone field
        addFormRow(formPanel, gbc, y++, "Phone:", phoneInput, phoneError);
        // CIN field
        addFormRow(formPanel, gbc, y++, "specialty:", spInput, cinError);
        addFormRow(formPanel, gbc, y++, "password:", passwordInput, roleError);

        // Role field



        // Button panel
        y++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton cancelButton = createModernButton("Cancel", new Color(200, 20, 20));
        cancelButton.addActionListener(e -> {
            this.dispose();
//            new UserList().setVisible(true);
        });

        JButton submitButton = createModernButton("Add Cocteur", new Color(0, 120, 215));
        submitButton.addActionListener(e -> {
            boolean hasError = (F.dtoPhone(passwordInput , phoneError) | F.dtoSpecialty(spInput, cinError) | F.dtoName(nameInput , nameError) |F.dtoPassword(passwordInput , phoneError)) ;

            if (!hasError) {
                F.submitDocteur(nameInput.getText(), phoneInput.getText(),
                        spInput.getText(), passwordInput.getText());
                this.dispose();
                new ListDocteur().setVisible(true);
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        gbc.gridx = 1; gbc.gridy = y;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(buttonPanel, gbc);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(300, 32));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return field;
    }

    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setPreferredSize(new Dimension(300, 32));
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        return comboBox;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }

    private JLabel createErrorLabel() {
        JLabel label = new JLabel();
        label.setForeground(new Color(232, 17, 35)); // MS red
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return label;
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


    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText,
                            JComponent input, JLabel errorLabel) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(createLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(input, gbc);
        row++;
        gbc.gridx = 1; gbc.gridy = row;
        panel.add(errorLabel, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddDocteur());
    }
}