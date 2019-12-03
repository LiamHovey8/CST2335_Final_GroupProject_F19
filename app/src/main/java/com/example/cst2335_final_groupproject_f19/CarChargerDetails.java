package com.example.cst2335_final_groupproject_f19;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Activity to display details regarding a specific Car Charging Station ***NOT IN USE***
 */
public class CarChargerDetails extends AppCompatActivity {
    /**
     * Gets a handle on a database object
     */
    SQLiteDatabase carChargerDB;

    /**
     * Creates the current activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_charger_search_details);

        Toolbar tBar = findViewById(R.id.navigation_toolbar);
        setSupportActionBar(tBar);

        // Gets the car charging station details from previous page
        Intent stationDetails = getIntent();

        TextView locationText = findViewById(R.id.locationText);
        String location = stationDetails.getStringExtra("LOCATION");
        locationText.setText("Location: " + location);

        TextView latitudeText = findViewById(R.id.latitudeText);
        String latitude = stationDetails.getStringExtra("LATITUDE");
        latitudeText.setText("Latitude: " + latitude);

        TextView longitudeText = findViewById(R.id.longitudeText);
        String longitude = stationDetails.getStringExtra("LONGITUDE");
        longitudeText.setText("Longitude: " + longitude);

        TextView telephoneText = findViewById(R.id.telephoneText);
        String contact = stationDetails.getStringExtra("CONTACT");
        telephoneText.setText("Contact Phone: " + contact);

        Button directionsButton = findViewById(R.id.directionsButton);
        if (directionsButton != null) {
            // On click go to Google Maps directions
            directionsButton.setOnClickListener(v -> {
                Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            });
        }

        // Gets a database
        CarChargerDatabaseHelper dbOpener = new CarChargerDatabaseHelper(this);
        carChargerDB = dbOpener.getWritableDatabase();

        Button favouriteButton = findViewById(R.id.favouriteButton);
        if (favouriteButton != null) {
            // On click add to favourites list/database
            favouriteButton.setOnClickListener(v -> {
                Toast.makeText(this, "Station added to favourites", Toast.LENGTH_LONG).show();

                // Add to the database and get the new ID
                ContentValues newRowValues = new ContentValues();
                // Put string location in the LOCATION column
                newRowValues.put(CarChargerDatabaseHelper.COL_LOCATION, location);
                newRowValues.put(CarChargerDatabaseHelper.COL_LATITUDE, latitude);
                newRowValues.put(CarChargerDatabaseHelper.COL_LONGITUDE, longitude);
                newRowValues.put(CarChargerDatabaseHelper.COL_CONTACT, contact);

                carChargerDB.insert(CarChargerDatabaseHelper.TABLE_NAME, null, newRowValues);

                Intent goToFavourites = new Intent(CarChargerDetails.this, CarChargerFavourites.class);

                // Starts the next activity
                startActivity(goToFavourites);
            });
        }
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
                Toast.makeText(this, "You're already in the activity", Toast.LENGTH_LONG).show();
                break;
            case R.id.recipe_page_menu_link:
                Intent goToRecipePage = new Intent(CarChargerDetails.this, RecipePage.class);
                CarChargerDetails.this.startActivityForResult(goToRecipePage, 10);
                break;
            case R.id.currency_exchange_page_menu_link:
                Intent goToCurrencyExchange = new Intent(CarChargerDetails.this, CurrencyExchangeMain.class);
                CarChargerDetails.this.startActivityForResult(goToCurrencyExchange, 10);
                break;
            case R.id.news_page_menu_link:
                Intent goToNewsPage = new Intent(CarChargerDetails.this, NewsPage.class);
                CarChargerDetails.this.startActivityForResult(goToNewsPage, 10);
                break;
            case R.id.go_to_app_favourites:
                Intent goToCarChargerFavourites = new Intent(CarChargerDetails.this, CarChargerFavourites.class);
                CarChargerDetails.this.startActivityForResult(goToCarChargerFavourites, 10);
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
}
