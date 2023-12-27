package main.data;

import main.types.UserType;

import java.time.LocalDate;

public class Person {
    protected int userID;
    protected String userType;
    protected String firstName;
    protected String lastName;
    protected LocalDate dateOfBirth;
    protected String phoneNumber;
    protected String address;

    public Person(){
        this.userID = -1;
        this.userType = UserType.necunoscut;
        this.firstName ="";
        this.lastName="";
        this.dateOfBirth=null;
        this.phoneNumber="";
        this.address="";
    }
    public Person(int userID, String userType ,String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String address){
        this.userID = userID;
        this.userType = userType;
        this.firstName =firstName;
        this.lastName=lastName;
        this.dateOfBirth=dateOfBirth;
        this.phoneNumber=phoneNumber;
        this.address=address;
    }

    public int getUserID() {
        return userID;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public String getAddress() {
        return address;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getUserType() {
        return userType;
    }
}
