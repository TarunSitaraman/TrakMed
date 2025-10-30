package com.pharma.inventory;

import com.pharma.inventory.database.DatabaseManager;
import com.pharma.inventory.model.Medicine;
import com.pharma.inventory.model.StockRequest;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SupplierDashboard extends JFrame {
    private DatabaseManager dbManager;
    private JTable medicineTable;
    private DefaultTableModel medicineTableModel;
    private JTable requestsTable;
    private DefaultTableModel requestsTableModel;
    private JTextField medicineIdField, medicineNameField, quantityField;
    private JComboBox<String> actionCombo;

    public SupplierDashboard() {
        dbManager = DatabaseManager.getInstance();
        setTitle("Supplier Dashboard - Pharmaceutical Inventory");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadMedicineData();
        loadRequestsData();
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 255, 240));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(34, 139, 34));
        JLabel headerLabel = new JLabel("SUPPLIER DASHBOARD");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));

        tabbedPane.addTab("View Medicine Stock", createViewStockPanel());
        tabbedPane.addTab("Manage Stock", createManageStockPanel());
        tabbedPane.addTab("Hospital Requests", createRequestsPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(240, 255, 240));
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

        String[] columns = {"ID", "Medicine Name", "Quantity", "Supplier", "Last Updated"};
        medicineTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        medicineTable = new JTable(medicineTableModel);
        medicineTable.setFont(new Font("Arial", Font.PLAIN, 12));
        medicineTable.setRowHeight(25);
        medicineTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(medicineTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(34, 139, 34));
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
        formPanel.add(new JLabel("Medicine Name:"), gbc);

        gbc.gridx = 1;
        medicineNameField = new JTextField(15);
        formPanel.add(medicineNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 1;
        quantityField = new JTextField(15);
        formPanel.add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Action:"), gbc);

        gbc.gridx = 1;
        actionCombo = new JComboBox<>(new String[]{"Add", "Remove"});
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

    private JPanel createRequestsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Hospital Stock Requests");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Request ID", "Medicine Name", "Current Stock", "Requested Qty", "Time", "Status"};
        requestsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        requestsTable = new JTable(requestsTableModel);
        requestsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        requestsTable.setRowHeight(25);
        requestsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(requestsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton fulfillButton = new JButton("Fulfill Selected Request");
        fulfillButton.setBackground(new Color(34, 139, 34));
        fulfillButton.setForeground(Color.WHITE);
        fulfillButton.setFont(new Font("Arial", Font.BOLD, 12));
        fulfillButton.addActionListener(e -> fulfillRequest());
        buttonPanel.add(fulfillButton);

        JButton clearButton = new JButton("Clear Fulfilled Requests");
        clearButton.setBackground(new Color(70, 130, 180));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Arial", Font.BOLD, 12));
        clearButton.addActionListener(e -> clearFulfilledRequests());
        buttonPanel.add(clearButton);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(255, 165, 0));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.addActionListener(e -> loadRequestsData());
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadMedicineData() {
        medicineTableModel.setRowCount(0);
        List<Medicine> medicines = dbManager.getAllMedicines();
        int totalQuantity = 0;

        for (Medicine med : medicines) {
            Object[] row = {
                med.getId(),
                med.getName(),
                med.getQuantity(),
                med.getSupplierName(),
                med.getLastUpdatedFormatted()
            };
            medicineTableModel.addRow(row);
            totalQuantity += med.getQuantity();
        }
        JOptionPane.showMessageDialog(this, 
            "Total Medicines: " + medicines.size() + "\nTotal Stock Quantity: " + totalQuantity,
            "Stock Summary", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void executeStockAction() {
        String medicineId = medicineIdField.getText().trim();
        String medicineName = medicineNameField.getText().trim();
        String quantityStr = quantityField.getText().trim();
        String action = (String) actionCombo.getSelectedItem();

        if (medicineId.isEmpty() || medicineName.isEmpty() || quantityStr.isEmpty()) {
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

            if (action.equals("Add")) {
                if (med == null) {
                    Medicine newMed = new Medicine(medicineId, medicineName, quantity, 50, "MedSupply Co");
                    dbManager.addMedicine(newMed);
                    JOptionPane.showMessageDialog(this, "New medicine added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int newQuantity = med.getQuantity() + quantity;
                    dbManager.updateMedicineQuantity(medicineId, newQuantity);
                    JOptionPane.showMessageDialog(this, "Stock updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (action.equals("Remove")) {
                if (med == null) {
                    JOptionPane.showMessageDialog(this, "Medicine ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int newQuantity = med.getQuantity() - quantity;
                if (newQuantity < 0) {
                    JOptionPane.showMessageDialog(this, "Insufficient stock!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dbManager.updateMedicineQuantity(medicineId, newQuantity);
                JOptionPane.showMessageDialog(this, "Stock removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            medicineIdField.setText("");
            medicineNameField.setText("");
            quantityField.setText("");
            loadMedicineData();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadRequestsData() {
        requestsTableModel.setRowCount(0);
        List<StockRequest> requests = dbManager.getAllStockRequests();
        int pendingCount = 0;

        for (StockRequest req : requests) {
            Object[] row = {
                req.getRequestId(),
                req.getMedicineName(),
                req.getCurrentStock(),
                req.getRequestedQuantity(),
                req.getRequestTimeFormatted(),
                req.getStatus()
            };
            requestsTableModel.addRow(row);
            if (req.getStatus().equals("Pending")) {
                pendingCount++;
            }
        }

        if (pendingCount > 0) {
            JOptionPane.showMessageDialog(this, 
                "You have " + pendingCount + " pending request(s) from hospitals!",
                "Pending Requests", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void fulfillRequest() {
        int selectedRow = requestsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request to fulfill!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int requestId = (int) requestsTableModel.getValueAt(selectedRow, 0);
        String status = (String) requestsTableModel.getValueAt(selectedRow, 5);

        if (status.equals("Fulfilled")) {
            JOptionPane.showMessageDialog(this, "This request has already been fulfilled!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        List<StockRequest> requests = dbManager.getAllStockRequests();
        for (StockRequest req : requests) {
            if (req.getRequestId() == requestId) {
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Fulfill request for " + req.getMedicineName() + "?\nQuantity: " + req.getRequestedQuantity() + " units",
                    "Confirm Fulfillment", 
                    JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    dbManager.fulfillRequest(req);
                    JOptionPane.showMessageDialog(this, 
                        "Request fulfilled successfully!\nMedicine sent to hospital.",
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    loadRequestsData();
                    loadMedicineData();
                }
                break;
            }
        }
    }

    private void clearFulfilledRequests() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Clear all fulfilled requests?", 
            "Confirm Clear", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            dbManager.clearFulfilledRequests();
            loadRequestsData();
            JOptionPane.showMessageDialog(this, "Fulfilled requests cleared!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
