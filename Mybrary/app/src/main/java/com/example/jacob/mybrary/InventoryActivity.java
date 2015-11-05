package com.example.jacob.mybrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collection;

public class InventoryActivity extends AppCompatActivity {

    // cite LonelyTwitter here

    ArrayAdapter<Book> adapter;
    ListView listView;
    ArrayList<Book> inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        fillInventory();
    }

    void deleteBook(){

        String name = "";
        Inventory i = new Inventory();

        Boolean bool = i.deleteBookByName(name);

        adapter.notifyDataSetChanged();

    }

    void fillInventory(){

        listView = (ListView) findViewById(R.id.inventoryListView); // controller?

        Book book = new Book("testName", 0, "It's A Book", true);

        Inventory i = new Inventory();
        i.addBook(book);
        inventory = i.getBooks();

        adapter = new ArrayAdapter<Book>(this, R.layout.simple_list_item, inventory);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }





}
