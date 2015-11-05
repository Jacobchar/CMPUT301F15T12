package com.example.jacob.mybrary;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
        loadUser(myUser, this);
        setText();
    }

    public void loadUser(User user, Context context){
        try {
            FileInputStream fis = context.openFileInput("USER.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
            user = (gson.fromJson(in, User.class));
            fis.close();
        } catch (FileNotFoundException e) {
            //Do Nothing
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (NullPointerException e){
            //Do Nothing
        }
    }

    public void setText(){
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        nameTextView.setText(myUser.getName());
        TextView cityTextView = (TextView) findViewById(R.id.cityTextView);
        cityTextView.setText(myUser.getCity());
        TextView phoneNumberTextView = (TextView) findViewById(R.id.phoneTextView);
        phoneNumberTextView.setText(myUser.getPhoneNumber());
        TextView emailTextView = (TextView) findViewById(R.id.emailTextView);
        emailTextView.setText(myUser.getEmailAddress());
        TextView genderTextView = (TextView) findViewById(R.id.genderTextView);
        genderTextView.setText(myUser.getGender());
        TextView bioTextView = (TextView) findViewById(R.id.bioTextView);
        bioTextView.setText(myUser.getBio());
    }
}
