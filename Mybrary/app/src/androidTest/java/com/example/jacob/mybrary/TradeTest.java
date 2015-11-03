package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by davidross on 2015-11-02.
 * Test moving items from user1 to user2
 * Assumes the trade has been previously accepted.
 */
public class TradeTest extends ActivityInstrumentationTestCase2 {
    public TradeTest (){
        super(Trade.class);
    }

    public void testTradeItems(){
        User user1 = new User("Bob", "12345", "Male","Likes Trades");
        User user2 = new User("Harry","123456","N/A","Dislikes Trades");

        Book book1 = new Book("Files",1,"Documentary",false);
        Book book2 = new Book("Murphy",1,"Law",false);

        Inventory user1Inv = user1.getInventory();
        user1Inv.addBook(book1);

        Inventory user2Inv = user2.getInventory();
        user2Inv.addBook(book2);

        assertTrue("Book 1 not added to user 1 before trade",user1Inv.hasBook(book1));
        assertTrue("Book 2 not added to user 2 before trade",user2Inv.hasBook(book2));

        Trade trade = new Trade();
        trade.tradeBooks(user1,book1,user2,book2);

        assertTrue("User 1 book 2 was not added after trade",user1Inv.hasBook(book2));
        assertFalse("User 1 book 1 was not removed after trade",user1Inv.hasBook(book1));

        assertTrue("User 2 book 1 was not added after trade",user2Inv.hasBook(book1));
        assertFalse("User 2 book 2 was not removed after trade",user2Inv.hasBook(book2));
    }
}
