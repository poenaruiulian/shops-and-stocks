package main.classes;

import java.time.LocalDate;

public class Employee extends Person {
    private int shopID;
    private LocalDate dateOfEmployment;

    public Employee() {
        super();
        shopID = -1;
        dateOfEmployment = null;
    }
    public Employee(int userID, String userType, String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String address, int shopID, LocalDate dateOfEmployment) {
        super(userID, userType, firstName, lastName, dateOfBirth, phoneNumber, address);
        this.shopID = shopID;
        this.dateOfEmployment = dateOfEmployment;
    }

    public int getShopID() {
        return shopID;
    }
    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }
}
