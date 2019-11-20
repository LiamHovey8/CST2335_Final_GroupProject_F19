package com.example.cst2335_final_groupproject_f19;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.page_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.recipe_page_menu_link:

                break;
            case R.id.car_charger_page_menu_link:
                Intent goToCarChargerFinder = new Intent(MainActivity.this, CarChargerFinder.class);
                MainActivity.this.startActivityForResult(goToCarChargerFinder, 10);
                break;
            case R.id.currency_exchange_page_menu_link:
                Intent goToCurrencyExchange = new Intent(MainActivity.this, CurrencyExchangeMain.class);
                MainActivity.this.startActivityForResult(goToCurrencyExchange, 10);
                break;
            case R.id.news_page_menu_link:
                Intent goToNewsPage = new Intent(MainActivity.this, NewsPage.class);
                MainActivity.this.startActivityForResult(goToNewsPage, 10);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar tBar = (Toolbar)findViewById(R.id.navigation_toolbar);
        setSupportActionBar(tBar);
        ImageButton exchange = findViewById(R.id.coin);
        if(exchange != null) {
            exchange.setOnClickListener(clk -> {
                Intent goToCurrencyExchange = new Intent(MainActivity.this, CurrencyExchangeMain.class);

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
            news.setOnClickListener(clk -> {
                Intent goToNewsPage = new Intent(MainActivity.this, NewsPage.class);

                MainActivity.this.startActivityForResult(goToNewsPage, 30);
            });
        }
        ImageButton food = findViewById(R.id.recipe);
        if(news != null) {
            food.setOnClickListener(clk -> {
                Intent goToRecipePage = new Intent(MainActivity.this, RecipePage.class);

                MainActivity.this.startActivityForResult(goToRecipePage, 40);
            });
        }
    }
}
