package com.example.cst2335_final_groupproject_f19;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecipePage extends AppCompatActivity {
    private ArrayList<Recipe> recipeList =new ArrayList<>();

    int positionClicked =0;
    MyOwnAdapter listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list_page);
    }

    protected class MyOwnAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return recipeList.size();
        }

        public Recipe getItem(int position){
            return recipeList.get(position);
        }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            View newView = inflater.inflate(R.layout.recipe_list_row, parent, false );

            Recipe thisRow = getItem(position);
            TextView rowName = (TextView)newView.findViewById(R.id.row_name);


            rowName.setText("Name:" + thisRow.getName());

            //return the row:
            return newView;
        }

        public long getItemId(int position)
        {
            return getItem(position).getId();
        }
    }

}
