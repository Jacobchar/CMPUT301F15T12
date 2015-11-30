package com.example.jacob.mybrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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
 * Displays a list of friends, a button to add friends, and methods to long and normal
 * click to remove friends and interact with friends
 */

public class FriendsListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    private AlertDialog infoDialog;
    private FriendListController friendListController = new FriendListController();
    LocalUser localUser = LocalUser.getInstance();
    DataManager dataManager = DataManager.getInstance();
    ConnectionManager connectionManager = ConnectionManager.getInstance();
    private AlertDialog alert;
    private TradeController tradeController = new TradeController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        fillFriendsList();

    }

    @Override
    public void onResume(){
        super.onResume();
        onLongClickListener(listView);
        onClickListener(listView);
        fillFriendsList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Listens for long clicks on listview, prompts to delete user with dialog,
     * and starts a new process to update on the backend accordingly
     * @param view
     */
    public void onLongClickListener(final ListView view){
        view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                final String name = (String) view.getItemAtPosition(pos);

                AlertDialog.Builder builder = new AlertDialog.Builder(FriendsListActivity.this);
                builder.setMessage("Unfriend " + name + "?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        friendListController.removeFriend(name, FriendsListActivity.this);
                        dialog.cancel();
                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                alert = builder.create();
                alert.show();
                return true;
            }
        });
    }

    /**
     * Listens for regular clicks on listview, then prompts user with a dialog to offer a new
     * trade, cancel, or view that user's profile.
     * @param view
     */
    public void onClickListener(final ListView view){
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String name = (String) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(FriendsListActivity.this);
                builder.setMessage("");
                builder.setCancelable(true);
                builder.setPositiveButton("Offer Trade", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new offerTrade().execute(name);
                        dialog.cancel();
                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("View Profile", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new showFriend().execute(name);
                        dialog.cancel();
                    }
                });

                alert = builder.create();
                alert.show();

            }

        });
    }

    /**
     * populates the listView by calling new thread from the friendsListController
     * which retrieves the new friends list from the server when connected to the network
     */
    public void fillFriendsList(){
        listView = (ListView) findViewById(R.id.listView);
        //ArrayList<String> friends = localUser.getFriendsList().getNames();
        friendListController.updateFriendList(this, listView, FriendsListActivity.this);
     }

    /**
     * Prompts user with a dialog, which they can can enter the name of a friend to add,
     * and then starts a process to add that friend
     * @param view
     */
    public void addNewFriend(View view){

        // http://stackoverflow.com/questions/10903754/input-text-dialog-android

        String promptString = "Enter the username of the friend you'd like to add: ";

        infoDialog = new AlertDialog.Builder(this).create();
        infoDialog.setMessage(promptString);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        infoDialog.setView(input);
        infoDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                "Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = input.getText().toString();
                        //Add friend on separate thread
                        friendListController.addNewFriend(username, FriendsListActivity.this);
                        refresh();
                    }
                });

        infoDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        refresh();
                    }
                });
        fillFriendsList();
        infoDialog.show();
    }

    /**
     * Starts new activity based on friend that was clicked on
     */
    private class showFriend extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... name) {

            connectionManager.updateConnectivity(FriendsListActivity.this);
            User myFriend = localUser.getFriendsList().getByName(name[0]).get(0);
            return myFriend;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(User result) {
            Intent intent = new Intent(FriendsListActivity.this, ViewUserActivity.class);
            intent.putExtra("user", result);
            startActivity(intent);
        }
    }

    /**
     * start a new activity to offer trade based on friend clicked on
     */
    private class offerTrade extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... name) {

            connectionManager.updateConnectivity(FriendsListActivity.this);
            User myFriend = localUser.getFriendsList().getByName(name[0]).get(0);
            return myFriend;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(User result) {
            Trade newTrade = tradeController.createNewTrade(result);
            Intent intent = new Intent(FriendsListActivity.this, ProposeTradeActivity.class);
            intent.putExtra("currentTrade", newTrade.getTradeID());
            startActivity(intent);
        }
    }

    private void refresh() {
        finish();
        startActivity(getIntent());
    }
}
