package com.example.jacob.mybrary;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Dominic on 2015-10-31.
 */
public class ConnectionManager {
    private HttpURLConnection connection;
    private String connstr;

    private static ConnectionManager ourInstance = new ConnectionManager();

    public static ConnectionManager getInstance() {
        return ourInstance;
    }

    private ConnectionManager() {
        connstr = "http://cmput301.softwareprocess.es:8080/cmput301f15t12/";
    }

    public void put(String path, Integer id, String json) throws IOException {
        URL url = new URL(connstr + path + id.toString());
        connection = (HttpURLConnection) url.openConnection();
    }
}
