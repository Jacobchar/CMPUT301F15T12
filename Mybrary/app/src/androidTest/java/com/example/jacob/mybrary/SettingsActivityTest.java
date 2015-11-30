package com.example.jacob.mybrary;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.CheckBox;

import junit.framework.Assert;

/**
 * Tests for the Settings Activity
 */
public class SettingsActivityTest extends ActivityInstrumentationTestCase2{
    public SettingsActivityTest() {
        super(SettingsActivity.class);
    }

    /**
     * Tests if the EditProfileActivity Opens on a button press
     */
    public void testOpenEditProfileActivity(){
        SettingsActivity activity = (SettingsActivity) getActivity();
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(EditProfileActivity.class.getName(), null, false);
        Button openEditProfileButton = (Button) activity.findViewById(R.id.editProfileButton);
        TouchUtils.clickView(this, openEditProfileButton);
        Activity nextActivity = activityMonitor.waitForActivityWithTimeout(5);
        assertEquals(nextActivity.getClass().getName(), EditProfileActivity.class.getName());
        nextActivity.finish();
    }

    public void testCheckBox(){
        SettingsActivity activity = (SettingsActivity) getActivity();
        CheckBox checkBox = (CheckBox) activity.findViewById(R.id.downloadImagesCheckBox);
        TouchUtils.clickView(this, checkBox);
        assertTrue(LocalUser.getInstance().getDownloadImages());
        TouchUtils.clickView(this, checkBox);
        assertFalse(LocalUser.getInstance().getDownloadImages());
    }
}
