package com.example.cst2335_final_groupproject_f19;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

        new CarChargerQuery().execute();

        ListView theList = findViewById(R.id.searchList);
        theList.setAdapter(carChargerAdapter = new CarChargerAdapter());
        theList.setOnItemClickListener((lv, vw, pos, id) -> {
            //alertBox();
            Toast.makeText(CarChargerFinder.this,
                    "You clicked on: " + pos, Toast.LENGTH_SHORT).show();
        });

        /*Button alertButton = findViewById(R.id.searchButton);
        alertButton.setOnClickListener(click -> alertBox());*/
    }

    /*public void alertBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Details regarding the selected station will display here!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Accept
                        setContentView(R.layout.car_charger_view_page);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel
                        setContentView(R.layout.car_charger_view_page);
                    }
                });

        builder.create().show();
    }*/

    private class CarChargerQuery extends AsyncTask<String, String, String> {
        String locationName, latitude, longitude, contactPhone, searchCoordinates;

        @Override                       //Type 1
        protected String doInBackground(String... strings) {
            String ret = null;
            String queryURL = "https://api.openchargemap.io/v3/poi/?output=json&countrycode=CA&camelcase=true&verbose=false&compact=true&maxresults=10" + searchCoordinates;

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
                String title = jObject.getString("title");
                locationName = title;

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
