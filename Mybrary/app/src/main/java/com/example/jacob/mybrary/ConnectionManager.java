package com.example.jacob.mybrary;

import java.net.HttpURLConnection;

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
    }

    public void Connect(String connstr) {

    }

    public void setConnectionString(String connstr) {
        this.connstr = connstr;
    }
}
