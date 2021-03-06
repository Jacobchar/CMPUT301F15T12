package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mason Strong on 11/3/2015.
 */
public class FriendsListTest extends ActivityInstrumentationTestCase2 {
    public FriendsListTest() { super(FriendsList.class); }

    public void testAddFriend(){
        User user = new User("Joe", "joe@gmail.com", "123-5678", "Male", "this is my bio", "Edmonton");
        FriendsList friendsList = new FriendsList();
        friendsList.addFriend(user);
        //this test is coupled with testHasFriend
        assertTrue(friendsList.hasFriend(user));
    }

    public void testRemoveFriend(){
        User user = new User("Joe", "joe@gmail.com", "123-5678", "Male", "this is my bio", "Edmonton");
        FriendsList friendsList = new FriendsList();
        friendsList.addFriend(user);
        friendsList.removeFriend(user);
        //test is coupled with testHasFriend
        assertFalse(friendsList.hasFriend(user));
    }

    public void testHasFriend(){
        User user1 = new User("Joe", "joe@gmail.com", "123-5678", "Male", "this is my bio", "Edmonton");
        User user2 = new User("Vicky", "vicky@gmail.com", "234-5678", "Female - maybe", "this is my bio", "Beaumont");
        User user3 = new User("Dom", "dom@gmail.com", "333-4678", "Male", "this is my bio", "St. Albert");
        User user4 = new User("Jake", "jake@gmail.com", "666-6668", "Male", "this is my bio", "Edmonton");
        FriendsList friendsList = new FriendsList();
        friendsList.addFriend(user1);
        friendsList.addFriend(user2);
        friendsList.addFriend(user3);
        //this test is coupled with testAddFriend
        assertTrue(friendsList.hasFriend(user1));
        assertTrue(friendsList.hasFriend(user2));
        assertTrue(friendsList.hasFriend(user3));
       // assertFalse(friendsList.hasFriend(user4));
    }

    public void testNumFriends(){
        User user1 = new User("Joe", "joe@gmail.com", "123-5678", "Male", "this is my bio", "Edmonton");
        User user2 = new User("Vicky", "vicky@gmail.com", "234-5678", "Female - maybe", "this is my bio", "Beaumont");
        User user3 = new User("Dom", "dom@gmail.com", "333-4678", "Male", "this is my bio", "St. Albert");
        FriendsList friendsList = new FriendsList();
        friendsList.addFriend(user1);
        friendsList.addFriend(user2);
        friendsList.addFriend(user3);
        assertTrue(friendsList.numFriends()==3);
    }

    public void testGetUser(){
        User user1 = new User("Joe", "joe@gmail.com", "123-5678", "Male", "this is my bio", "Edmonton");
        User user2 = new User("Vicky", "vicky@gmail.com", "234-5678", "Female - maybe", "this is my bio", "Beaumont");
        User user3 = new User("Dom", "dom@gmail.com", "333-4678", "Male", "this is my bio", "St. Albert");
        FriendsList friendsList = new FriendsList();

        friendsList.addFriend(user1);
        friendsList.addFriend(user2);
        friendsList.addFriend(user3);

        DataManager dataManager = DataManager.getInstance();
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        connectionManager.updateConnectivity(getInstrumentation().getTargetContext().getApplicationContext());

        try {
            dataManager.storeUser(user1);
            dataManager.storeUser(user2);
            dataManager.storeUser(user3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<User> userList = friendsList.getUsers();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(userList.get(0).equals(user1));
        assertTrue(userList.get(1).equals(user2));
        assertTrue(userList.get(2).equals(user3));


        try {
            dataManager.removeUser(user1.getUUID().toString());
            dataManager.removeUser(user2.getUUID().toString());
            dataManager.removeUser(user3.getUUID().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void testGetNames(){
        User user1 = new User("Joe", "joe@gmail.com", "123-5678", "Male", "this is my bio", "Edmonton");
        User user2 = new User("Vicky", "vicky@gmail.com", "234-5678", "Female - maybe", "this is my bio", "Beaumont");
        User user3 = new User("Dom", "dom@gmail.com", "333-4678", "Male", "this is my bio", "St. Albert");
        FriendsList friendsList = new FriendsList();

        friendsList.addFriend(user1);
        friendsList.addFriend(user2);
        friendsList.addFriend(user3);

        DataManager dataManager = DataManager.getInstance();
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        if (connectionManager.updateConnectivity(getInstrumentation().getTargetContext().getApplicationContext()))
        {
            System.out.println("we're connected!");
        }

        try {
            if (dataManager.storeUser(user1)){
                System.out.println("user1 got added!");
            }
            dataManager.storeUser(user2);
            dataManager.storeUser(user3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<String> userNames = friendsList.getNames();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(userNames.get(0).equals(user1.getName()));
        assertTrue(userNames.get(1).equals(user2.getName()));
        assertTrue(userNames.get(2).equals(user3.getName()));

        try {
            dataManager.removeUser(user1.getUUID().toString());
            dataManager.removeUser(user2.getUUID().toString());
            dataManager.removeUser(user3.getUUID().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void testGetByName(){
        User user1 = new User("Joe", "joe@gmail.com", "123-5678", "Male", "this is my bio", "Edmonton");
        User user2 = new User("Vicky", "vicky@gmail.com", "234-5678", "Female - maybe", "this is my bio", "Beaumont");
        User user3 = new User("Dom", "dom@gmail.com", "333-4678", "Male", "this is my bio", "St. Albert");
        FriendsList friendsList = new FriendsList();

        friendsList.addFriend(user1);
        friendsList.addFriend(user2);
        friendsList.addFriend(user3);

        DataManager dataManager = DataManager.getInstance();
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        if (connectionManager.updateConnectivity(getInstrumentation().getTargetContext().getApplicationContext()))
        {
            System.out.println("we're connected!");
        }

        try {
            if (dataManager.storeUser(user1)){
                System.out.println("user1 got added!");
            }
            dataManager.storeUser(user2);
            dataManager.storeUser(user3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<String> userNames = friendsList.getNames();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        System.out.println(friendsList.getByName("Joe").get(0).getName());
        assertTrue(friendsList.getByName("Joe").get(0).getName().equals(user1.getName()));

        try {
            dataManager.removeUser(user1.getUUID().toString());
            dataManager.removeUser(user2.getUUID().toString());
            dataManager.removeUser(user3.getUUID().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
