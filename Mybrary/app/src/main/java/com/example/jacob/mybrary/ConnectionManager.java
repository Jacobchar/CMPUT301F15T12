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

    public void put(String path, String json, NetworkResultsHandler onComplete) {
        // TODO: Validate input strings
        PutTask putTask = new PutTask(onComplete);
        putTask.execute(path, json);
    }

    public void get(String path, NetworkResultsHandler onComplete) {
        // TODO: Validate input strings
        GetTask getTask = new GetTask(onComplete);
        getTask.execute(path);
    }

    public void query(String path, String query, NetworkResultsHandler onComplete) {

    }

    public void remove(String path, NetworkResultsHandler onComplete) {
        // TODO: Validate input strings
        RemoveTask removeTask = new RemoveTask(onComplete);
        removeTask.execute(path);
    }

    // Source: http://developer.android.com/training/basics/network-ops/connecting.html
    // Check for connectivity before using any other methods
    public boolean checkNetwork(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    private class PutTask extends AsyncTask<String, Void, String> {
        Throwable ex;
        NetworkResultsHandler onComplete;

        public PutTask(NetworkResultsHandler handler) {
            onComplete = handler;
        }

        protected String doInBackground(String... params) {
            // params[0] == path
            // params[1] == json

            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            try {
                RequestBody requestBody = RequestBody.create(JSON, params[1]);
                Request request = new Request.Builder()
                        .url(connstr + params[0])
                        .post(requestBody)
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                ex = e;
                return "";
            }
        }

        protected void onPostExecute(String result) {
            if (ex == null) {
                onComplete.run(result);
            } else {
                throw new RuntimeException(ex);
            }
        }
    }

    private class GetTask extends AsyncTask<String, Void, String> {
        Throwable ex;
        NetworkResultsHandler onComplete;

        public GetTask(NetworkResultsHandler handler) {
            onComplete = handler;
        }

        protected String doInBackground(String... params) {
            // params[0] == path

            OkHttpClient client = new OkHttpClient();
            try {
                Request request = new Request.Builder()
                        .url(connstr + params[0])
                        .build();
                Response response = client.newCall(request).execute();

                return response.body().string();
            } catch (Exception e) {
                ex = e;
                return "";
            }
        }

        protected void onPostExecute(String result) {
            if (ex == null) {
                onComplete.run(result);
            } else {
                throw new RuntimeException(ex);
            }
        }
    }

    private class RemoveTask extends AsyncTask<String, Void, String> {
        Throwable ex;
        NetworkResultsHandler onComplete;

        public RemoveTask(NetworkResultsHandler handler) {
            onComplete = handler;
        }

        // Source: http://stackoverflow.com/a/1051105
        protected String doInBackground(String... params) {
            // params[0] == path

            OkHttpClient client = new OkHttpClient();
            try {
                Request request = new Request.Builder()
                        .url(connstr + params[0])
                        .delete()
                        .build();
                Response response = client.newCall(request).execute();

                return response.body().string();
            } catch (Exception e) {
                ex = e;
                return "";
            }
        }

        protected void onPostExecute(String result) {
            if (ex == null) {
                onComplete.run(result);
            } else {
                throw new RuntimeException(ex);
            }
        }
    }
}
