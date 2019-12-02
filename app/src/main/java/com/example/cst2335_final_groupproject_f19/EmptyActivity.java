package com.example.cst2335_final_groupproject_f19;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class EmptyActivity extends AppCompatActivity {
    /**
     * Receives bundle and sets up fragment
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        /**
         * Receive bundle
         */
        Bundle dataToPass = getIntent().getExtras();

        //This is copied directly from FragmentExample.java lines 47-54
        CurrencyDetailFragment dFragment = new CurrencyDetailFragment();
        /**
         * Pass data to the fragment
         */
        dFragment.setArguments( dataToPass );
        /**
         * Tells fragment that it's on a phone
         */
        dFragment.setTablet(false);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentLocation, dFragment)
                .addToBackStack("AnyName")
                .commit();
    }
}
