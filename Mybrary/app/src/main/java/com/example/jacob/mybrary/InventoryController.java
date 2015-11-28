package com.example.jacob.mybrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.UUID;

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


    public void searchForBook(){
        /* Dom/Jake's tests:
            try {
                DataManager dataManager = DataManager.getInstance();

                Book book3 = new Book("testBook3", 3, "testCategory3", true);

                //Wait for entries to be indexed
                Thread.sleep(1000);

                ArrayList<Book> returnedBooks = dataManager.searchBooks("{\"query\":{\"query_string\":{\"default_field\":\"name\",\"query\":\"testBook2\"}}}");

                assertTrue(returnedBooks.size() == 1);
                assertTrue(returnedBooks.contains(book2));

            } catch (Exception e) {

            }
        */
    }


    public Inventory getInventory(){
        return localUser.getInventory();
    }

    public void refreshList(){
        adapter.notifyDataSetChanged();
    }






}
