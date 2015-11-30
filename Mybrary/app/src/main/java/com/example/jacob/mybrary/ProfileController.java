package com.example.jacob.mybrary;

import android.util.Patterns;
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
 * Controller Class for the Edit Profile Activity
 */
public class ProfileController {

    public ProfileController() {
    }

    /**
     * Updates the local user with information from a EditProfileActivity inputted activity
     * @param context EditProfileActivity that provides the information to update the user
     * @return Returns true if the fields are valid, returns false if not.
     */
    public boolean updateUser(EditProfileActivity context){
        TextView nameView = (TextView) context.findViewById(R.id.nameEditView);
        String name = nameView.getText().toString();
        EditText cityView = (EditText) context.findViewById(R.id.cityEditView);
        String city = cityView.getText().toString();
        EditText genderView = (EditText) context.findViewById(R.id.genderEditView);
        String gender = genderView.getText().toString();
        EditText phoneView = (EditText) context.findViewById(R.id.phoneEditView);
        String phone = phoneView.getText().toString();
        if(!Patterns.PHONE.matcher(phone).matches()){
            Toast phoneToast = Toast.makeText(context, "Phone Number Not Valid", Toast.LENGTH_SHORT);
            phoneToast.show();
            return false;
        }
        EditText emailView = (EditText) context.findViewById(R.id.emailEditView);
        String email = emailView.getText().toString();
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast emailToast = Toast.makeText(context, "Email Not Valid", Toast.LENGTH_SHORT);
            emailToast.show();
            return false;
        }
        EditText bioView = (EditText) context.findViewById(R.id.bioEditView);
        String bio = bioView.getText().toString();
        final User gotUser = new User(name, email, phone, gender, bio, city);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                LocalUser.getInstance().setSelf(gotUser);
                try {
                    DataManager.getInstance().saveLocalUser();
                }catch (IOException e){

                }
            }
        });
        t.run();
        return true;
    }


}
