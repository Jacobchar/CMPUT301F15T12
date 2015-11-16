package com.example.jacob.mybrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Victoria.
 *
 * Inventory Activity, displays to the inventory to the user. Allows for deleting a book on a long
 * click, adding a book, and searching your inventory.
 *
 * Search functionality deferred to P5.
 *
 */

public class InventoryActivity extends AppCompatActivity {

    // used LonelyTwitter here

    private ArrayAdapter<Book> adapter;
    private ListView listView;
    private Inventory inventory = new Inventory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        fillInventory();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                final Book book = (Book) listView.getItemAtPosition(pos);

                AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
                builder.setMessage("What would you like to do?");
                builder.setCancelable(true);
                builder.setPositiveButton("Delete Item", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        inventory.deleteBookByName(book.getName());
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Edit Item", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editItem(book.getItemID());
                        dialog.cancel();
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });


    }

    /**
     * Fills your inventory ListView on opening the inventory activity.
     */
    void fillInventory(){

        listView = (ListView) findViewById(R.id.inventoryListView);

        //Book book = new Book("testName", 0, "It's A Book", true);
        //Book book2 = new Book("BookBook", 1, "No Book", false);

        //inventory.addBook(book);
        //inventory.addBook(book2);

        adapter = new ArrayAdapter<>(this, R.layout.simple_list_item, inventory.getBooks());

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void addNewItem(View view){

        Intent intent = new Intent(this, AddNewItem.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("inv", inventory);

        intent.putExtras(bundle);

        startActivityForResult(intent, 1);

    }

    public void editItem(UUID id){

        Intent intent = new Intent(this, EditBookActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("inv", inventory);
        bundle.putSerializable("id", id);

        intent.putExtras(bundle);

        startActivityForResult(intent, 1);

    }

    @Override
    // cite http://stackoverflow.com/questions/17242713/how-to-pass-parcelable-object-from-child-to-parent-activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            inventory = (Inventory) data.getSerializableExtra("inv");
        }
        adapter.notifyDataSetChanged();
        fillInventory();
    }

    public void searchForBook(){
        /* Dom/Jake's tests:
            try {
                DataManager dataManager = DataManager.getInstance();

                Book book3 = new Book("testBook3", 3, "testCategory3", true);

                //Wait for entries to be indexed
                Thread.sleep(1000);

                ArrayList<Book> returnedBooks = dataManager.searchBooks("{\"query\":{\"query_string\":{\"default_field\":\"name\",\"query\":\"testBook2\"}}}");

                assertTrue(returnedBooks.size() == 1);
                assertTrue(returnedBooks.contains(book2));

            } catch (Exception e) {

            }
        */
    }







}
