package com.example.cst2335_final_groupproject_f19;
/**
 * Page to display details of an article and save article
 * Author: Lindsay Deng
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * display detailed information about an article
 * allow user to save to article
 * send saved article information to database
 */

public class NewsArticle extends AppCompatActivity {

    /**
     * get article information from news main page
     * Connect and save to database     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_article);
        Toolbar tBar = (Toolbar) findViewById(R.id.navigation_toolbar);
        setSupportActionBar(tBar);
        SQLiteDatabase db;


        Intent dataFromPreviousPage = getIntent();
        String title = dataFromPreviousPage.getStringExtra("newsTitle");
        String description = dataFromPreviousPage.getStringExtra("newsDescription");
        String openURL = dataFromPreviousPage.getStringExtra("newsURL");

        TextView savedTitle = findViewById(R.id.newsTitle);
        savedTitle.setText(title);

        TextView savedDescription = findViewById(R.id.newsDescription);
        savedDescription.setText(description);

        Button gotoURL = findViewById(R.id.newsGoToPage);


        gotoURL.setOnClickListener(clik ->{
            Intent webOpen = new Intent(android.content.Intent.ACTION_VIEW);
            webOpen.setData(Uri.parse(openURL));
            startActivity(webOpen);

                });



        NewsDatabaseHelper dbOpener = new NewsDatabaseHelper(this);
        db = dbOpener.getWritableDatabase();
        String [] columns = {NewsDatabaseHelper.COL_ID,
                NewsDatabaseHelper.COL_NAME,
                NewsDatabaseHelper.COL_AUTHOR, NewsDatabaseHelper.COL_TITLE,
                NewsDatabaseHelper.COL_DESCRIPTION,  NewsDatabaseHelper.COL_URL,
                NewsDatabaseHelper.COL_URL_TO_IMAGE,
                NewsDatabaseHelper.COL_PUBLISHED_AT,
                NewsDatabaseHelper.COL_CONTENT
        };
        Cursor results = db.query(false, NewsDatabaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        int idColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_ID);
        int nameColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_NAME);
        int authorColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_AUTHOR);
        int titleColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_TITLE);
        int descriptionColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_DESCRIPTION);
        int urlColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_URL);
        int urlToImageColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_URL_TO_IMAGE);
        int publishedAtColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_PUBLISHED_AT);
        int contentColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_CONTENT);


        while(results.moveToNext())
        {
            String _id = results.getString(idColIndex);
            String name = results.getString(nameColIndex);
            String author = results.getString(authorColIndex);
            String _title = results.getString(titleColIndex);
            String _description = results.getString(descriptionColIndex);
            String _url = results.getString(urlColIndex);
            String urlToImage = results.getString(urlToImageColIndex);
            String published = results.getString(publishedAtColIndex);
            String content = results.getString(contentColIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
//            contactsList.add(new Contact(name, email, id));
        }

        Button saveButton = findViewById(R.id.newsSave);

        saveButton.setOnClickListener( click ->
        {

            ContentValues newRowValues = new ContentValues();

            newRowValues.put(NewsDatabaseHelper.COL_TITLE, title);

            newRowValues.put(NewsDatabaseHelper.COL_DESCRIPTION, description);
            newRowValues.put(NewsDatabaseHelper.COL_URL, openURL);

            long newId = db.insert(NewsDatabaseHelper.TABLE_NAME, null, newRowValues);
            Toast.makeText(this, "Article added to Saved Articles", Toast.LENGTH_LONG).show();


        });

    }

    /**
     * display tool bars to go to other pages
     * @param item
     * @return true if successful
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.recipe_page_menu_link:
                Intent goToNewsPage = new Intent(NewsArticle.this, RecipePage.class);
                NewsArticle.this.startActivityForResult(goToNewsPage, 10);
                break;
            case R.id.car_charger_page_menu_link:
                Intent goToCarChargerFinder = new Intent(NewsArticle.this, CarChargerFinder.class);
                NewsArticle.this.startActivityForResult(goToCarChargerFinder, 10);
                break;
            case R.id.currency_exchange_page_menu_link:
                Intent goToCurrencyExchange = new Intent(NewsArticle.this, CurrencyExchangeMain.class);
                NewsArticle.this.startActivityForResult(goToCurrencyExchange, 10);
                break;

            case R.id.news_page_menu_link:

                break;
        }
        return true;
    }

    /**
     * inflator for displaying tool bars
     * @param menu
     * @return true if successful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.page_menu, menu);
        return true;
    }
}
