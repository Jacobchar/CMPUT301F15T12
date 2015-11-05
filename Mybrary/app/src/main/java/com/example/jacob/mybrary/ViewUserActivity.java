package com.example.jacob.mybrary;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ViewUserActivity extends AppCompatActivity {

    User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        //loadUser(myUser);
        //Test
        myUser = new User("Name", "Email", "4121", "Gender", "Bio", "City");
        setText(findViewById(R.id.textLayout));
    }

    public void loadUser(User user){
        //Load In a User from a saved File

    }

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
