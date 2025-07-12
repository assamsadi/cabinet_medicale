package Ordonnance.page;

import Ordonnance.DB.OrdonnanceSchema;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

public class OrdonnanceListByDoc extends JFrame {
    private OrdonnanceFuction F = new OrdonnanceFuction();
    String name = "";
    private DefaultTableModel model;
    private JTable table;

    public OrdonnanceListByDoc(String name) {
        this.name = name;
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
        headerPanel.setBackground(new Color(0, 120, 215)); // MS blue
        headerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("List Ordonnance");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Create add button panel
        JButton addButton ;
        // Create refresh button panel
        if (Objects.equals(name, "")){

            addButton = createModernButton("Add New User", new Color(0, 120, 215));
        }
        else {
            addButton = createModernButton("close", new Color(100, 40, 15));

        }
        addButton.addActionListener(e -> {
            this.setVisible(false);
//            new OrdonnanceAdd().setVisible(true);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create table with data
        String[] columnNames = {"ID", "Pation", "Docteur", "Medicament", "Vu Ordonnance", "Delete"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 4; // Only Edit and Delete columns editable
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // Center align first 3 columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 3; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Custom renderer for Medicament column to support multiline HTML
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                setText(value != null ? value.toString() : "");
            }
        });

        // Custom renderer and editor for Edit and Delete buttons
        table.getColumn("Vu Ordonnance").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());

        table.getColumn("Vu Ordonnance").setCellEditor(new ButtonEditor(new JCheckBox(), "Vu Ordonnance") {
            @Override
            protected void handleClick(int row) {
                // Handle edit click here
                String ordonnanceId = table.getValueAt(row, 0).toString();
                String ordonnance= table.getValueAt(row, 3).toString();
                System.out.println("Edit clicked for ID: " + ordonnanceId);
                new PrintOrdo(ordonnance).setVisible(true);
                // You can open edit form or dialog here
            }
        });

        table.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), "Delete") {
            @Override
            protected void handleClick(int row) {
                String ordonnanceId = table.getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(
                        OrdonnanceListByDoc.this,
                        "Are you sure you want to delete this entry?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    F.delete(ordonnanceId);
                    loadData();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Status bar
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        statusPanel.setBackground(new Color(240, 240, 240));
        JLabel statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusPanel.add(statusLabel);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        add(mainPanel);
        loadData();
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
        List<OrdonnanceSchema> data = F.paginateByDoc(name);

        for (OrdonnanceSchema ordonnance : data) {
            System.out.println(ordonnance.getMedicament());
            String medicamentsStr = ordonnance.getMedicament();
            if (medicamentsStr != null) {
                // Replace newlines with <br> for HTML multiline display
                medicamentsStr = "<html>" + medicamentsStr.replace("\n", "<br>") + "</html>";
            } else {
                medicamentsStr = "";
            }

            model.addRow(new Object[]{
                    ordonnance.get_id(),
                    ordonnance.getPation(),
                    ordonnance.getDocteur(),
                    medicamentsStr,
                    "Vu Ordonnance",
                    "Delete"
            });
        }

        // Adjust row heights for multiline medicament cells
        for (int row = 0; row < table.getRowCount(); row++) {
            Object val = table.getValueAt(row, 3);
            int lines = 1;
            if (val != null) {
                String s = val.toString();
                lines = s.split("<br>").length;
            }
            table.setRowHeight(row, Math.max(30, lines * 20)); // 20px per line approx
        }
    }

    // ButtonRenderer class for Edit/Delete buttons
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.PLAIN, 12));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(3, 8, 3, 8)));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
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
            if ("Vu Ordonnance".equals(value)) {
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

    // ButtonEditor class for Edit/Delete buttons
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

            if ("Edit".equals(label)) {
                button.setBackground(new Color(0, 120, 215));
                button.setForeground(Color.WHITE);
            } else if ("Delete".equals(label)) {
                button.setBackground(new Color(232, 17, 35));
                button.setForeground(Color.WHITE);
            }

            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                handleClick(row);
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected abstract void handleClick(int row);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OrdonnanceListByDoc(""));
    }
}
