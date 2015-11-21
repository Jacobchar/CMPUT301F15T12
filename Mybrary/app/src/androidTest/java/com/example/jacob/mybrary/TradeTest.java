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

    public void testAccepted(){
        User user1 = new User("Bob","Email1", "12345", "Spirit","Likes Trades","Chicago");
        User user2 = new User("Harry","Email2", "123456","Male","Dislikes Trades", "Chicago");

        Trade trade = new Trade(user1,user2);

        trade.setUser1Accepted(true);
        assertTrue(trade.getUser1Accepted());
        assertFalse(trade.getUser2Accepted());

        trade.setUser1Accepted(false);
        trade.setUser2Accepted(true);
        assertTrue(trade.getUser2Accepted());
        assertFalse(trade.getUser1Accepted());

    }

    public void testTradeIDUnique(){
        User user1 = new User("Bob","Email1", "12345", "Spirit","Likes Trades","Chicago");
        User user2 = new User("Harry","Email2", "123456","Male","Dislikes Trades", "Chicago");

        Trade trade1 = new Trade(user1, user2);
        Trade trade2 = new Trade(user2,user1);

        assertNotSame(trade1.getTradeID(),trade2.getTradeID());
    }

    public void testUserOffer(){
        User user1 = new User("Bob","Email1", "12345", "Spirit","Likes Trades","Chicago");
        User user2 = new User("Harry","Email2", "123456","Male","Dislikes Trades", "Chicago");

        Book book1 = new Book("Files",1,"Documentary",false);
        Book book2 = new Book("Murphy",1,"Law",false);

        ArrayList<Book> user1Offer = new ArrayList<>();
        user1Offer.add(book1);
        ArrayList<Book> user2Offer = new ArrayList<>();
        user2Offer.add(book2);

        Trade trade = new Trade(user1,user2);

        trade.setUser1Offer(user1Offer);
        assertEquals(user1Offer, trade.getUser1Offer());

        trade.setUser2Offer(user2Offer);
        assertEquals(user2Offer, trade.getUser2Offer());
    }

    public void testIsComplete(){
        User user1 = new User("Bob","Email1", "12345", "Spirit","Likes Trades","Chicago");
        User user2 = new User("Harry","Email2", "123456","Male","Dislikes Trades", "Chicago");

        Trade trade = new Trade(user1,user2);

        assertFalse(trade.isComplete());

        trade.setIsComplete(true);
        assertTrue(trade.isComplete());
    }

}
