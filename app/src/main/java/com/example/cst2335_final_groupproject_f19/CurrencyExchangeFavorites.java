package com.example.cst2335_final_groupproject_f19;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class CurrencyExchangeFavorites extends AppCompatActivity {

    /**
     * Exchange item in database
     */
    public static final String ITEM_SELECTED = "ITEM";
    /**
     * Exchange item ID in database
     */
    public static final String ITEM_ID = "ID";
    public static final int EMPTY_ACTIVITY = 345;
    public static final int PUSHED_DELETE = 35;
    /**
     * Base adapter for array list
     */
    myListAdapter theAdapter;
    /**
     * Array list for favourite conversions
     */
    ArrayList<Conversion> favouritesArrayList2 = new ArrayList<>();

    CurrencyOpenHelper dbOpener;
    SQLiteDatabase db;


    /**
     * OnCreate runs on startup
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Sets layout of class
         */
        setContentView(R.layout.currency_exchange_favorites);
        /**
         * Creates list view
         */
        ListView theListView = findViewById(R.id.favouritesList2);
        /**
         * Establishes base adapter for list view
         */
        theListView.setAdapter(theAdapter = new myListAdapter());
        /**
         * Create values based on edit texts
         */
        EditText favInput = findViewById(R.id.favInput);
        EditText favOutput = findViewById(R.id.favOutput);
        /**
         * Create boolean to check if emulator is a tablet
         */
        boolean isTablet=findViewById(R.id.fragmentLocation)!=null;//



        /**
         * Create on click listener to pass data to fragment if list item is selected
         * runs in two different ways based on if emulator is tablet or phone
         */
        theListView.setOnItemClickListener( (list, item, position, id) -> {
            Bundle dataToPass = new Bundle();


            dataToPass.putString(ITEM_SELECTED, favouritesArrayList2.get(position).getListPiece());

            dataToPass.putLong(ITEM_ID, favouritesArrayList2.get(position).getId());
            if (isTablet) {
                CurrencyDetailFragment dFragment = new CurrencyDetailFragment(); //add a DetailFragment
                dFragment.setArguments(dataToPass); //pass it a bundle for information
                dFragment.setTablet(true);  //tell the fragment if it's running on a tablet or not
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                        .addToBackStack("AnyName") //make the back button undo the transaction
                        .commit(); //actually load the fragment.
            } else //isPhone
            {
                Intent nextActivity = new Intent(CurrencyExchangeFavorites.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivityForResult(nextActivity, EMPTY_ACTIVITY); //make the transition
            }
            });
        /**
         * Create database for favourites
         */
        dbOpener = new CurrencyOpenHelper(this);
        db = dbOpener.getWritableDatabase();

        /**
         * Query all results from the database
         */
        String [] columns = {CurrencyOpenHelper.COL_ID, CurrencyOpenHelper.COL_InputCurr, CurrencyOpenHelper.COL_InputVal, CurrencyOpenHelper.COL_OutputCurr, CurrencyOpenHelper.COL_OutputVal};
        Cursor results = db.query(false, CurrencyOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        /**
         * Find all the column indices
         */
        int inputCurrColIndex = results.getColumnIndex(CurrencyOpenHelper.COL_InputCurr);
        int inputValColIndex = results.getColumnIndex(CurrencyOpenHelper.COL_InputVal);
        int outputCurrColIndex = results.getColumnIndex(CurrencyOpenHelper.COL_OutputCurr);
        int outputValColIndex = results.getColumnIndex(CurrencyOpenHelper.COL_OutputVal);
        int idColIndex = results.getColumnIndex(CurrencyOpenHelper.COL_ID);
        /**
         * Add database items to array list
         */
        while(results.moveToNext())
        {
            String inputCurrency = results.getString(inputCurrColIndex);
            String inputValue = results.getString(inputValColIndex);
            String outputCurrency = results.getString(outputCurrColIndex);
            String outputValue = results.getString(outputValColIndex);


            long id = results.getLong(idColIndex);

            //add the new message to the array list:
            favouritesArrayList2.add(new Conversion(0.0, 0.0, inputCurrency, outputCurrency, id));

        }
        /**
         * Button to add new favourite conversion to database and list
         */
        Button addNewFav = (Button)findViewById(R.id.addFavourite);

        /**
         * Adds new favourite conversion to database and updates array list and list view
         */
        addNewFav.setOnClickListener(s -> {
            String stringNewFavInput = favInput.getText().toString();
            String stringNewFavOutput = favOutput.getText().toString();



            //add to the database and get the new ID
            ContentValues newRowValues = new ContentValues();
            //put string name in the message column:
            newRowValues.put(CurrencyOpenHelper.COL_InputCurr, stringNewFavInput);
            newRowValues.put(CurrencyOpenHelper.COL_OutputCurr, stringNewFavOutput);

            //insert in the database:
            long newId = db.insert(CurrencyOpenHelper.TABLE_NAME, null, newRowValues);


            Conversion newFavConversion = new Conversion(1.0,1.0,stringNewFavInput,stringNewFavOutput, newId);
            favouritesArrayList2.add(newFavConversion);
            theAdapter.notifyDataSetChanged();

        });

    }

    /**
     * Base adapter for currency
     */
    public class myListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return favouritesArrayList2.size();
        }

        @Override
        public Conversion getItem(int position) {
            return favouritesArrayList2.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View aRow = convertView;

            if (convertView == null)
                aRow = getLayoutInflater().inflate(R.layout.a_conversion, null);


            TextView rowText = aRow.findViewById(R.id.aConversion);
            Conversion thisConversion = getItem(position);
            rowText.setText(thisConversion.getListPiece());

            return aRow;

        }
        public void deleteItem(long id) {
            for (Conversion x : favouritesArrayList2) {
                if (x.getId() == id) {
                    favouritesArrayList2.remove(x);
                    notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    /**
     * If delte button is selected, calls method to delete selected item from database
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EMPTY_ACTIVITY)
        {
            if(resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {
                long id = data.getLongExtra(ITEM_ID, 0);
                deleteConversionId(id);
            }
        }
    }

    /**
     * deletes selected item from database
     * @param id
     */
    public void deleteConversionId(long id)
    {
        Log.e("Delete this conversion:" , " id="+id);

        String idString = String.valueOf(id);
        db.delete(CurrencyOpenHelper.TABLE_NAME, CurrencyOpenHelper.COL_ID + "=?", new String[] {idString});
        setResult(PUSHED_DELETE);
        theAdapter.deleteItem(id);

    }
}
