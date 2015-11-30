package com.example.jacob.mybrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.UUID;
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
 * Inventory Activity, displays to the inventory to the user. Allows for deleting a book on a long
 * click, adding a book, and searching your inventory.
 *
 * Created by Victoria.
 *
 */

public class InventoryActivity extends AppCompatActivity {

    private ArrayAdapter<Book> adapter;
    private AlertDialog alert;
    private InventoryController inventoryController = new InventoryController();
    private Activity activity;
    private ListView inventoryListView;
    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        inventoryListView = (ListView) findViewById(R.id.inventoryListView);
        activity = this;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                inventoryController.fillInventory(activity, inventoryListView);
            }
        });
        thread.start();


        inventoryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                final Book book = (Book) inventoryListView.getItemAtPosition(pos);

                AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
                builder.setMessage("What would you like to do?");
                builder.setCancelable(true);

                builder.setPositiveButton("Delete Item", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                connectionManager.updateConnectivity(activity);
                                inventoryController.getInventory().deleteBookByName(book.getName());

                            }
                        });
                        thread.start();
                        inventoryController.refreshList();
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Edit Item", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editItem(book.getItemID());
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
     * Opens Add New Item Activity.
     * @param view Takes in a view
     */
    public void addNewItem(View view) {

        Intent intent = new Intent(activity, AddNewItem.class);
        startActivityForResult(intent, 1);

    }

    /**
     * This function asks the user for a textual input, and to specify if they want to search
     * for a book by Name or Category. It then delegates to inventoryController to search through
     * all of the current users friends for the given book criteria.
     * @param view Takes in a view.
     */
    public void searchInventory(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
        builder.setMessage("Input a Book Name or Category:");
        builder.setCancelable(true);

        final EditText searchParam = new EditText(InventoryActivity.this);
        searchParam.setInputType(InputType.TYPE_CLASS_TEXT);
        searchParam.setHint("Type 'a' for all results like a*");
        builder.setView(searchParam);

        builder.setNegativeButton("Search By Name", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                inventoryController.searchForBookByName(searchParam.getText().toString(), activity, getApplicationContext());
                dialog.cancel();
            }
        });

        builder.setNeutralButton("Search By Category", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                inventoryController.searchForBookByCategory(searchParam.getText().toString(), activity, getApplicationContext());
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alert = builder.create();
        alert.show();

    }

    /**
     * Opens the Edit Book Activity
     * @param id Takes in the UUID of the book the User wants to edit
     */
    public void editItem(UUID id) {

        Intent intent = new Intent(this, EditBookActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("id", id);

        intent.putExtras(bundle);
        startActivityForResult(intent, 1);

    }

    @Override
    /**
     * Gets a notification of an activity started from this activity's completion. On completion,
     * this function refreshes the user's inventory.
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // cite http://stackoverflow.com/questions/17242713/how-to-pass-parcelable-object-from-child-to-parent-activity
        super.onActivityResult(requestCode, resultCode, data);

        inventoryController.refreshList();
        inventoryController.fillInventory(activity, inventoryListView);
    }


    public AlertDialog getAlertDialog(){
        return this.alert;
    }

}
