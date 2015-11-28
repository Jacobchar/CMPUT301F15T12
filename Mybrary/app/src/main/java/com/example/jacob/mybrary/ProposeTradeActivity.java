package com.example.jacob.mybrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Interface linked to from a friends inventory, allowing a trade offer to be sent
 * This is also linked to from the modify trade button in an individual trade
 */
public class ProposeTradeActivity extends AppCompatActivity {
    UUID currentTrade;
    TradeController tradeController;
    ListView yourTradeOffer;
    ListView theirTradeOffer;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propose_trade);

        tradeController = new TradeController();

        // http://stackoverflow.com/questions/2965109/passing-data-between-activities-in-android
        // Answered by Pentium10
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            currentTrade = (UUID) extras.getSerializable("currentTrade");
        }

        yourTradeOffer = (ListView) findViewById(R.id.yourItemsListView);
        theirTradeOffer = (ListView) findViewById(R.id.theirItemsListView);
        tradeController.getCurrentTradeOffer(this,currentTrade,yourTradeOffer,theirTradeOffer);

        onClickListener(yourTradeOffer, currentTrade, 1);
        onClickListener(theirTradeOffer,currentTrade,0);
    }

    /**
     * Listens for clicks to delete an item from a trade offer
     * @param view The view to watch for clicks
     * @param tradeID current trade being watched
     * @param callingView 1 for your offer, 0 for their offer
     */
    public void onClickListener(final ListView view, final UUID tradeID,final int callingView) {
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                final Book book = (Book) view.getItemAtPosition(pos);

                AlertDialog.Builder builder = new AlertDialog.Builder(ProposeTradeActivity.this);
                builder.setMessage("Remove book from offer?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        tradeController.changeOffer(tradeID, book, callingView, false);
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
            }

        });
    }

    public void sendRequestButton(View v){

    }

    public void addYourOfferButton(View v){
        Intent intent = new Intent(ProposeTradeActivity.this, AddBookToTradeActivity.class);
        // http://stackoverflow.com/questions/2965109/passing-data-between-activities-in-android
        // Answered by Pentium10
        intent.putExtra("currentTrade", currentTrade);
        intent.putExtra("addYourTrade",1);
        startActivity(intent);

    }

    public void addTheirOfferButton(View v){
        Intent intent = new Intent(ProposeTradeActivity.this, AddBookToTradeActivity.class);
        // http://stackoverflow.com/questions/2965109/passing-data-between-activities-in-android
        // Answered by Pentium10
        intent.putExtra("currentTrade", currentTrade);
        startActivity(intent);
    }

}
