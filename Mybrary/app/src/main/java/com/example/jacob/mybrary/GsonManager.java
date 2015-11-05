package com.example.jacob.mybrary;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Jacob on 2015-11-04.
 */
public class GsonManager {

    private static GsonManager instance = null;

    protected GsonManager(){}

    public static GsonManager getInstance() {
        if(instance == null) {
            instance = new GsonManager();
        }
        return instance;
    }

    // Note: Methods following methods must be protected for singleton to work

    private String bookFile = "Books";
    private ArrayList<Book> library = new ArrayList<>();

    protected void saveBook(Context context){
        //The following code reflects what we did with the lonely twitter lab
        try{
            FileOutputStream fos = context.openFileOutput(bookFile, Context.MODE_PRIVATE);
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(this.library, output);
            output.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected void loadLibrary(Context context){
        //The following code reflects what we did with the lonely twitter lab
        try {
            FileInputStream fis = context.openFileInput(bookFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html, 2015-09-23
            Type hasMapType = new TypeToken<ArrayList<Long>>() {}.getType();
            this.library = gson.fromJson(in, hasMapType);

        } catch (FileNotFoundException e) {
            this.library = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
