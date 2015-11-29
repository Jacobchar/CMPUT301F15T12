package com.example.jacob.mybrary;

import android.app.Activity;
import android.content.Intent;
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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<Book> queryVal = dataManager.searchBooks(query);
                    Iterator<Book> iterator = queryVal.iterator();
                    while (iterator.hasNext()){
                        returnVal.add(iterator.next());
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        thread.start();



        if (returnVal.size() == 0){

            // no books returned

        } else {
            // grab all the UUIDs of my friends
            ArrayList<User> friends = LocalUser.getInstance().getFriendsList().getUsers();

            Iterator<Book> bookIterator = returnVal.iterator();

            boolean bookExists = false;
            // check if book has an owner in my friends list
            while (bookIterator.hasNext()) {
                Iterator<User> iterator = friends.iterator();
                while (iterator.hasNext()) {
                    int i = bookIterator.next().getOwnerID().compareTo(iterator.next().getUUID());
                    if (i == 0){
                        bookExists = true;
                        break;
                    }
                }
                if (!bookExists){
                    bookIterator.remove(); // will this remove it from just iterator or list too?
                }
            }

            // display final matches

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
