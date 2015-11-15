package com.example.jacob.mybrary;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Simple class for managing Gson actions. It is currently a simple container class but more complex
 * functionality may be needed in the future.
 *
 * Created by Jacob on 2015-11-04.
 */
public class GsonManager {

    private static GsonManager instance = null;

    protected GsonManager(){}

    /**
     * Creating as a Singleton
     * @return Returns the singleton instance
     */
    public static GsonManager getInstance() {
        if(instance == null) {
            instance = new GsonManager();
        }
        return instance;
    }

    private Gson gson = new Gson();

    /**
     * converts an object to a Json string
     * @param object the object to be converted
     * @return Returns a JSON string representation of the object.
     */
    public String toJson(Object object){
        return gson.toJson(object);
    }

    /**
     * Converts a JSON string into an object.
     * @param json String representation of the object to be converted.
     * @param classOfT Class of the object to be returned.
     * @param <T> Type to return the object as.
     * @return Returns an object of the specified type as described in the JSON string.
     */
    public <T> T fromJson(String json, Class<T> classOfT) { return gson.fromJson(json, classOfT); }

    /**
     * Converts a JSON element into a generic object.
     * @param json String representation of the object to be converted.
     * @param typeOfT Type of the object to be returned.
     * @param <T> Type to return the object as.
     * @return Returns an object of the specified type as described in the JSON string.
     */
    public <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }
}
