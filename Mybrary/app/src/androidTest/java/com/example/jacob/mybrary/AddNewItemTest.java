package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Tests the Add New Item Activity on the app.
 * Created by Victoria on 2015-11-16.
 */
public class AddNewItemTest  extends ActivityInstrumentationTestCase2 {

    private InventoryController inventoryController = new InventoryController();

    public AddNewItemTest(){
            super(AddNewItem.class);
        }

    public void testSetNewName(){
        AddNewItem activity = (AddNewItem) getActivity();

        final TextView text = activity.getNameText();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                text.setText("testName1");
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


        final Inventory inv = inventoryController.getInventory();
        Book book = inv.getBookByName("testName1");

        assertTrue(book.getName().equals("testName1"));

    }


    public void testsetNewQuantity(){
        AddNewItem activity = (AddNewItem) getActivity();

        final TextView textName = activity.getNameText();
        final TextView text = activity.getQuantityText();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                textName.setText("testName2");
                text.setText("3");
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

        final Inventory inv = inventoryController.getInventory();
        Book book = inv.getBookByName("testName2");

        assertTrue(book.getQuantity() == 3);

    }

    public void testsetNewCategory(){
        AddNewItem activity = (AddNewItem) getActivity();

        final TextView textName = activity.getNameText();
        final Spinner text = activity.getCategoryText();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                textName.setText("testName3");
                text.setSelection(2);
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

        final Inventory inv = inventoryController.getInventory();
        Book book = inv.getBookByName("testName3");

        assertTrue(book.getCategory().equals("Biography"));

    }

    public void testNewSharedWithOthers(){
        AddNewItem activity = (AddNewItem) getActivity();

        final TextView textName = activity.getNameText();
        final CheckBox checkBox = activity.getCheckBox();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                textName.setText("testName4");
                checkBox.setChecked(true);
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

        final Inventory inv = inventoryController.getInventory();
        Book book = inv.getBookByName("testName4");

        assertTrue(book.isSharedWithOthers());
    }


    public void testAddComment(){
        AddNewItem activity = (AddNewItem) getActivity();

        final TextView textName = activity.getNameText();
        final TextView text = activity.getCommentText();

        final String string = "Great book.";

        activity.runOnUiThread(new Runnable() {
            public void run() {
                textName.setText("testName5");
                text.setText(string);
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

        final Inventory inv = inventoryController.getInventory();
        Book book = inv.getBookByName("testName5");

        assertTrue(book.getComments().contains(string));

    }


}
