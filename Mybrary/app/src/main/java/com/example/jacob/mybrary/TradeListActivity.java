package com.example.jacob.mybrary;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

/**
 * The interface which shows a list of all in progress, and completed trades
 */

public class TradeListActivity extends AppCompatActivity {
    private ArrayAdapter<Trade> adapter;
    private ListView tradeListView;
    private ArrayList<Trade> occuredTrades = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_list);

        getTrades();

        tradeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                final Trade trade = (Trade) tradeListView.getItemAtPosition(pos);

                // needs to open a window asking if you want to modify, or delete the trade
                return true;
            }
        });

    }

    /**
     * Get the list of all trades relevant to this user
     */
    private void getTrades(){
        tradeListView = (ListView) findViewById(R.id.tradeListView);
        Trade trade = new Trade(new User("Harry","","","","",""), new User("Mouse","","","","",""));
        occuredTrades = new ArrayList<>();
        occuredTrades.add(trade);

        adapter = new ArrayAdapter<>(this, R.layout.simple_list_item, occuredTrades);

        tradeListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
