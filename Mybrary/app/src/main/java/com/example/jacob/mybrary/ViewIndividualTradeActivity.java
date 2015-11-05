package com.example.jacob.mybrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ViewIndividualTradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_individual_trade);

    }

    public void modifyTradeButton(View v){
        // note: FromActivity.class, ToActivity.class
        Intent intent = new Intent(this, ProposeTradeActivity.class);
        startActivity(intent);
    }

    public void acceptTradeButton(View v){

    }

    public void declineTradeButton(View v){

    }

}
