package com.example.cst2335_final_groupproject_f19;


import android.app.Activity;
import android.content.Intent;
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
        TextView recipeURL = (TextView)result.findViewById(R.id.recipe_detailed_url);
        ImageView recipeImage = (ImageView)result.findViewById(R.id.recipe_image);
   //     message.setText(dataFromActivity.getString(RecipeFragment.ITEM_SELECTED));

        //show the id:
     //   TextView idView = (TextView)result.findViewById(R.id.idText);
//        idView.setText("ID=" + id);

        // get the delete button, and add a click listener:
//        Button deleteButton = (Button)result.findViewById(R.id.deleteButton);
  /*//      deleteButton.setOnClickListener( clk -> {

            if(isTablet) { //both the list and details are on the screen:
                RecipePage parent = (RecipePage) getActivity();
  //              parent.deleteMessageId((int)id); //this deletes the item and updates the list


                //now remove the fragment since you deleted it from the database:
                // this is the object to be removed, so remove(this):
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
            //for Phone:
            else //You are only looking at the details, you need to go back to the previous list page
            {
                EmptyRecipeActivity parent = (EmptyRecipeActivity) getActivity();
                Intent backToFragmentExample = new Intent();
    //            backToFragmentExample.putExtra(RecipeFragment.ITEM_ID, dataFromActivity.getLong(RecipeFragment.ITEM_ID ));

                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                parent.finish(); //go back
            }
        });*/
        return result;
    }
}
