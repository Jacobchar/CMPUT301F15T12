package com.example.jacob.mybrary;

import android.test.AndroidTestCase;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Dominic on 2015-10-31.
 */
public class ConnectionManagerTest extends AndroidTestCase {
    public void testPut() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        String path = "/testing/";
        String json = "{\"Name\":\"Name1\"}";
        connectionManager.put(path, json);
    }

    public void testQuery() {

    }
}
