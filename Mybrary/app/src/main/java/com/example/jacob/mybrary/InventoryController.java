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
 * Controller class that deals with changes to the Inventory, and helps with the InventoryActivity's
 * functionality. This class handles filling the inventory in an activity listView, cloning items
 * from your Friends' inventories, and searching through inventories using elastisearch.
 *
 * Created by Victoria.
 */

public class InventoryController {

    final private DataManager dataManager = DataManager.getInstance();
    private LocalUser localUser = LocalUser.getInstance();
    private ArrayAdapter<Book> adapter;
    private Activity localActivity = null;
    private Context localContext = null;


    /**
     * Fills your inventory ListView on opening the inventory activity, with localUser's inventory.
     */
    void fillInventory(Activity activity, ListView listView){

        localActivity = activity;

        adapter = new ArrayAdapter<Book>(activity, R.layout.simple_list_item, localUser.getInventory().getBooks());

        listView.setAdapter(adapter);
        refreshList();
    }

    /**
     * Fills your inventory ListView on opening the inventory activity, with a given inventory.
     */
    void fillInventory(Activity activity, ListView listView, Inventory inventory){

        localActivity = activity;

        adapter = new ArrayAdapter<Book>(activity, R.layout.simple_list_item, inventory.getBooks());

        listView.setAdapter(adapter);
        refreshList();
    }

    /**
     * This function takes a user's given book name parameter, turns it into a partial matching
     * elastisearch query and passes it to an asynchronous task, runQuery()
     * @param name The given book name the user wants to match. Can be full or partial match.
     * @param activity The current activity
     * @param context The current context
     */
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

    /**
     * This function takes a user's given category parameter, turns it into a partial matching
     * elastisearch query and passes it to an asynchronous task, runQuery()
     * @param category The given category the user wants to match. Can be full or partial match.
     * @param activity The current activity
     * @param context The current context
     */
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

    /**
     * Executes an elastisearch query and sorts through a resultset on an asynchronous task.
     * This function also weeds out any books that
     * belong to the user, or that don't belong to any of the user's friends. On completion, this
     * function opens a new ViewGivenInventoryActivity and displays the results of the query.
     */
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

    /**
     * Clones an item clicked on from a friend's inventory, and creates a book in your inventory
     * with the same attributes.
     * @param book Book the user wants to clone
     */
    public void cloneItem(Book book){

        Book clonedBook = new Book(book.getName(), book.getQuantity(), book.getCategory(), book.isSharedWithOthers());
        // add photo later
        localUser.getInventory().addBook(clonedBook);

    }


    public Inventory getInventory(){
        return localUser.getInventory();
    }

    /**
     * Notifies the adapter that it needs to refresh the data set.
     */
    public void refreshList(){
        adapter.notifyDataSetChanged();
    }


}
