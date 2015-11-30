package com.example.jacob.mybrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.IOException;

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
     * Toggles whether the User downloads images automatically based on if the Checkbox is checked
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
