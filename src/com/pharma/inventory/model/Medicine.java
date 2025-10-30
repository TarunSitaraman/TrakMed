package com.pharma.inventory.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Medicine {
    private String id;
    private String name;
    private int quantity;
    private int threshold;
    private String supplierName;
    private LocalDateTime lastUpdated;

    public Medicine(String id, String name, int quantity, int threshold, String supplierName) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.threshold = threshold;
        this.supplierName = supplierName;
        this.lastUpdated = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { 
        this.quantity = quantity;
        this.lastUpdated = LocalDateTime.now();
    }

    public int getThreshold() { return threshold; }
    public void setThreshold(int threshold) { this.threshold = threshold; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }

    public String getLastUpdatedFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return lastUpdated.format(formatter);
    }

    public boolean isLowStock() {
        return quantity < threshold;
    }

    public String getStatus() {
        return isLowStock() ? "Low Stock" : "In Stock";
    }
}
