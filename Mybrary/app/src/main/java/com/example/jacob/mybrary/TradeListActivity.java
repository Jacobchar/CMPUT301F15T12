package com.example.jacob.mybrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

/**
 * The interface which shows a list of all in progress, and completed trades
 */

public class TradeListActivity extends AppCompatActivity {
    private ListView tradeListView;
    private ArrayList<Trade> occuredTrades = new ArrayList<>();
    private AlertDialog alert;
    private ArrayAdapter<Trade> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_list);

        getTrades();

        tradeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                final Trade trade = (Trade) tradeListView.getItemAtPosition(pos);

                AlertDialog.Builder builder = new AlertDialog.Builder(TradeListActivity.this);
                builder.setMessage("Edit or Delete the Trade?");
                builder.setCancelable(true);
                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(TradeListActivity.this, ProposeTradeActivity.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        occuredTrades.remove(trade);
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                alert = builder.create();
                alert.show();
                return true;
            }
        });

    }

    /**
     * Get the list of all trades relevant to this user'
     * Todo: Currently is just all trades
     */
    private void getTrades(){
                tradeListView = (ListView) findViewById(R.id.tradeListView);
                Trade trade = new Trade(new User("Harry", "", "", "", "", ""), new User("Mouse", "", "", "", "", ""));
                occuredTrades = new ArrayList<>();
                occuredTrades.add(trade);

                adapter = new ArrayAdapter<>(TradeListActivity.this, R.layout.simple_list_item, occuredTrades);

                tradeListView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
    }

    public AlertDialog getAlertDialog(){
        return this.alert;
    }

    public ArrayList<Trade> getOccuredTrades(){
        return this.occuredTrades;
    }
}
