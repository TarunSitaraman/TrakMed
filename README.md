# 🏥 TRAKMED: Pharmaceutical Inventory Management System

**TRAKMED** is a Java Swing-based application designed to streamline the pharmaceutical supply chain. It bridges the gap between Hospital inventory needs and Supplier stock management through real-time synchronization, automated low-stock detection, and a seamless request-fulfillment cycle.

---


## 🔍 Overview

This system solves the problem of manual stock tracking by automating the communication between hospitals and suppliers. 
* **Hospitals** can manage their local inventory and automatically alert suppliers when stock runs low.
* **Suppliers** can view these alerts as requests, fulfill them, and instantly update the hospital's inventory.

**Note:** By default, the system uses an in-memory database (data resets on close), but it is configured to support persistent storage via MySQL.

---

## 🌟 Key Features

### 🏥 Hospital Dashboard
* **Inventory Management:** View complete medicine lists with real-time stock status.
* **Stock Operations:** Add, Remove, or Set stock quantities (simulating customer checkouts or manual restocking).
* **Automated Logic:**
    * **Low Stock Detection:** Automatically flags items below **50 units**.
    * **Supplier Notification:** Triggers an alert to the supplier dashboard immediately upon low stock.
* **Alert History:** specific view of all alerts sent to the supplier.
* **Real-time Sync:** Updates instantly when the supplier fulfills a request.

### 🚚 Supplier Dashboard
* **Global Inventory View:** View and manage the master list of all medicines.
* **Catalog Management:** Add new medicines or modify existing stock details.
* **Request Fulfillment:**
    * View stock requests with **Pending/Fulfilled** status.
    * **One-Click Fulfillment:** Automatically updates the hospital's inventory (adds requested amount + 50 unit buffer).
    * **Housekeeping:** Clear fulfilled requests from the view.

---

## 🔐 Login Credentials

Use the following credentials to access the different dashboards:

| User Role | Username | Password |
| :--- | :--- | :--- |
| **Hospital** | `hospital` | `hospital123` |
| **Supplier** | `supplier` | `supplier123` |

---

## 🛠 Tech Stack & Requirements

* **Language:** Java (JDK 8 or higher)
* **GUI Framework:** Java Swing (Core Java)
* **Database:** In-memory (Default) / MySQL (Optional via `mysql-connector-j-9.5.0.jar`)
* **External Libraries:** None required for basic operation (Standard Java libraries only).

---

## 📂 Project Structure

```text
TRAKMED/
├── bin/                        # Compiled .class files
├── lib/
│   └── mysql-connector-j-9.5.0.jar
├── src/
│   └── com/
│       └── pharma/
│           ├── database/
│           │   └── DatabaseManager.java
│           ├── model/
│           │   ├── Medicine.java
│           │   └── StockRequest.java
│           └── ui/
│               ├── HospitalDashboard.java
│               ├── LoginFrame.java
│               ├── PharmaceuticalInventory.java
│               └── SupplierDashboard.java
├── .gitattributes
├── LICENSE
├── README.md
├── run.bat                     # Windows execution script
└── run.sh                      # Linux/Mac execution script

```


## How to Compile and Run

1. Navigate to the project directory:
   ```
   cd PharmaceuticalInventorySystem
   ```

2. Compile all Java files:
   ```
   javac -cp "lib/mysql-connector-j-9.5.0.jar" -d bin src/com/pharma/inventory/*.java src/com/pharma/inventory/model/*.java src/com/pharma/inventory/database/*.java
   ```

3. Run the application:
   ```
   java -cp "bin;lib/mysql-connector-j-9.5.0.jar" com.pharma.inventory.PharmaceuticalInventorySystem
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

- By default, all data is stored in memory and will be lost when the application closes
- Optionally, data can be stored in a SQL database by providing connection details in the configuration (see setup instructions)
- Low stock threshold is set to 50 units for most medicines
- When fulfilling requests, the system adds requested quantity + 50 units buffer
- All operations are logged with timestamps


