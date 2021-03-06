package com.example.jacob.mybrary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
 * This data management abstraction offers a simplified way of retrieving data. The data may either
 * be remote or cached locally.
 *
 * (Sample partial match query: "{\"query\":{\"query_string\":{\"analyze_wildcard\":true,\"default_field\":\"name\",\"query\":\"Vic*\"}}}")
 *
 * Created by Dominic on 2015-11-04.
 */
public class DataManager {

    private static DataManager ourInstance = new DataManager();

    public static DataManager getInstance() { return ourInstance; }

    private DataManager() {

    }

    /**
     * Loads the saved LocalUser object into the LocalUser singleton.
     * @throws IOException Thrown when there was an error reading the file (including FileNotFoundException).
     */
    public void loadLocalUser() throws IOException {
        String userjson = FileManager.getInstance().readFile("localUser.json");
        LocalUser user = GsonManager.getInstance().fromJson(userjson, LocalUser.class);
        LocalUser.setInstance(user);
    }

    /**
     * Saves the LocalUser object to a file
     * @throws IOException Thrown when there was an error writing to the file.
     */
    public void saveLocalUser() throws IOException {
        LocalUser user = LocalUser.getInstance();
        String userjson = GsonManager.getInstance().toJson(user);
        if (ConnectionManager.getInstance().isConnected()) {
            pushOfflineItems();
            storeUser(user);
        }
        FileManager.getInstance().saveJson("localUser.json", userjson);
    }

    /**
     * Pushes all items that were made while offline onto the server.
     * @throws IOException Thrown if an error occurred during communication.
     */
    public void pushOfflineItems() throws IOException {
        if (ConnectionManager.getInstance().isConnected()) {
            FileManager fm = FileManager.getInstance();
            ConnectionManager cm = ConnectionManager.getInstance();

            //Go through the offline folders and push any items found.
            File bookDir = new File(fm.getAppFolderName() + "Offline/Books");
            if (bookDir.exists()) {
                for (File book : bookDir.listFiles()) {
                    String id = book.getName();
                    String json = fm.readFile(book);
                    cm.put("Books/" + id, json);
                    book.delete();
                }
            }

            File tradeDir = new File(fm.getAppFolderName() + "Offline/Trades");
            if (tradeDir.exists()) {
                for (File trade : tradeDir.listFiles()) {
                    String id = trade.getName();
                    String json = fm.readFile(trade);
                    cm.put("Trades/" + id, json);
                    trade.delete();
                }
            }

            File photoDir = new File(fm.getAppFolderName() + "Offline/Photos");
            if (photoDir.exists()) {
                for (File photo : photoDir.listFiles()) {
                    String id = photo.getName();
                    String json = fm.readFile(photo);
                    cm.put("Photos/" + id, json);
                    photo.delete();
                }
            }
        }
    }

    //======================= BOOKS ==============================
    /**
     * Stores a book on the server and caches it for later if there's no network connectivity.
     * @param book The book to be stored
     * @return Returns a boolean value indicating whether the book was stored on the server or not.
     * @throws IOException Thrown if an error occurred during communication.
     */
    public Boolean storeBook(Book book) throws IOException {
        FileManager fm = FileManager.getInstance();
        String result = "";
        String path = "Books/" + book.getItemID().toString();
        String json = GsonManager.getInstance().toJson(book);
        if (ConnectionManager.getInstance().isConnected()) {
            pushOfflineItems();
            result = ConnectionManager.getInstance().put(path, json);
        } else {
            //Store for later
            fm.saveJson("Offline/Books/" + book.getItemID().toString(), json);
        }
        fm.saveJson("Books/" + book.getItemID().toString(), json);

        //Every time a book is added, the local user has changed.
        saveLocalUser();

        //TODO: Add better verification.
        return result.contains("{\"_index\":\"cmput301f15t12\",\"_type\":\"Books\",\"_id\":\"" + book.getItemID().toString());
    }

    /**
     * Removes a book from the server and the local cache.
     * @param id Identifier of the book to be removed.
     * @return Returns a boolean value indicating whether the request was successful or not.
     * @throws IOException Thrown if an error occurred during communication.
     */
    public Boolean removeBook(String id) throws IOException {
        FileManager.getInstance().removeFile("Books/" + id);

        //Every time a book is deleted, the local user has changed.
        saveLocalUser();

        if (ConnectionManager.getInstance().isConnected()) {
            pushOfflineItems();
            String result = ConnectionManager.getInstance().remove("Books/" + id);
            return result.contains("{\"found\":true,\"_index\":\"cmput301f15t12\",\"_type\":\"Books\",\"_id\":\"" + id);
        } else {
            return false;
        }
    }

    /**
     * Retrieves a stored book from the server or locally if there is no connection.
     * @param id Identifier of the book to be retrieved.
     * @return Returns the Book object stored on the server.
     * @throws IOException Thrown if an error occurred during communication.
     * @throws JSONException Thrown if the JSON was malformed (Possibly if an older version of an object is retrieved).
     */
    public Book retrieveBook(String id) throws IOException, JSONException {
        if (ConnectionManager.getInstance().isConnected()) {
            pushOfflineItems();
            String result = ConnectionManager.getInstance().get("Books/" + id);
            JSONObject obj = new JSONObject(result);
            String bookjson = obj.getJSONObject("_source").toString();
            FileManager.getInstance().saveJson("Books/" + id, bookjson);
            return GsonManager.getInstance().fromJson(bookjson, Book.class);
        } else {
            String bookjson = FileManager.getInstance().readFile("Books/" + id);
            return GsonManager.getInstance().fromJson(bookjson, Book.class);
        }
    }

    /**
     * Searches books for a given quality. Elasticsearch allows for various search methods.
     * @param query JSON based query to be searched for.
     * @return Returns an arraylist of Book objects that match the given query.
     * @throws IOException Thrown if an error occurred during communication.
     * @throws JSONException Thrown if the JSON was malformed (Possibly if an older version of an object is retrieved).
     */
    public ArrayList<Book> searchBooks(String query) throws IOException, JSONException {
        ArrayList<Book> rv = new ArrayList<>();

        String result = ConnectionManager.getInstance().query("Books/", query);
        JSONObject obj = new JSONObject(result);
        JSONArray jsonArray = obj.getJSONObject("hits").getJSONArray("hits");
        //This makes me feel so dirty. Why doesn't JSONArray support foreach?
        for (int i = 0; i < jsonArray.length(); i++) {
            String bookjson = jsonArray.getJSONObject(i).getJSONObject("_source").toString();
            rv.add(GsonManager.getInstance().fromJson(bookjson, Book.class));
        }
        return rv;
    }

    //======================= USERS ==============================
    /**
     * Stores a user on the server and caches it for later if there's no network connectivity.
     * @param user The user to be stored.
     * @return Returns a boolean value indicating whether the request was successful or not
     * @throws IOException Thrown if an error occurred during communication.
     */
    public Boolean storeUser(User user) throws IOException {
        FileManager fm = FileManager.getInstance();
        String result = "";
        String path = "Users/" + user.getUUID().toString();
        String json = GsonManager.getInstance().toJson(user);
        if (ConnectionManager.getInstance().isConnected()) {
            pushOfflineItems();
            result = ConnectionManager.getInstance().put(path, json);
        }
        fm.saveJson("Users/" + user.getUUID().toString(), json);
        //TODO: Add better verification.
        return result.contains("{\"_index\":\"cmput301f15t12\",\"_type\":\"Users\",\"_id\":\"" + user.getUUID().toString());
    }

    /**
     * Removes a user from the server and the local cache.
     * @param id Identifier of the user to be removed.
     * @return Returns a boolean value indicating whether the request was successful or not.
     * @throws IOException Thrown if an error occurred during communication.
     */
    public Boolean removeUser(String id) throws IOException {
        FileManager.getInstance().removeFile("Users/" + id);
        if (ConnectionManager.getInstance().isConnected()) {
            pushOfflineItems();
            String result = ConnectionManager.getInstance().remove("Users/" + id);
            return result.contains("{\"found\":true,\"_index\":\"cmput301f15t12\",\"_type\":\"Users\",\"_id\":\"" + id);
        } else {
            return false;
        }
    }

    /**
     * Retrieves a stored user from the server or locally if there is no connection.
     * @param id Identifier of the user to be retrieved.
     * @return Returns the User object stored on the server.
     * @throws IOException Thrown if an error occurred during communication.
     * @throws JSONException Thrown if the JSON was malformed (Possibly if an older version of an object is retrieved).
     */
    public User retrieveUser(String id) throws IOException, JSONException {
        if (ConnectionManager.getInstance().isConnected()) {
            pushOfflineItems();
            String result = ConnectionManager.getInstance().get("Users/" + id);
            JSONObject obj = new JSONObject(result);
            String userjson = obj.getJSONObject("_source").toString();
            FileManager.getInstance().saveJson("Users/" + id, userjson);
            return GsonManager.getInstance().fromJson(userjson, User.class);
        } else {
            String userjson = FileManager.getInstance().readFile("Users/" + id);
            return GsonManager.getInstance().fromJson(userjson, User.class);
        }
    }

    /**
     * Searches users for a given quality. Elasticsearch allows for various search methods.
     * @param query JSON based query to be searched for.
     * @return Returns an arraylist of User objects that match the given query.
     * @throws IOException Thrown if an error occurred during communication.
     * @throws JSONException Thrown if the JSON was malformed (Possibly if an older version of an object is retrieved).
     */
    public ArrayList<User> searchUsers(String query) throws IOException, JSONException {
        ArrayList<User> rv = new ArrayList<>();

        String result = ConnectionManager.getInstance().query("Users/", query);
        JSONObject obj = new JSONObject(result);
        JSONArray jsonArray = obj.getJSONObject("hits").getJSONArray("hits");
        //This makes me feel so dirty. Why doesn't JSONArray support foreach?
        for (int i = 0; i < jsonArray.length(); i++) {
            String userjson = jsonArray.getJSONObject(i).getJSONObject("_source").toString();
            rv.add(GsonManager.getInstance().fromJson(userjson, User.class));
        }
        return rv;
    }

    //======================= TRADES ==============================
    /**
     * Stores a trade on the server.
     * @param trade The trade to be stored.
     * @return Returns a boolean value indicating whether the request was successful or not
     * @throws IOException Thrown if an error occurred during communication.
     */
    public Boolean storeTrade(Trade trade) throws IOException {
        FileManager fm = FileManager.getInstance();
        String result = "";
        String path = "Trades/" + trade.getTradeID().toString();
        String json = GsonManager.getInstance().toJson(trade);
        if (ConnectionManager.getInstance().isConnected()) {
            pushOfflineItems();
            result = ConnectionManager.getInstance().put(path, json);
        } else {
            //Store for later
            fm.saveJson("Offline/Trades/" + trade.getTradeID().toString(), json);
        }
        fm.saveJson("Trades/" + trade.getTradeID().toString(), json);

        //Every time a trade is added, the local user has changed.
        saveLocalUser();

        //TODO: Add better verification.
        return result.contains("{\"_index\":\"cmput301f15t12\",\"_type\":\"Trades\",\"_id\":\"" + trade.getTradeID().toString());
    }

    /**
     * Removes a trade from the server.
     * @param id Identifier of the trade to be removed.
     * @return Returns a boolean value indicating whether the request was successful or not.
     * @throws IOException Thrown if an error occurred during communication.
     */
    public Boolean removeTrade(String id) throws IOException {
        FileManager.getInstance().removeFile("Trades/" + id);

        //Every time a trade is deleted, the local user has changed.
        saveLocalUser();

        if (ConnectionManager.getInstance().isConnected()) {
            pushOfflineItems();
            String result = ConnectionManager.getInstance().remove("Trades/" + id);
            return result.contains("{\"found\":true,\"_index\":\"cmput301f15t12\",\"_type\":\"Trades\",\"_id\":\"" + id);
        } else {
            return false;
        }
    }

    /**
     * Retrieves a stored trade from the server.
     * @param id Identifier of the trade to be retrieved.
     * @return Returns the Trade object stored on the server.
     * @throws IOException Thrown if an error occurred during communication.
     * @throws JSONException Thrown if the JSON was malformed (Possibly if an older version of an object is retrieved).
     */
    public Trade retrieveTrade(String id) throws IOException, JSONException {
        if (ConnectionManager.getInstance().isConnected()) {
            pushOfflineItems();
            String result = ConnectionManager.getInstance().get("Trades/" + id);
            JSONObject obj = new JSONObject(result);
            String tradejson = obj.getJSONObject("_source").toString();
            FileManager.getInstance().saveJson("Trades/" + id, tradejson);
            return GsonManager.getInstance().fromJson(tradejson, Trade.class);
        } else {
            String tradejson = FileManager.getInstance().readFile("Trades/" + id);
            return GsonManager.getInstance().fromJson(tradejson, Trade.class);
        }
    }

    /**
     * Searches trades for a given quality. Elasticsearch allows for various search methods.
     * @param query JSON based query to be searched for.
     * @return Returns an arraylist of Trade objects that match the given query.
     * @throws IOException Thrown if an error occurred during communication.
     * @throws JSONException Thrown if the JSON was malformed (Possibly if an older version of an object is retrieved).
     */
    public ArrayList<Trade> searchTrades(String query) throws IOException, JSONException {
        ArrayList<Trade> rv = new ArrayList<>();

        String result = ConnectionManager.getInstance().query("Trades/", query);
        JSONObject obj = new JSONObject(result);
        JSONArray jsonArray = obj.getJSONObject("hits").getJSONArray("hits");
        //This makes me feel so dirty. Why doesn't JSONArray support foreach?
        for (int i = 0; i < jsonArray.length(); i++) {
            String tradejson = jsonArray.getJSONObject(i).getJSONObject("_source").toString();
            rv.add(GsonManager.getInstance().fromJson(tradejson, Trade.class));
        }
        return rv;
    }

    //======================= PHOTOS ==============================
    /**
     * Stores a photo on the server.
     * @param photo The photo to be stored.
     * @return Returns a boolean value indicating whether the request was successful or not
     * @throws IOException Thrown if an error occurred during communication.
     */
    public Boolean storePhoto(Photo photo) throws IOException {
        FileManager fm = FileManager.getInstance();
        String result = "";
        String path = "Photos/" + photo.getPhotoID().toString();
        String json = GsonManager.getInstance().toJson(photo);
        if (ConnectionManager.getInstance().isConnected()) {
            result = ConnectionManager.getInstance().put(path, json);
        } else {
            //Store for later
            fm.saveJson("Offline/Photos/" + photo.getPhotoID().toString(), json);
        }
        fm.saveJson("Photos/" + photo.getPhotoID().toString(), json);

        //TODO: Add better verification.
        return result.contains("{\"_index\":\"cmput301f15t12\",\"_type\":\"Photos\",\"_id\":\"" + photo.getPhotoID().toString());
    }

    /**
     * Removes a photo from the server.
     * @param id Identifier of the photo to be removed.
     * @return Returns a boolean value indicating whether the request was successful or not.
     * @throws IOException Thrown if an error occurred during communication.
     */
    public Boolean removePhoto(String id) throws IOException {
        FileManager.getInstance().removeFile("Photos/" + id);
        if (ConnectionManager.getInstance().isConnected()) {
            String result = ConnectionManager.getInstance().remove("Photos/" + id);
            return result.contains("{\"found\":true,\"_index\":\"cmput301f15t12\",\"_type\":\"Photos\",\"_id\":\"" + id);
        } else {
            return false;
        }
    }

    /**
     * Retrieves a stored photo from the server.
     * @param id Identifier of the photo to be retrieved.
     * @return Returns the Photo object stored on the server.
     * @throws IOException Thrown if an error occurred during communication.
     * @throws JSONException Thrown if the JSON was malformed (Possibly if an older version of an object is retrieved).
     */
    @Deprecated
    public Photo retrievePhoto(String id) throws IOException, JSONException {
        if (ConnectionManager.getInstance().isConnected()) {
            String result = ConnectionManager.getInstance().get("Photos/" + id);
            JSONObject obj = new JSONObject(result);
            String photoJson = obj.getJSONObject("_source").toString();
            return GsonManager.getInstance().fromJson(photoJson, Photo.class);
        } else {
            String photoJson = FileManager.getInstance().readFile("Photos/" + id);
            return GsonManager.getInstance().fromJson(photoJson, Photo.class);
        }
    }

    /**
     * Retrieves a locally cached photo.
     * @param id Identifier of the photo to be retrieved.
     * @return Returns the Photo object with the given id.
     * @throws IOException Thrown if an error occured while reading the file.
     * @throws JSONException Thrown if the JSON was malformed (Possibly if an older version of an object is retrieved).
     */
    public Photo retrieveCachedPhoto(String id) throws IOException, JSONException {
        String photoJson = FileManager.getInstance().readFile("Photos/" + id);
        return GsonManager.getInstance().fromJson(photoJson, Photo.class);
    }

    /**
     * Retrieves a stored photo from the server.
     * @param id Identifier of the photo to be retrieved.
     * @return Returns the Photo object stored on the server.
     * @throws IOException Thrown if an error occurred during communication.
     * @throws JSONException Thrown if the JSON was malformed (Possibly if an older version of an object is retrieved).
     */
    public Photo retrieveOnlinePhoto(String id) throws IOException, JSONException {
        String result = ConnectionManager.getInstance().get("Photos/" + id);
        JSONObject obj = new JSONObject(result);
        String photoJson = obj.getJSONObject("_source").toString();
        FileManager.getInstance().saveJson("Photos/" + id, photoJson);
        return GsonManager.getInstance().fromJson(photoJson, Photo.class);
    }
}
