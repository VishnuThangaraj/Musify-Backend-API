package com.vishnuthangaraj.Musify.Models;

public class User {
    private String name, mobileNumber;

    // Np-Args Constructor
    public User() {
    }

    // All-Args Constructor
    public User(String name, String mobileNumber) {
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    // Getters and Setters
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
