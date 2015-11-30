package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by victoria on 2015-11-29.
 */
public class InventoryControllerTest extends ActivityInstrumentationTestCase2 {
    public InventoryControllerTest(){ super(InventoryController.class); }

    InventoryController inventoryController = new InventoryController();

    public void testFillInventory(){
        // this is tested in InventoryActivityTest
    }

    public void testCloneItem(){
        LocalUser localUser = LocalUser.getInstance();
        int invSize = localUser.getInventory().sizeInventory();

        Book book = new Book();
        inventoryController.cloneItem(book);

    assertEquals((int) localUser.getInventory().sizeInventory(), invSize + 1);

    }

    // these two are incredibly hard to test. need to make sure user has a set of local friends
    public void testSearchForBookByName(){}

    public void testSearchForBookByCategory(){}


}
