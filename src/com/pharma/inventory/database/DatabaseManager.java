package com.pharma.inventory.database;

import com.pharma.inventory.model.Medicine;
import com.pharma.inventory.model.StockRequest;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance;
    private List<Medicine> medicines;
    private List<StockRequest> stockRequests;

    private DatabaseManager() {
        medicines = new ArrayList<>();
        stockRequests = new ArrayList<>();
        initializeSampleData();
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void initializeSampleData() {
        medicines.add(new Medicine("MED001", "Paracetamol 500mg", 120, 50, "MedSupply Co"));
        medicines.add(new Medicine("MED002", "Ibuprofen 400mg", 30, 50, "MedSupply Co"));
        medicines.add(new Medicine("MED003", "Amoxicillin 250mg", 200, 100, "MedSupply Co"));
        medicines.add(new Medicine("MED004", "Aspirin 75mg", 45, 50, "MedSupply Co"));
        medicines.add(new Medicine("MED005", "Metformin 500mg", 150, 80, "MedSupply Co"));
        medicines.add(new Medicine("MED006", "Lisinopril 10mg", 25, 60, "MedSupply Co"));
        medicines.add(new Medicine("MED007", "Atorvastatin 20mg", 180, 70, "MedSupply Co"));
        medicines.add(new Medicine("MED008", "Omeprazole 20mg", 90, 50, "MedSupply Co"));
        medicines.add(new Medicine("MED009", "Ciprofloxacin 500mg", 40, 50, "MedSupply Co"));
        medicines.add(new Medicine("MED010", "Azithromycin 250mg", 60, 50, "MedSupply Co"));
    }

    public List<Medicine> getAllMedicines() {
        return new ArrayList<>(medicines);
    }

    public Medicine getMedicineById(String id) {
        for (Medicine med : medicines) {
            if (med.getId().equals(id)) {
                return med;
            }
        }
        return null;
    }

    public void addMedicine(Medicine medicine) {
        medicines.add(medicine);
    }

    public boolean updateMedicineQuantity(String id, int newQuantity) {
        Medicine med = getMedicineById(id);
        if (med != null) {
            med.setQuantity(newQuantity);
            return true;
        }
        return false;
    }

    public void addStockRequest(StockRequest request) {
        stockRequests.add(request);
    }

    public List<StockRequest> getAllStockRequests() {
        return new ArrayList<>(stockRequests);
    }

    public List<StockRequest> getPendingStockRequests() {
        List<StockRequest> pending = new ArrayList<>();
        for (StockRequest req : stockRequests) {
            if (req.getStatus().equals("Pending")) {
                pending.add(req);
            }
        }
        return pending;
    }

    public void fulfillRequest(StockRequest request) {
        Medicine med = getMedicineById(request.getMedicineId());
        if (med != null) {
            med.setQuantity(med.getQuantity() + request.getRequestedQuantity());
            request.setStatus("Fulfilled");
        }
    }

    public void clearFulfilledRequests() {
        stockRequests.removeIf(req -> req.getStatus().equals("Fulfilled"));
    }
}
