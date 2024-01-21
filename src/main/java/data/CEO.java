package main.java.data;

import main.java.helpers.FileProcessing;
import main.java.types.TransactionType;
import main.java.types.UserType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class corresponding to the role of CEO.
 */
public class CEO extends Person {
    private List<Employee> employeeList;
    private List<Shop> shopList;

    public CEO() {
        super();
        this.employeeList = null;
        this.shopList = null;
    }

    public CEO(int userID, String userType, String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String address, List<Employee> employeeList, List<Shop> shopList) {
        super(userID, userType, firstName, lastName, dateOfBirth, phoneNumber, address);
        this.employeeList = employeeList;
        this.shopList = shopList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    /**
     * Open a shop with an empty list of employees and products. The id of the manager is -1.
     */
    public void openShop() {
        int newShopId = FileProcessing.getLastIDUsed("shops");
        Shop newShop = new Shop(newShopId + 1, this.userID, -1, new ArrayList<Employee>(), new ArrayList<Product>());

        this.shopList.add(newShop);

        FileProcessing.updateUserInFile(this);
        FileProcessing.addNewShopToFile(newShop);
    }

    /**
     * Closing a shop deletes it from the file with shops and every employee and shop managers are deleted too from the files of users. The products too.
     *
     * @param shopToCloseId The id of the shop to close.
     */
    public void closeShop(int shopToCloseId) {
        Shop shopToClose = FileProcessing.getShopFromFile(shopToCloseId);

        this.shopList = this.shopList.stream().filter(shop -> shop.getShopID() != shopToCloseId).toList();

        for (Product product : shopToClose.getProductList()) {
            FileProcessing.deleteProductFromFile(product.getProductId());
        }

        for (Employee employee : shopToClose.getEmployeesList()) {
            FileProcessing.deleteUserFromFile(employee.getUserID());
            this.employeeList = this.employeeList.stream().filter(employeee -> employee.userID != employeee.userID).toList();
        }

        FileProcessing.deleteUserFromFile(shopToClose.getShopManagerID());
        FileProcessing.updateUserInFile(this);
        FileProcessing.deleteShopFromFile(shopToCloseId);
    }

    /**
     * Transfers an employee from a shop to another and from a shop manager list to another shop manager list. This method only allows to transfer employees, not managers.
     *
     * @param employeeId the id of the employee we want to transfer
     * @param shopId     the shop we want the employee to be transfered in
     */
    public void transferEmployee(int employeeId, int shopId) {
        Employee employee = (Employee) FileProcessing.getUserFromFile(employeeId);

        Shop employeeCurrentShop = FileProcessing.getShopFromFile(employee.getShopID());
        Shop employeeFutureShop = FileProcessing.getShopFromFile(shopId);

        List<Employee> currentShopEmployeesList = employeeCurrentShop.getEmployeesList();
        currentShopEmployeesList = currentShopEmployeesList.stream().filter(employeeList -> employeeList.getUserID() != employee.getUserID()).toList();

        employeeCurrentShop.setEmployeesList(currentShopEmployeesList);
        FileProcessing.updateShopInFile(employeeCurrentShop);

        List<Employee> futureShopEmployeesList = employeeFutureShop.getEmployeesList();
        futureShopEmployeesList.add(employee);
        employeeFutureShop.setEmployeesList(futureShopEmployeesList);
        FileProcessing.updateShopInFile(employeeFutureShop);

        if (employeeCurrentShop.getShopManagerID() != -1) {
            ShopManager employeeCurrenManager = (ShopManager) FileProcessing.getUserFromFile(employeeCurrentShop.getShopManagerID());
            employeeCurrenManager.setEmployeeList(currentShopEmployeesList.stream().filter(employee1 -> employee1.userID != employeeCurrenManager.userID).toList());
            FileProcessing.updateUserInFile(employeeCurrenManager);
        }

        if (employeeFutureShop.getShopManagerID() != -1) {
            ShopManager employeeFutureManager = (ShopManager) FileProcessing.getUserFromFile(employeeFutureShop.getShopManagerID());
            employeeFutureManager.setEmployeeList(futureShopEmployeesList.stream().filter(employee1 -> employee1.userID != employeeFutureManager.userID).toList());
            FileProcessing.updateUserInFile(employeeFutureManager);
        }

        employee.setShopID(employeeFutureShop.getShopID());
        FileProcessing.updateUserInFile(employee);

        for (Shop shop : this.shopList) {
            if (shop.getShopID() == employeeCurrentShop.getShopID()) {
                shop.setEmployeesList(employeeCurrentShop.getEmployeesList());
            }
            if (shop.getShopID() == employeeFutureShop.getShopID()) {
                shop.setEmployeesList(employeeFutureShop.getEmployeesList());
            }
        }
    }

    /**
     * This function allows the CEO to hire a new manager.
     * @param firstName   The first name of the manager a CEO would like to hire.
     * @param lastName    The last name of the manager a CEO would like to hire.
     * @param dateOfBirth The date of birth of the manager a CEO would like to hire.
     * @param phoneNumber The phone of the manager a CEO would like to hire.
     * @param address     The address of the manager a CEO would like to hire.
     * @param shopID      The shopID of the manager a CEO would like to hire.
     */
    public void hireManager(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String address, int shopID) {
        int lastUserId = FileProcessing.getLastIDUsed("users");
        Shop updatedShop = FileProcessing.getShopFromFile(shopID);

        ShopManager newManager = new ShopManager(lastUserId + 1, UserType.manager, firstName, lastName, dateOfBirth, phoneNumber, address, shopID, LocalDate.now(), updatedShop.getEmployeesList());
        FileProcessing.addNewUser(newManager);

        updatedShop.setShopManagerID(newManager.userID);
        FileProcessing.updateShopInFile(updatedShop);

        for (Shop shop : this.shopList) {
            if (shop.getShopID() == shopID) {
                shop.setShopManagerID(newManager.userID);
                break;
            }
        }

        this.employeeList.add(newManager);
        FileProcessing.updateUserInFile(this);
    }

    /**
     * This function allows the CEO to fire a manager.
     * @param managerID The id of the manager to delete.
     * @param shopID    The id of the shop from where the manager is deleted. The id of the manager per shop will be -1.
     */
    public void fireManager(int managerID, int shopID) {
        Shop updatedShop = FileProcessing.getShopFromFile(shopID);
        updatedShop.setShopManagerID(-1);
        FileProcessing.updateShopInFile(updatedShop);
        ShopManager managerToRemove = (ShopManager) FileProcessing.getUserFromFile(managerID);
        this.employeeList = new ArrayList<>(this.employeeList.stream().filter(employee -> employee.userID != managerToRemove.userID).toList());
        FileProcessing.updateUserInFile(this);
        for (Shop shop : this.shopList) {
            if (shop.getShopID() == shopID) {
                shop.setShopManagerID(-1);
                break;
            }
        }
        FileProcessing.deleteUserFromFile(managerID);
    }

    /**
     * This function allows the CEO to add a new product to the shop stock.
     * @param productName The name of the product the CEO wants to add to the shops stock.
     * @param quantity The initial quantity of the product the CEO want to add to the shops stock.
     * @param unit The unit in which the quantity is measured.
     * @param pricePerUnit The price of a unit.
     * @param shopID The id of the shop.
     */
    public void addItem(String productName, double quantity, String unit, double pricePerUnit, int shopID) {
        int lastIdOfProduct = FileProcessing.getLastIDUsed("products");
        Product newProduct = new Product(lastIdOfProduct + 1, productName, quantity, unit, pricePerUnit);
        FileProcessing.addNewProductToFile(newProduct);

        Shop shop = FileProcessing.getShopFromFile(shopID);
        List<Product> listOfShopsProducts = shop.getProductList();
        listOfShopsProducts.add(newProduct);
        shop.setProductList(listOfShopsProducts);

        for (Shop shop1 : this.shopList) {
            if(shop.getShopID()==shop1.getShopID()){
                shop1.setProductList(listOfShopsProducts);
            }
        }
        FileProcessing.updateShopInFile(shop);
    }

    /**
     * This functions allows the CEO to remove a product from the shop stock.
     * @param productId The id of the product we want to remove from the stock.
     * @param shopID The id of the shop the modifications will be made.
     */
    public void removeItem(int productId, int shopID) {
        Product productToRemove = FileProcessing.getProductFromFile(productId);
        Shop shop = FileProcessing.getShopFromFile(shopID);
        List<Product> updatedProductList = shop.getProductList();
        updatedProductList = updatedProductList.stream().filter(product -> product.getProductId() != productToRemove.getProductId()).toList();
        shop.setProductList(updatedProductList);

        FileProcessing.updateShopInFile(shop);
        FileProcessing.deleteProductFromFile(productToRemove.getProductId());

        for (Shop shop1 : this.shopList) {
            if(shop.getShopID()==shop1.getShopID()){
                shop1.setProductList(updatedProductList);
            }
        }
    }

    /**
     * This function helps the CEO to modify the quantity of a product from a specific shop stock.
     * @param productId The id of the product we want to modify the quantity of.
     * @param transactionType The type of transaction we are making , addition or subtraction.
     * @param quantity The quantity with what we want to modify the product.
     * @param shopID The id of the shop in which we want to modify the product.
     */
    public void modifyItem(int productId, String transactionType, double quantity, int shopID) {
        Product productToUpdate = FileProcessing.getProductFromFile(productId);
        double productUpdatedQuantity = productToUpdate.getQuantity();
        productUpdatedQuantity += Objects.equals(transactionType, TransactionType.adding) ? quantity : (quantity * (-1));
        productToUpdate.setQuantity(productUpdatedQuantity);
        FileProcessing.updateProductInFile(productToUpdate);

        Shop shop = FileProcessing.getShopFromFile(shopID);
        List<Product> listOfProducts = shop.getProductList();
        for (Product product : listOfProducts) {
            if (product.getProductId() == productToUpdate.getProductId()) {
                product = productToUpdate;
            }
        }
        shop.setProductList(listOfProducts);

        FileProcessing.updateShopInFile(shop);
    }

    /**
     * This function helps the CEO to hire a new employee.
     * @param firstName The first name of the employee the CEO wants to hire.
     * @param lastName The last name of the employee the CEO want to hire.
     * @param dateOfBirth The date of birth of the employee.
     * @param phoneNumber The phone number of the employee.
     * @param address The address of the employee.
     * @param shopID The shop id in which we will make the hiring.
     */
    public void hireEmployee(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String address, int shopID) {
        int lastIdUsedForUsers = FileProcessing.getLastIDUsed("users");
        Employee newEmployee = new Employee(lastIdUsedForUsers + 1, UserType.employee, firstName, lastName, dateOfBirth, phoneNumber, address, shopID, LocalDate.now());
        FileProcessing.addNewUser(newEmployee);

        this.employeeList.add(newEmployee);
        FileProcessing.updateUserInFile(this);

        Shop shop = FileProcessing.getShopFromFile(shopID);
        List<Employee> listOfEmployees = shop.getEmployeesList();
        listOfEmployees.add(newEmployee);
        shop.setEmployeesList(listOfEmployees);
        FileProcessing.updateShopInFile(shop);

        if (shop.getShopManagerID() != -1) {
            ShopManager shopManagerOfShop = (ShopManager) FileProcessing.getUserFromFile(shop.getShopManagerID());
            List<Employee> employeeListCEO = shopManagerOfShop.getEmployeeList();
            employeeListCEO.add(newEmployee);
            shopManagerOfShop.setEmployeeList(employeeListCEO);
            FileProcessing.updateUserInFile(shopManagerOfShop);
        }

        for (Shop shopThis : this.shopList) {
            if (shopThis.getShopID() == shopID) {
                shopThis.setEmployeesList(listOfEmployees);
                break;
            }
        }
    }

    /**
     * This function helps the CEO to fire an employee.
     * @param employeeID The id of the employee the CEO want to fire.
     * @param shopID The id of the shop from where the employee will be fired.
     */
    public void fireEmployee(int employeeID, int shopID) {
        Employee employeeToFire = (Employee) FileProcessing.getUserFromFile(employeeID);

        this.employeeList = new ArrayList<>(this.employeeList.stream().filter(employee -> employee.userID != employeeToFire.userID).toList());
        FileProcessing.updateUserInFile(this);

        Shop shop = FileProcessing.getShopFromFile(shopID);
        List<Employee> listOfEmployees = shop.getEmployeesList();
        listOfEmployees = listOfEmployees.stream().filter(employee -> employee.getUserID() != employeeToFire.getUserID()).toList();
        shop.setEmployeesList(listOfEmployees);
        FileProcessing.updateShopInFile(shop);

        if (shop.getShopManagerID() != -1) {
            ShopManager shopManagerOfShop = (ShopManager) FileProcessing.getUserFromFile(shop.getShopManagerID());
            List<Employee> employeeListCEO = shopManagerOfShop.getEmployeeList();
            employeeListCEO = employeeListCEO.stream().filter(employee -> employee.getUserID() != employeeToFire.getUserID()).toList();
            shopManagerOfShop.setEmployeeList(employeeListCEO);
            FileProcessing.updateUserInFile(shopManagerOfShop);
        }

        FileProcessing.deleteUserFromFile(employeeToFire.getUserID());

        for (Shop shopThis : this.shopList) {
            if (shopThis.getShopID() == shopID) {
                shopThis.setEmployeesList(listOfEmployees);
                break;
            }
        }

    }
}
