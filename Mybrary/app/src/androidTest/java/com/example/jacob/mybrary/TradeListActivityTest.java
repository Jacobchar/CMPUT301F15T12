package com.example.jacob.mybrary;

import android.app.Instrumentation;
import android.content.DialogInterface;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;

/**
 * Test the trade view
 * Tests are based on the labs "lonelyTwitter" activity tests
 * Created by David on 2015-11-14.
 * ToDo: Fix tests to use the server
 */
public class TradeListActivityTest extends ActivityInstrumentationTestCase2 {
    public TradeListActivityTest (){
        super(TradeListActivity.class);
    }

    public void testTradeList(){
        LocalUser user = LocalUser.getInstance();
        DataManager saver = DataManager.getInstance();
        User user2 = new User("TestUser1", "", "", "", "", "");
        Trade testTrade = new Trade(user, user2);
        try{
            saver.storeTrade(testTrade);
            Thread.sleep(2000);
            TradeListActivity activity = (TradeListActivity) getActivity();
            ListView  text = (ListView) activity.findViewById(R.id.tradeListView);
            Trade trade = (Trade) text.getAdapter().getItem(0);
            assertNotNull(trade);
            saver.removeTrade(testTrade.getTradeID().toString());
        }
        catch(IOException e){
            fail("No test on server");
        }
        catch(InterruptedException e){
            fail("thread is tired");
        }
    }

    public void testLongClick(){
        LocalUser user = LocalUser.getInstance();
        DataManager saver = DataManager.getInstance();
        User user2 = new User("TestUser1", "", "", "", "", "");
        Trade testTrade = new Trade(user, user2);
        try{
            saver.storeTrade(testTrade);
            Thread.sleep(2000);
            TradeListActivity activity = (TradeListActivity) getActivity();
            assertNotNull(activity);

            ListView text = (ListView) activity.findViewById(R.id.tradeListView);

            // http://stackoverflow.com/questions/23454654/how-to-simulate-an-user-click-to-a-listview-item-in-junit-testing
            // Answered by Markn12, edited by Nathan Tuggy
            TouchUtils.longClickView(this,text.getChildAt(0));

            // http://stackoverflow.com/questions/13349530/android-testing-dialog-check-it-isshowing
            // Answered by Robin Chander
            assertTrue(activity.getAlertDialog().isShowing());
            saver.removeTrade(testTrade.getTradeID().toString());
        }
        catch(IOException e){
            fail("No test on server");
        }
        catch(InterruptedException e){
            fail("thread is tired");
        }
    }

    public void testViewButton(){
        LocalUser user = LocalUser.getInstance();
        DataManager saver = DataManager.getInstance();
        User user2 = new User("TestUser1", "", "", "", "", "");
        Trade testTrade = new Trade(user, user2);
        try{
            saver.storeTrade(testTrade);
            Thread.sleep(2000);
            TradeListActivity activity = (TradeListActivity) getActivity();
            assertNotNull(activity);

            ListView text = (ListView) activity.findViewById(R.id.tradeListView);

            // http://stackoverflow.com/questions/23454654/how-to-simulate-an-user-click-to-a-listview-item-in-junit-testing
            // Answered by Markn12, edited by Nathan Tuggy
            TouchUtils.longClickView(this, text.getChildAt(0));

            final Button editButton = activity.getAlertDialog().getButton(DialogInterface.BUTTON_POSITIVE);

            //following from https://developer.android.com/training/activity-testing/activity-functional-testing.html
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
                    getInstrumentation().addMonitor(ViewIndividualTradeActivity.class.getName(),
                            null, false);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    editButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            // Lonely Twitter from the labs
            // Validate that ReceiverActivity is started
            ViewIndividualTradeActivity receiverActivity = (ViewIndividualTradeActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    ViewIndividualTradeActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);

            // Close the opened activity
            receiverActivity.finish();

            assertFalse(activity.getAlertDialog().isShowing());
            saver.removeTrade(testTrade.getTradeID().toString());
        }
        catch(IOException e){
            fail("No test on server");
        }
        catch(InterruptedException e){
            fail("thread is tired");
        }
    }

    public void testDeleteButton(){
        LocalUser user = LocalUser.getInstance();
        DataManager saver = DataManager.getInstance();
        User user2 = new User("TestUser1", "", "", "", "", "");
        Trade testTrade = new Trade(user, user2);
        try{
            saver.storeTrade(testTrade);
            Thread.sleep(2000);

            TradeListActivity activity = (TradeListActivity) getActivity();
            assertNotNull(activity);
            ListView text = (ListView) activity.findViewById(R.id.tradeListView);

            int startNumberOfTrades = text.getAdapter().getCount();
            // http://stackoverflow.com/questions/23454654/how-to-simulate-an-user-click-to-a-listview-item-in-junit-testing
            // Answered by Markn12, edited by Nathan Tuggy
            TouchUtils.longClickView(this, text.getChildAt(0));

            final Button deleteButton = activity.getAlertDialog().getButton(DialogInterface.BUTTON_NEGATIVE);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    deleteButton.performClick();

                }
            });
            getInstrumentation().waitForIdleSync();

            saver.removeTrade(testTrade.getTradeID().toString());

            int endNumberTrades = ((ListView) activity.findViewById(R.id.tradeListView)).getAdapter().getCount();
            assertTrue(endNumberTrades ==( startNumberOfTrades-1));
        }
        catch(IOException e){
            fail("No test on server");
        }
        catch(InterruptedException e){
            fail("thread is tired");
        }
    }

    public void testCancelButton() {
        LocalUser user = LocalUser.getInstance();
        DataManager saver = DataManager.getInstance();
        User user2 = new User("TestUser1", "", "", "", "", "");
        Trade testTrade = new Trade(user, user2);
        try{
            saver.storeTrade(testTrade);
            Thread.sleep(2000);

            final TradeListActivity activity = (TradeListActivity) getActivity();
            assertNotNull(activity);

            ListView text = (ListView) activity.findViewById(R.id.tradeListView);

            // http://stackoverflow.com/questions/23454654/how-to-simulate-an-user-click-to-a-listview-item-in-junit-testing
            // Answered by Markn12, edited by Nathan Tuggy
            TouchUtils.longClickView(this, text.getChildAt(0));

            final Button cancelButton = activity.getAlertDialog().getButton(DialogInterface.BUTTON_NEUTRAL);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cancelButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            assertFalse(activity.getAlertDialog().isShowing());
            saver.removeTrade(testTrade.getTradeID().toString());
        }
        catch(IOException e){
            fail("No test on server");
        }
        catch(InterruptedException e){
            fail("thread is tired");
        }


    }



}
