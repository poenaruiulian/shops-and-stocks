package main.classes;

import main.enums.UserType;

import java.util.Date;
import java.util.List;

public class CEO extends Person{
    private List<Employee> employeeList;
    private List<Shop> shopList;

    public CEO(){
        super();
        this.employeeList = null;
        this.shopList = null;
    }
    public CEO(int userID, UserType userType , String firstName, String lastName, Date dateOfBirth, String phoneNumber, String address, List<Employee> employeeList, List<Shop> shopList){
        super(userID, userType, firstName, lastName, dateOfBirth, phoneNumber, address);
        this.employeeList = employeeList;
        this.shopList=shopList;
    }

}
