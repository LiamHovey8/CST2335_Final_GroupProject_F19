package com.example.cst2335_final_groupproject_f19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class NewsEmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_empty);


        Intent dataFromPreviousPage = getIntent();
        String title = dataFromPreviousPage.getStringExtra("newsTitle");
        String description = dataFromPreviousPage.getStringExtra("newsDescription");

        TextView savedTitle = findViewById(R.id.newsTitle);
        savedTitle.setText("Title: " + title);

        TextView savedDescription = findViewById(R.id.newsDescription);
        savedDescription.setText("Description: " + description);

        Button gotoURL = findViewById(R.id.goToPage);
//        ImageView Button = (ImageView)findViewById(R.id.button);
//
//
//        searchButton.setOnClickListener(clik ->{
//
//                });
//
//        gotoURL.setOnClickListener(new OnClickListener() {
//
//            public void onClick(View v) {
//                Uri uri = Uri.parse("http://google.com/");
//
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//            }
//        });




    }
}
