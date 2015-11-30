package com.example.jacob.mybrary;

        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
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