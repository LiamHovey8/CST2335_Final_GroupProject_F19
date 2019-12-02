package com.example.cst2335_final_groupproject_f19;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class RecipeFragment extends Fragment {

    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;

    public void setTablet(boolean tablet) { isTablet = tablet; }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
 //       id = dataFromActivity.getLong(RecipeFragment.ITEM_ID );

        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_recipe, container, false);


        TextView recipeTitle = (TextView)result.findViewById(R.id.recipe_detailed_title);
        Button recipeURL = (Button) result.findViewById(R.id.recipe_detailed_url);
        ImageView recipeImage = (ImageView)result.findViewById(R.id.recipe_image);
        recipeTitle.setText(dataFromActivity.getString(RecipePage.title));
        recipeImage.setContentDescription(dataFromActivity.getString(RecipePage.imageURL));
        recipeURL.setText(dataFromActivity.getString(RecipePage.webPageURL));



        recipeURL.setOnClickListener(clik ->{
            Intent webOpen = new Intent(android.content.Intent.ACTION_VIEW);
            webOpen.setData(Uri.parse(dataFromActivity.getString("webPageURL")));
            startActivity(webOpen);

        });



        return result;
    }
}
