package com.prism.pickany247.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SampleSQLiteDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "modules_database";
    public static final String PERSON_TABLE_NAME = "modules";
    public static final String PERSON_COLUMN_ID = "_id";
    public static final String PERSON_ID = "id";
    public static final String PERSON_COLUMN_CATEGOERY = "catName";
    public static final String PERSON_COLUMN_IMAGE = "image";
    public static final String PERSON_COLUMN_TITLE = "title";


   /* private String id;
    private String image;
    private String category;
    private String image_type;
    private String position;
    private String title;*/

    public SampleSQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + PERSON_TABLE_NAME + " (" +
                PERSON_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PERSON_ID + " TEXT, " +
                PERSON_COLUMN_CATEGOERY + " TEXT, " +
                PERSON_COLUMN_IMAGE + " TEXT, " +
                PERSON_COLUMN_TITLE + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PERSON_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
