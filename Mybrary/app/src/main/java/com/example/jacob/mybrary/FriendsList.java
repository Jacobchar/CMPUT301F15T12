package com.example.jacob.mybrary;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mason Strong on 11/3/2015.
 */
public class FriendsList implements Parcelable{
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

    /**
     * This returns a list of user objects that corresponds with given UUIDs in friendlist
     * @return ArrayList of users
     */
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

    /**
     * returns an arrayList of names corresponding with UUIDs in friendslist
     * @return
     */
    public ArrayList<String> getNames(){
        ArrayList<String> userNames = new ArrayList<>();
        ArrayList<User> userList = getUsers();
        for (User user : userList){
            userNames.add(user.getName());
        }
        return userNames;
    }

    public ArrayList<User> getByName(String name){
        DataManager dataManager = DataManager.getInstance();
        ArrayList<User> userList = null;
        try {
            //this should be modified to do a partial match
            userList = dataManager.searchUsers("{\"query\":{\"query_string\":{\"default_field\":\"name\",\"query\":\""+ name +"\"}}}");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userList;
    }
    /**
     * returns the number of friends in the friendList
     * @return
     */
    public int numFriends(){
        return friendList.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(friendList);
    }

    protected FriendsList(Parcel parcel){
        friendList = (Collection<UUID>) parcel.readValue(Collection.class.getClassLoader());
    }

    public static final Parcelable.Creator<FriendsList> CREATOR = new Parcelable.Creator<FriendsList>() {
        @Override
        public FriendsList createFromParcel(Parcel in) {
            return new FriendsList(in);
        }

        @Override
        public FriendsList[] newArray(int size) {
            return new FriendsList[size];
        }
    };

}
