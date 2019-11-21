package com.example.cst2335_final_groupproject_f19;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class CarChargerFinder extends AppCompatActivity {
    ArrayList<String> objects = new ArrayList<>(Arrays.asList("Item 1", "Item 2", "Item 3"));
    BaseAdapter carChargerAdapter;
    ProgressBar searchProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_charger_view_page);

        //new CarChargerQuery().execute();

       /* ListView theList = findViewById(R.id.searchList);
        theList.setAdapter(carChargerAdapter = new CarChargerAdapter());
        theList.setOnItemClickListener((lv, vw, pos, id) -> {
            //alertBox();
            Toast.makeText(CarChargerFinder.this,
                    "You clicked on: " + pos, Toast.LENGTH_SHORT).show();
        });*/

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
                Intent goToRecipePage = new Intent(CarChargerFinder.this, RecipePage.class);
                CarChargerFinder.this.startActivityForResult(goToRecipePage, 10);
                break;
            case R.id.currency_exchange_page_menu_link:
                Intent goToCurrencyExchange = new Intent(CarChargerFinder.this, CurrencyExchangeMain.class);
                CarChargerFinder.this.startActivityForResult(goToCurrencyExchange, 10);
                break;
            case R.id.news_page_menu_link:
                Intent goToNewsPage = new Intent(CarChargerFinder.this, NewsPage.class);
                CarChargerFinder.this.startActivityForResult(goToNewsPage, 10);
                break;
        }
        return true;
    }

    private class CarChargerQuery extends AsyncTask<String, String, String> {
        String locationName, latitude, longitude, contactPhone;

        @Override                       //Type 1
        protected String doInBackground(String... strings) {
            String ret = null;
            String queryURL = "https://api.openchargemap.io/v3/poi/?output=json&countrycode=CA&camelcase=true&verbose=false&maxresults=10&latitude=" + latitude + "&longitude=" + longitude;

            try {       // Connect to the server:
                URL url = new URL(queryURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();

                //Set up the JSON object parser:
                // json is UTF-8 by default
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                JSONObject jObject = new JSONObject(result);

                jObject.getJSONObject("addressInfo");
                String title = jObject.getString("title");
                locationName = title;
                String lat = jObject.getString("latitude");
                latitude = lat;
                String lon = jObject.getString("longitude");
                longitude = lon;
                String phone = jObject.getString("contactTelephone1");
                if (phone != null) {
                    contactPhone = phone;
                }

            } catch (MalformedURLException mfe) {
                ret = "Malformed URL exception";
            } catch (IOException ioe) {
                ret = "IO Exception. Is the Wifi connected?";
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //What is returned here will be passed as a parameter to onPostExecute:
            return ret;
        }

        @Override
        protected void onPostExecute(String sentFromDoInBackground) {
            super.onPostExecute(sentFromDoInBackground);
            // Update GUI stuff only:
            searchProgressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values[0]);
            // Update GUI stuff only:
            searchProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private class CarChargerAdapter extends BaseAdapter {

        public int getCount() {
            return objects.size();
        } //This function tells how many objects to show

        public String getItem(int position) {
            return objects.get(position);
        }  //This returns the string at position p

        public long getItemId(int p) {
            return p;
        } //This returns the database id of the item at position p

        public View getView(int p, View recycled, ViewGroup parent) {
            View thisRow = recycled;

            if (recycled == null)
                thisRow = getLayoutInflater().inflate(R.layout.car_charger_list_row, null);

            TextView numberText = thisRow.findViewById(R.id.numberField);
            numberText.setText("Result #: " + p + ", ");

            TextView itemText = thisRow.findViewById(R.id.itemField);
            itemText.setText("is " + getItem(p));

            return thisRow;
        }
    }
}
