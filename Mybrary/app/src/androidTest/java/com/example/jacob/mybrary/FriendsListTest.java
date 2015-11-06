package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;

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
}
