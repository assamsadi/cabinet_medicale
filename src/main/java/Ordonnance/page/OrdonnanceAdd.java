package Ordonnance.page;

import Ordonnance.DB.Medicament;
import TEST.OrdonnancePdf;
import consultation.page.ConsultationListByDoc;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class OrdonnanceAdd extends JFrame {
    private final ArrayList<Medicament> medicaments = new ArrayList<>();
    private JTable table;
    private MedicamentTableModel tableModel;
    OrdonnanceFuction F = new OrdonnanceFuction();
    String  nameDoc = "" ;
    String  namePas = "" ;

    public OrdonnanceAdd(String  nameD  , String  nameP) {
        this.nameDoc = nameD;
        this.namePas = nameP;
        setTitle("Add New Medicament");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 120, 215));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("Add New Medicament");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameInput = createTextField();
        JTextField dureeInput = createTextField();
        JTextField descriptionInput = createTextField();
        JButton addButton = createModernButton("Add Medicament +" ,new Color(23,1,0));
        JButton submitButton = createModernButton("Create" , new Color(23,33,100));

        JLabel error = createErrorLabel();

        Border defaultBorder = nameInput.getBorder();

        addFormRow(formPanel, gbc, 0, "Name:", nameInput);
        addFormRow(formPanel, gbc, 1, "Duration:", dureeInput);
        addFormRow(formPanel, gbc, 2, "Description:", descriptionInput);

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(addButton, gbc);

        gbc.gridy = 4;
        formPanel.add(error, gbc);

        // Table
        tableModel = new MedicamentTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(new Color(220, 220, 220));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(500, 200));
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(tableScroll, gbc);
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        formPanel.add(submitButton, gbc);

        // Add Medicament Logic
        addButton.addActionListener(e -> {
            nameInput.setBorder(defaultBorder);
            dureeInput.setBorder(defaultBorder);
            error.setText("");

            try {
                String name = nameInput.getText().trim();
                int duree = Integer.parseInt(dureeInput.getText().trim());
                String desc = descriptionInput.getText().trim();

                if (name.isEmpty() || desc.isEmpty()) {
                    error.setText("All fields are required.");
                    return;
                }

                medicaments.add(new Medicament(name, duree, desc));
                tableModel.fireTableDataChanged();
                nameInput.setText("");
                dureeInput.setText("");
                descriptionInput.setText("");
                nameInput.requestFocus();
            } catch (NumberFormatException ex) {
                dureeInput.setBorder(BorderFactory.createLineBorder(Color.RED));
                error.setText("Duration must be a valid number.");
            }
        });

        // Submit button logic: send formatted list of medicaments
        submitButton.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Medicament m : medicaments) {
                sb.append(m.getName())
                        .append(", ")
                        .append(m.getDuree())
                        .append(", ")
                        .append(m.getDesecrition())
                        .append("\n");
            }
            F.submit(nameDoc, namePas, sb.toString());
            String formatted = formatMedication(sb.toString());
            OrdonnancePdf pdf = new OrdonnancePdf();
            pdf.generer(namePas, nameDoc,
                    "",
                    formatted
            );
            setVisible(false);
//            ConsultationListByDoc c = new ConsultationListByDoc(nameD);

        });

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    // == UI Helpers ==
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(300, 32));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return field;
    }
    public static String formatMedication(String input) {
        StringBuilder result = new StringBuilder();
        String[] lines = input.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split(",\\s*");
            if (parts.length != 3) continue;

            String medicament = parts[0].trim();
            String duree = parts[1].trim();
            String description = parts[2].trim();

            result.append("medevament : ")
                    .append(medicament)
                    .append("   la duree ")
                    .append(duree)
                    .append(" j  desecreption : ")
                    .append(description)
                    .append("\n");
        }

        return result.toString();
    }
    private JLabel createErrorLabel() {
        JLabel label = new JLabel();
        label.setForeground(Color.RED);
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

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField input) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lbl, gbc);
        gbc.gridx = 1;
        panel.add(input, gbc);
    }

    // == Data Model ==

    class MedicamentTableModel extends AbstractTableModel {
        private final String[] columns = {"Name", "Duration", "Description", "Action"};

        @Override
        public int getRowCount() {
            return medicaments.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Medicament m = medicaments.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> m.getName();
                case 1 -> m.getDuree();
                case 2 -> m.getDesecrition();
                case 3 -> "Delete";
                default -> null;
            };
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return col == 3;
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setText("Delete");
            setForeground(Color.RED.darker());
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Delete");
            button.setForeground(Color.RED.darker());
            button.addActionListener(e -> {
                medicaments.remove(row);
                tableModel.fireTableDataChanged();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Delete";
        }
    }

    public static void main(String[] args) {
        new OrdonnanceAdd("oussma" , "asa");

    }
}
