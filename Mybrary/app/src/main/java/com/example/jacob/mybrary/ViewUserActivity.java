package com.example.jacob.mybrary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

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

    public void showInventory(View view){
        Intent intent = new Intent(this,InventoryActivity.class);
        intent.putExtra("inv", myUser.getInventory());
        startActivity(intent);
    }
}
