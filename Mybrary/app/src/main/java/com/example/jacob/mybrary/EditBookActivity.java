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

public class EditBookActivity extends AppCompatActivity {

    private Inventory inventory;
    private UUID id;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        Bundle bundle = this.getIntent().getExtras();
        if( bundle != null) {
            inventory = (Inventory) bundle.getSerializable("inv");
            id = (UUID) bundle.getSerializable("id");
        } else {
            inventory = new Inventory();
        }
    }


    /**
     * Allows user to add a new book to their inventory.
     */
    public void saveNewBookInfo(View view){

        Book book = inventory.getBookByID(id);
        inventory.deleteBookByID(id);

        TextView t = (TextView) findViewById(R.id.nameEditView);
        String tmp = t.getText().toString();
        if (!t.getText().toString().equals(""))
            book.setName(t.getText().toString());

        t = (TextView) findViewById(R.id.QuantityEditView);
        if (!t.getText().toString().equals(""))
            book.setQuantity(Integer.parseInt(t.getText().toString()));

        t = (TextView) findViewById(R.id.categoryEditView);
        if (!t.getText().toString().equals(""))
            book.setCategory(t.getText().toString());

        t = (TextView) findViewById(R.id.commentEditView);
        if (!t.getText().toString().equals(""))
            book.addNewComment(t.getText().toString());

        CheckBox c = (CheckBox) findViewById(R.id.shareEditView);
        if (c.isEnabled()){
            book.setSharedWithOthers(true);
        } else {
            book.setSharedWithOthers(false);
        }

        inventory.addBook(book);

        Intent data = new Intent();
        data.putExtra("inv", inventory);

        setResult(Activity.RESULT_OK, data);
        finish();

    }
}
