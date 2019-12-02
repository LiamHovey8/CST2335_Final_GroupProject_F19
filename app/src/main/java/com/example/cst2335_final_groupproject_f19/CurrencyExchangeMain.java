package com.example.cst2335_final_groupproject_f19;



import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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





    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_SR = "Send or Receive";
    public static final String ITEM_ID = "ID";
    public static final int EMPTY_ACTIVITY = 345;
    public static final int PUSHED_DELETE = 35;

    /**
     * Open helper for database
     */
    public CurrencyOpenHelper dbOpener;
    /**
     * SQLiteDatabase
     */
    public SQLiteDatabase db;
    /**
     * Button to run conversions
     */
    Button runConversionButton;
    /**
     * Button to display instruction dialog box
     */
    Button showHelpMenuButton;

    int duration = 10;
    View view;
    /**
     * Array list for sample calculations
     */
    ArrayList<Conversion> favouritesArrayList = new ArrayList<>();
    /**
     * Base adapter for list
     */
    BaseAdapter theAdapter;
    /**
     * JSONObject to store results of doInBackground
     */
    JSONObject jObject;
    /**
     * JSONObject for passing array
     */
    JSONObject x;
    /**
     * values user will enter
     */
    Double retrievedInputRate;
    Double retrievedOutputRate;
    /**
     * Progress bar to show doInBackground progress
     */
    ProgressBar showProgressBar;

    /**
     * SharedPreferences for storing input and output currencies
     */
    private SharedPreferences mPreferences;
    private SharedPreferences mPreferences2;
    /**
     * EditTexts where users will input currencies
     */
    protected EditText inputCurrency;
    protected EditText outputCurrency;
    /**
     * Editors for shared preferences
     */
    protected SharedPreferences.Editor editor;//Error here, fixed
    protected SharedPreferences.Editor editor2;//Error here, fixed




    /**
    onCreate function is the first command to run in the project.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Set layout
         */
        setContentView(R.layout.currency_exchange_home);
        /**
         * Create toolbar
         */
        Toolbar tBar = (Toolbar)findViewById(R.id.navigation_toolbar);
        setSupportActionBar(tBar);
        /**
         * Declare boolean to check if emulator is a tablet
         */
        boolean isTablet=findViewById(R.id.fragmentLocation)!=null;//
        /**
         * Create shared preferences to save input and output currencies
         */
        mPreferences = getSharedPreferences("mySharedPref", MODE_PRIVATE);
        mPreferences2 = getSharedPreferences("mySharedPref2", MODE_PRIVATE);

        inputCurrency = findViewById(R.id.inputCurrency);
        outputCurrency = findViewById(R.id.outputCurrency);

        /**
         * Set information in shared preferences
         */
        String inputCurrencyPref = mPreferences.getString("mySharedPref", "");
        String outputCurrencyPref = mPreferences2.getString("mySharedPref2", "");
        editor = mPreferences.edit();
        editor2=mPreferences2.edit();

        inputCurrency.setText(inputCurrencyPref);
        outputCurrency.setText(outputCurrencyPref);


        /**
         * Create writeable database
         */

        dbOpener = new CurrencyOpenHelper(this);
        db = dbOpener.getWritableDatabase();

        /**
         * Creates layout and toolbar
         */


        showProgressBar=findViewById(R.id.progressBar);
        showProgressBar.setVisibility(View.VISIBLE);
        /**
         * Sets JSONObject x to null.
        */
        x=null;

        /**
         * Creates items for array list
         */
        Conversion anEx = new Conversion(1.0, 1.5, "CAD", "USD",1);
        Conversion anEx2 = new Conversion(1.0, 1.4, "CAD", "EUR",2);
        /**
         * Populate array list
         */
        favouritesArrayList.add(anEx);
        favouritesArrayList.add(anEx2);

        /**
         * Create list view
         */
        ListView theListView = findViewById(R.id.favouritesList);

        /**
         * Set adapter for the list view
         */
        theListView.setAdapter(theAdapter = new myListAdapter());

        /**
         * Set up on item click listener to run toast when items are selected
         */

        theListView.setOnItemClickListener((listView, aView, position, idNum) -> {
            Toast.makeText(this, getResources().getText(R.string.convFrom) + favouritesArrayList.get(position).getInputCurrency() + " -> " + favouritesArrayList.get(position).getOutputCurrency(), Toast.LENGTH_LONG).show();
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
        /**
         * Set up intent to navigate to currencyExchangeFavourites if the Edit Favourites button is selected
         */
        Button enterFavourites = findViewById(R.id.enterFavourite);
        if(enterFavourites != null) {
            enterFavourites.setOnClickListener(clk -> {
                Intent goToCurrencyExchangeFavourites = new Intent(CurrencyExchangeMain.this, CurrencyExchangeFavorites.class);

                CurrencyExchangeMain.this.startActivityForResult(goToCurrencyExchangeFavourites, 30);
            });
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
             * to post execute.
             * Publishes progress to toolbar at each step
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
                publishProgress(25);

                /**
                 * Establish inStream
                 */
                InputStream inStream = urlConnection.getInputStream();
                /**
                 * Establish buffered reader
                 */
                BufferedReader conversionReader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
                publishProgress(50);

                /**
                 * Create string builder to interpret online information
                 */
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = conversionReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                publishProgress(75);

                /**
                 * Create jObject from result of string builder
                 */

                jObject = new JSONObject(result);
                publishProgress(100);

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

        /**
         * Sets toolbar to visible when receiving progress updates
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            showProgressBar=findViewById(R.id.progressBar);
            showProgressBar.setVisibility(View.VISIBLE);

            showProgressBar.setProgress(values[0]);

        }

        /**
         * Makes progress bar invisible after execution complete
         * sets value of JSONObject x and runs alreadyRun() method
         * @param sentFromDoInBackground
         */
        @Override
        protected void onPostExecute(String sentFromDoInBackground) {

            super.onPostExecute(sentFromDoInBackground);
            showProgressBar.setVisibility(View.INVISIBLE);

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

                .setNegativeButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
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

            //inputCurrency = findViewById(R.id.inputCurrency);
            String inputCurrencyString = inputCurrency.getText().toString();

            //outputCurrency = findViewById(R.id.outputCurrency);
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

            Double convertedOutput = (Double.parseDouble(inputValueNum) / conversionRate);

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
                Snackbar sn = Snackbar.make(findViewById(R.id.navigation_toolbar), getResources().getText(R.string.snackbar), Snackbar.LENGTH_LONG);
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

    /**
     * Deletes exchanges selected from fragment based on their id
     * @param id
     */
    public void deleteMessageId(int id)
    {
        Log.e("Delete this message:" , " id="+id);
        //int deleteID=id-1;
        int deleteID=id;
        String idString = String.valueOf(deleteID);
        db.delete(CurrencyOpenHelper.TABLE_NAME, CurrencyOpenHelper.COL_ID + "=?", new String[] {idString});
        setResult(PUSHED_DELETE);
        favouritesArrayList.remove(id);
        theAdapter.notifyDataSetChanged();
        //Log.i("ViewContact", "Deleted " + numDeleted + " rows");

    }

    /**
     * Commits values to shared preferences if activity is paused
     */
    @Override
    protected void onPause() {
        super.onPause();



        editor.putString("mySharedPref", inputCurrency.getText().toString());
        editor2.putString("mySharedPref2", outputCurrency.getText().toString());

        editor.commit();
        editor2.commit();

    }
}

