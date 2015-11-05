package com.example.jacob.mybrary;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditProfileActivity extends AppCompatActivity {

    ProfileController myProfileController = new ProfileController();
    User myUser;
    Boolean mode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //loadUser(myUser);
        myUser = new User("Name", "Email", "4121", "Gender", "Bio", "City");
        setText(mode);

    }

    public void setUser(View v){
        final View layout = View.inflate(this, R.layout.activity_edit_profile, null);
        EditText nameView = (EditText) layout.findViewById(R.id.nameEditView);
        String name = nameView.getText().toString();
        EditText cityView = (EditText) layout.findViewById(R.id.cityEditView);
        String city = cityView.getText().toString();
        EditText genderView = (EditText) layout.findViewById(R.id.genderEditView);
        String gender = genderView.getText().toString();
        EditText phoneView = (EditText) layout.findViewById(R.id.phoneEditView);
        String phone = phoneView.getText().toString();
        EditText emailView = (EditText) layout.findViewById(R.id.emailEditView);
        String email = emailView.getText().toString();
        EditText bioView = (EditText) layout.findViewById(R.id.bioEditView);
        String bio = bioView.getText().toString();
        User gotUser = new User(name, email, phone, gender, bio, city);
        myProfileController.updateUser(gotUser, this);
        finish();
    }

    public void loadUser(User user){
        //Load User from whereever
    }
    
    public void setText(Boolean mode){
        if (mode) {
            EditText nameEditText = (EditText) findViewById(R.id.nameEditView);
            nameEditText.setText(myUser.getName(), TextView.BufferType.EDITABLE);
            EditText cityEditText = (EditText) findViewById(R.id.cityEditView);
            cityEditText.setText(myUser.getCity(), TextView.BufferType.EDITABLE);
            EditText phoneNumberEditText = (EditText) findViewById(R.id.phoneEditView);
            phoneNumberEditText.setText(myUser.getPhoneNumber(), TextView.BufferType.EDITABLE);
            EditText emailEditText = (EditText) findViewById(R.id.emailEditView);
            emailEditText.setText(myUser.getEmailAddress(), TextView.BufferType.EDITABLE);
            EditText genderEditText = (EditText) findViewById(R.id.genderEditView);
            genderEditText.setText(myUser.getGender(), TextView.BufferType.EDITABLE);
            EditText bioEditText = (EditText) findViewById(R.id.bioEditView);
            bioEditText.setText(myUser.getBio(), TextView.BufferType.EDITABLE);
        }
    }

}
