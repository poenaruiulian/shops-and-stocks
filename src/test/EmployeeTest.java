package test;

import main.data.Employee;
import main.helpers.FileProcessing;
import main.types.TransactionType;
import main.types.UnitType;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void addItemTest() {
        Employee employee = (Employee) FileProcessing.getUserFromFile(2);
        employee.addItem("Ciocolata Alba",45, UnitType.bucata,5);
    }

    @Test
    void removeItemTest() {
        Employee employee = (Employee) FileProcessing.getUserFromFile(2);
        employee.removeItem(2);
        employee.removeItem(5);
    }

    @Test
    void modifyItemTest() {
        Employee employee = (Employee) FileProcessing.getUserFromFile(2);
        employee.modifyItem(5, TransactionType.adding,10);
        employee.modifyItem(3, TransactionType.substracing,10);
    }
}