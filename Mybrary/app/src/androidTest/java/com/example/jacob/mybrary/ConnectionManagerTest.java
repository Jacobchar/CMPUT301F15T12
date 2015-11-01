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

        Integer id = 0xdeadbeef;
        String path = "testing/";
        String json = "{\"Name\":\"Name1\"}";
        try {
            connectionManager.put(path, id, json);
        } catch (IOException e) {
            fail();
        }

        String value = connectionManager.get(id);
        assertEquals(value, "{\"_index\":\"cmput301f15t12\"," +
                "\"_type\":\"testing\"," +
                "\"_id\":\"3735928559\"," +
                "\"_version\":1," +
                "\"found\":true," +
                "\"_source\":{\"Name\":\"Name1\"}" +
                "}");

        connectionManager.remove(path, id);
    }

    public void testQuery() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        try {
            connectionManager.put("testing/", 1, "{\"val\":1}");
            connectionManager.put("testing/", 2, "{\"val\":2}");
            connectionManager.put("testing/", 3, "{\"val\":3}");
        } catch (IOException e) {
            fail();
        }

        String query = "{\"query\":{\"query_string\":{\"value\":\"2\"}}}";
        String value = connectionManager.query(query);
        assertEquals(value, "{\"took\":1,\"timed_out\":false,\"_shards\":" +
                "{\"total\":1,\"successful\":1,\"failed\":0},\"hits\":" +
                "{\"total\":1,\"max_score\":1.0,\"hits\":" +
                "[{\"_index\":\"cmput301f15t12\",\"_type\":\"testing\"," +
                "\"_id\":\"2\",\"_score\":1.0,\"_source\":{\"val\":2}}]}}");

        connectionManager.remove("testing/", 1);
        connectionManager.remove("testing/", 2);
        connectionManager.remove("testing/", 3);
    }

    public void testGet() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        Integer id = 0xdeadbeef;
        String path = "testing/";
        String json = "{\"Name\":\"Name1\"}";
        try {
            connectionManager.put(path, id, json);
        } catch (IOException e) {
            fail();
        }

        String value = connectionManager.get(id);
        assertEquals(value, "{\n" +
                "\"_index\":\"cmput301f15t12\",\n" +
                "\"_type\":\"testing\",\n" +
                "\"_id\":\"3735928559\",\n" +
                "\"_version\":1,\n" +
                "\"found\":true,\n" +
                "\"_source\":{\"Name\":\"Name1\"}\n" +
                "}");

        connectionManager.remove("testing/", id);
    }

    public void testRemove() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        Integer id = 0xdeadbeef;
        String path = "testing/" + id.toString();
        String json = "{\"Name\":\"Name1\"}";
        try {
            connectionManager.put(path, json);
        } catch (IOException e) {
            fail();
        }

        connectionManager.remove("testing/", id);
        String value = connectionManager.get("testing/", id);
        assertEquals(value, "{\"_index\":\"cmput301f15t12\",\"_type\":\"testing\",\"_id\":\"1\",\"found\":false}");
    }
}
