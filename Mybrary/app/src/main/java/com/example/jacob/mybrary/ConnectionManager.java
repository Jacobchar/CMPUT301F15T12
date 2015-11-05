package com.example.jacob.mybrary;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by Dominic on 2015-10-31.
 *
 * Low level http manager. Allows json objects to be put and retrieved from the server at a
 * specific path. The path should not have a leading slash and should include the id at the end
 * when appropriate.
 */
public class ConnectionManager {
    private final String connstr;

    private static ConnectionManager ourInstance = new ConnectionManager();

    public static ConnectionManager getInstance() {
        return ourInstance;
    }

    private ConnectionManager() {
        connstr = "http://cmput301.softwareprocess.es:8080/cmput301f15t12/";
    }

    public String put(String path, String json) throws IOException{
        // TODO: Validate input strings
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(connstr + path)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String get(String path) throws IOException {
        // TODO: Validate input strings
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(connstr + path)
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    public String query(String path, String query) throws IOException{
        // TODO: Validate input strings
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(JSON, query);
        Request request = new Request.Builder()
                .url(connstr + path + "_search")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    public String remove(String path) throws IOException{
        // TODO: Validate input strings
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(connstr + path)
                .delete()
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    // Source: http://developer.android.com/training/basics/network-ops/connecting.html
    // Check for connectivity before using any other methods
    public boolean checkNetwork(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
