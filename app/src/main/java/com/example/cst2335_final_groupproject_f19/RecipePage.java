package com.example.cst2335_final_groupproject_f19;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecipePage extends AppCompatActivity {
    private ArrayList<Recipe> recipeList =new ArrayList<>();

    int positionClicked =0;
    MyOwnAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list_page);
        recipeList.add(new Recipe("publisher","f2f_url","title","source_url","recipe_id","image_url",0.0,"publisher_url"));
        ListView recipeList =findViewById(R.id.the_list);
        recipeList.setAdapter(listAdapter=new MyOwnAdapter());
        recipeList.setOnItemClickListener( ( lv, vw, pos, id) ->{

            Toast.makeText( RecipePage.this,
                    "You clicked on:" + pos, Toast.LENGTH_SHORT).show();

        } );
    }

    protected class MyOwnAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return recipeList.size();
        }

        public Recipe getItem(int position){
            return recipeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View old, ViewGroup parent) {
            View newView=old;

            if(old==null)
                newView = getLayoutInflater().inflate(R.layout.recipe_list_row, null);

            TextView RecipeTitle=newView.findViewById(R.id.title);
            Recipe thisRow=getItem(position);

            RecipeTitle.setText("Title:" + thisRow.getTitle());

            //return the row:
            return newView;
        }
    }

}
