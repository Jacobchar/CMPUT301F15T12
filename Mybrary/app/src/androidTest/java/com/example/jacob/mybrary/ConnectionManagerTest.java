package com.example.jacob.mybrary;

import android.test.AndroidTestCase;

import java.io.IOException;

/**
 * Created by Dominic on 2015-10-31.
 *
 * Simple tests for ConnectionManager. More tests should be added for better coverage.
 */
public class ConnectionManagerTest extends AndroidTestCase {
    public void testPut() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        String path = "testing/deadbeef";
        String json = "{\"Name\":\"Name1\"}";

        connectionManager.put(path, json, new NetworkResultsHandler() {
            @Override
            public void run(String result) {

            }
        });

        connectionManager.get(path, new NetworkResultsHandler() {
            @Override
            public void run(String result) {
                assertEquals(result, "{\"_index\":\"cmput301f15t12\"," +
                        "\"_type\":\"testing\"," +
                        "\"_id\":\"3735928559\"," +
                        "\"_version\":1," +
                        "\"found\":true," +
                        "\"_source\":{\"Name\":\"Name1\"}" +
                        "}");
            }
        });

        connectionManager.remove(path, new NetworkResultsHandler() {
            @Override
            public void run(String result) {

            }
        });
    }

//    public void testQuery() {
//        ConnectionManager connectionManager = ConnectionManager.getInstance();
//
//        try {
//            connectionManager.put("testing/", 1, "{\"val\":1}");
//            connectionManager.put("testing/", 2, "{\"val\":2}");
//            connectionManager.put("testing/", 3, "{\"val\":3}");
//        } catch (IOException e) {
//            fail();
//        }
//
//        String query = "{\"query\":{\"query_string\":{\"value\":\"2\"}}}";
//        String value = connectionManager.query("testing/", query);
//        assertEquals(value, "{\"took\":1,\"timed_out\":false,\"_shards\":" +
//                "{\"total\":1,\"successful\":1,\"failed\":0},\"hits\":" +
//                "{\"total\":1,\"max_score\":1.0,\"hits\":" +
//                "[{\"_index\":\"cmput301f15t12\",\"_type\":\"testing\"," +
//                "\"_id\":\"2\",\"_score\":1.0,\"_source\":{\"val\":2}}]}}");
//
//        connectionManager.remove("testing/", 1);
//        connectionManager.remove("testing/", 2);
//        connectionManager.remove("testing/", 3);
//    }

    public void testGet() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        String path = "testing/deadbeef";
        String json = "{\"Name\":\"Name1\"}";

        connectionManager.put(path, json, new NetworkResultsHandler() {
            @Override
            public void run(String result) {

            }
        });

        connectionManager.get(path, new NetworkResultsHandler() {
            @Override
            public void run(String result) {
                assertEquals(result, "{\n" +
                        "\"_index\":\"cmput301f15t12\",\n" +
                        "\"_type\":\"testing\",\n" +
                        "\"_id\":\"3735928559\",\n" +
                        "\"_version\":1,\n" +
                        "\"found\":true,\n" +
                        "\"_source\":{\"Name\":\"Name1\"}\n" +
                        "}");
            }
        });

        connectionManager.remove(path, new NetworkResultsHandler() {
            @Override
            public void run(String result) {

            }
        });
    }

    public void testRemove() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        String path = "testing/deadbeef";
        String json = "{\"Name\":\"Name1\"}";
        connectionManager.put(path, json, new NetworkResultsHandler() {
            @Override
            public void run(String result) {

            }
        });

        connectionManager.remove(path, new NetworkResultsHandler() {
            @Override
            public void run(String result) {

            }
        });
        connectionManager.get(path, new NetworkResultsHandler() {
            @Override
            public void run(String result) {
                assertEquals(result, "{\"_index\":\"cmput301f15t12\",\"_type\":\"testing\",\"_id\":\"1\",\"found\":false}");
            }
        });
    }
}
