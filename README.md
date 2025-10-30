# Pharmaceutical Inventory Management System

## Overview
This is a Java Swing-based pharmaceutical inventory management system with two user types: Hospital and Supplier.

## Features

### Hospital Dashboard
1. View complete medicine inventory with stock status
2. Add/Remove/Set stock quantities (for customer checkouts and restocking)
3. Automatic low stock detection and supplier notification
4. View all alerts sent to supplier
5. Real-time synchronization with supplier database

### Supplier Dashboard
1. View all medicine inventory
2. Add new medicines or modify existing stock
3. View hospital stock requests with pending/fulfilled status
4. Fulfill hospital requests (automatically updates hospital inventory)
5. Clear fulfilled requests
6. Real-time synchronization with hospital database

## Login Credentials

- **Hospital User**
  - Username: hospital
  - Password: hospital123

- **Supplier User**
  - Username: supplier
  - Password: supplier123

## Technical Requirements

- Java Development Kit (JDK) 8 or higher
- No external libraries required (uses only Java Swing and core Java)

## How to Compile and Run

### Option 1: Using Command Line

1. Navigate to the project directory:
   ```
   cd PharmaceuticalInventorySystem
   ```

2. Compile all Java files:
   ```
   javac -d bin src/com/pharma/inventory/*.java src/com/pharma/inventory/model/*.java src/com/pharma/inventory/database/*.java
   ```

3. Run the application:
   ```
   java -cp bin com.pharma.inventory.PharmaceuticalInventorySystem
   ```

### Option 2: Using an IDE (Eclipse, IntelliJ IDEA, NetBeans)

1. Import the project as a Java project
2. Ensure the source folder is set to `src`
3. Run `PharmaceuticalInventorySystem.java` as the main class

## Project Structure

```
PharmaceuticalInventorySystem/
├── src/
│   └── com/
│       └── pharma/
│           └── inventory/
│               ├── PharmaceuticalInventorySystem.java (Main class)
│               ├── LoginFrame.java
│               ├── HospitalDashboard.java
│               ├── SupplierDashboard.java
│               ├── model/
│               │   ├── Medicine.java
│               │   └── StockRequest.java
│               └── database/
│                   └── DatabaseManager.java
└── README.md
```

## How It Works

1. **Shared Database**: Both Hospital and Supplier share the same in-memory database (DatabaseManager singleton)
2. **Low Stock Detection**: When hospital stock falls below threshold (default: 50 units), an automatic alert is created
3. **Request System**: Hospital alerts create stock requests that appear in supplier dashboard
4. **Fulfillment**: Supplier can fulfill requests, which automatically updates hospital inventory
5. **Real-time Sync**: Both dashboards can refresh to see latest changes

## Sample Data

The system comes pre-loaded with 10 medicines:
- Paracetamol 500mg (120 units)
- Ibuprofen 400mg (30 units - LOW STOCK)
- Amoxicillin 250mg (200 units)
- Aspirin 75mg (45 units - LOW STOCK)
- Metformin 500mg (150 units)
- Lisinopril 10mg (25 units - LOW STOCK)
- Atorvastatin 20mg (180 units)
- Omeprazole 20mg (90 units)
- Ciprofloxacin 500mg (40 units - LOW STOCK)
- Azithromycin 250mg (60 units)

## Notes

- All data is stored in memory and will be lost when the application closes
- Low stock threshold is set to 50 units for most medicines
- When fulfilling requests, the system adds requested quantity + 50 units buffer
- All operations are logged with timestamps

## Author
Created for pharmaceutical inventory management and supply chain coordination.
