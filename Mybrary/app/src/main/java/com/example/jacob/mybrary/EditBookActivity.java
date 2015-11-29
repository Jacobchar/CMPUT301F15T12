package com.example.jacob.mybrary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.IOException;
import java.sql.Connection;
import java.util.UUID;

public class EditBookActivity extends AppCompatActivity {

    private InventoryController inventoryController = new InventoryController();
    private UUID id;
    private ConnectionManager connectionManager = ConnectionManager.getInstance();
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        Bundle bundle = this.getIntent().getExtras();

        if( bundle != null) {
            id = (UUID) bundle.getSerializable("id");
        }
    }


    /**
     * Allows user to add a new book to their inventory.
     */
    public void saveNewBookInfo(View view){

        activity = this;

        final Book book = inventoryController.getInventory().getBookByID(id);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                inventoryController.getInventory().deleteBookByID(id);
            }
        });
        thread.start();

        if (id != null) {
            TextView t = (TextView) findViewById(R.id.nameEditView);
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
            if (c.isEnabled()) {
                book.setSharedWithOthers(true);
            } else {
                book.setSharedWithOthers(false);
            }

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    connectionManager.updateConnectivity(activity);
                    inventoryController.getInventory().addBook(book);
                }
            });
            thread.start();
        }

        setResult(Activity.RESULT_OK);
        finish();

    }

    public void takePhoto(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
        //Upload the photo
    }

    public TextView getNameText(){
        return (TextView) findViewById(R.id.nameEditView);
    }

    public TextView getQuantityText(){
        return (TextView) findViewById(R.id.QuantityEditView);
    }

    public TextView getCategoryText(){
        return (TextView) findViewById(R.id.categoryEditView);
    }

    public CheckBox getCheckBox(){
        return (CheckBox) findViewById(R.id.shareEditView);
    }

    public TextView getCommentText(){
        return (TextView) findViewById(R.id.commentEditView);
    }

    public Button getSaveButton(){ return (Button) findViewById(R.id.saveEditBookButton); }

    @Override
    public void onPause(){
        super.onPause();
        connectionManager.updateConnectivity(activity);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                DataManager dataManager = DataManager.getInstance();
                try {
                    dataManager.saveLocalUser();
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }

}
