package com.example.cst2335_final_groupproject_f19;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * Activity for displaying favourite Car Charging Stations saved by the user
 */
public class CarChargerFavourites extends AppCompatActivity {
    /**
     * An ArrayList for saving Car Charging Stations
     */
    private ArrayList<CarChargingStation> carChargerList = new ArrayList<>();
    private BaseAdapter carChargerAdapter;

    /**
     * Gets a handle on a database object
     */
    private SQLiteDatabase carChargerDB;

    /**
     * Creates the current activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_charger_favourites_list);

        // Shows the toolbar at the top of the page
        Toolbar tBar = findViewById(R.id.navigation_toolbar);
        setSupportActionBar(tBar);

        // Create an adapter object and send it to the Favourites ListView
        ListView favouritesList = findViewById(R.id.favouritesList);
        favouritesList.setAdapter(carChargerAdapter = new CarChargerAdapter());

        // Get a database
        CarChargerDatabaseHelper dbOpener = new CarChargerDatabaseHelper(this);
        carChargerDB = dbOpener.getWritableDatabase();

        // Query all the results from the database
        String[] columns = {CarChargerDatabaseHelper.COL_ID, CarChargerDatabaseHelper.COL_LOCATION, CarChargerDatabaseHelper.COL_LATITUDE, CarChargerDatabaseHelper.COL_LONGITUDE, CarChargerDatabaseHelper.COL_CONTACT};
        Cursor results = carChargerDB.query(false, CarChargerDatabaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        // Find the column indices
        int idColIndex = results.getColumnIndex(CarChargerDatabaseHelper.COL_ID);
        int locationColIndex = results.getColumnIndex(CarChargerDatabaseHelper.COL_LOCATION);
        int latitudeColIndex = results.getColumnIndex(CarChargerDatabaseHelper.COL_LATITUDE);
        int longitudeColIndex = results.getColumnIndex(CarChargerDatabaseHelper.COL_LONGITUDE);
        int contactColIndex = results.getColumnIndex(CarChargerDatabaseHelper.COL_CONTACT);

        // Iterate over the results, return true if there is a next item
        while (results.moveToNext()) {
            long id = results.getLong(idColIndex);
            String storedLocation = results.getString(locationColIndex);
            String storedLatitude = results.getString(latitudeColIndex);
            String storedLongitude = results.getString(longitudeColIndex);
            String storedContact = results.getString(contactColIndex);

            // Add the new Contact to the array list
            carChargerList.add(new CarChargingStation(id, storedLocation, storedLatitude, storedLongitude, storedContact));
        }

        favouritesList.setOnItemClickListener((lv, vw, pos, id) -> {
            Snackbar sb = Snackbar.make(favouritesList, "Delete this from Favourites?", Snackbar.LENGTH_LONG).setAction("Confirm", a -> deleteMessageId(pos, carChargerList.get(pos).getId()));
            sb.show();
        });
    }

    /**
     * Displays the options menu item
     *
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.page_menu, menu);
        return true;
    }

    /**
     * What to do when each item on the options menu is selected
     *
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.car_charger_page_menu_link:
                Intent goToCarCharger = new Intent(CarChargerFavourites.this, CarChargerFinder.class);
                CarChargerFavourites.this.startActivityForResult(goToCarCharger, 10);                break;
            case R.id.recipe_page_menu_link:
                Intent goToRecipePage = new Intent(CarChargerFavourites.this, RecipePage.class);
                CarChargerFavourites.this.startActivityForResult(goToRecipePage, 10);
                break;
            case R.id.currency_exchange_page_menu_link:
                Intent goToCurrencyExchange = new Intent(CarChargerFavourites.this, CurrencyExchangeMain.class);
                CarChargerFavourites.this.startActivityForResult(goToCurrencyExchange, 10);
                break;
            case R.id.news_page_menu_link:
                Intent goToNewsPage = new Intent(CarChargerFavourites.this, NewsPage.class);
                CarChargerFavourites.this.startActivityForResult(goToNewsPage, 10);
                break;
            case R.id.go_to_app_favourites:
                break;
            case R.id.go_to_app_help:
                helpAlert();
                break;
        }
        return true;
    }

    /**
     * Displays an Alert Dialog the user with instructions for app use
     */
    public void helpAlert() {
        View middle = getLayoutInflater().inflate(R.layout.car_charger_alert_extra, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setPositiveButton("OK", (dialog, id) -> {
                    // What to do on Accept
                }).setView(middle);

        builder.create().show();
    }

    /**
     * Deletes a given Car Charging Station from both the database and ArrayList
     *
     * @param position
     * @param id
     */
    public void deleteMessageId(int position, long id) {
        carChargerDB.delete(CarChargerDatabaseHelper.TABLE_NAME, CarChargerDatabaseHelper.COL_ID + "=?", new String[]{Long.toString(id)});
        carChargerList.remove(position);
        carChargerAdapter.notifyDataSetChanged();
    }

    /**
     * Creates an Adapter object that extends from Base Adapter
     */
    private class CarChargerAdapter extends BaseAdapter {

        public int getCount() {
            return carChargerList.size();
        } // This function tells how many objects to show

        public CarChargingStation getItem(int position) {
            return carChargerList.get(position);
        }  // This returns the string at the given position

        public long getItemId(int position) {
            return position;
        } // This returns the database id of the item at the given position

        public View getView(int position, View recycled, ViewGroup parent) {
            View thisRow = recycled;

            if (recycled == null)
                thisRow = getLayoutInflater().inflate(R.layout.car_charger_favourites_row, null);

            TextView locationText = thisRow.findViewById(R.id.locationField);
            TextView latitudeText = thisRow.findViewById(R.id.latitudeField);
            TextView longitudeText = thisRow.findViewById(R.id.longitudeField);
            TextView contactText = thisRow.findViewById(R.id.contactField);

            CarChargingStation thisChargingStation = getItem(position);

            locationText.setText(thisChargingStation.getLocationName());
            latitudeText.setText("Latitude: " + thisChargingStation.getLatitude());
            longitudeText.setText("Longitude: " + thisChargingStation.getLongitude());
            contactText.setText("Contact: " + thisChargingStation.getContactPhone());

            return thisRow;
        }
    }
}
