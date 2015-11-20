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


    public void removeFriend(String friend, Context context) {
        class oneOff implements Runnable{
            String friend;
            Context context;

            oneOff(String f, Context c){
                friend = f;
                context = c;
            }

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
        }
    }

    public void addNewFriend(String friend, Context context) {
        class oneOff implements Runnable{
            String friend;
            Context context;

            oneOff(String f, Context c){
                friend = f;
                context = c;
            }

            public void run() {
                connectionManager.updateConnectivity(context);
                try {
                    userList = dataManager.searchUsers(friend);
                    if (userList.size()!=0){
                        localUser.getFriendsList().addFriend(userList.get(0));
                    }
                    else {
                        //notify friend not found?
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
