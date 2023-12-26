package test;

import main.data.Employee;
import main.files.FileProcessing;
import main.types.TransactionType;
import main.types.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void addItemTest() {
        Employee employee = (Employee) FileProcessing.getUserFromFile(2);
        employee.addItem("Ciocolata Alba",45, Unit.bucata,5);
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