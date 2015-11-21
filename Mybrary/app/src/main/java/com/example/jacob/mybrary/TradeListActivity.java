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

    TradeController controller = new TradeController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_list);

        tradeListView = controller.getTradeList(this);

        tradeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                final Trade trade = (Trade) tradeListView.getItemAtPosition(pos);

                AlertDialog.Builder builder = new AlertDialog.Builder(TradeListActivity.this);
                builder.setMessage("View or Delete the Trade?");
                builder.setCancelable(true);
                builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(TradeListActivity.this, ViewIndividualTradeActivity.class);
                        // http://stackoverflow.com/questions/2965109/passing-data-between-activities-in-android
                        // Answered by Pentium10
                        intent.putExtra("currentTrade",trade.getTradeID());
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        controller.deleteTrade(TradeListActivity.this, trade);
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



    public AlertDialog getAlertDialog(){
        return this.alert;
    }

    public ArrayList<Trade> getOccuredTrades(){
        return this.occuredTrades;
    }
}
