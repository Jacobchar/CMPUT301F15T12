package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import org.json.JSONException;

import java.io.IOException;
import java.util.UUID;

/**
 * Tests for the trade Controller
 * Created by David on 2015-11-14.
 * Todo: Add tests for hte rest of trade controller
 */
public class TradeControllerTest extends ActivityInstrumentationTestCase2 {
    public TradeControllerTest() {
        super(TradeController.class);
    }


    public void testSetAcceptedStatus() {
        TradeController controller = new TradeController();
        // All trade controller is based off local user
        LocalUser localUser = LocalUser.getInstance();

        User user2 = new User("TestUser1", "", "", "", "", "");
        Trade testTrade = new Trade(localUser, user2);

        DataManager saver = DataManager.getInstance();

        assertFalse("Initial accepted status was not false", testTrade.getUser1Accepted());

        try {
            assertTrue("File not added to server", saver.storeTrade(testTrade));
            Thread.sleep(1000);
            controller.setAcceptedStatus(true, testTrade.getTradeID());
            Thread.sleep(2000);
            assertTrue("Accepted status did not change to true", saver.retrieveTrade(testTrade.getTradeID().toString()).getUser1Accepted());
            controller.setAcceptedStatus(false, testTrade.getTradeID());
            Thread.sleep(2000);
            assertFalse("Accepted status did not change to false", saver.retrieveTrade(testTrade.getTradeID().toString()).getUser1Accepted());
            assertTrue("File not removed from server", saver.removeTrade(testTrade.getTradeID().toString()));
        } catch (IOException e) {
            fail("Trade failed to store on the server");
        } catch (InterruptedException e) {
            fail("waiting for server to catch up interrupted");
        } catch (JSONException e){
            fail("Retreive from server error");
        }
    }
}

