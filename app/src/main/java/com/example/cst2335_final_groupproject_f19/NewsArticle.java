package com.example.cst2335_final_groupproject_f19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class NewsArticle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_article);


        Intent dataFromPreviousPage = getIntent();
        String title = dataFromPreviousPage.getStringExtra("newsTitle");
        String description = dataFromPreviousPage.getStringExtra("newsDescription");

        TextView savedTitle = findViewById(R.id.newsTitle);
        savedTitle.setText(title);

        TextView savedDescription = findViewById(R.id.newsDescription);
        savedDescription.setText(description);

        Button gotoURL = findViewById(R.id.goToPage);


        gotoURL.setOnClickListener(clik ->{
            Intent webOpen = new Intent(android.content.Intent.ACTION_VIEW);
            webOpen.setData(Uri.parse(dataFromPreviousPage.getStringExtra("newsURL")));
            startActivity(webOpen);

                });



       




    }
}
