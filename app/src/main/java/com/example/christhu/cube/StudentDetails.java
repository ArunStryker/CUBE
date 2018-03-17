package com.example.christhu.cube;

/**
 * Created by christhu on 13/3/18.
 */

public class StudentDetails {


    private String name;
    private String phoneNumber;

    public StudentDetails() {
        // This is default constructor.
    }

    public String getStudentName() {

        return name;
    }

    public void setStudentName(String name) {

        this.name = name;
    }

    public String getStudentPhoneNumber() {

        return phoneNumber;
    }

    public void setStudentPhoneNumber(String phonenumber) {

        this.phoneNumber = phonenumber;
    }

}