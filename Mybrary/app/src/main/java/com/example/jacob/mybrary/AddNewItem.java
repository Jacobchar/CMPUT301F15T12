package com.example.jacob.mybrary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.UUID;

public class AddNewItem extends AppCompatActivity {

    private Inventory inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        Bundle bundle = this.getIntent().getExtras();
        if( bundle != null) {
            inventory = (Inventory) bundle.getSerializable("inv");
        } else {
            inventory = new Inventory();
        }
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

        t = (TextView) findViewById(R.id.categoryEditView);
        book.setCategory(t.getText().toString());

        CheckBox c = (CheckBox) findViewById(R.id.shareEditView);
        if (c.isEnabled()){
            book.setSharedWithOthers(true);
        } else {
            book.setSharedWithOthers(false);
        }

        t = (TextView) findViewById(R.id.commentEditView);
        book.addNewComment(t.getText().toString());

        inventory.addBook(book);

        Intent data = new Intent();
        data.putExtra("inv", inventory);

        setResult(Activity.RESULT_OK, data);
        finish();

    }





}