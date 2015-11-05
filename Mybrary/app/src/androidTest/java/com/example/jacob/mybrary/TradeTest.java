package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by davidross on 2015-11-02.
 * Test moving items from user1 to user2
 */
public class TradeTest extends ActivityInstrumentationTestCase2 {
    public TradeTest (){
        super(Trade.class);
    }
    public void testOneToOneTrade(){
        User user1 = new User("Bob","Username1","Email1", "12345", "Spirit","Likes Trades","Chicago");
        User user2 = new User("Harry","Username2","Email2", "123456","Male","Dislikes Trades", "Chicago");

        Book book1 = new Book("Files",1,"Documentary",false);
        Book book2 = new Book("Murphy",1,"Law",false);

        Inventory user1Inv = user1.getInventory();
        user1Inv.addBook(book1);

        Inventory user2Inv = user2.getInventory();
        user2Inv.addBook(book2);

        assertTrue("Book 1 not added to user 1 before trade", user1Inv.hasBook(book1));
        assertTrue("Book 2 not added to user 2 before trade", user2Inv.hasBook(book2));



        ArrayList<Book> user1TradeOffer= new ArrayList();
        user1TradeOffer.add(book1);

        ArrayList<Book> user2TradeOffer= new ArrayList();
        user2TradeOffer.add(book2);

        Trade trade = new Trade(user1,user2);

        trade.setUser1Accepted(true);
        trade.setUser2Accepted(true);

        trade.tradeBooks(user1TradeOffer,user2TradeOffer);

        assertTrue("User 1 book 2 was not added after trade",user1Inv.hasBook(book2));
        assertFalse("User 1 book 1 was not removed after trade",user1Inv.hasBook(book1));

        assertTrue("User 2 book 1 was not added after trade",user2Inv.hasBook(book1));
        assertFalse("User 2 book 2 was not removed after trade",user2Inv.hasBook(book2));
    }

    public void testManyToOneTrade(){
        User user1 = new User("Bob","Username1","Email1", "12345", "Spirit","Likes Trades","Chicago");
        User user2 = new User("Harry","Username2","Email2", "123456","Male","Dislikes Trades", "Chicago");

        Book book1 = new Book("Files",1,"Documentary",false);
        Book book2 = new Book("Murphy",1,"Law",false);
        Book book3 = new Book("Thomas",1,"Court",false);

        Inventory user1Inv = user1.getInventory();
        user1Inv.addBook(book1);

        Inventory user2Inv = user2.getInventory();
        user2Inv.addBook(book2);
        user2Inv.addBook(book3);

        assertTrue("Book 1 not added to user 1 before trade", user1Inv.hasBook(book1));
        assertTrue("Book 2 not added to user 2 before trade", user2Inv.hasBook(book2));

        ArrayList<Book> user1TradeOffer= new ArrayList();
        user1TradeOffer.add(book1);

        ArrayList<Book> user2TradeOffer= new ArrayList();
        user2TradeOffer.add(book2);
        user2TradeOffer.add(book3);

        Trade trade = new Trade(user1,user2);
        trade.setUser1Accepted(true);
        trade.setUser2Accepted(true);
        trade.tradeBooks(user1TradeOffer,user2TradeOffer);

        assertTrue("User 1 book 2 was not added after trade", user1Inv.hasBook(book2));
        assertTrue("User 1 book 3 was not added after trade", user1Inv.hasBook(book3));
        assertFalse("User 1 book 1 was not removed after trade", user1Inv.hasBook(book1));

        assertTrue("User 2 book 1 was not added after trade", user2Inv.hasBook(book1));
        assertFalse("User 2 book 2 was not removed after trade", user2Inv.hasBook(book2));
        assertFalse("User 2 book 3 was not removed after trade",user2Inv.hasBook(book3));
    }

    public void testNoneToOneTrade(){
        User user1 = new User("Bob","Username1","Email1", "12345", "Spirit","Likes Trades","Chicago");
        User user2 = new User("Harry","Username2","Email2", "123456","Male","Dislikes Trades", "Chicago");

        Book book1 = new Book("Files",1,"Documentary",false);

        Inventory user1Inv = user1.getInventory();
        user1Inv.addBook(book1);

        Inventory user2Inv = user2.getInventory();


        assertTrue("Book 1 not added to user 1 before trade", user1Inv.hasBook(book1));

        ArrayList<Book> user1TradeOffer= new ArrayList();
        user1TradeOffer.add(book1);

        ArrayList<Book> user2TradeOffer= new ArrayList();


        Trade trade = new Trade(user1,user2);

        trade.setUser1Accepted(true);
        trade.setUser2Accepted(true);

        trade.tradeBooks(user1TradeOffer,user2TradeOffer);


        assertFalse("User 1 book 1 was not removed after trade", user1Inv.hasBook(book1));

        assertTrue("User 2 book 1 was not added after trade",user2Inv.hasBook(book1));
    }

    public void testUserNotAccepted(){
        User user1 = new User("Bob","Username1","Email1", "12345", "Spirit","Likes Trades","Chicago");
        User user2 = new User("Harry","Username2","Email2", "123456","Male","Dislikes Trades", "Chicago");

        Book book1 = new Book("Files",1,"Documentary",false);
        Book book2 = new Book("Murphy",1,"Law",false);

        Inventory user1Inv = user1.getInventory();
        user1Inv.addBook(book1);

        Inventory user2Inv = user2.getInventory();
        user2Inv.addBook(book2);

        assertTrue("Book 1 not added to user 1 before trade", user1Inv.hasBook(book1));
        assertTrue("Book 2 not added to user 2 before trade", user2Inv.hasBook(book2));

        ArrayList<Book> user1TradeOffer= new ArrayList();
        user1TradeOffer.add(book1);

        ArrayList<Book> user2TradeOffer= new ArrayList();
        user2TradeOffer.add(book2);

        Trade trade = new Trade(user1,user2);

        trade.setUser2Accepted(true);

        trade.tradeBooks(user1TradeOffer, user2TradeOffer);

        assertTrue("User 1 lost book 1", user1Inv.hasBook(book1));
        assertFalse("User 1 gained book 2", user1Inv.hasBook(book2));

        assertTrue("User 2 lost book 2",user2Inv.hasBook(book2));
        assertFalse("User 2 gained book 1", user2Inv.hasBook(book1));
    }

    public void testNeitherUserAccepted(){
        User user1 = new User("Bob","Username1","Email1", "12345", "Spirit","Likes Trades","Chicago");
        User user2 = new User("Harry","Username2","Email2", "123456","Male","Dislikes Trades", "Chicago");

        Book book1 = new Book("Files",1,"Documentary",false);
        Book book2 = new Book("Murphy",1,"Law",false);

        Inventory user1Inv = user1.getInventory();
        user1Inv.addBook(book1);

        Inventory user2Inv = user2.getInventory();
        user2Inv.addBook(book2);

        assertTrue("Book 1 not added to user 1 before trade", user1Inv.hasBook(book1));
        assertTrue("Book 2 not added to user 2 before trade", user2Inv.hasBook(book2));

        ArrayList<Book> user1TradeOffer= new ArrayList();
        user1TradeOffer.add(book1);

        ArrayList<Book> user2TradeOffer= new ArrayList();
        user2TradeOffer.add(book2);

        Trade trade = new Trade(user1,user2);

        trade.tradeBooks(user1TradeOffer,user2TradeOffer);

        assertTrue("User 1 lost book 1",user1Inv.hasBook(book1));
        assertFalse("User 1 gained book 2",user1Inv.hasBook(book2));

        assertTrue("User 2 lost book 2",user2Inv.hasBook(book2));
        assertFalse("User 2 gained book 1",user2Inv.hasBook(book1));
    }

}
