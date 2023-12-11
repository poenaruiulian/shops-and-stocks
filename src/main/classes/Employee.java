package main.classes;

import main.enums.UserType;

import java.util.Date;
import java.util.List;

public class Employee extends Person {
    private int shopID;
    private Date dateOfEmployment;

    public Employee() {
        super();
        shopID = -1;
        dateOfEmployment = new Date();
    }
    public Employee(int userID, UserType userType, String firstName, String lastName, Date dateOfBirth, String phoneNumber, String address, int shopID, Date dateOfEmployment) {
        super(userID, userType, firstName, lastName, dateOfBirth, phoneNumber, address);
        this.shopID = shopID;
        this.dateOfEmployment = dateOfEmployment;
    }


}
