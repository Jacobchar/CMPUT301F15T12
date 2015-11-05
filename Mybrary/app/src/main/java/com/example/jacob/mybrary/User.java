package com.example.jacob.mybrary;

import java.util.UUID;

//Class to hold the data for an individual user of the app
public class User {
    private String name;
    private String emailAddress;
    private String phoneNumber;
    private String gender;
    private String bio;
    private String city;
    private UUID myUUID;
    private Inventory inventory;

    public User(String name, String emailAddress, String phoneNumber, String gender, String bio, String city) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.bio = bio;
        this.city = city;
        this.inventory = new Inventory();
    }

    public String getName() {
        return name;
    }
    
    public String getEmailAddress(){
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getBio() {
        return bio;
    }
    
    public String getCity(){
        return city;
    }

    public UUID getUUID() {
        return myUUID;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
