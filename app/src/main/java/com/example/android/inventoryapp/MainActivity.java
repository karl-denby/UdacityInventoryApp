package com.example.android.inventoryapp;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Product>> {

    // Loader lifecycle Events
    @Override
    public ProductLoader onCreateLoader(int id, Bundle args) {
        return new ProductLoader(this);
    }

    @Override public void onLoaderReset(Loader<List<Product>> param1) {
        // todo: something
    }

    @Override
    public void onLoadFinished(Loader<List<Product>> loader, List<Product> data) {
        /**
         * turn "List<Product> data" into "ArrayList<Product> arrayOfProduct"
         * attach data to listView
         */
        ArrayList<Product> arrayOfProducts;
        arrayOfProducts = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            arrayOfProducts.add(i, data.get(i));
        }
        ProductAdapter productAdapter = new ProductAdapter(this, arrayOfProducts);

        // attach data to listView and tell it to refresh
        ListView lvProducts = (ListView) findViewById(R.id.lvProducts);
        lvProducts.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

/* Functionality
ListView Population:
The listView populates with the current products stored in the table.

Add product button:
The Add product button prompts the user for information about the product and a picture, each of
which are then properly stored in the table.

Input validation:
User input is validated. In particular, empty product information is not accepted.

Sale button:
The sale button on each list item properly reduces the quantity available by one, unless that would
result in a negative quantity.

Detail View intent:
Clicking on the rest of each list item sends the user to the detail screen for the correct product.

Modify quantity buttons:
The modify quantity buttons in the detail view properly increase and decrease the quantity available
for the correct product.

The student may also add input for how much to increase or decrease the quantity by.

Order Button:
The ‘order more’ button sends an intent to either a phone app or an email app to contact the supplier
using the information stored in the database.

Delete button:
The delete button prompts the user for confirmation and, if confirmed, deletes the product record
entirely and sends the user back to the main activity.
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get our Database object and loader
        ProductDatabaseHelper db_helper = new ProductDatabaseHelper(this);
        SQLiteDatabase db = db_helper.getWritableDatabase();
        getSupportLoaderManager().initLoader(0, null, MainActivity.this).forceLoad();

        // Show "No Content Available" when listView is empty
        View header = getLayoutInflater().inflate(R.layout.list_header, null);
        ListView lvProducts = (ListView) findViewById(R.id.lvProducts);
        TextView tvNoContent = (TextView) findViewById(R.id.tvNoContent);
        lvProducts.addHeaderView(header);
        lvProducts.setEmptyView(tvNoContent);


        // Todo: fake data for now
        //arrayOfProducts.add(new Product("Nexus 6", 1, 400, "Google", 0));
        //arrayOfProducts.add(new Product("Nexus 6p", 5, 600, "Google", 0));
        //arrayOfProducts.add(new Product("Nexus 5x", 5, 380, "Google", 0));
        //arrayOfProducts.add(new Product("Moto 360", 2, 275, "Google", 0));

        //ProductAdapter productAdapter = new ProductAdapter(this, arrayOfProducts);
        //lvProducts.setAdapter(productAdapter);
    }
}
