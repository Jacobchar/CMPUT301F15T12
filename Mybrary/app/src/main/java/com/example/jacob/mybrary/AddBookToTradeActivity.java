package com.example.jacob.mybrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.UUID;

public class AddBookToTradeActivity extends AppCompatActivity {
    UUID currentTrade;
    TradeController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_to_trade);
        controller = new TradeController();

        // http://stackoverflow.com/questions/2965109/passing-data-between-activities-in-android
        // Answered by Pentium10
        Bundle extras = getIntent().getExtras();
        int callingButton = getIntent().getIntExtra("addYourTrade",0);
        if(extras !=null) {
            currentTrade = (UUID) extras.getSerializable("currentTrade");
        }

            controller.getUserOfferSelections(currentTrade, this, callingButton);


    }
}
