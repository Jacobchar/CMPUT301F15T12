package com.example.jacob.mybrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.util.UUID;

public class ViewGivenInventoryActivity extends AppCompatActivity {

    private InventoryController inventoryController = new InventoryController();
    private ListView inventoryListView;
    private TextView titleEditText;
    private Inventory inventory;
    private Activity activity;
    private String activityTitle = "";
    private ConnectionManager connectionManager = ConnectionManager.getInstance();

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

                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });

    }


}
