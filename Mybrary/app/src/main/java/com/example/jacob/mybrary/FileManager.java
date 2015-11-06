package com.example.jacob.mybrary;

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

    /**
     * Required for Singleton
     * @return
     */
    public static FileManager getInstance(){
        if(instance == null){
            instance = new FileManager();
        }
        return instance;
    }

    /**
     * Saving our json element to a specified file
     * @param path name of file being saved too
     * @param content json element being saved
     */
    public void saveJson(String path, String content) {

        try {
            FileOutputStream fos = new FileOutputStream(path);
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

    public String readFile(String path){

        

    }

}
