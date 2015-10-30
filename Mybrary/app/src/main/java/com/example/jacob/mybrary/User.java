package com.example.jacob.mybrary;

import java.util.UUID;

//User Class
public class User {
    private String name;
    private String phoneNumber;
    private String gender;
    private String bio;
    private UUID myUUID;
    private Inventory inventory;

    public User(String name, String phoneNumber, String gender, String bio) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.bio = bio;
        this.inventory = new Inventory();
    }

    public String getName() {
        return name;
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

    public UUID getUUID() {
        return myUUID;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
