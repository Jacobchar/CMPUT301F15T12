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
    private Boolean isConnected = false;

    private static ConnectionManager ourInstance = new ConnectionManager();

    /**
     * Gets the instance of the connection manager.
     * @return Returns the instance of the connection manager.
     */
    public static ConnectionManager getInstance() {
        return ourInstance;
    }

    private ConnectionManager() {
        connstr = "http://cmput301.softwareprocess.es:8080/cmput301f15t12/";
    }

    /**
     * Sends a post request to the server with the given data.
     * @param path Location on the server for the object to be stored.
     * @param json Json representation of the object to be stored.
     * @return Returns a string containing the result of the operation.
     * @throws IOException Thrown if an error occured during the transmission
     */
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

    /**
     * Retrieves the object from the server at the given location.
     * @param path Location on the server of the object to be retrieved.
     * @return Returns a string containing the result of the operation.
     * @throws IOException Thrown if an error occured during the transmission.
     */
    public String get(String path) throws IOException {
        // TODO: Validate input strings
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(connstr + path)
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    /**
     * Searches the given location based on the given query.
     * @param path Location on the server to be searched.
     * @param query Json query to be sent to the server.
     * @return Returns a string containing the result of the operation.
     * @throws IOException Thrown if an error occured during the transmission.
     */
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

    /**
     * Removes a given object from the server. CAUTION: USE THIS CAREFULLY, IT IS POSSIBLE TO DELETE
     * AN ENTIRE FOLDER.
     * @param path Location of the object to be deleted.
     * @return Returns a string containing the result of the operation.
     * @throws IOException Thrown if an error occured during the transmission.
     */
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

    /**
     * This method should be called before any other network functionality should be used.
     *
     * Source: http://developer.android.com/training/basics/network-ops/connecting.html
     * @param context Context of the app
     * @return Returns a boolean indicating whether a network connection exists.
     */
    public boolean updateConnectivity(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        isConnected = networkInfo != null && networkInfo.isConnected();
        return isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
