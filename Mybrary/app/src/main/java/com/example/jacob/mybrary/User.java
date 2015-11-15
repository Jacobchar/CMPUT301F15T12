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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (emailAddress != null ? !emailAddress.equals(user.emailAddress) : user.emailAddress != null)
            return false;
        if (phoneNumber != null ? !phoneNumber.equals(user.phoneNumber) : user.phoneNumber != null)
            return false;
        if (gender != null ? !gender.equals(user.gender) : user.gender != null) return false;
        if (bio != null ? !bio.equals(user.bio) : user.bio != null) return false;
        if (city != null ? !city.equals(user.city) : user.city != null) return false;
        if (myUUID != null ? !myUUID.equals(user.myUUID) : user.myUUID != null) return false;
        if (inventory != null ? !inventory.equals(user.inventory) : user.inventory != null)
            return false;
        return !(friendsList != null ? !friendsList.equals(user.friendsList) : user.friendsList != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (bio != null ? bio.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (myUUID != null ? myUUID.hashCode() : 0);
        result = 31 * result + (inventory != null ? inventory.hashCode() : 0);
        result = 31 * result + (friendsList != null ? friendsList.hashCode() : 0);
        return result;
    }
}
