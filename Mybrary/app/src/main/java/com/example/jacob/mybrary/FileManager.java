package com.example.jacob.mybrary;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * FileManager handles interactions with the file system and simplifies the process of accessing
 * files.
 *
 * Created by Jacob on 2015-11-04.
 */
public class FileManager {
    private static String appFolderName = "/data/data/com.example.jacob.mybrary/";

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
        File file = new File(appFolderName + path);
        //Create any parent folders if needed
        file.getParentFile().mkdirs();
        //Only creates a file if one doesn't exist
        file.createNewFile();

        FileWriter writer = new FileWriter(appFolderName + path);
        BufferedWriter out = new BufferedWriter(writer);
        out.write(content);
        out.close();
        writer.close();
    }

    /**
     * Loads the file's json element
     * @param path the path (relative to the app directory) of the file to be loaded.
     * @return Returns the string found in the given file.
     */
    public String readFile(String path) throws IOException {
        StringBuilder rv = new StringBuilder();
        String line;
        FileReader reader = new FileReader(appFolderName + path);
        BufferedReader in = new BufferedReader(reader);
        while ((line = in.readLine()) != null) {
            rv.append(line);
        }
        in.close();
        reader.close();
        return rv.toString();
    }

    /**
     * Loads the json string from a given file.
     * @param file File object representing the desired file.
     * @return Returns a string containing the contents of the file.
     * @throws IOException Thrown if an error occured while reading the file.
     */
    public String readFile(File file) throws IOException {
        StringBuilder rv = new StringBuilder();
        String line;
        FileReader reader = new FileReader(file);
        BufferedReader in = new BufferedReader(reader);
        while ((line = in.readLine()) != null) {
            rv.append(line);
        }
        in.close();
        reader.close();
        return rv.toString();
    }

    /**
     * Removes a specified file from the file system.
     * @param path Relative path of the file to be removed.
     * @return Returns a boolean indicating if the operation was successful.
     * @throws IOException
     */
    public Boolean removeFile(String path) throws IOException {
        File file = new File(appFolderName + path);
        return file.delete();
    }

    /**
     * Gets the path to the local app folder.
     * @return Returns a string containing the local app folder path.
     */
    public String getAppFolderName() {
        return appFolderName;
    }

    /**
     * Checks if a file exists at the given path relative to the local app folder.
     * @param path String representing the relative path to the desired file.
     * @return Returns a boolean indicating whether the file exists.
     */
    public Boolean fileExists(String path) {
        File file = new File(appFolderName + path);
        return file.exists();
    }
}
