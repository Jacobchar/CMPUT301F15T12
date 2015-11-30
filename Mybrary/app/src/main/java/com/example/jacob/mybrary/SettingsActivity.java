package com.example.jacob.mybrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

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
 * Activity for adjusting app settings
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        CheckBox checkBox = (CheckBox) findViewById(R.id.downloadImagesCheckBox);
        checkBox.setChecked(LocalUser.getInstance().getDownloadImages());

    }

    /**
     * Opens the EditProfileActivity
     * @param view View required to be passed when called from the XML file
     */
    public void openEditProfileActivity(View view) {
        // note: FromActivity.class, ToActivity.class
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    /**
     * Toggles whether the User downloads images automatically based on if the Checkbox is checkedo
     * @param v View required to be passed when called from the XML file
     */
    public void toggleDownloadImages(View v){
        CheckBox checkBox = (CheckBox) findViewById(R.id.downloadImagesCheckBox);
        if (checkBox.isChecked()){
            LocalUser.getInstance().setDownloadImages(true);
        }
        else{
            LocalUser.getInstance().setDownloadImages(false);
        }
    }
}
