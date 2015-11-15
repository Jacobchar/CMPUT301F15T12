package com.example.jacob.mybrary;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Mason Strong on 11/3/2015.
 */
public class FriendsList {
    private Collection<UUID> friendList;
    DataManager dataManager = DataManager.getInstance();

    FriendsList(){
        friendList = new ArrayList<>();
    }

    /**
     * Takes in a user object and takes in its UUID to be stored in an arraylist
     * @param user the user to be added to the list
     */
    public void addFriend(User user){
        friendList.add(user.getUUID());
    }

    /**
     * Takes in a UUID and stores it into the arraylist of friend IDs
     * @param uuid the ID of the user to be added
     */
    public void addFriend(UUID uuid){
        friendList.add(uuid);
    }

    /**
     * Removes a friend based on the user object
     * @param user to be removed
     */
    public void removeFriend(User user){
        friendList.remove(user.getUUID());
    }

    /**
     * Removes a friend based the uuid of the friend
     * @param uuid of the friend to be removed
     */
    public void removeFriend(UUID uuid){
        friendList.remove(uuid);
    }

    /**
     * Checks to see if a user is present in the friendslist
     * @param user to be checked for in the list
     * @return
     */
    public boolean hasFriend(User user){
        if (friendList.contains(user.getUUID()))
            return true;
        else
            return false;
    }

    /**
     * Checks to see if a uuid is present in the friendslist
     * @param uuid of the user to be checked for in the list
     * @return
     */
    public boolean hasFriend(UUID uuid){
        if (friendList.contains(uuid))
            return true;
        else
            return false;
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> userList = new ArrayList<>();
        for (UUID uuid : friendList){
            try {
                userList.add(dataManager.retrieveUser(uuid.toString()));
            } catch (IOException e) {
                e.printStackTrace();
                //return null;
            } catch (JSONException e) {
                e.printStackTrace();
                //return null;
            }
        }
        return userList;
    }

    public int numFriends(){
        return friendList.size();
    }
}
