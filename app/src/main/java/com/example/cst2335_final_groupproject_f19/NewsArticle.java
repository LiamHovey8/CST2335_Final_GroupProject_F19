package com.example.cst2335_final_groupproject_f19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;


public class NewsArticle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_article);
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
        });




    }
}
