package com.example.reviewapp.data.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.reviewapp.data.helper.DBHelper;
import com.example.reviewapp.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public final class MyDatabase {

    @SuppressLint("Range")
    public static List<SanPham> getAll(Context context){
        SQLiteDatabase db = DBHelper.getReadDatabase(context);
        Cursor cursor = db.rawQuery("select * from " + DBHelper.Entry.TABLE_NAME, null);
        List<SanPham> listSp = new ArrayList<>();
        while (cursor.moveToNext()){
            listSp.add(new SanPham(
                    cursor.getString(cursor.getColumnIndex(DBHelper.Entry._ID)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.Entry.NAME)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.Entry.PRICE))
            ));
        }
        cursor.close();
        return listSp;
    }

    public static SanPham insert(Context context, SanPham sp){
        ContentValues values = new ContentValues();
        values.put(DBHelper.Entry._ID, sp.getId());
        values.put(DBHelper.Entry.NAME, sp.getName());
        values.put(DBHelper.Entry.PRICE, sp.getPrice());

        SQLiteDatabase db = DBHelper.getWriteDatabase(context);
        long id = db.insert(DBHelper.Entry.TABLE_NAME, DBHelper.Entry._ID, values);
        db.close();
        if (id < 0) {
            sp.setId("-1");
        }
        return sp;
    }

    public static SanPham update(Context context, SanPham sp){
        ContentValues values = new ContentValues();
        values.put(DBHelper.Entry._ID, sp.getId());
        values.put(DBHelper.Entry.NAME, sp.getName());
        values.put(DBHelper.Entry.PRICE, sp.getPrice());

        SQLiteDatabase db = DBHelper.getWriteDatabase(context);
        int id = db.update(DBHelper.Entry.TABLE_NAME, values, DBHelper.Entry._ID + " = ?", new String[]{sp.getId()});
        db.close();
        if (id < 0) {
            sp.setId("-1");
        }
        return sp;
    }

    public static boolean delete(Context context, SanPham sp){
        SQLiteDatabase db = DBHelper.getWriteDatabase(context);
        int id = db.delete(DBHelper.Entry.TABLE_NAME, DBHelper.Entry._ID + " = ?", new String[]{sp.getId()});
        db.close();
        db.close();
        if (id < 0) {
            return false;
        }
        return true;
    }
}
