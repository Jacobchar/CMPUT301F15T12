package com.example.jacob.mybrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
 * This activity allows a user to view either the results of a inventory search, or their friends'
 * inventory. It takes in a title, which is the title displayed at the top of the activity, such as
 * "John's Inventory" or "Search Results", and a given inventory to display. On long click, a user
 * can clone a shown item into their inventory.
 *
 * Created by Victoria.
 */
public class ViewGivenInventoryActivity extends AppCompatActivity {

    private InventoryController inventoryController = new InventoryController();
    private ListView inventoryListView;
    private TextView titleEditText;
    private Inventory inventory;
    private Activity activity;
    private String activityTitle = "";
    private ConnectionManager connectionManager = ConnectionManager.getInstance();
    private AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_given_inventory);

        Bundle bundle = this.getIntent().getExtras();

        if( bundle != null) {
            inventory = (Inventory) bundle.getSerializable("inventory");
            activityTitle = (String) bundle.getSerializable("title");
        }

        inventoryListView = (ListView) findViewById(R.id.givenInventoryListView);
        titleEditText = (TextView) findViewById(R.id.givenInventoryTitleView);
        titleEditText.setText(activityTitle);

        activity = this;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                inventoryController.fillInventory(activity, inventoryListView, inventory);
            }
        });
        thread.start();

        inventoryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                final Book book = (Book) inventoryListView.getItemAtPosition(pos);

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewGivenInventoryActivity.this);
                builder.setMessage("Would you like to clone this item into your inventory?");
                builder.setCancelable(true);

                builder.setPositiveButton("Clone Item", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                connectionManager.updateConnectivity(activity);
                                inventoryController.cloneItem(book);

                            }
                        });
                        thread.start();
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

    public AlertDialog getAlertDialog(){
        return alert;
    }


}
