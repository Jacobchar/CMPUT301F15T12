package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Tests the Edit Profile Activity
 */
public class EditProfileActivityTest extends ActivityInstrumentationTestCase2 {
    private String name = "TestUser";
    private String emailAddress = "user@test.com";
    private String phoneNum = "555-5555";
    private String gender = "Male";
    private String bio = "Likes to take long walks on the beach";
    private String city = "Edmonton";

    public EditProfileActivityTest (){
        super(EditProfileActivity.class);
    }

    public void testSetText(){
        EditProfileActivity activity = (EditProfileActivity) getActivity();
        assertNotNull(activity);
        TextView nameTextView = (TextView) activity.findViewById(R.id.nameTextView);
        assertEquals(nameTextView.getText().toString(), LocalUser.getInstance().getName());
        TextView cityTextView = (TextView) activity.findViewById(R.id.cityTextView);
        assertEquals(cityTextView.getText().toString(), LocalUser.getInstance().getCity());
        TextView phoneNumberTextView = (TextView) activity.findViewById(R.id.phoneTextView);
        assertEquals(phoneNumberTextView.getText().toString(), LocalUser.getInstance().getPhoneNumber());
        TextView emailTextView = (TextView) activity.findViewById(R.id.emailTextView);
        assertEquals(emailTextView.getText().toString(), LocalUser.getInstance().getEmailAddress());
        TextView genderTextView = (TextView) activity.findViewById(R.id.genderTextView);
        assertEquals(genderTextView.getText().toString(), LocalUser.getInstance().getGender());
        TextView bioTextView = (TextView) activity.findViewById(R.id.bioTextView);
        assertEquals(bioTextView.getText().toString(), LocalUser.getInstance().getBio());
    }

    /**
     * Tests the input into the text boxes
     * NOTE: Test currently fails due to wrong input way
     */
    public void testFillBoxes(){
        User testUser = new User(name, emailAddress, phoneNum, gender, bio, city);
        
        EditProfileActivity activity = (EditProfileActivity) getActivity();
        assertNotNull(activity);
        EditText nameView = (EditText) activity.findViewById(R.id.nameEditView);
        nameView.setText(testUser.getName());
        assertEquals(nameView.getText().toString(), testUser.getName());
        EditText cityView = (EditText) activity.findViewById(R.id.cityEditView);
        cityView.setText(testUser.getCity());
        assertEquals(cityView.getText().toString(), testUser.getCity());
        EditText genderView = (EditText) activity.findViewById(R.id.genderEditView);
        genderView.setText(testUser.getGender());
        assertEquals(genderView.getText().toString(), testUser.getGender());
        EditText phoneView = (EditText) activity.findViewById(R.id.phoneEditView);
        phoneView.setText(testUser.getPhoneNumber());
        assertEquals(phoneView.getText().toString(), testUser.getPhoneNumber());
        EditText emailView = (EditText) activity.findViewById(R.id.emailEditView);
        emailView.setText(testUser.getEmailAddress());
        assertEquals(emailView.getText().toString(), testUser.getEmailAddress());
        EditText bioView = (EditText) activity.findViewById(R.id.bioEditView);
        bioView.setText(testUser.getBio());
        assertEquals(bioView.getText().toString(), testUser.getBio());
    }

    /**
     * Tests the user pressing the "Set Profile" button
     * NOTE: Test currently fails due to wrong input way
     */
    public void testClickSetProfile(){
        User testUser = new User(name, emailAddress, phoneNum, gender, bio, city);

        EditProfileActivity activity = (EditProfileActivity) getActivity();
        assertNotNull(activity);

        EditText nameView = (EditText) activity.findViewById(R.id.nameEditView);
        nameView.setText(testUser.getName());
        EditText cityView = (EditText) activity.findViewById(R.id.cityEditView);
        cityView.setText(testUser.getCity());
        EditText genderView = (EditText) activity.findViewById(R.id.genderEditView);
        genderView.setText(testUser.getGender());
        EditText phoneView = (EditText) activity.findViewById(R.id.phoneEditView);
        phoneView.setText(testUser.getPhoneNumber());
        EditText emailView = (EditText) activity.findViewById(R.id.emailEditView);
        emailView.setText(testUser.getEmailAddress());
        EditText bioView = (EditText) activity.findViewById(R.id.bioEditView);
        bioView.setText(testUser.getBio());

        Button setProfileButton = (Button) activity.findViewById(R.id.setProfileButton);
        TouchUtils.clickView(this, setProfileButton);

        assertEquals(LocalUser.getInstance().getName(), name);
        assertEquals(LocalUser.getInstance().getCity(), city);
        assertEquals(LocalUser.getInstance().getGender(), gender);
        assertEquals(LocalUser.getInstance().getPhoneNumber(), phoneNum);
        assertEquals(LocalUser.getInstance().getEmailAddress(), emailAddress);
        assertEquals(LocalUser.getInstance().getBio(), bio);
    }
}

