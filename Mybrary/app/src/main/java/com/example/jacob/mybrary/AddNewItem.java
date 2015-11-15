package com.example.jacob.mybrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.UUID;

public class AddNewItem extends AppCompatActivity {

    private Inventory inventory = new Inventory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
    }

    /**
     * Allows user to add a new book to their inventory.
     */
    public void saveNewBookInfo(View view){

        Book book = new Book();

        TextView t = (TextView) findViewById(R.id.nameEditView);
        book.setName(t.getText().toString());

        t = (TextView) findViewById(R.id.QuantityEditView);
        book.setQuantity(Integer.parseInt(t.getText().toString()));

        t = (TextView) findViewById(R.id.commentEditView);
        book.setCategory(t.getText().toString());

        CheckBox c = (CheckBox) findViewById(R.id.shareEditView);
        if (c.isEnabled()){
            book.setSharedWithOthers(true);
        } else {
            book.setSharedWithOthers(false);
        }

        inventory.addBook(book);
        finish();

    }





}
