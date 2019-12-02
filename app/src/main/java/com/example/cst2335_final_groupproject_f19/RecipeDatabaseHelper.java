package com.example.cst2335_final_groupproject_f19;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class RecipeDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDatabaseFile";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME="RecipeFav";
    public static final String COL_ID= "_id";
    public static final String publisher="PUBLISHER";
    public static final String f2f_url="F2F_URL";
    public static final String title="TITLE";
    public static final String source_url="SOURCE_URL";
    public static final String image_url="IMAGE_URL";
    public static final String social_rank="SOCIAL_RANK";
    public static final String publisher_url="PUBLISHER_URL";


    public RecipeDatabaseHelper(Activity ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + publisher + " TEXT, "
                + f2f_url + " TEXT, "
                + title + " TEXT, "
                + source_url + " TEXT, "
                + image_url + " TEXT, "
                + social_rank + " INTEGER, "
                + publisher_url + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
