package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

/**
 * Tests the View User Activity
 */
public class ViewUserActivityTest extends ActivityInstrumentationTestCase2 {

    public ViewUserActivityTest (){
        super(ViewUserActivity.class);
    }

    public void testSetText(){
        ViewUserActivity activity = (ViewUserActivity) getActivity();
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

}
