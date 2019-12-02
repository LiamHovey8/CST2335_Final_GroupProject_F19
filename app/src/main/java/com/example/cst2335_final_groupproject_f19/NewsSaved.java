package com.example.cst2335_final_groupproject_f19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewsSaved extends AppCompatActivity {
    protected SQLiteDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_saved);

        NewsDatabaseHelper opener = new NewsDatabaseHelper(this);
        db =  opener.getWritableDatabase();

        TextView titleText =findViewById(R.id.newsSaved1);
        TextView descriptionText = findViewById(R.id.newsSaved2);

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


        String _title = null;
        String _description = null;
        String _url = null;
        while(results.moveToNext())
        {

            _title = results.getString(titleColIndex);
            _description = results.getString(descriptionColIndex);
            _url = results.getString(urlColIndex);

            long id = results.getLong(idColIndex);


        }


        titleText.setText(_title);
        descriptionText.setText(_description);

//        Button gotoURL = findViewById(R.id.newsSavedPage);
//
//
//        gotoURL.setOnClickListener(clik ->{
//            Intent webOpen = new Intent(android.content.Intent.ACTION_VIEW);
//            webOpen.setData(Uri.parse(_url));
//            startActivity(webOpen);
//
//        });





    }
}
