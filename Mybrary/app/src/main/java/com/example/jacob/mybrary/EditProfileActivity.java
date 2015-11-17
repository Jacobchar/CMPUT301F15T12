package com.example.jacob.mybrary;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Activity to Edit a User object
 *
 * Loading and Saving User not yet implemented
 * MVC Style not Completely Implemented yet
 */
public class EditProfileActivity extends AppCompatActivity {
    ProfileController myProfileController = new ProfileController();
    LocalUser myUser;
    Boolean mode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        try {
            DataManager.getInstance().loadLocalUser();
        }catch (IOException e){

        }
        myUser = LocalUser.getInstance();

        setText(mode);

    }

    /**
     * Activity to take in the Text from the Edit Text boxes and Updates the User with
     * the information
     * @param v
     */
    public void setUser(View v){
        TextView nameView = (TextView) this.findViewById(R.id.nameEditView);
        String name = nameView.getText().toString();
        EditText cityView = (EditText) this.findViewById(R.id.cityEditView);
        String city = cityView.getText().toString();
        EditText genderView = (EditText) this.findViewById(R.id.genderEditView);
        String gender = genderView.getText().toString();
        EditText phoneView = (EditText) this.findViewById(R.id.phoneEditView);
        String phone = phoneView.getText().toString();
        EditText emailView = (EditText) this.findViewById(R.id.emailEditView);
        String email = emailView.getText().toString();
        EditText bioView = (EditText) this.findViewById(R.id.bioEditView);
        String bio = bioView.getText().toString();
        User gotUser = new User(name, email, phone, gender, bio, city);
        LocalUser.getInstance().setSelf(gotUser);
        try {
            DataManager.getInstance().saveLocalUser();
        }catch (IOException e){

        }
        finish();
    }

    /**
     * Sets the Text within the Edit Text boxes to the values from a User
     * @param mode
     */
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
