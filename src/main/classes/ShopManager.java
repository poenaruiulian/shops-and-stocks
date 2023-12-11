package main.classes;

import main.enums.UserType;

import java.time.LocalDate;
import java.util.Date;
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
}
