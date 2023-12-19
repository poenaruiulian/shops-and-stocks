package test;

import main.data.*;
import main.types.Unit;
import main.types.UserType;
import main.files.FileProcessing;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class FileProcessingTest {

    @org.junit.jupiter.api.Test
    void addNewShopToFile() {
        int lastUsedId = FileProcessing.getLastIDUsed("shops");
        List<Employee> employees = new ArrayList<>();
        employees.add((Employee) FileProcessing.getUserFromFile(1));
        employees.add((Employee) FileProcessing.getUserFromFile(2));

        List<Product> products = new ArrayList<>();
        products.add(FileProcessing.getProductFromFile(0));
        products.add(FileProcessing.getProductFromFile(1));
        products.add(FileProcessing.getProductFromFile(2));
        products.add(FileProcessing.getProductFromFile(3));
        Shop shop = new Shop(lastUsedId + 1, 4, 3, employees.stream().toList(), products.stream().toList());
        FileProcessing.addNewShopToFile(shop);
    }

    @org.junit.jupiter.api.Test
    void addNewUserTest() {
        int lastUsedId = FileProcessing.getLastIDUsed("users");
        int lastUsedIdShops = FileProcessing.getLastIDUsed("shops");

        LocalDate dateOfBirth1 = LocalDate.of(2003, 1, 2);
        LocalDate dateOfBirth2 = LocalDate.of(2003, 5, 13);
        LocalDate dateOfBirth3 = LocalDate.of(2003, 12, 1);
        LocalDate dateOfBirth4 = LocalDate.of(1990, 12, 1);
        LocalDate dateOfBirth5 = LocalDate.of(1985, 12, 1);
        LocalDate dateOfEmployment1 = LocalDate.of(2023, 3, 2);
        LocalDate dateOfEmployment2 = LocalDate.of(2020, 5, 12);
        LocalDate dateOfEmployment3 = LocalDate.of(2022, 1, 30);
        LocalDate dateOfEmployment4 = LocalDate.of(2010, 1, 30);


        Employee employee1 = new Employee(lastUsedId + 1, UserType.employee, "Andrei", "Ciubota", dateOfBirth1, "111 111 111", "Teiului, nr. 22", lastUsedIdShops + 1, dateOfEmployment1);
        Employee employee2 = new Employee(lastUsedId + 2, UserType.employee, "Mihai", "Stefanescu", dateOfBirth2, "222 222 222", "Mrejelor, nr. 2", lastUsedIdShops + 1, dateOfEmployment2);
        Employee employee3 = new Employee(lastUsedId + 3, UserType.employee, "Miruna", "Andronescu", dateOfBirth3, "333 444 222", "Hainelor, nr. 1 scara B", lastUsedIdShops + 1, dateOfEmployment3);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        ShopManager shopManager = new ShopManager(lastUsedId + 4, UserType.manager, "George", "Giulea", dateOfBirth4, "444 444 444", "Junimea, nr. 4, ap 2", lastUsedIdShops + 1, dateOfEmployment4, employees);

        Shop shop = new Shop(lastUsedIdShops + 1, lastUsedId + 5, lastUsedId + 4, employees, null);
        List<Shop> shops = new ArrayList<>();
        shops.add(shop);

        CEO ceo = new CEO(lastUsedId + 5, UserType.ceo, "June", "Sef", dateOfBirth5, "555 555 555", "Sefilor, nr. 10, et 5", employees, shops);

        FileProcessing.addNewUser(employee1);
        FileProcessing.addNewUser(employee2);
        FileProcessing.addNewUser(employee3);

        FileProcessing.addNewUser(shopManager);

        employees.add(shopManager);
        FileProcessing.addNewUser(ceo);

    }


    @org.junit.jupiter.api.Test
    void addNewProductToFileTest() {
        int lastUsedId = FileProcessing.getLastIDUsed("products");
        Product product = new Product(lastUsedId + 1, "Banane", 50.0, Unit.kilogram, 12);
        FileProcessing.addNewProductToFile(product);
    }

    @org.junit.jupiter.api.Test
    void getShopFromFile() {
        Shop shop = FileProcessing.getShopFromFile(FileProcessing.getLastIDUsed("shops"));
        System.out.println(shop.getOwnerID());
        System.out.println(FileProcessing.getUserFromFile(shop.getOwnerID()).getFirstName());
    }

    @org.junit.jupiter.api.Test
    void getUserFromFileTest() {
        Employee employee = (Employee) FileProcessing.getUserFromFile(2);
        System.out.println(employee.getFirstName());

        ShopManager shopManager = (ShopManager) FileProcessing.getUserFromFile(3);
        System.out.println(shopManager.getFirstName());
    }

    @org.junit.jupiter.api.Test
    void getProductFromFile() {
        Product product = FileProcessing.getProductFromFile(0);
        System.out.println(product.getProductName());
    }

    @org.junit.jupiter.api.Test
    void getLastIDUsedTest() {
        String fisier1 = "products";
        System.out.println(FileProcessing.getLastIDUsed(fisier1));

        String fisier2 = "users";
        System.out.println(FileProcessing.getLastIDUsed(fisier2));

        String fisier3 = "shops";
        System.out.println(FileProcessing.getLastIDUsed(fisier3));
    }

    @org.junit.jupiter.api.Test
    void updateProductInFileTest(){
        Product updatedProduct = new Product(3,"Pepene",20,Unit.bucata,20);
        FileProcessing.updateProductInFile(updatedProduct);
    }
    @org.junit.jupiter.api.Test
    void udpateUserInFileTest(){
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 2);
        LocalDate dateOfEmployment = LocalDate.of(2017, 3, 2);


        Employee employee = new Employee(1,UserType.employee,"Dorel","Mirel",dateOfBirth,"777 777 777","Strada Plosnitelor",0,dateOfEmployment);
        FileProcessing.updateUserInFile(employee);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        ShopManager shopManager = new ShopManager(3,UserType.employee,"Dorel","Mirel",dateOfBirth,"777 777 777","Strada Plosnitelor",0,dateOfEmployment,employees);
        FileProcessing.updateUserInFile(shopManager);

        employees.add(shopManager);
        List<Shop> shops = new ArrayList<>();
        shops.add(FileProcessing.getShopFromFile(0));
        CEO ceo = new CEO(4,UserType.ceo,"Dorel","Mirel",dateOfBirth,"777 777 777","Strada Plosnitelor",employees,shops);
        FileProcessing.updateUserInFile(ceo);
    }

    @org.junit.jupiter.api.Test
    void updateShopInFileTest(){
        Shop updatedShop = FileProcessing.getShopFromFile(0);
        List<Product> products = updatedShop.getProductList();

        Product newProduct = FileProcessing.getProductFromFile(4);
        products.add(newProduct);

        updatedShop.setProductList(products);

        FileProcessing.updateShopInFile(updatedShop);
    }

    @org.junit.jupiter.api.Test
    void deleteProductFromFileTest(){
        int productToDeleteId = 7;
        FileProcessing.deleteProductFromFile(productToDeleteId);
    }

    @org.junit.jupiter.api.Test
    void deleteUserFromFileTest(){
        FileProcessing.deleteUserFromFile(0);
    }

    @org.junit.jupiter.api.Test
    void deleteShopFromFileTest(){
        FileProcessing.deleteShopFromFile(0);
    }
}