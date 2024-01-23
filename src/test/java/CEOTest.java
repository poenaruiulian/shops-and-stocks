package test.java;

import main.java.data.CEO;
import main.java.helpers.FileProcessing;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class CEOTest {

    @Test
    void openShop() {
        CEO ceo = (CEO) FileProcessing.getUserFromFile(4);
        ceo.openShop();
    }

    @Test
    void closeShop() {
        CEO ceo = (CEO) FileProcessing.getUserFromFile(4);
        ceo.closeShop(1);
    }

    @Test
    void transferEmployee() {
        CEO ceo = (CEO) FileProcessing.getUserFromFile(4);
        ceo.transferEmployee(6,1);
    }

    @Test
    void hireManager() {
        CEO ceo = (CEO) FileProcessing.getUserFromFile(4);
        ceo.hireManager("Huji", "Bujie", LocalDate.of(2003, 1, 2), "0001", "Strada Muschilor",1);
    }

    @Test
    void fireManager() {
        CEO ceo = (CEO) FileProcessing.getUserFromFile(4);
        ceo.fireManager(8,1);
    }

    @Test
    void addItem() {
    }

    @Test
    void removeItem() {
    }

    @Test
    void modifyItem() {
    }

    @Test
    void hireEmployee() {
        CEO ceo = (CEO) FileProcessing.getUserFromFile(4);
        ceo.hireEmployee("Falea", "Lulea", LocalDate.of(2003, 1, 2), "0001", "Strada Muschilor",1);
    }

    @Test
    void fireEmployee() {
        CEO ceo = (CEO) FileProcessing.getUserFromFile(4);
        ceo.fireEmployee(7,1);
    }
}