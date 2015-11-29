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

    public void fillFriendsList(){
        listView = (ListView) findViewById(R.id.listView);
        //ArrayList<String> friends = localUser.getFriendsList().getNames();
        friendListController.updateFriendList(this, listView, FriendsListActivity.this);
     }

    public void deleteFriend(){

    }

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
                    }
                });

        infoDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        fillFriendsList();
        infoDialog.show();
    }

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

}
