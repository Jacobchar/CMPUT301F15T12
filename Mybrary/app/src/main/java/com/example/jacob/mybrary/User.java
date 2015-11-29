package com.example.jacob.mybrary;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.UUID;

//Class to hold the data for an individual user of the app

/**
 * Created by Ben
 *
 * Model class that represents a User of the app. Contains information specific to the User,
 * the User's inventory, and a unique User id generated on creation.
 *
 */
public class User implements Parcelable, Comparable {
    private String name;
    private String emailAddress;
    private String phoneNumber;
    private String gender;
    private String bio;
    private String city;
    private UUID myUUID;
    private Inventory inventory;
    private FriendsList friendsList;
    private int succTrades = 0;
    private Boolean downloadImages = false;

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
     * Returns the Inventory of the User that is shared with Others
     * @return
     */
    public Inventory getPublicInventory() {

        return inventory.getPublicBooks();
    }

    /**
     * Returns the Friendslist of the User
     * @return
     */
    public FriendsList getFriendsList() {
        return friendsList;
        //return new FriendsList();
    }

    public int getSuccTrades() {
        return succTrades;
    }

    public void setSuccTrades(int succTrades) {
        this.succTrades = succTrades;
    }

    public Boolean getDownloadImages() {
        return downloadImages;
    }

    public void setDownloadImages(Boolean downloadImages) {
        this.downloadImages = downloadImages;
    }

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
        return !(myUUID != null ? !myUUID.equals(user.myUUID) : user.myUUID != null);

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
        return result;
    }

    public void setSelf(User newUser){
        this.name = newUser.getName();
        this.emailAddress = newUser.getEmailAddress();
        this.phoneNumber = newUser.getPhoneNumber();
        this.gender = newUser.getGender();
        this.bio = newUser.getBio();
        this.city = newUser.getCity();
    }

    public void setUUID(UUID myUUID) {
        this.myUUID = myUUID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(emailAddress);
        dest.writeString(phoneNumber);
        dest.writeString(gender);
        dest.writeString(bio);
        dest.writeString(city);
        dest.writeValue(myUUID);
        dest.writeValue(inventory);
        dest.writeValue(friendsList);
        dest.writeInt(succTrades);
        dest.writeString(downloadImages.toString());
    }

    //Got from http://www.parcelabler.com/
    protected User(Parcel parcel){
        name = parcel.readString();
        emailAddress = parcel.readString();
        phoneNumber = parcel.readString();
        gender = parcel.readString();
        bio = parcel.readString();
        city = parcel.readString();
        myUUID = (UUID) parcel.readValue(UUID.class.getClassLoader());
        inventory = (Inventory) parcel.readValue(Inventory.class.getClassLoader());
        friendsList = (FriendsList) parcel.readValue(FriendsList.class.getClassLoader());
        succTrades = parcel.readInt();
        downloadImages = Boolean.valueOf(parcel.readString());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int compareTo(Object o) {
        if (o.getClass().equals(User.class)){
            User user = (User) o;
            return succTrades - user.getSuccTrades();
        }
        return 0;
    }
}
