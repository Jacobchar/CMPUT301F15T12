package com.example.jacob.mybrary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Dominic on 2015-11-04.
 *
 * This data management abstraction offers a simplified way of retrieving data. The data may either
 * be remote or cached locally.
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
        String userjson = FileManager.getInstance().readFile("/data/data/com.example.jacob.mybrary/localUser.json");
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
        FileManager.getInstance().saveJson("/data/data/com.example.jacob.mybrary/localUser.json", userjson);
    }

    //======================= BOOKS ==============================

    /**
     * Stores a book on the server.
     * @param book The book to be stored
     * @return Returns a boolean value indicating whether the request was successful or not
     * @throws IOException Thrown if an error occurred during communication.
     */
    public Boolean storeBook(Book book) throws IOException {
        String json = GsonManager.getInstance().toJson(book);
        String path = "Books/" + book.getItemID().toString();
        String result = ConnectionManager.getInstance().put(path, json);
        //TODO: Add better verification.
        return result.contains("{\"_index\":\"cmput301f15t12\",\"_type\":\"Books\",\"_id\":\"" + book.getItemID().toString());
    }

    /**
     * Removes a book from the server.
     * @param id Identifier of the book to be removed.
     * @return Returns a boolean value indicating whether the request was successful or not.
     * @throws IOException Thrown if an error occurred during communication.
     */
    public Boolean removeBook(String id) throws IOException {
        String result = ConnectionManager.getInstance().remove("Books/" + id);
        return result.contains("{\"found\":true,\"_index\":\"cmput301f15t12\",\"_type\":\"Books\",\"_id\":\"" + id);
    }

    /**
     * Retrieves a stored book from the server.
     * @param id Identifier of the book to be retrieved.
     * @return Returns the Book object stored on the server.
     * @throws IOException Thrown if an error occurred during communication.
     * @throws JSONException Thrown if the JSON was malformed.
     */
    public Book retrieveBook(String id) throws IOException, JSONException {
        String result = ConnectionManager.getInstance().get("Books/" + id);
        JSONObject obj = new JSONObject(result);
        String bookjson = obj.getJSONObject("_source").toString();
        return GsonManager.getInstance().fromJson(bookjson, Book.class);
    }

    /**
     * Searches books for a given quality. Elasticsearch allows for various search methods.
     * @param query JSON based query to be searched for.
     * @return Returns an arraylist of Book objects that match the given query.
     * @throws IOException Thrown if an error occurred during communication.
     * @throws JSONException Thrown if the JSON was malformed.
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
    public Boolean storeUser(User user) {
        return false;
    }

    public Boolean removeUser(String id) {
        return false;
    }

    public User retrieveUser(String id) {
        return null;
    }

    public ArrayList<User> searchUsers(String query) {
        return null;
    }

    //======================= TRADES ==============================
    public Boolean storeTrade(Trade trade) throws IOException {
        String json = GsonManager.getInstance().toJson(trade);
        String path = "Trades/" + trade.getTradeID().toString();
        String result = ConnectionManager.getInstance().put(path, json);
        //TODO: Add better verification.
        return result.contains("{\"_index\":\"cmput301f15t12\",\"_type\":\"Trades\",\"_id\":\"" + trade.getTradeID().toString());
    }

    public Boolean removeTrade(String id) throws IOException {
        String result = ConnectionManager.getInstance().remove("Trades/" + id);
        return result.contains("{\"found\":true,\"_index\":\"cmput301f15t12\",\"_type\":\"Trades\",\"_id\":\"" + id);
    }

    public Trade retrieveTrade(String id) throws IOException, JSONException {
        String result = ConnectionManager.getInstance().get("Trades/" + id);
        JSONObject obj = new JSONObject(result);
        String tradejson = obj.getJSONObject("_source").toString();
        return GsonManager.getInstance().fromJson(tradejson, Trade.class);
    }

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
    public Boolean storePhoto(Photo photo) {
        return false;
    }

    public Boolean removePhoto(String id) {
        return false;
    }

    public Photo retrievePhoto(String id) {
        return null;
    }

    public ArrayList<Photo> searchPhotos(String query) {
        return null;
    }

}
