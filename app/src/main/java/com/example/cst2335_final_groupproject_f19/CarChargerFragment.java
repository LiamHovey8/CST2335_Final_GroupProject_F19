package com.example.cst2335_final_groupproject_f19;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

/**
 * Class for displaying the fragment
 */
public class CarChargerFragment extends Fragment {
    /**
     * Gets a handle on a database object
     */
    private SQLiteDatabase carChargerDB;
    /**
     * Stores the value for tablet boolean
     */
    private boolean isTablet;
    /**
     * Stores the bundle of data from the previous activity
     */
    private Bundle dataFromActivity;

    /**
     * Sets fragment for tablet if boolean is true
     * @param tablet
     */
    public void setTablet(boolean tablet) {
        isTablet = tablet;
    }

    /**
     * Creates the view for the fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();

        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.car_charger_search_details, container, false);

        Toolbar tBar = result.findViewById(R.id.navigation_toolbar);

        // Shows the Car Charging Station details
        TextView locationText = result.findViewById(R.id.locationText);
        locationText.setText("Location: " + dataFromActivity.getString(CarChargerFinder.ITEM_LOCATION));

        TextView latitudeText = result.findViewById(R.id.latitudeText);
        latitudeText.setText("Latitude: " + dataFromActivity.getString(CarChargerFinder.ITEM_LATITUDE));

        TextView longitudeText = result.findViewById(R.id.longitudeText);
        longitudeText.setText("Longitude: " + dataFromActivity.getString(CarChargerFinder.ITEM_LONGITUDE));

        TextView telephoneText = result.findViewById(R.id.telephoneText);
        telephoneText.setText("Contact Phone: " + dataFromActivity.getString(CarChargerFinder.ITEM_CONTACT));

        Button directionsButton = result.findViewById(R.id.directionsButton);
        if (directionsButton != null) {
            // On click go to Google Maps directions
            directionsButton.setOnClickListener(v -> {
                Uri gmmIntentUri = Uri.parse("geo:" + dataFromActivity.getString(CarChargerFinder.ITEM_LATITUDE) + "," + dataFromActivity.getString(CarChargerFinder.ITEM_LONGITUDE));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            });
        }

        Button favouriteButton = result.findViewById(R.id.favouriteButton);
        if (favouriteButton != null) {
            // On click add to favourites list/database
            favouriteButton.setOnClickListener(v -> {
                if (isTablet) { // For the tablet both the ListView and details are on the screen
                    CarChargerFinder tabletParent = (CarChargerFinder) getActivity();

                    tabletParent.setSupportActionBar(tBar);

                    // Gets a database
                    CarChargerDatabaseHelper dbOpener = new CarChargerDatabaseHelper(tabletParent);
                    carChargerDB = dbOpener.getWritableDatabase();

                    // Add to the database and get the new ID
                    ContentValues newRowValues = new ContentValues();
                    // Put string location in the LOCATION column
                    newRowValues.put(CarChargerDatabaseHelper.COL_LOCATION, dataFromActivity.getString(CarChargerFinder.ITEM_LOCATION));
                    newRowValues.put(CarChargerDatabaseHelper.COL_LATITUDE, dataFromActivity.getString(CarChargerFinder.ITEM_LATITUDE));
                    newRowValues.put(CarChargerDatabaseHelper.COL_LONGITUDE, dataFromActivity.getString(CarChargerFinder.ITEM_LONGITUDE));
                    newRowValues.put(CarChargerDatabaseHelper.COL_CONTACT, dataFromActivity.getString(CarChargerFinder.ITEM_CONTACT));

                    carChargerDB.insert(CarChargerDatabaseHelper.TABLE_NAME, null, newRowValues);

                    Intent goToFavourites = new Intent(tabletParent, CarChargerFavourites.class);

                    // Starts the next activity
                    startActivity(goToFavourites);
                } else { // For the favourites button on the phone
                    CarChargerFrame phoneParent = (CarChargerFrame) getActivity();

                    phoneParent.setSupportActionBar(tBar);

                    // Gets a database
                    CarChargerDatabaseHelper dbOpener = new CarChargerDatabaseHelper(phoneParent);
                    carChargerDB = dbOpener.getWritableDatabase();

                    // Add to the database and get the new ID
                    ContentValues newRowValues = new ContentValues();
                    // Put string location in the LOCATION column
                    newRowValues.put(CarChargerDatabaseHelper.COL_LOCATION, dataFromActivity.getString(CarChargerFinder.ITEM_LOCATION));
                    newRowValues.put(CarChargerDatabaseHelper.COL_LATITUDE, dataFromActivity.getString(CarChargerFinder.ITEM_LATITUDE));
                    newRowValues.put(CarChargerDatabaseHelper.COL_LONGITUDE, dataFromActivity.getString(CarChargerFinder.ITEM_LONGITUDE));
                    newRowValues.put(CarChargerDatabaseHelper.COL_CONTACT, dataFromActivity.getString(CarChargerFinder.ITEM_CONTACT));

                    carChargerDB.insert(CarChargerDatabaseHelper.TABLE_NAME, null, newRowValues);

                    Intent goToFavourites = new Intent(phoneParent, CarChargerFavourites.class);

                    // Starts the next activity
                    startActivity(goToFavourites);
                }
            });
        }
        return result;
    }
}
