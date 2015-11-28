package com.example.jacob.mybrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Victoria.
 *
 * Inventory Activity, displays to the inventory to the user. Allows for deleting a book on a long
 * click, adding a book, and searching your inventory.
 *
 * Search functionality deferred to P5.
 *
 */

public class InventoryActivity extends AppCompatActivity {

    private ArrayAdapter<Book> adapter;
    private Inventory inventory = new Inventory();
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

    public void addNewItem(View view){

        Intent intent = new Intent(activity, AddNewItem.class);
        startActivityForResult(intent, 1);

    }

    public void editItem(UUID id){

        Intent intent = new Intent(this, EditBookActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("id", id);

        intent.putExtras(bundle);
        startActivityForResult(intent, 1);

    }

    public AlertDialog getAlertDialog(){
        return this.alert;
    }

    @Override
    // cite http://stackoverflow.com/questions/17242713/how-to-pass-parcelable-object-from-child-to-parent-activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        inventoryController.refreshList();
        inventoryController.fillInventory(activity, inventoryListView);
    }


}
