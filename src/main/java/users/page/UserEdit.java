package users.page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserEdit extends JFrame {
    private UserFunctions F = new UserFunctions();
    private String userId;

    public UserEdit(String id, String name, String phone, String cin, String role) {
        this.userId = id;
        setTitle("Edit User");
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
        headerPanel.setBackground(new Color(60, 120, 0)); // MS blue
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("Edit User");
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

        // Form fields with initial values
        JTextField nameInput = createTextField();
        nameInput.setText(name);
        JTextField phoneInput = createTextField();
        phoneInput.setText(phone);
        JTextField cinInput = createTextField();
        cinInput.setText(cin);
        String[] roles = {"admin", "user", "patient"};
        JComboBox<String> roleInput = createComboBox(roles);
        roleInput.setSelectedItem(role);

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
        addFormRow(formPanel, gbc, y++, "CIN:", cinInput, cinError);


        // Button panel
        y++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton cancelButton = createModernButton("Cancel", new Color(200, 2, 2));
        cancelButton.addActionListener(e -> {
            this.dispose();
            new UserList().setVisible(true);
        });

        JButton saveButton = createModernButton("Edit User", new Color(10, 100, 5));
        saveButton.addActionListener(e -> {
            boolean hasError = (F.dtoName(nameInput, nameError)) |
                    (F.dtoPhone(phoneInput, phoneError)) |
                    (F.dtoCin(cinInput, cinError));

            if (!hasError) {
                F.edit(userId, nameInput.getText(), phoneInput.getText(),
                        cinInput.getText(), (String) roleInput.getSelectedItem());
                JOptionPane.showMessageDialog(this,
                        "User updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new UserList().setVisible(true);
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

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
        SwingUtilities.invokeLater(() -> new UserEdit("1", "John Doe", "123456789", "AB123456", "user"));
    }
}