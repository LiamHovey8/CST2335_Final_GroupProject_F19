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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RecipePage extends AppCompatActivity {
    //an array list of objects that hold the data of recipes
    private ArrayList<Recipe> recipeList =new ArrayList<>();
    //will be used later don't worry
    public static final int EMPTY_ACTIVITY = 345;
    public static final String title = "title";
    public static final String imageURL = "imageURL";
    public static final String webPageURL = "webPageURL";
    //a copy of the adapter
    MyOwnAdapter recipeAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.page_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.recipe_page_menu_link:

                break;
            case R.id.car_charger_page_menu_link:
                Intent goToCarChargerFinder = new Intent(RecipePage.this, CarChargerFinder.class);
                RecipePage.this.startActivityForResult(goToCarChargerFinder, 10);
                break;
            case R.id.currency_exchange_page_menu_link:
                Intent goToCurrencyExchange = new Intent(RecipePage.this, CurrencyExchangeMain.class);
                RecipePage.this.startActivityForResult(goToCurrencyExchange, 10);
                break;
            case R.id.news_page_menu_link:
                Intent goToNewsPage = new Intent(RecipePage.this, NewsPage.class);
                RecipePage.this.startActivityForResult(goToNewsPage, 10);
                break;
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list_page);
        Toolbar tBar = (Toolbar)findViewById(R.id.navigation_toolbar);
        boolean isTablet=findViewById(R.id.recipe_frame_layout)!=null;
        setSupportActionBar(tBar);
        //find the progress bar
        ProgressBar progressBar = findViewById(R.id.recipe_progress_bar);
        //set the progress bar to be invisible so that it can be shown as a place holder
        progressBar.setVisibility(View.INVISIBLE);
        //the edit text box for search
        EditText recipeSearchText=findViewById(R.id.search_recipe_bar);
        //find the list view
        ListView recipeListView =findViewById(R.id.the_recipe_list);
        //use the adapter populate the list
        recipeListView.setAdapter(recipeAdapter=new MyOwnAdapter());
        //set Toasts and Snackbars to give information about the Recipe
        //will probably do something else later
        Button recipeSearchButton=findViewById(R.id.search_recipe);
        recipeSearchButton.setOnClickListener(clik ->{
            recipeList.clear();
            String searchText = recipeSearchText.getText().toString();
            if(searchText.equals("Chicken Breast")||searchText.equals("Lasagna")) {
                RecipeQuery newsQuery = new RecipeQuery();
                progressBar.setVisibility(View.VISIBLE);
                newsQuery.execute(searchText);
            }else {
                alertExample();
            }
        });
        recipeListView.setOnItemClickListener( ( lv, vw, pos, id) ->{
            Bundle recipeInfo= new Bundle();
            recipeInfo.putString("title",recipeList.get(pos).getTitle());
            recipeInfo.putString("imageURL",recipeList.get(pos).getImage_url());
            recipeInfo.putString("webPageURL",recipeList.get(pos).getF2f_url());
            if(isTablet){
                RecipeFragment rFragment=new RecipeFragment();
                rFragment.setArguments(recipeInfo);
                rFragment.setTablet(true);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.recipe_frame_layout,rFragment )
                        .addToBackStack("AnyName")
                        .commit();
            }else{
                Intent nextActivity=new Intent(RecipePage.this,EmptyRecipeActivity.class);
                nextActivity.putExtras(recipeInfo);
                startActivityForResult(nextActivity,EMPTY_ACTIVITY);
            }
            Toast.makeText( RecipePage.this,
                    "You clicked on: " + recipeList.get(pos).getTitle(), Toast.LENGTH_SHORT).show();
            Snackbar.make(vw,"the publisher is "+recipeList.get(pos).getPublisher(),Snackbar.LENGTH_LONG).show();
        } );

    }
    private void alertExample() {
        View middle = getLayoutInflater().inflate(R.layout.recipe_dialog_notification, null);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton(getString(R.string.recipe_positive), (dialog, id) -> {
            RecipeQuery newsQuery = new RecipeQuery();
            ProgressBar progressBar=findViewById(R.id.recipe_progress_bar);
            progressBar.setVisibility(View.VISIBLE);
            newsQuery.execute("Chicken Breast");
        });
        builder.setNegativeButton(getString(R.string.recipe_negative), (dialog, id) -> {
            RecipeQuery newsQuery = new RecipeQuery();
            ProgressBar progressBar=findViewById(R.id.recipe_progress_bar);
            progressBar.setVisibility(View.VISIBLE);
            newsQuery.execute("Lasagna");
        });
        builder.setView(middle);

        builder.create().show();
    }
    private class RecipeQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPostExecute(String s) {
            ProgressBar bar = findViewById(R.id.recipe_progress_bar);
            bar.setVisibility(View.INVISIBLE);
            ListView recipeList = findViewById(R.id.the_recipe_list);
            recipeList.setAdapter(recipeAdapter = new MyOwnAdapter());

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ProgressBar progressBar=findViewById(R.id.recipe_progress_bar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected String doInBackground(String... strings) {
            String ret =null;
            JSONObject jObject;
            try {       // Connect to the server:
                String ChickenURL="http://torunski.ca/FinalProjectChickenBreast.json";
                String LasagnaURL="http://torunski.ca/FinalProjectLasagna.json";
                if(strings[0].equals("Chicken Breast")) {
                    URL url = new URL(ChickenURL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inStream = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 5);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");

                    }
                    String result = sb.toString();
                    jObject = new JSONObject(result);
                }else{
                    URL url = new URL(LasagnaURL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inStream = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 5);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    String result = sb.toString();
                    jObject = new JSONObject(result);
                }
                //URL UVURL = new URL(queryUV);

                JSONArray recipeArray = jObject.getJSONArray("recipes");
                for (int i = 0; i < recipeArray.length(); i++) {
                    JSONObject o=recipeArray.getJSONObject(i);
                    String publisher=o.getString("publisher");
                    String f2f_url=o.getString("f2f_url");
                    String title=o.getString("title");
                    String source_url=o.getString("source_url");
                    String image_url=o.getString("image_url");
                    double social_rank=o.getDouble("social_rank");
                    String publisher_url=o.getString("publisher_url");
                    recipeList.add(new Recipe (publisher,f2f_url,title,source_url,image_url,social_rank,publisher_url));
                    int progress = ((i + 1) * 100 /recipeArray.length());
                    publishProgress(progress);

                }

            } catch (MalformedURLException e) {
                ret = "Malformed URL exception";
            } catch (IOException e) {
                ret = "IO Exception: WIFI not connected";
            } catch (JSONException e) {
                ret = "JSON exception";
            }
            return ret;
        }
    }


    private class MyOwnAdapter extends BaseAdapter {
        //the size of the array list we made earlier
        @Override
        public int getCount() {
            return recipeList.size();
        }
        //get a specific item in the list
        public Recipe getItem(int position){
            return recipeList.get(position);
        }
        //gets the position of the item
        @Override
        public long getItemId(int position) {
            return position;
        }
        //sets the individual row of the list
        public View getView(int position, View old, ViewGroup parent) {
            View newView=old;

            if(old==null)
                newView = getLayoutInflater().inflate(R.layout.recipe_list_row, null);

            TextView RecipeTitle=newView.findViewById(R.id.recipe_title);
            Recipe thisRow=getItem(position);

            RecipeTitle.setText(new StringBuilder().append("@string/").append(thisRow.getTitle()).toString());

            //return the row:
            return newView;
        }
    }

}
