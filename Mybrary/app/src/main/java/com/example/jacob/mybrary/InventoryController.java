package com.example.jacob.mybrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Victoria.
 *
 * Controller class that deals with changes to the Inventory, and helps with the InventoryActivity's
 * functionality.
 *
 */

public class InventoryController {

    final private DataManager dataManager = DataManager.getInstance();
    private LocalUser localUser = LocalUser.getInstance();
    private ArrayAdapter<Book> adapter;
    private Activity localActivity = null;
    private Context localContext = null;


    /**
     * Fills your inventory ListView on opening the inventory activity.
     */
    void fillInventory(Activity activity, ListView listView){

        localActivity = activity;

        adapter = new ArrayAdapter<Book>(activity, R.layout.simple_list_item, localUser.getInventory().getBooks());

        listView.setAdapter(adapter);
        refreshList();
    }

    void fillInventory(Activity activity, ListView listView, Inventory inventory){

        localActivity = activity;

        adapter = new ArrayAdapter<Book>(activity, R.layout.simple_list_item, inventory.getBooks());

        listView.setAdapter(adapter);
        refreshList();
    }

    public void searchForBookByName(String name, Activity activity, Context context){

        localActivity = activity;
        localContext = context;

        if (name == ""){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage("Don't enter empty parameters.");
            builder.setCancelable(true);

            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        } else {

            // grab all books that match / partially match a given name
            final String query = "{\"query\":{\"query_string\":{\"analyze_wildcard\":true,\"default_field\":\"name\",\"query\":\"" + name + "*\"}}}";

            ConnectionManager.getInstance().updateConnectivity(activity);
            new runQuery().execute(query);
        }

    }

    public void searchForBookByCategory(String category, Activity activity, Context context){

        localActivity = activity;
        localContext = context;

        if (category == "") {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage("Don't enter empty parameters.");
            builder.setCancelable(true);

            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        } else {
            // grab all books that match / partially match a given name
            final String query = "{\"query\":{\"query_string\":{\"analyze_wildcard\":true,\"default_field\":\"category\",\"query\":\"" + category + "*\"}}}";

            ConnectionManager.getInstance().updateConnectivity(activity);
            new runQuery().execute(query);
        }

    }

    private class runQuery extends AsyncTask<String, Void, ArrayList<Book>> {

        @Override
        protected ArrayList<Book> doInBackground(String... query) {

            ArrayList<Book> returnVal = null;
            try {
                returnVal = dataManager.searchBooks(query[0]);
            } catch (Exception e){
                e.printStackTrace();
            }

            if (returnVal.size() == 0){

                return returnVal;

            } else {
                // grab all the UUIDs of my friends
                ArrayList<User> friends = LocalUser.getInstance().getFriendsList().getUsers();

                Iterator<Book> bookIterator = returnVal.iterator();

                boolean bookExists = false;
                UUID bookID = null;
                UUID friendID = null;
                UUID localUserID = LocalUser.getInstance().getUUID();

                Book book = null;

                // check if book has an owner in my friends list
                while (bookIterator.hasNext()) {

                    Iterator<User> iterator = friends.iterator();
                    book = bookIterator.next();
                    bookID = book.getOwnerID();

                    int z = bookID.compareTo(localUserID);
                    if ((z != 0) & book.isSharedWithOthers()) { // make sure it's not YOUR book, and it's visible
                        while (iterator.hasNext()) {
                            friendID = iterator.next().getUUID();
                            int i = bookID.compareTo(friendID);
                            if (i == 0) {
                                bookExists = true;
                                break;
                            }
                        }
                    }
                    if (!bookExists){
                        //returnVal.remove(book);
                        bookIterator.remove();
                    }
                    bookExists = false;
                }
            }
            return returnVal;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<Book> returnVal) {

            Inventory inventory = new Inventory();
            inventory.convertFriendsArrayListToInventory(returnVal);

            Intent intent = new Intent(localActivity, ViewGivenInventoryActivity.class);
            intent.putExtra("title", "Search Results");
            intent.putExtra("inventory", inventory);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localContext.startActivity(intent);

        }
    }


    public Inventory getInventory(){
        return localUser.getInventory();
    }

    public void refreshList(){
        adapter.notifyDataSetChanged();
    }






}
