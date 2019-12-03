package com.example.cst2335_final_groupproject_f19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class NewsSaved extends AppCompatActivity {
    protected SQLiteDatabase db = null;
    MyListAdapter newsAdapter;
    ArrayList <News> newsLog = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_saved);
        Toolbar tBar = (Toolbar) findViewById(R.id.navigation_toolbar);
        setSupportActionBar(tBar);

        NewsDatabaseHelper opener = new NewsDatabaseHelper(this);
        db =  opener.getWritableDatabase();
        ListView newsList = findViewById(R.id.newsSavedList);
        newsList.setAdapter(newsAdapter = new MyListAdapter());

//        TextView titleText =findViewById(R.id.newsSaved1);
//        TextView descriptionText = findViewById(R.id.newsSaved2);

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

            String _title = results.getString(titleColIndex);
            long _id = results.getLong(idColIndex);
            String _author = results.getString(authorColIndex);

            newsLog.add(new News(_id, _title, _author));

        }


        newsList.setOnItemClickListener((lv, vw, pos, id) -> {
            Snackbar sb = Snackbar.make(vw, "Delete " + newsLog.get(pos).getTitle(), Snackbar.LENGTH_LONG).setAction("Confirm", a -> delete(pos, newsLog.get(pos).getId()));
            sb.show();

        });



    }

    private void delete (int position, long id){
        db.delete(NewsDatabaseHelper.TABLE_NAME, NewsDatabaseHelper.COL_ID + "=?", new String[] {Long.toString(id)});
        newsLog.remove(position);
        newsAdapter.notifyDataSetChanged();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.recipe_page_menu_link:
                Intent goToNewsPage = new Intent(NewsSaved.this, RecipePage.class);
                NewsSaved.this.startActivityForResult(goToNewsPage, 10);
                break;
            case R.id.car_charger_page_menu_link:
                Intent goToCarChargerFinder = new Intent(NewsSaved.this, CarChargerFinder.class);
                NewsSaved.this.startActivityForResult(goToCarChargerFinder, 10);
                break;
            case R.id.currency_exchange_page_menu_link:
                Intent goToCurrencyExchange = new Intent(NewsSaved.this, CurrencyExchangeMain.class);
                NewsSaved.this.startActivityForResult(goToCurrencyExchange, 10);
                break;

            case R.id.news_page_menu_link:

                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.page_menu, menu);
        return true;
    }



    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsLog.size();
        }

        @Override
        public News getItem(int position) {
            return newsLog.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View thisRow = convertView;
            News rowNews = getItem(position);

            thisRow = getLayoutInflater().inflate(R.layout.news_row_page, null);
            TextView itemTitle = thisRow.findViewById(R.id.newsTitle);
            TextView itemDescript = thisRow.findViewById(R.id.newsAuthor);
            //TextView itemURL = thisRow.findViewById(R.id.newsURL);



            itemTitle.setText("Title: " + rowNews.getTitle() + " ");
            itemDescript.setText("Author: " + rowNews.getAuthor() + " ");
            //itemURL.setText("URL: " + rowNews.getUrl() + " ");

            return thisRow;
        }
    }
}
