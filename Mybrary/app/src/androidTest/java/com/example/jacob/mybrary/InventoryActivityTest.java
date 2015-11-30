package com.example.jacob.mybrary;

import android.app.Instrumentation;
import android.content.DialogInterface;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Tests the Inventory Activity on the app.
 * Created by Victoria on 2015-11-16.
 */
public class InventoryActivityTest extends ActivityInstrumentationTestCase2 {

    InventoryController inventoryController = new InventoryController();

    public InventoryActivityTest(){
        super(InventoryActivity.class);
    }

    public void testInventoryList(){
        Book book = new Book("name", 0, "category", true);
        final Inventory inventory = new Inventory();
        inventory.addBook(book);

        final InventoryActivity activity = (InventoryActivity) getActivity();

        final ListView list = (ListView) activity.findViewById(R.id.inventoryListView);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryController.fillInventory(activity, list, inventory);
            }
        });
        getInstrumentation().waitForIdleSync();


        Book grabBook = (Book) list.getAdapter().getItem(0);
        assertNotNull(grabBook);

        assertTrue(grabBook.getName().equals("name"));
    }


    public void testLongClick(){

        Book book = new Book("name", 0, "category", true);
        final Inventory inventory = new Inventory();
        inventory.addBook(book);

        final InventoryActivity activity = (InventoryActivity) getActivity();

        final ListView list = (ListView) activity.findViewById(R.id.inventoryListView);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryController.fillInventory(activity, list, inventory);
            }
        });
        getInstrumentation().waitForIdleSync();

        TouchUtils.longClickView(this, list.getChildAt(0));

        assertTrue(activity.getAlertDialog().isShowing());

    }

    public void testAlertDialogEditChoice(){
        // cite http://stackoverflow.com/questions/9405561/test-if-a-button-starts-a-new-activity-in-android-junit-pref-without-robotium

        Book book = new Book("name", 0, "category", true);
        final Inventory inventory = new Inventory();
        inventory.addBook(book);

        final InventoryActivity activity = (InventoryActivity) getActivity();
        Instrumentation.ActivityMonitor a = getInstrumentation().addMonitor(EditBookActivity.class.getName(), null, false);

        final ListView list = (ListView) activity.findViewById(R.id.inventoryListView);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryController.fillInventory(activity, list, inventory);
            }
        });
        getInstrumentation().waitForIdleSync();

        TouchUtils.longClickView(this, list.getChildAt(0));

        final Button editButton = activity.getAlertDialog().getButton(DialogInterface.BUTTON_NEGATIVE);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });

        EditBookActivity editBookActivity = (EditBookActivity) getInstrumentation().waitForMonitorWithTimeout(a, 5000);
        assertNotNull(editBookActivity);
        editBookActivity.finish();

    }

    public void testAlertDialogCancelChoice(){
        // cite http://stackoverflow.com/questions/9405561/test-if-a-button-starts-a-new-activity-in-android-junit-pref-without-robotium

        Book book = new Book("name", 0, "category", true);
        final Inventory inventory = new Inventory();
        inventory.addBook(book);

        final InventoryActivity activity = (InventoryActivity) getActivity();
        Instrumentation.ActivityMonitor a = getInstrumentation().addMonitor(EditBookActivity.class.getName(), null, false);

        final ListView list = (ListView) activity.findViewById(R.id.inventoryListView);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryController.fillInventory(activity, list, inventory);
            }
        });
        getInstrumentation().waitForIdleSync();

        TouchUtils.longClickView(this, list.getChildAt(0));

        final Button editButton = activity.getAlertDialog().getButton(DialogInterface.BUTTON_NEUTRAL);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });

        EditBookActivity editBookActivity = (EditBookActivity) getInstrumentation().waitForMonitorWithTimeout(a, 5000);
        assertNull(editBookActivity);

    }

    public void testAlertDialogDeleteChoice(){
        // cite http://stackoverflow.com/questions/9405561/test-if-a-button-starts-a-new-activity-in-android-junit-pref-without-robotium

        Book book = new Book("name", 0, "category", true);
        LocalUser localUser = LocalUser.getInstance();
        localUser.getInventory().addBook(book);

        int invSize = localUser.getInventory().sizeInventory();

        final InventoryActivity activity = (InventoryActivity) getActivity();

        final ListView list = (ListView) activity.findViewById(R.id.inventoryListView);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryController.fillInventory(activity, list);
            }
        });
        getInstrumentation().waitForIdleSync();

        TouchUtils.longClickView(this, list.getChildAt(0));

        final Button editButton = activity.getAlertDialog().getButton(DialogInterface.BUTTON_POSITIVE);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        int endNum = list.getAdapter().getCount();
        assertEquals(endNum, invSize - 1);


    }


    public void testAddNewItemButton(){

        final InventoryActivity activity = (InventoryActivity) getActivity();
        Instrumentation.ActivityMonitor a = getInstrumentation().addMonitor(AddNewItem.class.getName(), null, false);

        final Button addNewItemButton = (Button) activity.findViewById(R.id.addButton);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                addNewItemButton.performClick();
            }
        });

        AddNewItem addNewItemActivity = (AddNewItem) getInstrumentation().waitForMonitorWithTimeout(a, 5000);
        assertNotNull(addNewItemActivity);
        addNewItemActivity.finish();
    }




}
