package com.example.jacob.mybrary;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Tests the Add New Item Activity on the app.
 * Created by Victoria on 2015-11-16.
 */
public class AddNewItemTest  extends ActivityInstrumentationTestCase2 {

    public AddNewItemTest(){
            super(AddNewItem.class);
        }

    public void testSetNewName(){
        AddNewItem activity = (AddNewItem) getActivity();

        final TextView text = activity.getNameText();


        activity.runOnUiThread(new Runnable() {
            public void run() {
                text.setText("testName");
            }
        });
        getInstrumentation().waitForIdleSync();

        final Button saveButton = activity.getSaveButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                saveButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        final Inventory inv = activity.getInventory();
        Book book = inv.getBookByName("testName");

        assertNotNull(book);

    }



    public void testsetNewQuantity(){}

    public void testsetNewCategory(){}

    public void testNewSharedWithOthers(){}

    public void testAddComment(){}


    /*
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
    */





}
