package com.example.cst2335_final_groupproject_f19;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Class that supports the fragment transaction
 */
public class CarChargerFrame extends AppCompatActivity {

    /**
     * Receives the bundled data and processes the fragment
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_charger_view_frame);

        // Gets the data that was passed from CarChargerFinder
        Bundle dataToPass = getIntent().getExtras();

        CarChargerFragment dFragment = new CarChargerFragment();
        dFragment.setArguments(dataToPass); // Passes data to the the fragment
        dFragment.setTablet(false); // Tells the fragment that it's on a phone
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.carChargerFragment, dFragment)
                .commit();
    }
}
