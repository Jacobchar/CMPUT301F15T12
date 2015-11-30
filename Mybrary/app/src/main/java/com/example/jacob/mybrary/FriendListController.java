package com.example.jacob.mybrary;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
/*
Copyright (C) 2015  Ben Schreiber , David Ross,Dominic Trottier,
                    Jake Charlebois, Mason Strong, Victoria Hessdorfer

        This file is part of Mybrary.

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * Interacts between the model and the activity to start processes on new threads
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
