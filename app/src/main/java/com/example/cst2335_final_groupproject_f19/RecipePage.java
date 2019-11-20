package com.example.cst2335_final_groupproject_f19;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RecipePage extends AppCompatActivity {
<<<<<<< HEAD
    //an array list of objects that hold the data of recipes
    private ArrayList<Recipe> recipeList =new ArrayList<>();
    //will be used later don't worry
    int positionClicked =0;
    //a copy of the adapter
    BaseAdapter listAdapter;
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
                Intent goToCurrencyExchange = new Intent(RecipePage.this, CurrencyExchange.class);
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
        setSupportActionBar(tBar);
        //find the progress bar
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        //set the progress bar to be visible so that it can be shown as a place holder
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(50);
        //fake data for use until data can be retreved
        recipeList.add(new Recipe("A Spicy Perspective","http://food2fork.com/view/bf5134","Vietnamese Banh Mi Salad","http://www.aspicyperspective.com/2013/01/vietnamese-chicken-salad.html","bf5134","http://static.food2fork.com/IMG_28181180x1807551.jpg",99.99896179226475,"http://www.aspicyperspective.com"));
        recipeList.add(new Recipe("A Spicy Perspective","http://food2fork.com/view/aa60ec","Butter Chicken Sliders with Pickled Mango Slaw","http://www.aspicyperspective.com/2013/03/butter-chicken-sliders-big-land-olakes-giveaway.html","aa60ec","http://static.food2fork.com/IMG_35501180x1803eee.jpg",99.99888808120132,"http://www.aspicyperspective.com"));
        recipeList.add(new Recipe("BBC Good Food","http://food2fork.com/view/495802","Chicken cacciatore","http://www.bbcgoodfood.com/recipes/4251/chicken-cacciatore","495802","http://static.food2fork.com/4251_MEDIUM71f0.jpg",99.99999994031722,"http://www.bbcgoodfood.com"));
        //find the list view
        ListView recipeListView =findViewById(R.id.the_recipe_list);
        //use the adapter populate the list
        recipeListView.setAdapter(listAdapter=new MyOwnAdapter());
        //set Toasts and Snackbars to give information about the Recipe
        //will probably do something else later
        recipeListView.setOnItemClickListener( ( lv, vw, pos, id) ->{

            Toast.makeText( RecipePage.this,
                    "You clicked on: " + recipeList.get(pos).getTitle(), Toast.LENGTH_SHORT).show();
            Snackbar.make(vw,"the publisher is "+recipeList.get(pos).getPublisher(),Snackbar.LENGTH_LONG).show();
        } );
    }

    private class MyNetworkQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ProgressBar progressBar=findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
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
=======
//    private ArrayList<Recipe> recipeList =new ArrayList<>();
//
//    int positionClicked =0;
//    MyOwnAdapter listAdapter;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.recipe_list_page);
//    }
//
//    protected class MyOwnAdapter extends BaseAdapter {
//        @Override
//        public int getCount() {
//            return recipeList.size();
//        }
//
//        public Recipe getItem(int position){
//            return recipeList.get(position);
//        }
//
//        public View getView(int position, View old, ViewGroup parent)
//        {
//            LayoutInflater inflater = getLayoutInflater();
//
//            View newView = inflater.inflate(R.layout.recipe_list_row, parent, false );
//
//            Recipe thisRow = getItem(position);
//            TextView rowName = (TextView)newView.findViewById(R.id.row_name);
//
//
//            rowName.setText("Name:" + thisRow.getName());
//
//            //return the row:
//            return newView;
//        }
//
//        public long getItemId(int position)
//        {
//            return getItem(position).getId();
//        }
//    }
>>>>>>> 6d75298c7cf426cd86b4b52b68cebe6900e460c6

}
