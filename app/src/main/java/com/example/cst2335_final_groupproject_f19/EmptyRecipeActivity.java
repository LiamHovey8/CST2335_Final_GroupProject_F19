package com.example.cst2335_final_groupproject_f19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EmptyRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_recipe);
        Bundle dataToPass = getIntent().getExtras();
        RecipeFragment dFragment = new RecipeFragment();
        dFragment.setArguments( dataToPass ); //pass data to the the fragment
        dFragment.setTablet(false); //tell the Fragment that it's on a phone.
        getSupportFragmentManager()
                .beginTransaction()
                        .add(R.id.recipe_frame_layout, dFragment)
                .addToBackStack("AnyName")
                .commit();
    }
}
