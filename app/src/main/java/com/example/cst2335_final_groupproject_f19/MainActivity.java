package com.example.cst2335_final_groupproject_f19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton exchange = findViewById(R.id.coin);
        if(exchange != null) {
            exchange.setOnClickListener(clk -> {
                Intent goToCurrencyExchange = new Intent(MainActivity.this, CurrencyExchange.class);

                MainActivity.this.startActivityForResult(goToCurrencyExchange, 10);
            });
        }
        ImageButton charger = findViewById(R.id.charger);
        if(charger != null) {
            charger.setOnClickListener(clk -> {
                Intent goToCarChargerFinder = new Intent(MainActivity.this, CarChargerFinder.class);

                MainActivity.this.startActivityForResult(goToCarChargerFinder, 20);
            });
        }
        ImageButton news = findViewById(R.id.news);
        if(news != null) {
            charger.setOnClickListener(clk -> {
                Intent goToNewsPage = new Intent(MainActivity.this, NewsPage.class);

                MainActivity.this.startActivityForResult(goToNewsPage, 30);
            });
        }
        ImageButton food = findViewById(R.id.recipe);
        if(news != null) {
            charger.setOnClickListener(clk -> {
                Intent goToRecipePage = new Intent(MainActivity.this, RecipePage.class);

                MainActivity.this.startActivityForResult(goToRecipePage, 40);
            });
        }
    }
}
