package com.example.jacob.mybrary;

        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 * Activity to display the top 10 traders, either friends or all users
 */

public class TopTradersActivity extends AppCompatActivity {
    TopTraderController topController = new TopTraderController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_traders);

        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.tradersToggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                topController.fillView(TopTradersActivity.this, isChecked);
            }
        });

        topController.fillView(this, false);
    }
}