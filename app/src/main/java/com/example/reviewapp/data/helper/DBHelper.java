package com.example.reviewapp.data.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static class Entry{
        public static final String TABLE_NAME = "san_pham";
        public static final String _ID = "_id";
        public static final String NAME = "name";
        public static final String PRICE = "price";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table if not exists " + Entry.TABLE_NAME + " ("
                + Entry._ID + " TEXT PRIMARY KEY, "
                + Entry.NAME + " TEXT, "
                + Entry.PRICE + " TEXT "
                + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL("drop table if exists " + Entry.TABLE_NAME);
            this.onCreate(db);
        }
    }

    public static SQLiteDatabase getWriteDatabase(Context context){
        DBHelper helper = new DBHelper(context);
        return helper.getWritableDatabase();
    }

    public static SQLiteDatabase getReadDatabase(Context context){
        return new DBHelper(context).getReadableDatabase();
    }
}
