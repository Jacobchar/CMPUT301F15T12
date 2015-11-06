package com.example.jacob.mybrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Activity to View a single User's Profile
 */
public class ViewUserActivity extends AppCompatActivity {

    User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        //loadUser(myUser);
        //Test until loading user is implemented
        myUser = new User("Name", "Email", "4121", "Gender", "Bio", "City");
        setText(findViewById(R.id.textLayout));
    }

    public void loadUser(User user){
        //Load In a User from a saved File

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
}
