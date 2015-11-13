package com.example.jacob.mybrary;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Jacob on 2015-11-04.
 */
public class FileManager {

    private static FileManager instance = null;


    protected FileManager(){}

    /**
     * Required for Singleton
     * @return Returns the current instance.
     */
    public static FileManager getInstance(){
        if(instance == null){
            instance = new FileManager();
        }
        return instance;
    }

    /**
     * Saving our json element to a specified file
     * @param path name of file being saved to
     * @param content json element being saved
     */
    public void saveJson(String path, String content) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        BufferedOutputStream out = new BufferedOutputStream(fos);
        out.write(content.getBytes());
        out.close();
        fos.close();
    }

    /**
     * Loads the file's json element
     * @param path the path of the file to be loaded.
     * @return Returns the string found in the given file.
     */
    public String readFile(String path) throws IOException {
        StringBuilder rv = new StringBuilder();
        String line;

        FileInputStream fis = new FileInputStream(path);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        while ((line = in.readLine()) != null) {
            rv.append(line);
        }
        return rv.toString();
    }

}
