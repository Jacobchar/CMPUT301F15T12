package com.example.jacob.mybrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

/**
 * Opens an Empty version of EditProfileActivity, to be called if there is no Local User
 * Opens text fields for writing profile information
 */
public class CreateUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        }

        /**
         * Activity to take in the Text from the Edit Text boxes and Updates the User with
         * the information
         * @param v
         */
        public void setUser(View v){
            TextView nameView = (TextView) this.findViewById(R.id.nameEditCreateView);
            String name = nameView.getText().toString();
            EditText cityView = (EditText) this.findViewById(R.id.cityEditCreateView);
            String city = cityView.getText().toString();
            EditText genderView = (EditText) this.findViewById(R.id.genderEditCreateView);
            String gender = genderView.getText().toString();
            EditText phoneView = (EditText) this.findViewById(R.id.phoneEditCreateView);
            String phone = phoneView.getText().toString();
            EditText emailView = (EditText) this.findViewById(R.id.emailEditCreateView);
            String email = emailView.getText().toString();
            EditText bioView = (EditText) this.findViewById(R.id.bioEditCreateView);
            String bio = bioView.getText().toString();
            User gotUser = new User(name, email, phone, gender, bio, city);
            LocalUser.getInstance().setSelf(gotUser);
            try {
                DataManager.getInstance().saveLocalUser();
            }catch (IOException e){

            }
            finish();
        }

}
