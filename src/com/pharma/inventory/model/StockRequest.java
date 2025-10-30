package com.pharma.inventory.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StockRequest {
    private static int requestCounter = 1;
    private int requestId;
    private String medicineId;
    private String medicineName;
    private int currentStock;
    private int requestedQuantity;
    private LocalDateTime requestTime;
    private String status;

    public StockRequest(String medicineId, String medicineName, int currentStock, int requestedQuantity) {
        this.requestId = requestCounter++;
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.currentStock = currentStock;
        this.requestedQuantity = requestedQuantity;
        this.requestTime = LocalDateTime.now();
        this.status = "Pending";
    }

    public int getRequestId() { return requestId; }
    public String getMedicineId() { return medicineId; }
    public String getMedicineName() { return medicineName; }
    public int getCurrentStock() { return currentStock; }
    public int getRequestedQuantity() { return requestedQuantity; }
    public LocalDateTime getRequestTime() { return requestTime; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getRequestTimeFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return requestTime.format(formatter);
    }
}
