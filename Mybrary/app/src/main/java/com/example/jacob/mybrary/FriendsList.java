package com.example.jacob.mybrary;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Mason Strong on 11/3/2015.
 */
public class FriendsList {
    private Collection<User> friendList;

    FriendsList(){
        friendList = new ArrayList<>();
    }

    public void addFriend(User user){
        friendList.add(user);
    }

    public void removeFriend(User user){
        friendList.remove(user);
    }

    public boolean hasFriend(User user){
        if (friendList.contains(user))
            return true;
        else
            return false;
    }

    public int numFriends(){
        return friendList.size();
    }
}
