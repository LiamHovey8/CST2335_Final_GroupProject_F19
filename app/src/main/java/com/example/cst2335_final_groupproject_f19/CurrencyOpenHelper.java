package com.example.cst2335_final_groupproject_f19;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CurrencyOpenHelper extends SQLiteOpenHelper {

    /**
     * Name of database
     */
    public static final String DATABASE_NAME = "MyDatabaseFile2";
    /**
     * Database version
     */
    public static final int VERSION_NUM = 1;
    /**
     * Table name
     */
    public static final String TABLE_NAME = "CurrencyTable2";
    /**
     * Table ID column
     */
    public static final String COL_ID = "_id";
    /**
     * Table input currency column
     */
    public static final String COL_InputCurr = "InputCurr";
    /**
     * Table output currency column
     */
    public static final String COL_OutputCurr = "OutputCurr";
    /**
     * Table input value column
     */
    public static final String COL_InputVal = "InputVal";
    /**
     * Table output value column
     */
    public static final String COL_OutputVal = "OutputVal";

    /**
     * Creates open helper
     * @param ctx
     */
    public CurrencyOpenHelper(Activity ctx){
        //The factory parameter should be null, unless you know a lot about Database Memory management
        super(ctx, DATABASE_NAME, null, VERSION_NUM );
    }

    /**
     * Creates table in database
     * @param db
     */
    public void onCreate(SQLiteDatabase db)
    {
        //Make sure you put spaces between SQL statements and Java strings:
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_InputCurr + " TEXT, "
                + COL_OutputCurr + " TEXT, "
                + COL_InputVal + " TEXT, "
                + COL_OutputVal  + " TEXT)");
    }

    /**
     * Upgrades table on command and creates appropriate new table
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Database upgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }

    /**
     * Downgrades table on command and creates appropriate new table
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Database downgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }
}
