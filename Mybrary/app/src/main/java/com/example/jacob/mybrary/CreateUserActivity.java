package com.example.jacob.mybrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
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
 * Opens an Empty version of EditProfileActivity, to be called if there is no Local User
 * Opens text fields for writing profile information
 */
public class CreateUserActivity extends AppCompatActivity {

    CreateUserController myCreateUserController = new CreateUserController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        }

        /**
         * Activity to take in the Text from the Edit Text boxes and Creates a User with
         * the information
         * @param v View required to be passed when called from the XML file
         */
        public void setUser(View v){
            Boolean state = myCreateUserController.updateUser(this);
            if (state) {
                Toast doneToast = Toast.makeText(this, "Profile Created", Toast.LENGTH_SHORT);
                doneToast.show();
                finish();
            }
        }


}
