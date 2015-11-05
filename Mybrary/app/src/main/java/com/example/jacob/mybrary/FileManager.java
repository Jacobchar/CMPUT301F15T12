package com.example.jacob.mybrary;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Jacob on 2015-11-04.
 */
public class FileManager {

    private static FileManager instance = null;


    protected FileManager(){}

    public static FileManager getInstance(){
        if(instance == null){
            instance = new FileManager();
        }
        return instance;
    }

    public void saveJson(String path, Context content) {

        try {
            FileOutputStream fos = content.openFileOutput(path, Context.MODE_PRIVATE);
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(this.getInstance(), output);
            output.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //public String readFile(String path){

    //}

}
