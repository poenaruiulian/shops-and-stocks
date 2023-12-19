package main.data;

import java.time.LocalDate;
import java.util.List;

public class CEO extends Person{
    private List<Employee> employeeList;
    private List<Shop> shopList;

    public CEO(){
        super();
        this.employeeList = null;
        this.shopList = null;
    }
    public CEO(int userID, String userType , String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String address, List<Employee> employeeList, List<Shop> shopList){
        super(userID, userType, firstName, lastName, dateOfBirth, phoneNumber, address);
        this.employeeList = employeeList;
        this.shopList=shopList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public List<Shop> getShopList() {
        return shopList;
    }
}
