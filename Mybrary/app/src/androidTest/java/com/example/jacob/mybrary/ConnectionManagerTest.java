package com.example.jacob.mybrary;

import android.test.AndroidTestCase;

/**
 * Created by Dominic on 2015-10-31.
 */
public class ConnectionManagerTest extends AndroidTestCase {
    public void testConnect() {
        ConnectionManager conn = ConnectionManager.getInstance();

        conn.setConnectionString("");
    }

    public void testConnectionFailed() {

    }

    public void testPut() {

    }

    public void testQuery() {

    }
}
