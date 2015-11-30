package com.example.jacob.mybrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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
 * Activity to View a single User's Profile
 *
 * Loading User not implemented yet
 */
public class ViewUserActivity extends AppCompatActivity {

    User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        Intent intent = getIntent();
        try {
            Bundle bundle = intent.getExtras();
            //Parcel parceledUser = intent.getParcelableExtra("user");
            myUser = bundle.getParcelable("user");
            setText(findViewById(R.id.textLayout));
        }catch (NullPointerException e){
            myUser = LocalUser.getInstance();
        }
    }

    /**
     * Sets the Text within the Edit Text boxes to the values from a User
     * @param v
     */
    public void setText(View v){
        TextView nameTextView = (TextView) v.findViewById(R.id.nameTextView);
        nameTextView.setText(myUser.getName());
        TextView cityTextView = (TextView) v.findViewById(R.id.cityTextView);
        cityTextView.setText(myUser.getCity());
        TextView phoneNumberTextView = (TextView) v.findViewById(R.id.phoneTextView);
        phoneNumberTextView.setText(myUser.getPhoneNumber());
        TextView emailTextView = (TextView) v.findViewById(R.id.emailTextView);
        emailTextView.setText(myUser.getEmailAddress());
        TextView genderTextView = (TextView) v.findViewById(R.id.genderTextView);
        genderTextView.setText(myUser.getGender());
        TextView bioTextView = (TextView) v.findViewById(R.id.bioTextView);
        bioTextView.setText(myUser.getBio());
    }

    /**
     * Opens the Inventory of the User being viewed
     * @param view View required to be passed when called from the XML file
     */
    public void showInventory(View view){
        Intent intent = new Intent(this, ViewGivenInventoryActivity.class);
        intent.putExtra("inventory", myUser.getPublicInventory());
        intent.putExtra("title", myUser.getName().toString() +"'s Inventory");
        startActivity(intent);
    }
}
