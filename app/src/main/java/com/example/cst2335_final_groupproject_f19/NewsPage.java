package com.example.cst2335_final_groupproject_f19;

import android.content.DialogInterface;
import android.os.Bundle;
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

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class NewsPage extends AppCompatActivity {
    BaseAdapter newsAdapter;
    ArrayList<News> newsLog = new ArrayList();
    ProgressBar bar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list_page);
        ListView newsList = findViewById(R.id.newsList);
        newsList.setAdapter(newsAdapter = new MyListAdapter());
        bar = findViewById(R.id.newsBar);
        bar.setVisibility(View.VISIBLE);
        bar.setProgress(25);
        newsLog.add(new News("engadget", "Engadget", "Jon Fingas", "Tesla Autopilot now recognizes traffic cones", "Tesla's Autopilot has been adept at recognizing other cars on the road, but those aren't the only things you have to worry about on the road -- what about the traffic cones that wall you off from roadwork? You can relax after today... well, for the most part.…",
                "https://www.engadget.com/2019/11/03/tesla-autopilot-traffic-cones/", "\"https://o.aolcdn.com/images/dims?thumbnail=1200%2C630&quality=80&image_uri=https%3A%2F%2Fo.aolcdn.com%2Fimages%2Fdims%3Fcrop%3D3048%252C1925%252C0%252C0%26quality%3D85%26format%3Djpg%26resize%3D1600%252C1010%26image_uri%3Dhttps%253A%252F%252Fs.yimg.com%252Fos%252Fcreatr-images%252F2019-11%252F7b90be30-fe6b-11e9-befb-4fbdc8463b71%26client%3Da1acac3e1b3290917d92%26signature%3Dc2db82af91f8e7cb21141fba9dfa050d6600b0f3&client=amp-blogside-v2&signature=c289ce4b842014b54e17f56915ca98dfc206985c",
                "2019-11-03T22:01:00Z", "You may not want to put all your trust in the feature just yet. Some early users have warned that Autopilot doesn't always recognize the cones, potentially leading to dangerous lane changes"));
        newsLog.add(new News("techcrunch", "TechCrunch", "Kirsten Korosec", "Elon Musk will reveal Tesla’s ‘Cybertruck’ all-electric pickup on Nov. 21",
                "Tesla CEO Elon Musk said Wednesday that the company will unveil its all-electric “cyber truck” Nov. 21 in Los Angeles ",
                "http://techcrunch.com/2019/11/06/elon-musk-will-reveal-teslas-cybertruck-all-electric-pickup-on-nov-21/",
                "https://techcrunch.com/wp-content/uploads/2018/06/GettyImages-930534182.jpeg?w=559",
                "2019-11-06T16:30:49Z", "Tesla CEO Elon Musk said Wednesday that the company will unveil its all-electric “cyber truck” Nov. 21 in Los Angeles near the serial entrepreneur’s other company SpaceX."
                ));
        newsLog.add(new News("wired", "Wired", "Lydia Horne", "Tesla Profits, Health Care Algorithm Bias, and More News",
                "Catch up on the most important news from today in two minutes or less.", "https://www.wired.com/story/tesla-profits-health-care-algorithm-bias-and-more-news/",
                "https://media.wired.com/photos/5db1f3aa0da2b80009c7fb30/191:100/w_1280,c_limit/Transpo-Teslafactory-AP_19176027711740.jpg",
                "2019-10-24T23:46:10Z", "Tesla looks to Shanghai and Joker fans head to the Bronx, but first, today's cartoon: What's rarer than a unicorn?"));
        Button alertDialogButton = findViewById(R.id.newsButton1);
        alertDialogButton.setOnClickListener( clik -> alertExample()

        );
        newsList.setOnItemClickListener((lv, vw, pos, id) -> {
            Toast.makeText(NewsPage.this, "You clicked on: " + newsLog.get(pos).getTitle(), Toast.LENGTH_SHORT).show();
            Snackbar.make(vw, "the author is " + newsLog.get(pos).getAuthor(), Snackbar.LENGTH_LONG).show();
        });
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
                TextView itemText = thisRow.findViewById(R.id.newsRow);
                TextView itemTextName = thisRow.findViewById(R.id.newsName);

                itemText.setText("ID: " + rowNews.getId() + ", ");
                itemTextName.setText("Name: " + rowNews.getName()+ " ");

            return thisRow;
        }
    }



    private void alertExample()
    {
        View middle = getLayoutInflater().inflate(R.layout.news_list_page, null);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The Message")
                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Accept
                    }
                })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel
                    }
                }).setView(middle);

        builder.create().show();
    }

}
