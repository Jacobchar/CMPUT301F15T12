package com.example.jacob.mybrary;


import android.test.ActivityInstrumentationTestCase2;

import java.util.UUID;

// Class tests the Basic Functionality of the User Class
public class UserTest extends ActivityInstrumentationTestCase2{
    private String name = "TestUser";
    private String username = "TestUsername";
    private String emailAddress = "user@test.com";
    private String phoneNum = "555-5555";
    private String gender = "Male";
    private String bio = "Likes to take long walks on the beach";
    private String city = "Edmonton";

    public UserTest() {
        super(User.class);
    }

    public void testCreateUser(){
        User testUser = new User(name, username, emailAddress, phoneNum, gender, bio, city);
    }

    public void testGetInventory(){
        User testUser = new User(name, username, emailAddress, phoneNum, gender, bio, city);
        Inventory testInventory = testUser.getInventory();
        //Getting items from an empty inventory should return an empty list, not null
        //assertTrue(testInventory.getItems().equals(null));
    }

    public void testGetUUID(){
        User testUser = new User(name, username, emailAddress, phoneNum, gender, bio, city);
        UUID testID = testUser.getUUID();
    }

    public void testGetName(){
        User testUser = new User(name, username, emailAddress, phoneNum, gender, bio, city);
        String gotName = testUser.getName();
        assertEquals(gotName, name);
    }
    
    public void testGetUsername(){
        User testUser = new User(name, username, emailAddress, phoneNum, gender, bio, city);
        String gotUsername = testUser.getUsername();
        assertEquals(gotUsername, username);
    }

    public void testGetPhoneNumber(){
        User testUser = new User(name, username, emailAddress, phoneNum, gender, bio, city);
        String gotPhoneNum = testUser.getPhoneNumber();
        assertEquals(gotPhoneNum, phoneNum);
    }

    public void testGetGender(){
        User testUser = new User(name, username, emailAddress, phoneNum, gender, bio, city);
        String gotGender = testUser.getGender();
        assertEquals(gotGender, gender);
    }

    public void testGetBio(){
        User testUser = new User(name, username, emailAddress, phoneNum, gender, bio, city);
        String gotBio = testUser.getBio();
        assertEquals(gotBio, bio);
    }
    
    public void testGetEmailAddress(){
        User testUser = new User(name, username, emailAddress, phoneNum, gender, bio, city);
        String gotEmail = testUser.getEmailAddress();
        assertEquals(gotEmail, emailAddress);
    }
    
    public void testGetCity(){
        User testUser = new User(name, username, emailAddress, phoneNum, gender, bio, city);
        String gotCity = testUser.getCity();
        assertEquals(gotCity, city);
    }
}
