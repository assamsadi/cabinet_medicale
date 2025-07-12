package consultation.page;

import docteur.page.DocteurFunction;
import users.page.AddUsers;
import users.page.UserFunctions;
import users.page.UserList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConsultationEdit extends JFrame {
    private ConsultationFuction F = new ConsultationFuction();
    private DocteurFunction Fd = new DocteurFunction();

    public ConsultationEdit( String id ,  String passion , String docteur ,String time , String problem ) {
        setTitle("Edit rendez-vou");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(60, 120, 0)); // MS blue
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("Edit rendez-vou");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField passionInput = createTextField(passion);
        JTextField dateField = new JTextField(10);
        dateField.setText(time);
        dateField.setPreferredSize(new Dimension(300, 32));
        dateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        dateField.setInputVerifier(new InputVerifier() {SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            public boolean verify(JComponent input) {
                String text = ((JTextField) input).getText();
                try {
                    format.setLenient(false);
                    format.parse(text);
                    return true;
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(input, "Invalid date format (dd/MM/yyyy)",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        });
        String[] roles = Fd.allDocteur();
        JComboBox<String> roleInput = createComboBox(roles);
        roleInput.setSelectedItem(docteur);
        roleInput.setSelectedItem(docteur);
        // Error labelsroleInput
        JLabel problemError = createErrorLabel();
        JLabel dateError = createErrorLabel();
        JLabel cinError = createErrorLabel();
        JLabel roleError = createErrorLabel();
        int y = 0;
        // Name field
        addFormRow(formPanel, gbc, y++, "Problem:", passionInput, problemError);
        // Phone field
        addFormRow(formPanel, gbc, y++, "Date:", dateField, dateError);

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(createLabel("Role:"), gbc);
        gbc.gridx = 1;
        formPanel.add(roleInput, gbc);
        y++;
        gbc.gridx = 1; gbc.gridy = y;
        formPanel.add(roleError, gbc);
        y++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton cancelButton = createModernButton("Cancel", new Color(200, 20, 20));
        cancelButton.addActionListener(e -> {
            this.dispose();
            new ConsultationList().setVisible(true);
        });

        JButton submitButton = createModernButton("Edit rendez-vou", new Color(10, 100, 5));
        submitButton.addActionListener(e -> {
            boolean hasError = false;

            if (!hasError) {
                F.edite(id, passionInput.getText(), (String) roleInput.getSelectedItem(),
                        passionInput.getText() , passionInput.getText() );
                this.dispose();
                new ConsultationList().setVisible(true);
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
    private JTextField createTextField(String str ) {
        JTextField field = new JTextField();
        field.setText(str);
        field.setPreferredSize(new Dimension(300, 32));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return field;
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
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
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
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConsultationEdit("we","ew","rachid","wewe","wewe"));
    }
}
