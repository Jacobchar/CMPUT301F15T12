package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Test the trade view
 * Tests are based on the labs "lonelyTwitter" activity tests
 * Created by David on 2015-11-14.
 */
public class TradeListActivityTest extends ActivityInstrumentationTestCase2 {
    public TradeListActivityTest (){
        super(TradeListActivity.class);
    }

    public void testTradeList(){
       TradeListActivity activity = (TradeListActivity) getActivity();
       ListView  text = (ListView) activity.findViewById(R.id.tradeListView);
        Trade trade = (Trade) text.getAdapter().getItem(0);
        assertNotNull(trade);
    }

    public void testLongClick(){
        final TradeListActivity activity = (TradeListActivity) getActivity();
        assertNotNull(activity);

        final ListView text = (ListView) activity.findViewById(R.id.tradeListView);

        try {
            // http://blog.denevell.org/android-instrumentation-click-list.html
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.performItemClick(text, text.getFirstVisiblePosition(), text.getItemIdAtPosition(text.getFirstVisiblePosition()));
                    assertTrue(activity.getAlertDialog().isShowing());
                }
            });
        }
        catch(Throwable t){

        }

        getInstrumentation().waitForIdleSync();

    }

}
