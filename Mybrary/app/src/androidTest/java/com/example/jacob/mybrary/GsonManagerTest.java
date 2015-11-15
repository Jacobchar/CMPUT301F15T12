package com.example.jacob.mybrary;

import android.test.AndroidTestCase;

import java.io.IOException;

/**
 * Created by Jacob on 2015-11-15.
 */
public class GsonManagerTest extends AndroidTestCase {

    public void testToJson(){
        GsonManager gm = GsonManager.getInstance();

        try {

        } catch (IOException e) {
            fail();
        }
    }

    public void testFromJson(){

    }

}
