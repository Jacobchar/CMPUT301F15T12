package com.example.jacob.mybrary;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Jacob on 2015-11-04.
 */
public class GsonManager {

    private String bookFile = "Books";
    private ArrayList<Book> newBook = new ArrayList<>();

    public void saveBook(Context context){
        //The following code reflects what we did with the lonely twitter lab
        try{
            FileOutputStream fos = context.openFileOutput(bookFile, Context.MODE_PRIVATE);
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(this.newBook, output);
            output.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
