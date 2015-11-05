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
    Boolean mode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        loadUser(myUser, this);
        setText(mode);

    }

    public void setUser(View v){
        EditText nameView = (EditText) v.findViewById(R.id.nameEditView);
        String name = nameView.getText().toString();
        EditText cityView = (EditText) v.findViewById(R.id.cityEditView);
        String city = cityView.getText().toString();
        EditText genderView = (EditText) v.findViewById(R.id.genderEditView);
        String gender = genderView.getText().toString();
        EditText phoneView = (EditText) v.findViewById(R.id.phoneEditView);
        String phone = phoneView.getText().toString();
        EditText emailView = (EditText) v.findViewById(R.id.emailEditView);
        String email = emailView.getText().toString();
        EditText bioView = (EditText) v.findViewById(R.id.bioEditView);
        String bio = bioView.getText().toString();
        User gotUser = new User(name, email, phone, gender, bio, city);
        myProfileController.updateUser(gotUser, this);
        finish();
    }

    public void loadUser(User user, Context context){
        try {
            FileInputStream fis = context.openFileInput("USER.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
            user = (gson.fromJson(in, User.class));
            fis.close();
            mode = true;
        } catch (FileNotFoundException e) {
            //Do Nothing
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (NullPointerException e){
            //Do Nothing
        }
    }
    
    public void setText(Boolean mode){
        if (mode) {
            EditText nameEditText = (EditText) findViewById(R.id.nameTextView);
            nameEditText.setText(myUser.getName());
            EditText cityEditText = (EditText) findViewById(R.id.cityTextView);
            cityEditText.setText(myUser.getCity());
            EditText phoneNumberEditText = (EditText) findViewById(R.id.phoneTextView);
            phoneNumberEditText.setText(myUser.getPhoneNumber());
            EditText emailEditText = (EditText) findViewById(R.id.emailTextView);
            emailEditText.setText(myUser.getEmailAddress());
            EditText genderEditText = (EditText) findViewById(R.id.genderTextView);
            genderEditText.setText(myUser.getGender());
            EditText bioEditText = (EditText) findViewById(R.id.bioTextView);
            bioEditText.setText(myUser.getBio());
        }
        else{
            EditText nameEditText = (EditText) findViewById(R.id.nameTextView);
            nameEditText.setText("");
            EditText cityEditText = (EditText) findViewById(R.id.cityTextView);
            cityEditText.setText("");
            EditText phoneNumberEditText = (EditText) findViewById(R.id.phoneTextView);
            phoneNumberEditText.setText("");
            EditText emailEditText = (EditText) findViewById(R.id.emailTextView);
            emailEditText.setText("");
            EditText genderEditText = (EditText) findViewById(R.id.genderTextView);
            genderEditText.setText("");
            EditText bioEditText = (EditText) findViewById(R.id.bioTextView);
            bioEditText.setText("");
        }
    }

}
