package com.example.jacob.mybrary;

import android.app.Instrumentation;
import android.content.DialogInterface;
import android.test.ActivityInstrumentationTestCase2;

import android.widget.Button;

/**
 * Created by davidross on 2015-11-19.
 */
public class ViewIndividualTradeActivityTest extends ActivityInstrumentationTestCase2 {
    public ViewIndividualTradeActivityTest (){
        super(ViewIndividualTradeActivity.class);
    } {
    }

    public void testDeclineButton() {
        ViewIndividualTradeActivity activity = (ViewIndividualTradeActivity) getActivity();

        // Make sure the alert dialog appears
        final Button declineButton = (Button) activity.findViewById(R.id.declineButton);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                declineButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(activity.getAlertDialog().isShowing());
    }

    public void testOfferCounterTrade(){
        ViewIndividualTradeActivity activity = (ViewIndividualTradeActivity) getActivity();

        final Button declineButton = (Button) activity.findViewById(R.id.declineButton);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                declineButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the "yes" button on the alert.

        final Button yesButton = activity.getAlertDialog().getButton(DialogInterface.BUTTON_POSITIVE);

        //following from https://developer.android.com/training/activity-testing/activity-functional-testing.html
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(ProposeTradeActivity.class.getName(),
                        null, false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                yesButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Lonely Twitter from the labs
        // Validate that ReceiverActivity is started
        ProposeTradeActivity receiverActivity = (ProposeTradeActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                ProposeTradeActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // Close the opened activity
        receiverActivity.finish();

        assertFalse(activity.getAlertDialog().isShowing());
    }

    public void testNoCounterOfferButton(){
        ViewIndividualTradeActivity activity = (ViewIndividualTradeActivity) getActivity();

        final Button declineButton = (Button) activity.findViewById(R.id.declineButton);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                declineButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the "no" button on the alert.

        final Button noButton = activity.getAlertDialog().getButton(DialogInterface.BUTTON_NEGATIVE);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                noButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertFalse(activity.getAlertDialog().isShowing());
    }

}

