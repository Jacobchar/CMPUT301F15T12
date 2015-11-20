package com.example.jacob.mybrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class FriendsListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    private AlertDialog infoDialog;
    private FriendListController friendListController = new FriendListController();
    LocalUser localUser = LocalUser.getInstance();
    DataManager dataManager = DataManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        fillFriendsList();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                final String name = (String) listView.getItemAtPosition(pos);

                AlertDialog.Builder builder = new AlertDialog.Builder(FriendsListActivity.this);
                builder.setMessage("Unfriend " + name + "?");
                builder.setCancelable(true);
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Delete friend from friendlist object

                        friendListController.removeFriend(name, FriendsListActivity.this);

                        //adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                infoDialog = builder.create();
                infoDialog.show();
                return true;
            }
        });
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


    public void fillFriendsList(){
        listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> friends = localUser.getFriendsList().getNames();

        //= new String[] {"Dominieque", "Jackylnn", "Victor", "Betty", "Daphne"};

        listAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, friends);
        listView.setAdapter(listAdapter);
        //listAdapter.notify();
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

                        try {
                            dataManager.saveLocalUser();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //TODO: update UI appropriately. Using the adapter? Synchronization issue + crashes...
                        //listAdapter.notify();
                    }
                });

        infoDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        infoDialog.show();

    }
}
