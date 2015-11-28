package com.example.jacob.mybrary;

import android.test.AndroidTestCase;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Dominic on 2015-10-31.
 *
 * Simple tests for ConnectionManager. More tests should be added for better coverage.
 *
 * Source for signals: http://stackoverflow.com/a/3802487
 */
public class ConnectionManagerTest extends AndroidTestCase {
    public void testPut() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        String path = "testing/deadbeef";
        String json = "{\"Name\":\"Name1\"}";

        try {
        connectionManager.put(path, json);

        String result = connectionManager.get(path);

        assertTrue(result.contains("\"_type\":\"testing\""));
        assertTrue(result.contains("\"_id\":\"deadbeef\""));
        assertTrue(result.contains("\"found\":true"));
        assertTrue(result.contains("\"_source\":{\"Name\":\"Name1\"}"));

        connectionManager.remove(path);
        } catch (IOException e) {
            fail();
        }
    }

    public void testQuery() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        try {
            connectionManager.put("testing/1", "{\"val\":1}");
            connectionManager.put("testing/2", "{\"val\":2}");
            connectionManager.put("testing/3", "{\"val\":3}");

            String query = "{\"query\":{\"query_string\":{\"default_field\":\"val\",\"query\":2}}}";
            String result = connectionManager.query("testing/", query);
            assertTrue(result.contains("\"hits\":{\"total\":1"));
            assertTrue(result.contains("\"_id\":\"2\""));
            assertTrue(result.contains("\"_source\":{\"val\":2}"));


            connectionManager.remove("testing/1");
            connectionManager.remove("testing/2");
            connectionManager.remove("testing/3");
        } catch (IOException e) {
            fail();
        }
    }

    public void testGet() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        String path = "testing/deadbeef";
        String json = "{\"Name\":\"Name1\"}";

        try {
            connectionManager.put(path, json);

            String result = connectionManager.get(path);
            assertTrue(result.contains("\"_type\":\"testing\""));
            assertTrue(result.contains("\"_id\":\"deadbeef\""));
            assertTrue(result.contains("\"found\":true"));
            assertTrue(result.contains("\"_source\":{\"Name\":\"Name1\"}"));

            connectionManager.remove(path);
        } catch (IOException e) {
            fail();
        }
    }

    public void testRemove() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        String path = "testing/deadbeef";
        String json = "{\"Name\":\"Name1\"}";

        try {
            connectionManager.put(path, json);

            connectionManager.remove(path);

            String result = connectionManager.get(path);
            assertEquals(result, "{\"_index\":\"cmput301f15t12\",\"_type\":\"testing\",\"_id\":\"deadbeef\",\"found\":false}");
        } catch (IOException e) {
            fail();
        }
    }

    public void testPartialMatch() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        try {
            String result = connectionManager.query("Users/", "{\"query\":{\"query_string\":{\"analyze_wildcard\":true,\"default_field\":\"name\",\"query\":\"Vic*\"}}}");
            assertNotNull(result);
        } catch (IOException e) {
            fail();
        }
    }
}
