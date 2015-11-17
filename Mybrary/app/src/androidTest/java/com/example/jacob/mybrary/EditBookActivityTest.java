package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Tests the Edit Item Activity on the app.
 *
 * Note: These tests don't get work. Need to pass inventory in a bundle still.
 *
 * Created by Victoria on 2015-11-16.
 */
public class EditBookActivityTest  extends ActivityInstrumentationTestCase2 {

    public EditBookActivityTest(){
        super(EditBookActivity.class);
    }

    public void testSetNewName(){
        EditBookActivity activity = (EditBookActivity) getActivity();

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

        assertTrue(book.getName().equals("testName"));

    }



    public void testsetNewQuantity(){
        EditBookActivity activity = (EditBookActivity) getActivity();

        final TextView textName = activity.getNameText();
        final TextView text = activity.getQuantityText();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                textName.setText("testName");
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

        final Inventory inv = activity.getInventory();
        Book book = inv.getBookByName("testName");

        assertTrue(book.getQuantity() == 3);

    }

    public void testsetNewCategory(){
        EditBookActivity activity = (EditBookActivity) getActivity();

        final TextView textName = activity.getNameText();
        final TextView text = activity.getCategoryText();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                textName.setText("testName");
                text.setText("Horror");
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

        assertTrue(book.getCategory().equals("Horror"));

    }

    public void testNewSharedWithOthers(){
        EditBookActivity activity = (EditBookActivity) getActivity();

        final TextView textName = activity.getNameText();
        final CheckBox checkBox = activity.getCheckBox();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                textName.setText("testName");
                checkBox.setEnabled(true);
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

        assertTrue(book.isSharedWithOthers());
    }


    public void testAddComment(){
        EditBookActivity activity = (EditBookActivity) getActivity();

        final TextView textName = activity.getNameText();
        final TextView text = activity.getCommentText();

        final String string = "Great book.";

        activity.runOnUiThread(new Runnable() {
            public void run() {
                textName.setText("testName");
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

        final Inventory inv = activity.getInventory();
        Book book = inv.getBookByName("testName");

        assertTrue(book.getComments().contains(string));

    }


}
