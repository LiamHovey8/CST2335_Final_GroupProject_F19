package com.example.cst2335_final_groupproject_f19;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class RecipeFragment extends Fragment {

    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;
    Bitmap bm=null;
    public static final int PUSHED_REMOVE = 35;
    public static final int PUSHED_ADD = 5338;

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
/*
    public class RecipeImageQuery extends AsyncTask<String, Integer, String> {
        Bitmap image;

        @Override
        protected void onPostExecute(String s) {
            bm = image;
        }

        @Override
        protected String doInBackground(String... strings) {
            if (tagName.equals("weather")) {
                String icon = xpp.getAttributeValue(null, "icon");
                String urlString = strings[0];
                if (!fileExistance(icon + ".png")) {
                    Bitmap imageRecipe = null;
                    URL ImageURL = new URL(urlString);
                    HttpURLConnection Connection = (HttpURLConnection) ImageURL.openConnection();
                    Connection.connect();
                    int responseCode = Connection.getResponseCode();
                    if (responseCode == 200) {
                        imageRecipe = BitmapFactory.decodeStream(Connection.getInputStream());
                    }
                    FileOutputStream outputStream = openFileOutput(icon + ".png", Context.MODE_PRIVATE);
                    imageRecipe.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    image = imageRecipe;
                    Log.i("image", icon + " image downloaded");
                    outputStream.flush();
                    outputStream.close();
                }
            }
            return null;
        }
    }*/
}
