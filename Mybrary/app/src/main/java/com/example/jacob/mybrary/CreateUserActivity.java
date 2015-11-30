package com.example.jacob.mybrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

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
