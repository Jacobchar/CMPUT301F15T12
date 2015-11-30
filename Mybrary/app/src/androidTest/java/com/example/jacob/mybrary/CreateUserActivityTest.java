package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Tests the activity to create
 */
public class CreateUserActivityTest extends ActivityInstrumentationTestCase2 {

    public CreateUserActivityTest (){
        super(CreateUserActivity.class);
    }

    /**
     * Tests the input into the text boxes
     */
    public void testFillBoxes(){
        String name = "TestUser";
        String emailAddress = "user@test.com";
        String phoneNum = "555-5555";
        String gender = "Male";
        String bio = "Likes to take long walks on the beach";
        String city = "Edmonton";
        final User testUser = new User(name, emailAddress, phoneNum, gender, bio, city);

        final CreateUserActivity activity = (CreateUserActivity) getActivity();
        assertNotNull(activity);

        try{
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CreateUserActivity activity = (CreateUserActivity) getActivity();
                    assertNotNull(activity);
                    EditText nameView = (EditText) activity.findViewById(R.id.nameEditCreateView);
                    nameView.setText(testUser.getName());
                    assertEquals(nameView.getText().toString(), testUser.getName());
                    EditText cityView = (EditText) activity.findViewById(R.id.cityEditCreateView);
                    cityView.setText(testUser.getCity());
                    assertEquals(cityView.getText().toString(), testUser.getCity());
                    EditText genderView = (EditText) activity.findViewById(R.id.genderEditCreateView);
                    genderView.setText(testUser.getGender());
                    assertEquals(genderView.getText().toString(), testUser.getGender());
                    EditText phoneView = (EditText) activity.findViewById(R.id.phoneEditCreateView);
                    phoneView.setText(testUser.getPhoneNumber());
                    assertEquals(phoneView.getText().toString(), testUser.getPhoneNumber());
                    EditText emailView = (EditText) activity.findViewById(R.id.emailEditCreateView);
                    emailView.setText(testUser.getEmailAddress());
                    assertEquals(emailView.getText().toString(), testUser.getEmailAddress());
                    EditText bioView = (EditText) activity.findViewById(R.id.bioEditCreateView);
                    bioView.setText(testUser.getBio());
                    assertEquals(bioView.getText().toString(), testUser.getBio());
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

}
