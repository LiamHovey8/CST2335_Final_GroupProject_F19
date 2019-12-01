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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;

public class CarChargerFavourites extends AppCompatActivity {
    ArrayList<String> carChargerList = new ArrayList<>(Arrays.asList("Item 1", "Item 2", "Item 3"));
    BaseAdapter carChargerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_charger_favourites_list);

        // Create an adapter object and send it to the Favourites ListView
        ListView favouritesList = findViewById(R.id.searchList);
        favouritesList.setAdapter(carChargerAdapter = new CarChargerAdapter());

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
                Toast.makeText(this, "You're already on this page!", Toast.LENGTH_LONG).show();
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
        }
        return true;
    }

    private class CarChargerAdapter extends BaseAdapter {

        public int getCount() {
            return carChargerList.size();
        } //This function tells how many objects to show

        public String getItem(int position) {
            return carChargerList.get(position);
        }  //This returns the string at position p

        public long getItemId(int p) {
            return p;
        } //This returns the database id of the item at position p

        public View getView(int p, View recycled, ViewGroup parent) {
            View thisRow = recycled;

            if (recycled == null)
                thisRow = getLayoutInflater().inflate(R.layout.car_charger_list_row, null);

            TextView numberText = thisRow.findViewById(R.id.locationField);
            numberText.setText("Result #: " + p + ", ");

            return thisRow;
        }
    }
}
