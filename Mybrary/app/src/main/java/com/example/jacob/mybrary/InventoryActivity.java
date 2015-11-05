package com.example.jacob.mybrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private ArrayList<Book> inventory;

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
                builder.setMessage("Do you want to delete this item?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteBook(book);
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
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

    void fillInventory(){

        listView = (ListView) findViewById(R.id.inventoryListView); // controller?

        Book book = new Book("testName", 0, "It's A Book", true);

        Inventory i = new Inventory();
        i.addBook(book);
        inventory = i.getBooks();
        // there's an issue with the inventory not storing this book

        adapter = new ArrayAdapter<Book>(this, R.layout.simple_list_item, inventory);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    void deleteBook(Book book){

        Inventory i = new Inventory();

        Boolean bool = i.deleteBookByName(book.getName());

        adapter.notifyDataSetChanged();

    }

    public void addNewItem(){

    }

    public void update(){


    }





}
