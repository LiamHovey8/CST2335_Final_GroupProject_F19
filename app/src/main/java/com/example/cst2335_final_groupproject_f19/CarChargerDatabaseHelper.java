package com.example.cst2335_final_groupproject_f19;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class to assist in opening a database for the Electric Car Charging Station Finder app
 */
public class CarChargerDatabaseHelper extends SQLiteOpenHelper {
    /**
     * Variables for the various components of the database
     */
    public static final String DATABASE_NAME = "CarChargerDatabase";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "ChargingStations";
    public static final String COL_ID = "_id";
    public static final String COL_LOCATION = "LOCATION";
    public static final String COL_LATITUDE = "LATITUDE";
    public static final String COL_LONGITUDE = "LONGITUDE";
    public static final String COL_CONTACT = "CONTACT";

    /**
     * Class constructor method
     *
     * @param ctx
     */
    public CarChargerDatabaseHelper(Activity ctx) {
        // The factory parameter should be null, unless you know a lot about Database Memory management
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    /**
     * Creates a new database object
     *
     * @param db
     */
    public void onCreate(SQLiteDatabase db) {
        // Make sure to put spaces between SQL statements and Java strings
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_LOCATION + " TEXT," + COL_LATITUDE + " TEXT," + COL_LONGITUDE + " TEXT," + COL_CONTACT + " TEXT)");
    }

    /**
     * Method is called in case of database version upgrade
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Database upgrade", "Old version:" + oldVersion + " newVersion:" + newVersion);

        // Delete the old table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create a new table
        onCreate(db);
    }

    /**
     * Method is called in case of database version downgrade
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Database downgrade", "Old version:" + oldVersion + " newVersion:" + newVersion);

        // Delete the old table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create a new table
        onCreate(db);
    }
}
