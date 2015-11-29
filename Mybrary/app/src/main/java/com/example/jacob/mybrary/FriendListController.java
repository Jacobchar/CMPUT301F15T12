package com.example.jacob.mybrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    public void updateUI(final ListView view, final ArrayAdapter<?> adapter, Activity parent) {
        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }


    public void updateFriendList(final Activity parent, final ListView friendListView, final Context context) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                connectionManager.updateConnectivity(context);
                ArrayList<String> myFriends = localUser.getFriendsList().getNames();

                ArrayAdapter<String> listAdapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, myFriends);
                updateUI(friendListView, listAdapter, parent);
            }
        });
        t.start();
    }

    public void removeFriend(final String friend,final Context context) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                connectionManager.updateConnectivity(context);
                localUser.getFriendsList().removeFriend(localUser.getFriendsList().getByName(friend).get(0));

                try {
                    dataManager.saveLocalUser();
                } catch (IOException e) {
                    e.printStackTrace();
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

                userList = localUser.getFriendsList().getByName(friend);
                if (userList.size() != 0) {
                    localUser.getFriendsList().addFriend(userList.get(0));
                    System.out.println(userList.get(0).getName());
                } else {
                    System.out.println("Crap!"); // this should display something to the user!!
                }

                try {
                    dataManager.saveLocalUser();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        t.start();
    }
}
