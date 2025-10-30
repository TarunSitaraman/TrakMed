package com.pharma.inventory;

import java.awt.*;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeCombo;

    public LoginFrame() {
        setTitle("Pharmaceutical Inventory System - Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("Pharmaceutical Inventory System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(25, 25, 112));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("User Type:"), gbc);

        gbc.gridx = 1;
        userTypeCombo = new JComboBox<>(new String[]{"Hospital", "Supplier"});
        userTypeCombo.setPreferredSize(new Dimension(200, 30));
        formPanel.add(userTypeCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(passwordField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(120, 35));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.addActionListener(e -> handleLogin());
        buttonPanel.add(loginButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String userType = (String) userTypeCombo.getSelectedItem();

        boolean validLogin = false;

        if (userType.equals("Hospital") && username.equals("hospital") && password.equals("123")) {
            validLogin = true;
            dispose();
            new HospitalDashboard();
        } else if (userType.equals("Supplier") && username.equals("supplier") && password.equals("456")) {
            validLogin = true;
            dispose();
            new SupplierDashboard();
        }

        if (!validLogin) {
            JOptionPane.showMessageDialog(this, 
                "Invalid credentials! Please try again.\n\nHospital: hospital/hospital123\nSupplier: supplier/supplier123",
                "Login Failed", 
                JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }
}
