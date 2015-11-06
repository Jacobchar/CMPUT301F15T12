package com.example.jacob.mybrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity to Edit a User object
 *
 * Loading and Saving User not yet implemented
 * MVC Style not Completely Implemented yet
 */
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

    /**
     * Activity to take in the Text from the Edit Text boxes and Updates the User with
     * the information
     * @param v
     */
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
