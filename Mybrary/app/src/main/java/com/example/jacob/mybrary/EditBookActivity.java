package com.example.jacob.mybrary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.sql.Connection;
import java.util.UUID;
/*
Copyright (C) 2015  Ben Schreiber , David Ross,Dominic Trottier,
                    Jake Charlebois, Mason Strong, Victoria Hessdorfer

        This file is part of Mybrary.

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * This Activity allows the User to edit a given item in their inventory. The item is chosen in
 * the main Inventory Activity, and is passed to this activity, by Book ID, in a bundle.
 *
 * Created by Victoria.
 */

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

        Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        Book book = new Book();
        String[] categories = book.getPossibleCategoriesWithNoChangeOption();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(adapter);
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

            Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
            if (!categorySpinner.getSelectedItem().toString().equals("No Change"))
                book.setCategory(categorySpinner.getSelectedItem().toString().trim());

            t = (TextView) findViewById(R.id.commentEditView);
            if (!t.getText().toString().equals(""))
                book.addNewComment(t.getText().toString());

            CheckBox c = (CheckBox) findViewById(R.id.shareEditView);
            if (c.isChecked()) {
                book.setSharedWithOthers(true);
            } else {
                book.setSharedWithOthers(false);
            }

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    DataManager dataManager = DataManager.getInstance();
                    connectionManager.updateConnectivity(activity);
                    inventoryController.getInventory().addBook(book);
                    try {
                        dataManager.saveLocalUser();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        setResult(Activity.RESULT_OK);
        finish();

    }

    /**
     * Starts Camera Activity.
     * @param view Takes in a View
     */
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

    public Spinner getCategoryText(){
        return (Spinner) findViewById(R.id.categorySpinner);
    }

    public CheckBox getCheckBox(){
        return (CheckBox) findViewById(R.id.shareEditView);
    }

    public TextView getCommentText(){
        return (TextView) findViewById(R.id.commentEditView);
    }

    public Button getSaveButton(){
        return (Button) findViewById(R.id.saveEditBookButton);
    }

    @Override
    public void onPause(){
        super.onPause();
    }
}
