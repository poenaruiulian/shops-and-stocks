package test.java;

import main.java.data.ShopManager;
import main.java.helpers.FileProcessing;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ShopManagerTest {

    @Test
    void hireEmployeeTest() {
        ShopManager shopManager =(ShopManager) FileProcessing.getUserFromFile(3);
        shopManager.hireEmployee("Gigel","Georgel", LocalDate.of(2003, 1, 2),"0001","Strada Muschilor");
    }

    @Test
    void fireEmployeeTest() {
        ShopManager shopManager =(ShopManager) FileProcessing.getUserFromFile(3);
        shopManager.fireEmployee(2);
    }
}