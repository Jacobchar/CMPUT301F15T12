package com.example.jacob.mybrary;

import android.content.DialogInterface;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
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

    public void testLongClick() throws Throwable{
        TradeListActivity activity = (TradeListActivity) getActivity();
        assertNotNull(activity);

        ListView text = (ListView) activity.findViewById(R.id.tradeListView);

        // http://stackoverflow.com/questions/23454654/how-to-simulate-an-user-click-to-a-listview-item-in-junit-testing
        // Answered by Markn12, edited by Nathan Tuggy
        TouchUtils.longClickView(this,text.getChildAt(0));
        assertTrue(activity.getAlertDialog().isShowing());


    }

}
