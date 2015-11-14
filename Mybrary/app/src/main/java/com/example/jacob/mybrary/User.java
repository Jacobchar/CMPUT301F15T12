package com.example.jacob.mybrary;

import java.util.UUID;

//Class to hold the data for an individual user of the app

/**
 * Created by Ben
 *
 * Model class that represents a User of the app. Contains information specific to the User,
 * the User's inventory, and a unique User id generated on creation.
 *
 */
public class User {
    private String name;
    private String emailAddress;
    private String phoneNumber;
    private String gender;
    private String bio;
    private String city;
    private UUID myUUID;
    private Inventory inventory;
    private FriendsList friendsList;

    /**
     * Generates a User with an empty Inventory and generated UUID
     * @param name
     * @param emailAddress
     * @param phoneNumber
     * @param gender
     * @param bio
     * @param city
     */
    public User(String name, String emailAddress, String phoneNumber, String gender, String bio, String city) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.bio = bio;
        this.city = city;
        this.inventory = new Inventory();
        this.myUUID = UUID.randomUUID();
        this.friendsList = new FriendsList();
    }

    /**
     * Returns the Name of the User
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the Email Address of the User
     * @return
     */
    public String getEmailAddress(){
        return emailAddress;
    }

    /**
     * Returns the Phone Number of the User
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the Gender of the User
     * @return
     */
    public String getGender() {
        return gender;
    }

    /**
     * Returns the Bio of the User
     * @return
     */
    public String getBio() {
        return bio;
    }

    /**
     * Returns the City of the User
     * @return
     */
    public String getCity(){
        return city;
    }

    /**
     * Returns the UUID of the User
     * @return
     */
    public UUID getUUID() {
        return myUUID;
    }

    /**
     * Returns the Inventory of the User
     * @return
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Returns the Friendslist of the User
     * @return
     */
    public FriendsList getFriendsList() { return friendsList;}

}
