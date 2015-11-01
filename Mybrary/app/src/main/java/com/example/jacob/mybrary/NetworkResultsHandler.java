package com.example.jacob.mybrary;

/**
 * Created by Dominic on 2015-11-01.
 *
 * This class can be used to define behaviour of a network operation upon completion.
 * The string 'result' will contain the text returned from the operation.
 */
public abstract class NetworkResultsHandler {
    public abstract void run(String result);
}
