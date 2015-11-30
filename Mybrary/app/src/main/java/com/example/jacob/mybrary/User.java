package com.example.jacob.mybrary;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

/**
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
     * @return Returns the Name of the User
     */
    public String getName() {
        return name;
    }

    /**
     * @return Returns the Email Address of the User
     */
    public String getEmailAddress(){
        return emailAddress;
    }

    /**
     * @return Returns the Phone Number of the User
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return Returns the Gender of the User
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return Returns the Bio of the User
     */
    public String getBio() {
        return bio;
    }

    /**
     * @return Returns the City of the User
     */
    public String getCity(){
        return city;
    }

    /**
     * @return Returns the UUID of the User
     */
    public UUID getUUID() {
        return myUUID;
    }

    /**
     * @return Returns the Inventory of the User
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * @return Returns the Inventory of the User that is shared with Others
     */
    public Inventory getPublicInventory() {

        ArrayList<Book> arrayList = inventory.getPublicBooks();
        Inventory inventory = new Inventory();
        inventory.convertFriendsArrayListToInventory(arrayList);
        return inventory;
    }

    /**
     * @return Returns the Friendslist of the User
     */
    public FriendsList getFriendsList() {
        return friendsList;
    }

    /**
     * @return returns the succTrades of the User
     */
    public int getSuccTrades() {
        return succTrades;
    }

    /**
     * Sets the succTrades of the user to the inputted value
     * @param succTrades Inputted Value
     */
    public void setSuccTrades(int succTrades) {
        this.succTrades = succTrades;
    }

    /**
     * @return Returns the state of the downloadImages boolean
     */
    public Boolean getDownloadImages() {
        return downloadImages;
    }

    /**
     * Sets the downloadImages boolean of the User to the inputted value
     * @param downloadImages inputted value
     */
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
    public String toString(){
        return this.getName() + " has " + this.getSuccTrades() + " trade(s).";
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

    /**
     * Sets the information fields of the User to that of an inputted user
     * @param newUser inputted User to change the user
     */
    public void setSelf(User newUser){
        this.name = newUser.getName();
        this.emailAddress = newUser.getEmailAddress();
        this.phoneNumber = newUser.getPhoneNumber();
        this.gender = newUser.getGender();
        this.bio = newUser.getBio();
        this.city = newUser.getCity();
    }

    /**
     * Sets the UUID of the User to an Inputted UUID
     * @param newUUID inputted UUID to
     */
    public void setUUID(UUID newUUID) {
        this.myUUID = newUUID;
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

    /**
     *
     * @param o User object to be compared with this user
     * @return Returns the difference between the inputted object and the user's successful trades
     * parameter
     */
    @Override
    public int compareTo(Object o) {
        if (o.getClass().equals(User.class)){
            User user = (User) o;
            return this.getSuccTrades()-user.getSuccTrades();
        }
        return 0;
    }
}
