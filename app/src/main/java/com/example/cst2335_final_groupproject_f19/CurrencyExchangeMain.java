package com.example.cst2335_final_groupproject_f19;



import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import android.widget.ListView;
import java.net.URL;


import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        x=null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_exchange_home);

        Conversion anEx = new Conversion(1.0, 1.5, "CAD", "USD");
        Conversion anEx2 = new Conversion(1.0, 1.4, "CAD", "EUR");

        favouritesArrayList.add(anEx);
        favouritesArrayList.add(anEx2);


        ListView theListView = findViewById(R.id.favouritesList);


        theListView.setAdapter(theAdapter = new myListAdapter());

        theListView.setOnItemClickListener((listView, aView, position, idNum) -> {
            Toast.makeText(this, "This is the conversion from " + favouritesArrayList.get(position).getInputCurrency() + " to " + favouritesArrayList.get(position).getOutputCurrency(), Toast.LENGTH_LONG).show();
        });



        runConversionButton = findViewById(R.id.runConversionButton);


        if (runConversionButton != null) {
            runConversionButton.setOnClickListener(v -> decision());
        }




        showHelpMenuButton = findViewById(R.id.showHelpMenuButton);
        if (showHelpMenuButton != null) {

            showHelpMenuButton.setOnClickListener(v -> alertExample());
        }





//
//        Button runSnackbar = (Button) findViewById(R.id.searchAsSnackbarButton);
//
//        runSnackbar.setOnClickListener(view -> {
//
//            Snackbar.make(findViewById(android.R.id.content), "sample snackbar", Snackbar.LENGTH_LONG).show();
//
//        });
    }

    public void decision(){
        retrieveConversions aConversion = new retrieveConversions();
        if(x==null)
            {aConversion.execute();}
        else{alreadyRun(); }
    }

    private class retrieveConversions extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... args) {

            String conversionsURL1 = "https://api.exchangeratesapi.io/latest";

            String ret = null;
            try {
                URL conversionsURL = new URL(conversionsURL1);
                HttpURLConnection urlConnection = (HttpURLConnection) conversionsURL.openConnection();
                InputStream inStream = urlConnection.getInputStream();
                BufferedReader conversionReader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = conversionReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                jObject = new JSONObject(result);


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

                x = jObject.getJSONObject("rates");

                alreadyRun();


            } catch
            (JSONException joe) {
                ret = "JSON exception";
            }

        }


    }


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

    public void alreadyRun() {
        String ret = null;
        try {
            EditText inputValue = findViewById(R.id.currencyValueInput);
            String inputValueNum = inputValue.getText().toString();

            EditText inputCurrency = findViewById(R.id.inputCurrency);
            String inputCurrencyString = inputCurrency.getText().toString();

            EditText outputCurrency = findViewById(R.id.outputCurrency);
            String outputCurrencyString = outputCurrency.getText().toString();

            String eur="EUR";
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

            Double conversionRate = retrievedInputRate / retrievedOutputRate;

            Double convertedOutput = (Double.parseDouble(inputValueNum) * conversionRate);

            EditText outputValue = findViewById(R.id.CurrencyValueOutput);
            outputValue.setText(convertedOutput.toString());
        } catch
        (JSONException joe) {
            ret = "JSON exception";
        }
    }
}

