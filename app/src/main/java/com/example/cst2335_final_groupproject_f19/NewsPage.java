package com.example.cst2335_final_groupproject_f19;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsPage extends AppCompatActivity {
    MyListAdapter newsAdapter;
    ArrayList<News> newsLog = new ArrayList();
    ProgressBar bar;
    String queryURL = "https://newsapi.org/v2/everything?apiKey=090058f5c8d644e6a082834eca3a4d31&q=";
    String newsURL;
    SQLiteDatabase db;

    protected SharedPreferences prefs;
    protected EditText searchEditText;
    protected SharedPreferences.Editor edit;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list_page);
        ListView newsList = findViewById(R.id.newsList);
        newsList.setAdapter(newsAdapter = new MyListAdapter());
        bar = findViewById(R.id.newsBar);
        bar.setVisibility(View.INVISIBLE);
        bar.setProgress(25);
        searchEditText = findViewById(R.id.newsEditText);

        Toolbar tBar = (Toolbar) findViewById(R.id.navigation_toolbar);
        setSupportActionBar(tBar);

        Button alertDialogButton = findViewById(R.id.newsButton2);
        alertDialogButton.setOnClickListener(clik -> alertExample()
        );

        newsList.setOnItemClickListener((lv, vw, pos, id) -> {
            Toast.makeText(NewsPage.this, "You clicked on: " + newsLog.get(pos).getTitle(), Toast.LENGTH_SHORT).show();
            Snackbar.make(vw, "the author is " + newsLog.get(pos).getAuthor(), Snackbar.LENGTH_LONG).show();


            Intent goToPage2 = new Intent(NewsPage.this, NewsArticle.class);
            goToPage2.putExtra("newsTitle", newsLog.get(pos).getTitle());
            goToPage2.putExtra("newsDescription", newsLog.get(pos).getDescription());
            goToPage2.putExtra("newsURL", newsLog.get(pos).getUrl());
            startActivity(goToPage2);


        });

        Button searchButton = findViewById(R.id.newsButton1);
        searchButton.setOnClickListener(clik ->{
            newsLog.clear();
            String searchText = searchEditText.getText().toString();
            newsURL = queryURL + searchText;
            NewsQuery  newsQuery = new NewsQuery();
            bar.setVisibility(View.VISIBLE);
            newsQuery.execute(newsURL);

        });

        prefs = getSharedPreferences("NewsReserveName", MODE_PRIVATE);
        edit = prefs.edit();
        String previous = prefs.getString("NewsReserveName", "");
        searchEditText.setText(previous);

//        NewsDatabaseHelper dbOpener = new NewsDatabaseHelper(this);
//        db = dbOpener.getWritableDatabase();
//        String [] columns = {NewsDatabaseHelper.COL_ID,
//                NewsDatabaseHelper.COL_NAME,
//                NewsDatabaseHelper.COL_AUTHOR, NewsDatabaseHelper.COL_TITLE,
//                NewsDatabaseHelper.COL_DESCRIPTION,  NewsDatabaseHelper.COL_URL,
//                NewsDatabaseHelper.COL_URL_TO_IMAGE, NewsDatabaseHelper.COL_PUBLISHED_AT,
//                NewsDatabaseHelper.COL_CONTENT
//        };
//        Cursor results = db.query(false, NewsDatabaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);
//        int idColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_ID);
//        int nameColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_NAME);
//        int authorColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_AUTHOR);
//        int titleColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_TITLE);
//        int descriptionColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_DESCRIPTION);
//        int urlColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_URL);
//        int urlToImageColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_URL_TO_IMAGE);
//        int publishedAtColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_PUBLISHED_AT);
//        int contentColIndex = results.getColumnIndex(NewsDatabaseHelper.COL_CONTENT);

    }

    @Override
    protected void onPause(){
        super.onPause();
        edit.putString("NewsReserveName", searchEditText.getText().toString());
        edit.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.recipe_page_menu_link:
                Intent goToNewsPage = new Intent(NewsPage.this, RecipePage.class);
                NewsPage.this.startActivityForResult(goToNewsPage, 10);
                break;
            case R.id.car_charger_page_menu_link:
                Intent goToCarChargerFinder = new Intent(NewsPage.this, CarChargerFinder.class);
                NewsPage.this.startActivityForResult(goToCarChargerFinder, 10);
                break;
            case R.id.currency_exchange_page_menu_link:
                Intent goToCurrencyExchange = new Intent(NewsPage.this, CurrencyExchangeMain.class);
                NewsPage.this.startActivityForResult(goToCurrencyExchange, 10);
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


    private void alertExample() {
        View middle = getLayoutInflater().inflate(R.layout.news_dialog_notification, null);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The Message")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Accept
                    }
                })
//                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // What to do on Cancel
//                    }
//                })
                .setView(middle);

        builder.create().show();
    }


    private class NewsQuery extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {

            String ret = null;
            JSONObject jObject;

            try {       // Connect to the server:
                URL url = new URL(strings[0]);
                //URL UVURL = new URL(queryUV);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 5);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                String result = sb.toString();
                jObject = new JSONObject(result);

                JSONArray newsArray = jObject.getJSONArray("articles");
                for (int i = 0; i < newsArray.length(); i++) {
                    JSONObject object = newsArray.getJSONObject(i);
                    JSONObject source = object.getJSONObject("source");
                    String id = source.getString("id");
                    String name = source.getString("name");

                    String author = object.getString("author");
                    String title = object.getString("title");
                    String description = object.getString("description");
                    String jsonURL = object.getString("url");
                    String urlToImage = object.getString("urlToImage");
                    String publishedAt = object.getString("publishedAt");
                    String content = object.getString("content");
                    newsLog.add(new News (id, name, author, title, description, jsonURL, urlToImage, publishedAt, content));
                    int progress = ((i + 1) * 100 /newsArray.length());
                    publishProgress(progress);

                }

            } catch (MalformedURLException e) {
                ret = "Malformed URL exception";
            } catch (IOException e) {
                ret = "IO Exception: WIFI not connected";
            } catch (JSONException e) {
                ret = "JSON exception";
            }
            return ret;
        }

        @Override
        protected void onPostExecute(String s) {
            ProgressBar bar = findViewById(R.id.newsBar);
            bar.setVisibility(View.INVISIBLE);
            ListView newsList = findViewById(R.id.newsList);
            newsList.setAdapter(newsAdapter = new MyListAdapter());

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        protected void getImage (){

        }

    }
}
