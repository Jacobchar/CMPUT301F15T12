package com.example.jacob.mybrary;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


/**
 * Controller Class for the Edit Profile Acticontextity
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
