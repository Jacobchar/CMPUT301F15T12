package com.example.jacob.mybrary;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
    private HttpURLConnection connection;
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

    // Source: http://stackoverflow.com/a/8376153
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
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
            String response = "";
            try {
                URL url = new URL(connstr + params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setChunkedStreamingMode(0);

                OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
                outputStream.write(params[1].getBytes());

                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                response = readStream(inputStream);
            } catch (FileNotFoundException fnfe) {
                int rc = 0;
                try {
                    rc = connection.getResponseCode();
                } catch (IOException ioe) {
                    ex = fnfe;
                }
                ex = new RuntimeException(Integer.toString(rc));
            } catch (Exception e) {
                ex = e;
            } finally {
                connection.disconnect();
            }
            return response;
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
            String response = "";
            try {
                URL url = new URL(connstr + params[0]);
                connection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                response = readStream(inputStream);
            } catch (FileNotFoundException fnfe) {
                int rc = 0;
                try {
                    rc = connection.getResponseCode();
                } catch (IOException ioe) {
                    ex = fnfe;
                }
                ex = new RuntimeException(Integer.toString(rc));
            } catch (Exception e) {
                ex = e;
            } finally {
                connection.disconnect();
            }
            return response;
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
            String response = "";
            try {
                URL url = new URL(connstr + params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded" );
                connection.setRequestMethod("DELETE");
                connection.connect();

                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                response = readStream(inputStream);
            } catch (FileNotFoundException fnfe) {
                int rc = 0;
                try {
                    rc = connection.getResponseCode();
                } catch (IOException ioe) {
                    ex = fnfe;
                }
                ex = new RuntimeException(Integer.toString(rc));
            } catch (Exception e) {
                ex = e;
            } finally {
                connection.disconnect();
            }
            return response;
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
