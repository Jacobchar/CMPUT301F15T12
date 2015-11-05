package com.example.jacob.mybrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

public class FriendsListActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        /*This should be replaced with data manager code to grab the users friend list*/
        /*
        FriendsList friendsList = new FriendsList();
        friendsList.addFriend(new User("Dominieque", "123", "M", "234"));
        friendsList.addFriend(new User("Jackylnn", "123", "M", "234"));
        friendsList.addFriend(new User("Victor", "123", "M", "234"));
        friendsList.addFriend(new User("Betty", "123", "M", "234"));
        friendsList.addFriend(new User("Daphne", "123", "M", "234"));
        */
        ////remove the above when proper data manager is implemented
        String[] friends = new String[] {"Dominieque", "Jackylnn", "Victor", "Betty", "Daphne"};

        listView = (ListView) findViewById(R.id.listView);

        //adapter needs to reference a new xml that is just a textView
        ListAdapter listAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, friends);
        listView.setAdapter(listAdapter);
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


    public void fillFriendsList(){



        //https://developer.xamarin.com/guides/android/user_interface/working_with_listviews_and_adapters/part_2_-_populating_a_listview_with_data/


    }

    public void addNewFriend(){

        // http://stackoverflow.com/questions/10903754/input-text-dialog-android

        /*

        String promptString = "Enter the username of the friend you'd like to add: ";

        final AlertDialog infoDialog = new AlertDialog.Builder(this).create();
        infoDialog.setMessage(promptString);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        infoDialog.setView(input);

        infoDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username = input.getText().toString();

                // validate that user exists
                // add friend if user does exist

                //final AlertDialog infoDialog2 = new AlertDialog.Builder(this).create();
                infoDialog.setMessage("Added!");

            }
        });

        infoDialog.setButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        infoDialog.show();

        */

    }
}
