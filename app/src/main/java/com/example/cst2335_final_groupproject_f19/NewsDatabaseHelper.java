package com.example.cst2335_final_groupproject_f19;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NewsDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "NewsDatabase";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "NewsHistory";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "NAME";
    public static final String COL_AUTHOR = "AUTHOR";
    public static final String COL_TITLE = "TITLE";
    public static final String COL_DESCRIPTION = "DESCRIPTION";
    public static final String COL_URL = "URL";
    public static final String COL_URL_TO_IMAGE = "URL_TO_IMAGE";
    public static final String COL_PUBLISHED_AT = "PUBLISHED_AT";
    public static final String COL_CONTENT = "CONTENT";


    public NewsDatabaseHelper(Activity ctx){
        //The factory parameter should be null, unless you know a lot about Database Memory management
        super(ctx, DATABASE_NAME, null, VERSION_NUM );
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT, " + COL_AUTHOR + " TEXT,"
                + COL_TITLE + " TEXT, "
                + COL_DESCRIPTION + " TEXT, "
                + COL_URL + " TEXT, "
                + COL_URL_TO_IMAGE + " TEXT, "
                + COL_PUBLISHED_AT + " TEXT, "
                + COL_CONTENT + " TEXT " + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Database upgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }
}
