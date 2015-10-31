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
    private URL connstr;

    private static ConnectionManager ourInstance = new ConnectionManager();

    public static ConnectionManager getInstance() {
        return ourInstance;
    }

    private ConnectionManager() {
        try {
            connstr = new URL("http://cmput301.softwareprocess.es:8080/cmput301f15t12/");
        } catch (MalformedURLException e) {
            // Hard-coded URL should never be malformed.
            throw new RuntimeException(e);
        }
    }

    public boolean put(String path, String json) throws IOException {
        connection = (HttpURLConnection) connstr.openConnection();

    }
}
