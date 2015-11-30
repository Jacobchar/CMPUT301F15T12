package com.example.jacob.mybrary;

import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Tests the ViewGivenInventory Activity on the app.
 * Created by Victoria on 2015-11-16.
 */
public class ViewGivenInventoryActivityTest extends ActivityInstrumentationTestCase2 {

    InventoryController inventoryController = new InventoryController();

    public ViewGivenInventoryActivityTest(){
        super(ViewGivenInventoryActivity.class);
    }

    public void setUp(){

        Inventory inv = new Inventory();
        Book book = new Book();
        inv.addBook(book);

        Intent i = new Intent();
        Bundle bundle = new Bundle();

        bundle.putSerializable("title", "TestTitle");
        bundle.putSerializable("inventory", inv);

        i.putExtras(bundle);
        setActivityIntent(i);
    }

    public void testInventoryList(){

        final ViewGivenInventoryActivity activity = (ViewGivenInventoryActivity) getActivity();

        final ListView list = (ListView) activity.findViewById(R.id.givenInventoryListView);
        final TextView title = (TextView) activity.findViewById(R.id.givenInventoryTitleView);


        Book grabBook = (Book) list.getAdapter().getItem(0);
        assertNotNull(grabBook);

        assertEquals((int) list.getChildCount(), 1);
        assertEquals(title.getText().toString(), "TestTitle");
    }


    public void testLongClick(){

        final ViewGivenInventoryActivity activity = (ViewGivenInventoryActivity) getActivity();

        final ListView list = (ListView) activity.findViewById(R.id.givenInventoryListView);

        TouchUtils.longClickView(this, list.getChildAt(0));

        assertTrue(activity.getAlertDialog().isShowing());

    }


}
