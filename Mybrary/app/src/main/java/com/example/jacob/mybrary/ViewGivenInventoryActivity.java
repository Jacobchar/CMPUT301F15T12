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
import android.widget.ListView;

import java.util.UUID;

public class ViewGivenInventoryActivity extends AppCompatActivity {

    private InventoryController inventoryController = new InventoryController();
    private Activity activity;
    private ListView inventoryListView;
    private Inventory inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_given_inventory);

        Bundle bundle = this.getIntent().getExtras();

        if( bundle != null) {
            inventory = (Inventory) bundle.getSerializable("inventory");
        }

        inventoryListView = (ListView) findViewById(R.id.givenInventoryListView);
        activity = this;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                inventoryController.fillInventory(activity, inventoryListView, inventory);
            }
        });
        thread.start();

    }


}
