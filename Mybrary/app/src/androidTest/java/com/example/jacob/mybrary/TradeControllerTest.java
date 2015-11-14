package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;

import java.util.UUID;

/**
 * Tests for the trade Controller
 * Created by David on 2015-11-14.
 */
public class TradeControllerTest  extends ActivityInstrumentationTestCase2 {
    public TradeControllerTest() {
        super(TradeController.class);
    }{
    }

    public void testCreateNewTrade(){
        User user1 = new User("Harry","","","","","");
        User user2 = new User("Bob","","","","","");

        TradeController cont = new TradeController();
        assertTrue(cont.getTradeList().size() == 0);

        cont.createNewTrade(user1, user2);
        assertTrue(cont.getTradeList().size() == 1);
    }

    public void testEditExistingTrade(){
        User user1 = new User("Harry","","","","","");
        User user2 = new User("Bob","","","","","");

        TradeController cont = new TradeController();
        cont.createNewTrade(user1, user2);

        UUID trade1ID = cont.getTradeList().get(0).getTradeID();

        assertTrue(cont.getTradeList().get(0) == cont.editExistingTrade(trade1ID));

        cont.createNewTrade(user1,user2);
        UUID trade2ID = cont.getTradeList().get(1).getTradeID();

        assertTrue(cont.getTradeList().get(1) == cont.editExistingTrade(trade2ID));
        assertFalse(cont.getTradeList().get(0) == cont.editExistingTrade(trade2ID));

    }


    public void testSetAcceptedStatus(){
        User user1 = new User("Harry","","","","","");
        User user2 = new User("Bob","","","","","");

        TradeController cont = new TradeController();
        cont.createNewTrade(user1, user2);

        Trade currentTrade = cont.getTradeList().get(0);
        UUID tradeID = currentTrade.getTradeID();

        // Check that it is initially false
        assertFalse(currentTrade.getUser1Accepted());

        // CHange user 1 to true, leave user 2 false
        cont.setAcceptedStatus(user1, true, tradeID);
        assertTrue(currentTrade.getUser1Accepted());
        assertFalse(currentTrade.getUser2Accepted());

        // Change user 1 back to false, set user 2 true
        cont.setAcceptedStatus(user1, false, tradeID);
        cont.setAcceptedStatus(user2, true,tradeID);
        assertTrue(currentTrade.getUser2Accepted());
        assertFalse(currentTrade.getUser1Accepted());

    }

}

