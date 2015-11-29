package com.example.jacob.mybrary;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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


    /**
     * Fills your inventory ListView on opening the inventory activity.
     */
    void fillInventory(Activity activity, ListView listView){

        adapter = new ArrayAdapter<Book>(activity, R.layout.simple_list_item, localUser.getInventory().getBooks());

        listView.setAdapter(adapter);
        refreshList();
    }

    public void searchForBookByName(String name, Activity activity){

        final ArrayList<Book> returnVal = new ArrayList<>();

        // grab all books that match / partially match a given name
        final String query = "{\"query\":{\"query_string\":{\"analyze_wildcard\":true,\"default_field\":\"name\",\"query\":\"" + name + "*\"}}}";

        ConnectionManager.getInstance().updateConnectivity(activity);
        new runQuery().execute(query);

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
                    if (z != 0) { // make sure it's not YOUR book
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
        protected void onPostExecute(ArrayList<Book> result) {
            //Intent intent = new Intent(FriendsListActivity.this, ViewUserActivity.class);
            //intent.putExtra("user", result);
            //startActivity(intent);
        }
    }


    public void searchForBookByCategory(){

    }


    public Inventory getInventory(){
        return localUser.getInventory();
    }

    public void refreshList(){
        adapter.notifyDataSetChanged();
    }






}
