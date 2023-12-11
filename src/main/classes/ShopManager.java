package main.classes;

import main.enums.UserType;

import java.util.Date;
import java.util.List;

public class ShopManager extends Employee {
    private List<Employee> employeeList;

    public ShopManager() {
        super();
        this.employeeList = null;
    }
    public ShopManager(int userID, UserType userType, String firstName, String lastName, Date dateOfBirth, String phoneNumber, String address, int shopID, Date dateOfEmployment, List<Employee> employeeList) {
        super(userID, userType, firstName, lastName, dateOfBirth, phoneNumber, address, shopID, dateOfEmployment);
        this.employeeList = employeeList;
    }
}
