package com.example.cst2335_final_groupproject_f19;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
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

public class CarChargerFinder extends AppCompatActivity {
    private EditText latitudeText, longitudeText;
    private String locationName, latitude, longitude, contactPhone;

    private SharedPreferences.Editor editor;
    private SharedPreferences searchCoordinates;

    private ProgressBar searchProgressBar;

    private ArrayList<CarChargingStation> carChargerList = new ArrayList<>();
    private BaseAdapter carChargerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_charger_view_page);

        // Creates a shared preferences file on the phone to store the user's searchCoordinates
        searchCoordinates = getSharedPreferences("SearchHistory", MODE_PRIVATE);
        latitudeText = findViewById(R.id.searchLatitude);
        longitudeText = findViewById(R.id.searchLongitude);

        latitude = latitudeText.getText().toString();
        longitude = longitudeText.getText().toString();

        // Retrieves the saved values from SharedPreferences
        String previousLatitude = searchCoordinates.getString("Latitude", "");
        String previousLongitude = searchCoordinates.getString("Longitude", "");
        latitudeText.setText(previousLatitude);
        longitudeText.setText(previousLongitude);

        searchProgressBar = findViewById(R.id.searchProgress);

        // Creates an adapter object and sends it to the ListView
        ListView theList = findViewById(R.id.searchList);
        theList.setAdapter(carChargerAdapter = new CarChargerAdapter());

//        ListView theList = findViewById(R.id.searchList);
//        theList.setAdapter(carChargerAdapter = new CarChargerAdapter());
//        theList.setOnItemClickListener((lv, vw, pos, id) -> {
//            //alertBox();
//            Toast.makeText(CarChargerFinder.this,
//                    "You clicked on: " + pos, Toast.LENGTH_SHORT).show();
//        });

        Toolbar tBar = findViewById(R.id.navigation_toolbar);
        setSupportActionBar(tBar);

        Button searchButton = findViewById(R.id.searchButton);
        if (searchButton != null) {
            // On click go to next page --> ChatRoomActivity
            searchButton.setOnClickListener(v -> {
                new CarChargerQuery().execute();
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
                Toast.makeText(this, "You're already on this page", Toast.LENGTH_LONG).show();
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
            case R.id.go_to_app_favourites:
                Intent goToCarChargerFavourites = new Intent(CarChargerFinder.this, CarChargerFavourites.class);
                CarChargerFinder.this.startActivityForResult(goToCarChargerFavourites, 10);
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

    @Override
    protected void onPause() {
        super.onPause();

        // Saves the values in the SharedPreferences with whitespace trimmed
        editor = searchCoordinates.edit();
        editor.putString("Latitude", latitudeText.getText().toString().trim());
        editor.putString("Longitude", longitudeText.getText().toString().trim());

        editor.commit();
    }

    private class CarChargerQuery extends AsyncTask<String, Integer, String> {
        //String locationName, contactPhone;

        @Override                       //Type 1
        protected String doInBackground(String... strings) {
            String ret = null;
            String queryURL = "https://api.openchargemap.io/v3/poi/?output=json&countrycode=CA&camelcase=true&maxresults=10&latitude=" + latitude + "&longitude=" + longitude;

            try {       // Connect to the server:
                URL url = new URL(queryURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();

                // Set up the JSON object parser:
                // json is UTF-8 by default
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String tempName = jsonObject1.getString("addressInfo");
                    if (tempName.equals("addressInfo")) {
                        JSONObject jsonObject2 = new JSONObject("addressInfo");
                        String title = jsonObject2.getString("title");
                        locationName = title;
                        publishProgress(25);
                        String lat = jsonObject2.getString("latitude");
                        latitude = lat;
                        publishProgress(50);
                        String lon = jsonObject2.getString("longitude");
                        longitude = lon;
                        publishProgress(75);
                        String phone = jsonObject2.getString("contactTelephone");
                        if (phone != null) {
                            contactPhone = phone;
                            publishProgress(100);
                        } else {
                            contactPhone = "N/A";
                            publishProgress(100);
                        }
                    }
                }

//                JSONObject jObject = new JSONObject(result);
//
//                jObject.getJSONObject("addressInfo");
//                String title = jObject.getString("title");
//                locationName = title;
//                publishProgress(25);
//                String lat = jObject.getString("latitude");
//                latitude = lat;
//                publishProgress(50);
//                String lon = jObject.getString("longitude");
//                longitude = lon;
//                publishProgress(75);
//                String phone = jObject.getString("contactTelephone");
//                if (phone != null) {
//                    contactPhone = phone;
//                    publishProgress(100);
//                } else {
//                    contactPhone = "N/A";
//                    publishProgress(100);
//                }


            } catch (MalformedURLException mfe) {
                ret = "Malformed URL Exception";
            } catch (IOException ioe) {
                ret = "IO Exception. Is the Wifi connected?";
            } catch (JSONException jse) {
                ret = "JSON Exception";
            }
            //What is returned here will be passed as a parameter to onPostExecute:
            return ret;
        }

        @Override
        protected void onPostExecute(String sentFromDoInBackground) {
            super.onPostExecute(sentFromDoInBackground);
            // Update GUI stuff only:
            searchProgressBar.setVisibility(View.INVISIBLE);
            // Add the new message to the list
            carChargerList.add(new CarChargingStation(locationName, latitude, longitude, contactPhone));
            // Update the listView
            carChargerAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Update GUI stuff only:
            searchProgressBar.setVisibility(View.VISIBLE);
            searchProgressBar.setProgress(values[0]);
        }
    }

    private class CarChargerAdapter extends BaseAdapter {

        public int getCount() {
            return carChargerList.size();
        } //This function tells how many objects to show

        public CarChargingStation getItem(int position) {
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
            CarChargingStation thisChargingStation = getItem(position);
            itemText.setText(thisChargingStation.getLocationName());

            return thisRow;
        }
    }
}
