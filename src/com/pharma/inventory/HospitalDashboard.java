package com.pharma.inventory;

import com.pharma.inventory.database.DatabaseManager;
import com.pharma.inventory.model.Medicine;
import com.pharma.inventory.model.StockRequest;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HospitalDashboard extends JFrame {
    private DatabaseManager dbManager;
    private JTable medicineTable;
    private DefaultTableModel tableModel;
    private JTextField medicineIdField, quantityField;
    private JComboBox<String> actionCombo;
    private JTextArea alertsArea;

    public HospitalDashboard() {
        dbManager = DatabaseManager.getInstance();
        setTitle("Hospital Dashboard - Pharmaceutical Inventory");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadMedicineData();
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(230, 240, 255));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel headerLabel = new JLabel("HOSPITAL DASHBOARD");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));

        tabbedPane.addTab("View Medicine Stock", createViewStockPanel());
        tabbedPane.addTab("Manage Stock", createManageStockPanel());
        tabbedPane.addTab("Low Stock Alerts", createAlertsPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(230, 240, 255));
        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(120, 35));
        logoutButton.setBackground(new Color(220, 20, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.addActionListener(e -> logout());
        bottomPanel.add(logoutButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createViewStockPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"ID", "Medicine Name", "Quantity", "Threshold", "Status", "Last Updated"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        medicineTable = new JTable(tableModel);
        medicineTable.setFont(new Font("Arial", Font.PLAIN, 12));
        medicineTable.setRowHeight(25);
        medicineTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(medicineTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(70, 130, 180));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.addActionListener(e -> loadMedicineData());
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createManageStockPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Medicine ID:"), gbc);

        gbc.gridx = 1;
        medicineIdField = new JTextField(15);
        formPanel.add(medicineIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 1;
        quantityField = new JTextField(15);
        formPanel.add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Action:"), gbc);

        gbc.gridx = 1;
        actionCombo = new JComboBox<>(new String[]{"Add", "Remove", "Set"});
        formPanel.add(actionCombo, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton executeButton = new JButton("Execute");
        executeButton.setPreferredSize(new Dimension(120, 35));
        executeButton.setBackground(new Color(34, 139, 34));
        executeButton.setForeground(Color.WHITE);
        executeButton.setFont(new Font("Arial", Font.BOLD, 14));
        executeButton.addActionListener(e -> executeStockAction());
        buttonPanel.add(executeButton);
        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createAlertsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Recent Low Stock Alerts Sent to Supplier");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(titleLabel, BorderLayout.NORTH);

        alertsArea = new JTextArea(20, 50);
        alertsArea.setEditable(false);
        alertsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(alertsArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh Alerts");
        refreshButton.setBackground(new Color(70, 130, 180));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.addActionListener(e -> loadAlerts());
        JPanel btnPanel = new JPanel();
        btnPanel.add(refreshButton);
        panel.add(btnPanel, BorderLayout.SOUTH);

        loadAlerts();
        return panel;
    }

    private void loadMedicineData() {
        tableModel.setRowCount(0);
        List<Medicine> medicines = dbManager.getAllMedicines();
        int lowStockCount = 0;

        for (Medicine med : medicines) {
            Object[] row = {
                med.getId(),
                med.getName(),
                med.getQuantity(),
                med.getThreshold(),
                med.getStatus(),
                med.getLastUpdatedFormatted()
            };
            tableModel.addRow(row);
            if (med.isLowStock()) {
                lowStockCount++;
                int lastRow = tableModel.getRowCount() - 1;
                medicineTable.setRowSelectionInterval(lastRow, lastRow);
            }
        }
        JOptionPane.showMessageDialog(this, 
            "Total Medicines: " + medicines.size() + "\nLow Stock Items: " + lowStockCount,
            "Stock Summary", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void executeStockAction() {
        String medicineId = medicineIdField.getText().trim();
        String quantityStr = quantityField.getText().trim();
        String action = (String) actionCombo.getSelectedItem();

        if (medicineId.isEmpty() || quantityStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity < 0) {
                JOptionPane.showMessageDialog(this, "Quantity cannot be negative!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Medicine med = dbManager.getMedicineById(medicineId);
            if (med == null) {
                JOptionPane.showMessageDialog(this, "Medicine ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int newQuantity = med.getQuantity();
            switch (action) {
                case "Add":
                    newQuantity += quantity;
                    break;
                case "Remove":
                    newQuantity -= quantity;
                    if (newQuantity < 0) {
                        JOptionPane.showMessageDialog(this, "Insufficient stock!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    break;
                case "Set":
                    newQuantity = quantity;
                    break;
            }

            dbManager.updateMedicineQuantity(medicineId, newQuantity);

            if (newQuantity < med.getThreshold()) {
                int requestedQty = med.getThreshold() + 50 - newQuantity;
                StockRequest request = new StockRequest(med.getId(), med.getName(), newQuantity, requestedQty);
                dbManager.addStockRequest(request);
                JOptionPane.showMessageDialog(this, 
                    "Stock updated successfully!\nLow stock alert sent to supplier for " + med.getName(),
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Stock updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            medicineIdField.setText("");
            quantityField.setText("");
            loadMedicineData();
            loadAlerts();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAlerts() {
        alertsArea.setText("");
        List<StockRequest> requests = dbManager.getAllStockRequests();
        if (requests.isEmpty()) {
            alertsArea.setText("No alerts sent yet.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("=== LOW STOCK ALERTS ===\n\n");
            for (StockRequest req : requests) {
                sb.append("Request ID: ").append(req.getRequestId()).append("\n");
                sb.append("Medicine: ").append(req.getMedicineName()).append("\n");
                sb.append("Current Stock: ").append(req.getCurrentStock()).append("\n");
                sb.append("Requested: ").append(req.getRequestedQuantity()).append(" units\n");
                sb.append("Time: ").append(req.getRequestTimeFormatted()).append("\n");
                sb.append("Status: ").append(req.getStatus()).append("\n");
                sb.append("----------------------------------------\n");
            }
            alertsArea.setText(sb.toString());
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", 
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame();
        }
    }
}
