package com.example.jacob.mybrary;

import android.test.AndroidTestCase;

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
        final CountDownLatch signal1 = new CountDownLatch(1);
        final CountDownLatch signal2 = new CountDownLatch(1);
        final CountDownLatch signal3 = new CountDownLatch(1);
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        String path = "testing/deadbeef";
        String json = "{\"Name\":\"Name1\"}";

        connectionManager.put(path, json, new NetworkResultsHandler() {
            @Override
            public void run(String result) {
                signal1.countDown();
            }
        });

        try {
            signal1.await();
        } catch (InterruptedException e) {
            fail();
        }

        connectionManager.get(path, new NetworkResultsHandler() {
            @Override
            public void run(String result) {
                assertTrue(result.contains("\"_type\":\"testing\""));
                assertTrue(result.contains("\"_id\":\"deadbeef\""));
                assertTrue(result.contains("\"found\":true"));
                assertTrue(result.contains("\"_source\":{\"Name\":\"Name1\"}"));
                signal2.countDown();
            }
        });

        try {
            signal2.await();
        } catch (InterruptedException e) {
            fail();
        }

        connectionManager.remove(path, new NetworkResultsHandler() {
            @Override
            public void run(String result) {
                signal3.countDown();
            }
        });

        try {
            signal3.await();
        } catch (InterruptedException e) {
            fail();
        }
    }

    public void testQuery() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        
        connectionManager.put("testing/1", "{\"val\":1}");
        connectionManager.put("testing/2", "{\"val\":2}");
        connectionManager.put("testing/3", "{\"val\":3}");

        String query = "{\"query\":{\"query_string\":{\"value\":\"2\"}}}";
        String value = connectionManager.query("testing/", query);
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
        final CountDownLatch signal1 = new CountDownLatch(1);
        final CountDownLatch signal2 = new CountDownLatch(1);
        final CountDownLatch signal3 = new CountDownLatch(1);
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        String path = "testing/deadbeef";
        String json = "{\"Name\":\"Name1\"}";

        connectionManager.put(path, json, new NetworkResultsHandler() {
            @Override
            public void run(String result) {
                signal1.countDown();
            }
        });

        try {
            signal1.await();
        } catch (InterruptedException e) {
            fail();
        }

        connectionManager.get(path, new NetworkResultsHandler() {
            @Override
            public void run(String result) {
                assertTrue(result.contains("\"_type\":\"testing\""));
                assertTrue(result.contains("\"_id\":\"deadbeef\""));
                assertTrue(result.contains("\"found\":true"));
                assertTrue(result.contains("\"_source\":{\"Name\":\"Name1\"}"));
                signal2.countDown();
            }
        });

        try {
            signal2.await();
        } catch (InterruptedException e) {
            fail();
        }

        connectionManager.remove(path, new NetworkResultsHandler() {
            @Override
            public void run(String result) {
                signal3.countDown();
            }
        });

        try {
            signal3.await();
        } catch (InterruptedException e) {
            fail();
        }
    }

    public void testRemove() {
        final CountDownLatch signal1 = new CountDownLatch(1);
        final CountDownLatch signal2 = new CountDownLatch(1);
        final CountDownLatch signal3 = new CountDownLatch(1);
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        String path = "testing/deadbeef";
        String json = "{\"Name\":\"Name1\"}";
        connectionManager.put(path, json, new NetworkResultsHandler() {
            @Override
            public void run(String result) {
                signal1.countDown();
            }
        });

        try {
            signal1.await();
        } catch (InterruptedException e) {
            fail();
        }

        connectionManager.remove(path, new NetworkResultsHandler() {
            @Override
            public void run(String result) {
                signal2.countDown();
            }
        });

        try {
            signal2.await();
        } catch (InterruptedException e) {
            fail();
        }

        connectionManager.get(path, new NetworkResultsHandler() {
            @Override
            public void run(String result) {
                assertEquals(result, "{\"_index\":\"cmput301f15t12\",\"_type\":\"testing\",\"_id\":\"deadbeef\",\"found\":false}");
                signal3.countDown();
            }
        });

        try {
            signal3.await();
        } catch (InterruptedException e) {
            fail();
        }
    }
}
