package com.example.cst2335_final_groupproject_f19;



import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import android.widget.ListView;
import java.net.URL;


import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class CurrencyExchangeMain extends AppCompatActivity {


    Button runConversionButton;
    Button showHelpMenuButton;
    Button enterFavourite;
    String sampleToast = "Here's a toast test";
    String sampleSnackBar = "Here's a snackbar test";
    int duration = 10;
    View view;
    ArrayList<Conversion> favouritesArrayList = new ArrayList<>();


    BaseAdapter theAdapter;
    JSONObject jObject;

    JSONObject x;

    Double retrievedInputRate;
    Double retrievedOutputRate;

    /**
    onCreate function is the first command to run in the project.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        /**
         * Creates layout and toolbar
         */
        setContentView(R.layout.currency_exchange_home);
        Toolbar tBar = (Toolbar)findViewById(R.id.navigation_toolbar);
        setSupportActionBar(tBar);
        /**
         * Sets JSONObject x to null.
        */
        x=null;

        /**
         * Creates items to and populates ListView.
         */
        Conversion anEx = new Conversion(1.0, 1.5, "CAD", "USD");
        Conversion anEx2 = new Conversion(1.0, 1.4, "CAD", "EUR");

        favouritesArrayList.add(anEx);
        favouritesArrayList.add(anEx2);


        ListView theListView = findViewById(R.id.favouritesList);


        theListView.setAdapter(theAdapter = new myListAdapter());

        theListView.setOnItemClickListener((listView, aView, position, idNum) -> {
            Toast.makeText(this, "This is the conversion from " + favouritesArrayList.get(position).getInputCurrency() + " to " + favouritesArrayList.get(position).getOutputCurrency(), Toast.LENGTH_LONG).show();
        });

        /**
         * Runs decision() method if the runConversionButton is clicked.
         */

        runConversionButton = findViewById(R.id.runConversionButton);

        if (runConversionButton != null) {
            runConversionButton.setOnClickListener(v -> decision());
        }

        /**
         * Runs alertExample() dialog box method if showHelpMenu button is clicked.
         */

        showHelpMenuButton = findViewById(R.id.showHelpMenuButton);
        if (showHelpMenuButton != null) {

            showHelpMenuButton.setOnClickListener(v -> alertExample());
        }

    }

    /**
     * Method checks if the JSONObject x containing the array of currency conversions is null and either:
     * a) runs Async to retrieve the information if it is null
     * b) calls the alreadyRun() method if it is not null
     */
    public void decision(){
        retrieveConversions aConversion = new retrieveConversions();
        if(x==null)
            {aConversion.execute();}
        else{alreadyRun(); }
    }

    /**
     * Runs Async task to connect to the internet and retrieve conversion values
     */
    private class retrieveConversions extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... args) {

            String conversionsURL1 = "https://api.exchangeratesapi.io/latest";

            String ret = null;

            /**
             * Attempt to establish connection to internet, retrieve exchange rates, and pass information
             * to post execute
             */
            try {
                /**
                 * Create new URL based on link
                 */
                URL conversionsURL = new URL(conversionsURL1);
                /**
                 * Establish URL connection
                 */
                HttpURLConnection urlConnection = (HttpURLConnection) conversionsURL.openConnection();
                /**
                 * Establish inStream
                 */
                InputStream inStream = urlConnection.getInputStream();
                /**
                 * Establish buffered reader
                 */
                BufferedReader conversionReader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
                /**
                 * Create string builder to interpret online information
                 */
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = conversionReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                /**
                 * Create jObject from result of string builder
                 */

                jObject = new JSONObject(result);
                /**
                 * Catch exceptions
                 */

            } catch (JSONException joe) {
                ret = "JSON exception";
            } catch (IOException ioe) {
                ret = "IO Exception. Is the Wifi connected?";
            }

            return ret;
        }


        @Override
        protected void onPostExecute(String sentFromDoInBackground) {

            super.onPostExecute(sentFromDoInBackground);
            String ret = null;


            try {
                /**
                 * Create JSONObject from information from doInBackground
                 */
                x = jObject.getJSONObject("rates");

                /**
                 * Runs alreadyRun() method to perform calculations and provide response.
                 */
                alreadyRun();


            } catch
            (JSONException joe) {
                ret = "JSON exception";
            }

        }


    }

    /**
     * Base adapter class to populate ListView
     */
    private class myListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return favouritesArrayList.size();
        }

        @Override
        public Conversion getItem(int position) {
            return favouritesArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View aRow = convertView;

            if (convertView == null)
                aRow = getLayoutInflater().inflate(R.layout.a_conversion, null);


            TextView rowText = aRow.findViewById(R.id.aConversion);
            Conversion thisConversion = getItem(position);
            rowText.setText(thisConversion.getListPiece());

            return aRow;

        }
    }

    /**
     * Produce dialog box with instructions for app use
     */
    public void alertExample() {
        View middle = getLayoutInflater().inflate(R.layout.dialog_box, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Instructions")

                .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).setView(middle);
        builder.create().show();
    }

    /**
     * Performs calculations using inputs and provides output
     */

    public void alreadyRun() {
        String ret = null;
        try {
            /**
             * Fetches values entered by user
             */
            EditText inputValue = findViewById(R.id.currencyValueInput);
            String inputValueNum = inputValue.getText().toString();

            EditText inputCurrency = findViewById(R.id.inputCurrency);
            String inputCurrencyString = inputCurrency.getText().toString();

            EditText outputCurrency = findViewById(R.id.outputCurrency);
            String outputCurrencyString = outputCurrency.getText().toString();

            String eur="EUR";

            /**
             * Because of JSON format, need to check if user is looking for values in euros and set retrieved input
             * rate appropriately
             */
            if(inputCurrencyString.equals(eur)){
                retrievedInputRate=1.0;
            }
            else {
                retrievedInputRate = x.getDouble(inputCurrencyString);
            }
            if(outputCurrencyString.equals(eur)){
                retrievedOutputRate=1.0;
            }
            else {
                retrievedOutputRate = x.getDouble(outputCurrencyString);
            }

            /**
             * Calculate conversion rate based on rates of input and output currencies
             */
            Double conversionRate = retrievedInputRate / retrievedOutputRate;

            Double convertedOutput = (Double.parseDouble(inputValueNum) * conversionRate);

            /**
             * Outputs values to app screen
             */

            EditText outputValue = findViewById(R.id.CurrencyValueOutput);
            outputValue.setText(convertedOutput.toString());
        } catch
        (JSONException joe) {
            ret = "JSON exception";
        }
    }

    /**
     * Create toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.page_menu, menu);
        return true;
    }

    /**
     * Switch case for options in tool bar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            /**
             * If user selects picture for currency exchange, snackbar notifies user that they are already
             * on this page
             */
            case R.id.currency_exchange_page_menu_link:
                Snackbar sn = Snackbar.make(findViewById(R.id.navigation_toolbar), "You are already on this page.", Snackbar.LENGTH_LONG);
                //sn.setAction("finish", v->finish());
                sn.show();

                break;
            /**
             * If user selects other options, navigates them to those pages.
             */
            case R.id.car_charger_page_menu_link:
                Intent goToCarChargerFinder = new Intent(CurrencyExchangeMain.this, CarChargerFinder.class);
                CurrencyExchangeMain.this.startActivityForResult(goToCarChargerFinder, 10);
                break;
            case R.id.recipe_page_menu_link:
                Intent goRecipePage = new Intent(CurrencyExchangeMain.this, RecipePage.class);
                CurrencyExchangeMain.this.startActivityForResult(goRecipePage, 10);
                break;
            case R.id.news_page_menu_link:
                Intent goToNewsPage = new Intent(CurrencyExchangeMain.this, NewsPage.class);
                CurrencyExchangeMain.this.startActivityForResult(goToNewsPage, 10);
                break;
        }
        return true;
    }
}

