package com.example.jacob.mybrary;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;

/**
 * Tests for the Main Activity
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2{

    public MainActivityTest() {
        super(MainActivity.class);
    }

    /**
     * Tests if the FriendsListActivity Opens on a button press
     */
    public void testOpenFriendsListActivity(){
        MainActivity activity = (MainActivity) getActivity();
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(FriendsListActivity.class.getName(), null, false);
        Button openFriendsListButton = (Button) activity.findViewById(R.id.FriendsListViewButton);
        TouchUtils.clickView(this, openFriendsListButton);
        Activity nextActivity = activityMonitor.waitForActivityWithTimeout(5);
        assertEquals(nextActivity.getClass().getName(), FriendsListActivity.class.getName());
        nextActivity.finish();
    }
    
    /**
     * Tests if the SettingsActivity Opens on a button press
     */
    public void testOpenSettingsActivity(){
        MainActivity activity = (MainActivity) getActivity();
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(SettingsActivity.class.getName(), null, false);
        Button openSettingsButton = (Button) activity.findViewById(R.id.SettingsViewButton);
        TouchUtils.clickView(this, openSettingsButton);
        Activity nextActivity = activityMonitor.waitForActivityWithTimeout(5);
        assertEquals(nextActivity.getClass().getName(), SettingsActivity.class.getName());
        nextActivity.finish();
    }

    /**
     * Tests if the InventoryActivity Opens on a button press
     */
    public void testOpenInventoryActivity(){
        MainActivity activity = (MainActivity) getActivity();
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(InventoryActivity.class.getName(), null, false);
        Button openInventoryButton = (Button) activity.findViewById(R.id.InventoryViewButton);
        TouchUtils.clickView(this, openInventoryButton);
        Activity nextActivity = activityMonitor.waitForActivityWithTimeout(5);
        assertEquals(nextActivity.getClass().getName(), InventoryActivity.class.getName());
        nextActivity.finish();
    }

    /**
     * Tests if the TradeListActivity Opens on a button press
     */
    public void testOpenTradeListActivity(){
        MainActivity activity = (MainActivity) getActivity();
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(TradeListActivity.class.getName(), null, false);
        Button openTradeListButton = (Button) activity.findViewById(R.id.TradeViewButton);
        TouchUtils.clickView(this, openTradeListButton);
        Activity nextActivity = activityMonitor.waitForActivityWithTimeout(5);
        assertEquals(nextActivity.getClass().getName(), TradeListActivity.class.getName());
        nextActivity.finish();
    }

    /**
     * Tests if the TopTradersActivity Opens on a button press
     */
    public void testOpenTopTradersActivity(){
        MainActivity activity = (MainActivity) getActivity();
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(TopTradersActivity.class.getName(), null, false);
        Button openTopTradersButton = (Button) activity.findViewById(R.id.TopTradersViewButton);
        TouchUtils.clickView(this, openTopTradersButton);
        Activity nextActivity = activityMonitor.waitForActivityWithTimeout(5);
        assertEquals(nextActivity.getClass().getName(), TopTradersActivity.class.getName());
        nextActivity.finish();
    }

    /**
     * Tests if the ViewUserActivity Opens on a button press
     */
    public void testOpenProfileActivity(){
        MainActivity activity = (MainActivity) getActivity();
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ViewUserActivity.class.getName(), null, false);
        Button openProfileButton = (Button) activity.findViewById(R.id.profileViewButton);
        TouchUtils.clickView(this, openProfileButton);
        Activity nextActivity = activityMonitor.waitForActivityWithTimeout(5);
        assertEquals(nextActivity.getClass().getName(), ViewUserActivity.class.getName());
        nextActivity.finish();
    }
}
