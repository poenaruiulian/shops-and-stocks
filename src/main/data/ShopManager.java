package main.data;

import main.files.FileProcessing;
import main.types.UserType;

import java.time.LocalDate;
import java.util.List;

public class ShopManager extends Employee {
    private List<Employee> employeeList;

    public ShopManager() {
        super();
        this.employeeList = null;
    }
    public ShopManager(int userID, String userType, String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String address, int shopID, LocalDate dateOfEmployment, List<Employee> employeeList) {
        super(userID, userType, firstName, lastName, dateOfBirth, phoneNumber, address, shopID, dateOfEmployment);
        this.employeeList = employeeList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    /**
     * Updates the employee list of the shop manager, CEO employee list and shop's employee list.
     * @param firstName The first name of a person a shop manager would hire
     * @param lastName The last name of a person a shop manager would hire
     * @param dateOfBirth The date of birth of a person a shop manager would hire
     * @param phoneNumber The phone number of a person a shop manager would hire
     * @param address The address of a person a shop manager would hire
     */
    public void hireEmployee(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String address){
        int lastIdUsedForUsers = FileProcessing.getLastIDUsed("users");
        Employee newEmployee = new Employee(lastIdUsedForUsers+1, UserType.employee, firstName, lastName, dateOfBirth, phoneNumber, address, this.shopID,LocalDate.now());
        FileProcessing.addNewUser(newEmployee);

        this.employeeList.add(newEmployee);
        FileProcessing.updateUserInFile(this);

        Shop shop = FileProcessing.getShopFromFile(this.shopID);
        List<Employee> listOfEmployees = shop.getEmployeesList();
        listOfEmployees.add(newEmployee);
        shop.setEmployeesList(listOfEmployees);
        FileProcessing.updateShopInFile(shop);

        CEO ceoOfShop = (CEO) FileProcessing.getUserFromFile(shop.getOwnerID());
        List<Employee> employeeListCEO = ceoOfShop.getEmployeeList();
        employeeListCEO.add(newEmployee);
        ceoOfShop.setEmployeeList(employeeListCEO);
        FileProcessing.updateUserInFile(ceoOfShop);
    }

    /**
     * Updates the employee list of the shop manager, CEO employee list and shop's employee list.
     * @param employeeID The id of the employee a shop manager would want to hire
     */
    public void fireEmployee(int employeeID){
        Employee employeeToFire = (Employee) FileProcessing.getUserFromFile(employeeID);

        this.employeeList.remove(employeeToFire);
        FileProcessing.updateUserInFile(this);

        Shop shop = FileProcessing.getShopFromFile(this.shopID);
        List<Employee> listOfEmployees = shop.getEmployeesList();
        listOfEmployees = listOfEmployees.stream().filter(employee -> employee.getUserID()!=employeeToFire.getUserID()).toList();
        shop.setEmployeesList(listOfEmployees);
        FileProcessing.updateShopInFile(shop);

        CEO ceoOfShop = (CEO) FileProcessing.getUserFromFile(shop.getOwnerID());
        List<Employee> employeeListCEO = ceoOfShop.getEmployeeList();
        employeeListCEO= employeeListCEO.stream().filter(employee -> employee.getUserID()!=employeeToFire.getUserID()).toList();
        ceoOfShop.setEmployeeList(employeeListCEO);
        FileProcessing.updateUserInFile(ceoOfShop);

        FileProcessing.deleteUserFromFile(employeeToFire.getUserID());

    }
}
