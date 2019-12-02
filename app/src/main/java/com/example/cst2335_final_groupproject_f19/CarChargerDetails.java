package com.example.cst2335_final_groupproject_f19;

import android.content.Intent;
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

public class CarChargerDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_charger_search_details);

        Toolbar tBar = findViewById(R.id.navigation_toolbar);
        setSupportActionBar(tBar);

        // Gets the car charging station details from previous page
        Intent stationDetails = getIntent();

        TextView locationText = findViewById(R.id.locationText);
        locationText.setText("Location: " + stationDetails.getStringExtra("locationName"));

        TextView latitudeText = findViewById(R.id.latitudeText);
        String latitude = stationDetails.getStringExtra("latitude");
        latitudeText.setText("Latitude: " + latitude);

        TextView longitudeText = findViewById(R.id.longitudeText);
        String longitude = stationDetails.getStringExtra("longitude");
        longitudeText.setText("Logitude: " + longitude);

        TextView telephoneText = findViewById(R.id.telephoneText);
        telephoneText.setText("Contact Phone: " + stationDetails.getStringExtra("contactPhone"));

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

        Button favouriteButton = findViewById(R.id.favouriteButton);
        if (favouriteButton != null) {
            // On click add to favourites list/database
            favouriteButton.setOnClickListener(v -> {
                // ADD CODE TO DO STUFF
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.page_menu, menu);
        return true;
    }

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
