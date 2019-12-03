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

/**
 * Main activity for the Electric Car Charging Station Finder app
 */
public class CarChargerFinder extends AppCompatActivity {
    /**
     * Static class variables to pass data between activities
     */
    public static final String ITEM_LOCATION = "LOCATION";
    public static final String ITEM_LATITUDE = "LATITUDE";
    public static final String ITEM_LONGITUDE = "LONGITUDE";
    public static final String ITEM_CONTACT = "CONTACT";
    public static final int DETAIL_ACTIVITY = 345;
    /**
     * Stores the user's search coordinates from EditText
     */
    private EditText latitudeText, longitudeText;

    /**
     * Stores info for use in SharedPreferences
     */
    private SharedPreferences.Editor editor;
    private SharedPreferences searchCoordinates;

    /**
     * Stores the activity's ProgressBar object
     */
    private ProgressBar searchProgressBar;

    /**
     * An ArrayList for saving Car Charging Stations
     */
    private ArrayList<CarChargingStation> carChargerList = new ArrayList<>();
    protected BaseAdapter carChargerAdapter;

    /**
     * Stores Strings used to query the external server
     */
    String queryURL = "https://api.openchargemap.io/v3/poi/?output=json&countrycode=CA&camelcase=true&maxresults=10&latitude=";
    String carChargerURL;

    /**
     * Creates the current activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_charger_view_page);

        // Creates a shared preferences file on the phone to store the user's searchCoordinates
        searchCoordinates = getSharedPreferences("SearchHistory", MODE_PRIVATE);
        latitudeText = findViewById(R.id.searchLatitude);
        longitudeText = findViewById(R.id.searchLongitude);

        // Retrieves the saved values from SharedPreferences
        String previousLatitude = searchCoordinates.getString("Latitude", "");
        String previousLongitude = searchCoordinates.getString("Longitude", "");
        latitudeText.setText(previousLatitude);
        longitudeText.setText(previousLongitude);

        // Shows the toolbar at the top of the page
        Toolbar tBar = findViewById(R.id.navigation_toolbar);
        setSupportActionBar(tBar);

        // Displays the search progress bar
        searchProgressBar = findViewById(R.id.searchProgress);

        // Creates an adapter object and sends it to the ListView
        ListView searchList = findViewById(R.id.searchList);
        searchList.setAdapter(carChargerAdapter = new CarChargerAdapter());

        //check if the FrameLayout is loaded
        boolean isTablet = findViewById(R.id.carChargerFragment) != null;

        searchList.setOnItemClickListener((list, item, position, id) -> {
            Bundle dataToPass = new Bundle();

            // This puts the selected strings into the next activity --> CarChargerDetails
            dataToPass.putString("LOCATION", carChargerList.get(position).getLocationName());
            dataToPass.putString("LATITUDE", carChargerList.get(position).getLatitude());
            dataToPass.putString("LONGITUDE", carChargerList.get(position).getLongitude());
            dataToPass.putString("CONTACT", carChargerList.get(position).getContactPhone());

            if (isTablet) {
                CarChargerFragment dFragment = new CarChargerFragment(); // Add a DetailFragment
                dFragment.setArguments(dataToPass); // Passes it a bundle for information
                dFragment.setTablet(true);  // Tells the fragment if it's running on a tablet or not
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.carChargerFragment, dFragment) // Adds the fragment in FrameLayout
                        .commit(); // Actually loads the fragment
            } else // isPhone
            {
                Intent goToDetails = new Intent(CarChargerFinder.this, CarChargerFrame.class);
                goToDetails.putExtras(dataToPass); // Sends data to next activity
                startActivityForResult(goToDetails, DETAIL_ACTIVITY); // Makes the transition
            }
        });

        Button searchButton = findViewById(R.id.searchButton);
        if (searchButton != null) {
            // On click go to next page --> ChatRoomActivity
            searchButton.setOnClickListener(v -> {
                carChargerList.clear();
                String searchLatitude = latitudeText.getText().toString();
                String searchLongitude = longitudeText.getText().toString();
                // Concatenates the URL with the search coordinates input by the user
                carChargerURL = queryURL + searchLatitude + "&longitude=" + searchLongitude;
                CarChargerQuery carChargerQuery = new CarChargerQuery();
                carChargerQuery.execute(carChargerURL);
            });
        }
    }

    /**
     * Displays the options menu item
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
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.car_charger_page_menu_link:
                Toast.makeText(this, "You're already in this activity", Toast.LENGTH_LONG).show();
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

    /**
     * Displays an Alert Dialog the user with instructions for app use
     */
    public void helpAlert() {
        View middle = getLayoutInflater().inflate(R.layout.car_charger_alert_extra, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setPositiveButton("OK", (dialog, id) -> {
                    // Do nothing on Accept (only option in this case)
                }).setView(middle);

        builder.create().show();
    }

    /**
     * Called when the activity is on pause
     */
    @Override
    protected void onPause() {
        super.onPause();

        // Saves the values in the SharedPreferences with whitespace trimmed
        editor = searchCoordinates.edit();
        editor.putString("Latitude", latitudeText.getText().toString().trim());
        editor.putString("Longitude", longitudeText.getText().toString().trim());

        // Commits the saved strings to the Editor
        editor.commit();
    }

    /**
     * Queries an external server for nearby Car Charging Stations using Async Task
     */
    private class CarChargerQuery extends AsyncTask<String, Integer, String> {
        String locationName, latitude, longitude, contactPhone;

        @Override                       // Type 1 passed to Async Task
        protected String doInBackground(String... strings) {
            String ret = null;

            try {       // Connects to the server using the internet
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();
                publishProgress(25);

                // Sets up the JSON object parser
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                // Reads the contents of the JSON input and saves results as a string
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                // Saves the string of results in a JSON array
                JSONArray jsonArray = new JSONArray(result);
                publishProgress(50);

                // Iterates over the array by JSON object
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    // Accesses the "addressInfo" object within the 1st JSON object
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("addressInfo");
                    // Below strings are accesses from within the inner JSON object
                    String title = jsonObject2.getString("title");
                    locationName = title;
                    String lat = jsonObject2.getString("latitude");
                    latitude = lat;
                    String lon = jsonObject2.getString("longitude");
                    longitude = lon;
                    String phone = jsonObject2.getString("contactTelephone1");
                    // Save the Contact Telephone if the result is not stored as "null"
                    if (phone != "null") {
                        contactPhone = phone;
                    } else {
                        contactPhone = "N/A";
                    }
                    // Adds the new message to the ArrayList
                    carChargerList.add(new CarChargingStation(locationName, latitude, longitude, contactPhone));
                    //publishProgress((i + 1) * 100 / jsonArray.length());
                    publishProgress(75);
                }
                publishProgress(100);
                // Catches various exceptions from above Try block
            } catch (MalformedURLException mfe) {
                ret = "Malformed URL Exception";
            } catch (IOException ioe) {
                ret = "IO Exception. Is the Wifi connected?";
            } catch (JSONException jse) {
                ret = "JSON Exception";
            }
            // What is returned here will be passed as a parameter to onPostExecute
            return ret;
        }

        @Override
        protected void onPostExecute(String sentFromDoInBackground) {
            super.onPostExecute(sentFromDoInBackground);
            // Updates GUI stuff only
            searchProgressBar.setVisibility(View.INVISIBLE);
            ListView searchList = findViewById(R.id.searchList);
            searchList.setAdapter(carChargerAdapter = new CarChargerAdapter());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Updates GUI stuff only
            searchProgressBar.setVisibility(View.VISIBLE);
            searchProgressBar.setProgress(values[0]);
        }
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
        }  // This returns the string at a given position

        public long getItemId(int position) {
            return position;
        } // This returns the database id of the item at given position

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
