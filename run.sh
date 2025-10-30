#!/bin/bash
echo "Compiling Pharmaceutical Inventory System..."
mkdir -p bin
javac -d bin src/com/pharma/inventory/*.java src/com/pharma/inventory/model/*.java src/com/pharma/inventory/database/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Running application..."
    java -cp bin com.pharma.inventory.PharmaceuticalInventorySystem
else
    echo "Compilation failed!"
    exit 1
fi
