package com.example.jacob.mybrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
/*
Copyright (C) 2015  Ben Schreiber , David Ross,Dominic Trottier,
                    Jake Charlebois, Mason Strong, Victoria Hessdorfer

        This file is part of Mybrary.

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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

    /**
     * Listens for a long click on the trade view, allowing the user to delete the chosen trade
     * @param view view being listened to
     */
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

    /**
     * Listens for regular clicks on a given trade so users can view hte trade
     * @param view view being clicked on
     */
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
