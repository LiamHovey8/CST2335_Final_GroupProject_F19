package com.example.cst2335_final_groupproject_f19;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class CarChargerFinder extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electric_car_view_page);
    }

    private class ForecastQuery extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String progress = "25%";
            return progress;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values[0]);
            // Update GUI stuff only:
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
