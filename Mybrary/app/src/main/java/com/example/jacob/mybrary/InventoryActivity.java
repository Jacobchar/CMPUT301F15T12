package com.example.jacob.mybrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

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

    // used LonelyTwitter here

    private ArrayAdapter<Book> adapter;
    private ListView listView;
    private Inventory inventory = new Inventory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        fillInventory();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                final Book book = (Book) listView.getItemAtPosition(pos);


                AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
                builder.setMessage("What would you like to do?");
                builder.setCancelable(true);
                builder.setNeutralButton("Delete Item", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        inventory.deleteBookByName(book.getName());
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });
                builder.setNeutralButton("Edit Item", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        // need a function here, pass it the Item UUID too
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

    /**
     * Fills your inventory ListView on opening the inventory activity.
     */
    void fillInventory(){

        listView = (ListView) findViewById(R.id.inventoryListView);

        Book book = new Book("testName", 0, "It's A Book", true);
        Book book2 = new Book("BookBook", 1, "No Book", false);

        inventory.addBook(book);
        inventory.addBook(book2);

        adapter = new ArrayAdapter<>(this, R.layout.simple_list_item, inventory.getBooks());

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void addNewItem(View view){

        Intent intent = new Intent(this, AddNewItem.class);
        startActivity(intent);

    }






}
