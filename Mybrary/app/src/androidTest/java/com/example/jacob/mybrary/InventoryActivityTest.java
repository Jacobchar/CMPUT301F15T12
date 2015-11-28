package com.example.jacob.mybrary;

import android.app.Instrumentation;
import android.content.DialogInterface;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.ListView;

/**
 * Tests the Inventory Activity on the app.
 * Created by Victoria on 2015-11-16.
 */
public class InventoryActivityTest extends ActivityInstrumentationTestCase2 {

    public InventoryActivityTest(){
        super(InventoryActivity.class);
    }

    public void testInventoryList(){
        Book book = new Book("name", 0, "category", true);
        Inventory inventory = new Inventory();
        inventory.addBook(book);

        InventoryActivity activity = (InventoryActivity) getActivity();
        activity.fillInventory(); // doesn't work, problem with thread

        ListView list = (ListView) activity.findViewById(R.id.inventoryListView);
        Book grabBook = (Book) list.getAdapter().getItem(0);
        assertNotNull(grabBook);

        assertTrue(grabBook.getName().equals("name"));
    }


    public void testLongClick(){

        InventoryActivity activity = (InventoryActivity) getActivity();
        assertNotNull(activity);

        //ListView list = (ListView) activity.findViewById(R.id.tradeListView);

        //TouchUtils.longClickView(this, list.getChildAt(0));

        assertTrue(activity.getAlertDialog().isShowing());

    }

    public void testEditButton(){
        InventoryActivity activity = (InventoryActivity) getActivity();


        //ListView list = (ListView) activity.findViewById(R.id.tradeListView);

        //TouchUtils.longClickView(this, list.getChildAt(0));

        final Button editButton = activity.getAlertDialog().getButton(DialogInterface.BUTTON_POSITIVE);

        //not complete
    }


}
