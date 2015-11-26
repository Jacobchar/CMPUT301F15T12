package com.example.jacob.mybrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
    }

    public void sendRequestButton(View v){

    }

    public void addYourOfferButton(View v){
        Intent intent = new Intent(this, AddBookToTradeActivity.class);
        // http://stackoverflow.com/questions/2965109/passing-data-between-activities-in-android
        // Answered by Pentium10
        intent.putExtra("currentTrade", currentTrade);
        intent.putExtra("addYourTrade",1);
        startActivity(intent);

    }

    public void addTheirOfferButton(View v){
        Intent intent = new Intent(this, AddBookToTradeActivity.class);
        // http://stackoverflow.com/questions/2965109/passing-data-between-activities-in-android
        // Answered by Pentium10
        intent.putExtra("currentTrade", currentTrade);
        startActivity(intent);
    }

}
