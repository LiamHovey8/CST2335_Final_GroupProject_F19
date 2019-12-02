package com.example.cst2335_final_groupproject_f19;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class CurrencyDetailFragment extends Fragment {
    /**
     * Boolean for if emulator is tablet
     */
    private boolean isTablet;
    /**
     * Bundle of information from activity
     */
    private Bundle dataFromActivity;
    /**
     * Item ID
     */
    private long id;

    /**
     * Set boolean to indicate emulator is tablet
     * @param tablet
     */
    public void setTablet(boolean tablet) { isTablet = tablet; }

    /**
     * Create fragment layout
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return result
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Get information from activity
         */
        dataFromActivity = getArguments();
        /**
         * Set ID from previous activity
         */
        id = dataFromActivity.getLong(CurrencyExchangeMain.ITEM_ID );

        /**
         * Inflate the layout for this fragment
         */
        View result =  inflater.inflate(R.layout.currency_fragment_detail, container, false);

        /**
         * show the conversion
         */
        TextView message = (TextView)result.findViewById(R.id.message);
        message.setText(dataFromActivity.getString(CurrencyExchangeMain.ITEM_SELECTED));

        /**
         * show the id
         */
        TextView idView = (TextView)result.findViewById(R.id.idText);
        idView.setText("ID=" + id);

        /**
         * get the delete button and add a click listener:
         */
        Button deleteButton = (Button)result.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener( clk -> {

            if(isTablet) {
                CurrencyExchangeFavorites parent = (CurrencyExchangeFavorites)getActivity();
                /**
                 * deletes the item and updates the list
                 */
                parent.deleteConversionId((int)id);


                /**
                 * Remove fragment from database
                 */
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
            else
            {
                /**
                 * Return to previous page
                 */
                EmptyActivity parent = (EmptyActivity) getActivity();
                Intent backToFragmentExample = new Intent();
                backToFragmentExample.putExtra(CurrencyExchangeMain.ITEM_ID, dataFromActivity.getLong(CurrencyExchangeMain.ITEM_ID ));
                /**
                 * //send data back to CurrencyExchangeMain in onActivityResult()
                 */
                parent.setResult(Activity.RESULT_OK, backToFragmentExample);
                /**
                 * Go back
                 */
                parent.finish();
            }
        });
        return result;
    }
}
