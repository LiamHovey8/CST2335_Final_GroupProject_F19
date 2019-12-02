package com.example.cst2335_final_groupproject_f19;

import android.content.Intent;
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

import java.util.ArrayList;

public class CarChargerFavourites extends AppCompatActivity {
    ArrayList<String> carChargerList = new ArrayList<>();
    BaseAdapter carChargerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_charger_favourites_list);

        // Create an adapter object and send it to the Favourites ListView
        ListView favouritesList = findViewById(R.id.favouritesList);
        favouritesList.setAdapter(carChargerAdapter = new CarChargerAdapter());

        favouritesList.setOnItemClickListener((lv, vw, pos, id) -> {
            Intent goToDetails = new Intent(CarChargerFavourites.this, CarChargerDetails.class);
            // This puts the selected strings into the next activity --> CarChargerDetails
//            goToDetails.putExtra("locationName", carChargerList.get(pos).getLocationName());
//            goToDetails.putExtra("latitude", carChargerList.get(pos).getLatitude());
//            goToDetails.putExtra("longitude", carChargerList.get(pos).getLongitude());
//            goToDetails.putExtra("contactPhone", carChargerList.get(pos).getContactPhone());
            // Start the next activity
            startActivity(goToDetails);
        });

        Toolbar tBar = findViewById(R.id.navigation_toolbar);
        setSupportActionBar(tBar);
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

    public void helpAlert() {
        View middle = getLayoutInflater().inflate(R.layout.car_charger_alert_extra, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setPositiveButton("OK", (dialog, id) -> {
                    // What to do on Accept
                }).setView(middle);

        builder.create().show();
    }

    private class CarChargerAdapter extends BaseAdapter {

        public int getCount() {
            return carChargerList.size();
        } //This function tells how many objects to show

        public String getItem(int position) {
            return carChargerList.get(position);
        }  //This returns the string at position p

        public long getItemId(int position) {
            return position;
        } //This returns the database id of the item at position p

        public View getView(int position, View recycled, ViewGroup parent) {
            View thisRow = recycled;

            if (recycled == null)
                thisRow = getLayoutInflater().inflate(R.layout.car_charger_list_row, null);

            TextView itemText = thisRow.findViewById(R.id.locationField);
            //CarChargingStation thisChargingStation = getItem(position);
            //itemText.setText(thisChargingStation.getLocationName());

            return thisRow;
        }
    }
}
