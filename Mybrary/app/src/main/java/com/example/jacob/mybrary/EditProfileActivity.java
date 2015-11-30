package com.example.jacob.mybrary;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
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
 * Activity to Edit a User object
 *
 */
public class EditProfileActivity extends AppCompatActivity {
    ProfileController myProfileController = new ProfileController();
    LocalUser myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        try {
            DataManager.getInstance().loadLocalUser();
        }catch (IOException e){

        }
        myUser = LocalUser.getInstance();

        setText();

    }

    /**
     * Activity to take in the Text from the Edit Text boxes and Updates the User with
     * the information
     * @param v View required to be passed when called from the XML file
     */
    public void setUser(View v){
        Boolean state = myProfileController.updateUser(this);
        if (state) {
            Toast doneToast = Toast.makeText(this, "Profile Changed", Toast.LENGTH_SHORT);
            doneToast.show();
            finish();
        }
    }

    /**
     * Sets the Text within the Edit Text boxes to the values from the user
     *
     */
    public void setText(){
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
