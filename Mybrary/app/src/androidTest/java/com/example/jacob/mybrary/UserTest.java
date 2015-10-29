package com.example.jacob.mybrary;


import android.test.ActivityInstrumentationTestCase2;

import java.util.UUID;

//Tests for the User Class
public class UserTest extends ActivityInstrumentationTestCase2{
    private String name = "TestUser";
    private String phoneNum = "555-5555";
    private String gender = "Male";
    private String bio = "Likes to take long walks on the beach";

    public UserTest() {
        super(User.class);
    }

    public void testCreateUser(){
        User testUser = new User(name, phoneNum, gender, bio);
    }

    public void testGetInventory(){
        User testUser = new User(name, phoneNum, gender, bio);
        Inventory testInventory = testUser.getInventory();
        assertTrue(testInventory.getItems().equals(Null));
    }

    public void testGetUUID(){
        User testUser = new User(name, phoneNum, gender, bio);
        UUID testID = testUser.getUUID;
    }

    public void testGetName(){
        User testUser = new User(name, phoneNum, gender, bio);
        String gotName = testUser.getName();
        assertEquals(gotName, name);
    }

    public void testGetPhoneNumber(){
        User testUser = new User(name, phoneNum, gender, bio);
        String gotPhoneNum = testUser.getPhoneNumber();
        assertEquals(gotPhoneNum, phoneNum);
    }

    public void testGetGender(){
        User testUser = new User(name, phoneNum, gender, bio);
        String gotGender = testUser.getGender();
        assertEquals(gotGender, gender);
    }

    public void testGetBio(){
        User testUser = new User(name, phoneNum, gender, bio);
        String gotBio = testUser.getBio();
        assertEquals(gotBio, bio);
    }
}
