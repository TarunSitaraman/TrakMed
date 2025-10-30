@echo off
echo Compiling Pharmaceutical Inventory System...
if not exist bin mkdir bin
javac -d bin src\com\pharma\inventory\*.java src\com\pharma\inventory\model\*.java src\com\pharma\inventory\database\*.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
    echo Running application...
    java -cp bin com.pharma.inventory.PharmaceuticalInventorySystem
) else (
    echo Compilation failed!
    pause
)
