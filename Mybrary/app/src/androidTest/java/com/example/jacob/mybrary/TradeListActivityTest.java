package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

/**
 * Test the trade view
 * Tests are based on the labs "lonelyTwitter" activity tests
 * Created by David on 2015-11-14.
 */
public class TradeListActivityTest extends ActivityInstrumentationTestCase2 {
    public TradeListActivityTest (){
        super(TradeListActivity.class);
    }

    ListView text;

    public void testTradeList(){
        TradeListActivity activity = (TradeListActivity) getActivity();
        text = (ListView) activity.findViewById(R.id.tradeListView);
        // Check if the adapter contains trades which will be shown on the interface
        assertFalse(text.getAdapter().getItem(0).toString().isEmpty());



    }

}
