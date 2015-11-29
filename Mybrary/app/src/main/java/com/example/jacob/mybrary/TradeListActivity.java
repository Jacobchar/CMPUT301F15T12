package com.example.jacob.mybrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;

/**
 * The interface which shows a list of all in progress, and completed trades
 */

public class TradeListActivity extends AppCompatActivity {
    private ListView inProgressTradeListView;
    private ListView completedTradeListView;
    private AlertDialog alert;

    private TradeController controller = new TradeController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_list);
    }

    /**
     * Update the view every time the activity is opened
     */
    @Override
    public void onResume(){
        super.onResume();
        inProgressTradeListView = (ListView) findViewById(R.id.inProgressTradeListView);
        completedTradeListView = (ListView) findViewById(R.id.completedTradesListView);

        controller.getTradeList(this,inProgressTradeListView, completedTradeListView);

        onLongClickListener(inProgressTradeListView);
        onLongClickListener(completedTradeListView);
        onClickListener(inProgressTradeListView);
        onClickListener(completedTradeListView);
    }

    public void onLongClickListener(final ListView view){
        view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                final Trade trade = (Trade) view.getItemAtPosition(pos);

                AlertDialog.Builder builder = new AlertDialog.Builder(TradeListActivity.this);
                builder.setMessage("Delete this trade?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        controller.deleteTrade(TradeListActivity.this, trade);
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
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

    public void onClickListener(final ListView view){
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Trade trade = (Trade) parent.getItemAtPosition(position);
                Intent intent = new Intent(TradeListActivity.this, ViewIndividualTradeActivity.class);
                // http://stackoverflow.com/questions/2965109/passing-data-between-activities-in-android
                // Answered by Pentium10
                intent.putExtra("currentTrade", trade.getTradeID());
                startActivity(intent);
            }

        });
    }




    public AlertDialog getAlertDialog(){
        return this.alert;
    }
}
