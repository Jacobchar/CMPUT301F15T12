package com.example.jacob.mybrary;


import com.google.gson.Gson;

import java.lang.reflect.Type;
/*
Copyright (C) 2015  Ben Schreiber , David Ross,Dominic Trottier,
                    Jake Charlebois, Mason Strong, Victoria Hessdorfer

        This file is part of Mybrary.

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * Simple class for managing Gson actions. It is currently a simple container class but more complex
 * functionality may be needed in the future.
 *
 * Created by Jacob on 2015-11-04.
 */
public class GsonManager {

    private static GsonManager instance = null;

    private GsonManager(){}

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
