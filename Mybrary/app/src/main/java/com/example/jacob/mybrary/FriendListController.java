package com.example.jacob.mybrary;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mason Strong on 11/18/2015.
 */
public class FriendListController {
    DataManager dataManager = DataManager.getInstance();
    ConnectionManager connectionManager = ConnectionManager.getInstance();
    ArrayList<User> userList = new ArrayList<>();
    LocalUser localUser = LocalUser.getInstance();

    FriendListController(){

    }


    public void removeFriend(final String friend,final Context context) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                connectionManager.updateConnectivity(context);
                try {
                    //remove friend based on username from localUser
                    localUser.getFriendsList().removeFriend(dataManager.searchUsers(friend).get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
        });
        t.start();
    }


    public void addNewFriend(final String friend, final Context context) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                connectionManager.updateConnectivity(context);
                try {
                    userList = dataManager.searchUsers(friend);
                    if (userList.size() != 0) {
                        localUser.getFriendsList().addFriend(userList.get(0));
                        System.out.println(userList.get(0).getName());
                    } else {
                        System.out.println("Crap!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
        });

        t.start();
    }
}
