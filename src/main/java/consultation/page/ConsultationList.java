package consultation.page;

import consultation.DB.ConsultationSchema;
import consultation.page.ConsultationFuction;
import users.DB.UserSchema;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ConsultationList extends JFrame {
    private ConsultationFuction F = new ConsultationFuction();
    private DefaultTableModel model;
    private JTable table;
    private int editingRow;

    public ConsultationList() {
        setTitle("Consultation Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Modern UI styling
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(60, 179, 150)); // MS blue
        headerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("rendez-vou List");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Create refresh button panel


//        buttonPanel.add(addButton);


        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create table with data
        String[] columnNames = {"ID", "Docteur", "Pation" , "Time", "Edit", "Delete"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 4; // Only the action columns are editable
            }
        };

        loadData();

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // Center align all columns except actions
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 3; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Custom renderer and editor for buttons
        table.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());

        table.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), "Edit") {
            @Override
            protected void handleClick(int row) {
                String userId = table.getValueAt(row, 0).toString();
                editUser(userId);
            }
        });

        table.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), "Delete") {
            @Override
            protected void handleClick(int row) {
                String userId = table.getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(
                        ConsultationList.this,
                        "Are you sure you want to delete this user?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    F.delete(userId);
                    loadData();

                }
            }
        });

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Status bar
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        statusPanel.setBackground(new Color(240, 240, 240));
        JButton statusLabel = createModernButton("Close" , new Color(200,3,3));
        statusLabel.addActionListener(e->{setVisible(false);});
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusPanel.add(statusLabel);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        add(mainPanel);
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

    private void loadData() {
        model.setRowCount(0); // Clear existing data
        List<ConsultationSchema> data = F.paginate();

        for (ConsultationSchema user : data) {
            model.addRow(new Object[]{
                    user.get_id(),
                    user.getDocteur(),
                    user.getPassion(),
                    user.getTime(),
                    "Edit",
                    "Delete"
            });
        }
    }

    private void editUser(String userId) {
        ConsultationSchema u = F.find(userId);
        ConsultationEdit e = new ConsultationEdit(  userId , u.getPassion() , u.getDocteur()  , u.getTime() , u.getProbleme() );
        setVisible(false);
    }

    // Modern button renderer
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.PLAIN, 12));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(3, 8, 3, 8)));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setContentAreaFilled(false);
            setOpaque(true);




            setSize(20,30);



            // Style buttons differently
            if ("Edit".equals(value)) {
                setBackground(new Color(0, 120, 215)); // MS blue
                setForeground(Color.WHITE);
            } else if ("Delete".equals(value)) {
                setBackground(new Color(232, 17, 35)); // MS red
                setForeground(Color.WHITE);
            }
            else {
                setBackground(new Color(205, 205, 121)); // MS red
                setForeground(Color.WHITE);
            }

            return this;
        }
    }

    // Modern button editor
    static abstract class ButtonEditor extends DefaultCellEditor {
        protected String label;
        private JButton button;
        private boolean isPushed;
        protected int row;

        public ButtonEditor(JCheckBox checkBox, String label) {
            super(checkBox);
            this.label = label;
            button = new JButton(label);
            button.setOpaque(true);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(3, 8, 3, 8)));
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Style buttons differently
            if ("Edit".equals(label)) {
                button.setBackground(new Color(0, 120, 215)); // MS blue
                button.setForeground(Color.WHITE);
            } else if ("Delete".equals(label)) {
                button.setBackground(new Color(232, 17, 35)); // MS red
                button.setForeground(Color.WHITE);
            }

            button.addActionListener(e -> {
                fireEditingStopped();
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                handleClick(row);
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected abstract void handleClick(int row);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ConsultationList();
        });
    }
}