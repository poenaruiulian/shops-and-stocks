package main.classes;

import main.enums.UserType;

import java.util.Date;

public class Person {
    private int userID;
    private UserType userType;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String phoneNumber;
    private String address;

    public Person(){
        this.userID = -1;
        this.userType = UserType.necunoscut;
        this.firstName ="";
        this.lastName="";
        this.dateOfBirth=new Date();
        this.phoneNumber="";
        this.address="";
    }
    public Person(int userID, UserType userType ,String firstName, String lastName, Date dateOfBirth, String phoneNumber, String address){
        this.userID = userID;
        this.userType = userType;
        this.firstName =firstName;
        this.lastName=lastName;
        this.dateOfBirth=dateOfBirth;
        this.phoneNumber=phoneNumber;
        this.address=address;
    }

}
