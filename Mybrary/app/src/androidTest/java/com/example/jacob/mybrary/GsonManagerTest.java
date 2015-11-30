package com.example.jacob.mybrary;

import android.test.AndroidTestCase;

/**
 * Simple tests for GsonManager.
 *
 * Created by Dominic on 2015-11-29.
 */
public class GsonManagerTest extends AndroidTestCase {
    public void testToJson() {
        User sampleuser = new User("name", "email", "1111111", "gender", "bio", "city");
        String producedjson = GsonManager.getInstance().toJson(sampleuser);
        String expectedjson = "{\"bio\":\"bio\",\"city\":\"city\",\"downloadImages\":false" +
                ",\"emailAddress\":\"email\",\"friendsList\":{\"friendList\":[]},\"gender\":" +
                "\"gender\",\"inventory\":{\"inventoryList\":[]},\"myUUID\":\"" +
                sampleuser.getUUID().toString() +
                "\",\"name\":\"name\",\"phoneNumber\":" +
                "\"1111111\",\"succTrades\":0}";
        assertTrue(producedjson.equals(expectedjson));
    }

    public void testFromJson() {
        String samplejson = "{\"bio\":\"testBio2\",\"city\":\"testCity2\",\"downloadImages\":false" +
                ",\"emailAddress\":\"testEmail2\",\"friendsList\":{\"friendList\":[]},\"gender\":" +
                "\"testGender2\",\"inventory\":{\"inventoryList\":[]},\"myUUID\":" +
                "\"fed5310e-850d-436d-bc8a-5cb720949bd5\",\"name\":\"testName2\",\"phoneNumber\":" +
                "\"testPhNo2\",\"succTrades\":0}";

        User user = GsonManager.getInstance().fromJson(samplejson, User.class);
        assertTrue(user.getName().equals("testName2"));
        assertTrue(user.getBio().equals("testBio2"));
        assertTrue(user.getCity().equals("testCity2"));
        assertTrue(user.getEmailAddress().equals("testEmail2"));
        assertTrue(user.getGender().equals("testGender2"));
    }
}
