package com.java.cakerecipe.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CustomerDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CUSTOMER.db";
    private static final int DATABASE_VERSION = 1;

    public CustomerDatabase(@Nullable Context context, @Nullable String name,
                        @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableMain = "Create table " + "CUSTOMER" + "("
                + "id integer primary key autoincrement, "
                + "name text,"
                + "phone text,"
                + "recipeName text,"
                + "dateTime text" + ")";
        db.execSQL(tableMain);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if exists " + "CUSTOMER");
        onCreate(db);
    }

    public boolean insert(String name, String phone, String recipeName, String dateTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO CUSTOMER VALUES (null, ?, ?, ?, ?)";
        db.execSQL(sql, new String[]{name, phone, recipeName, dateTime});
        return true;
    }

    public boolean update(String name, String phone, String recipeName, String dateTime, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE CUSTOMER SET name = ?, phone = ?, recipeName = ?, dateTime = ? WHERE id = ?";
        db.execSQL(sql, new String[]{name, phone, recipeName, dateTime, String.valueOf(id)});
        return true;
    }

    public boolean delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM CUSTOMER WHERE id = ?";
        db.execSQL(sql, new String[]{String.valueOf(id)});
        return true;
    }

    public Cursor readAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM CUSTOMER";
        return db.rawQuery(sql, null);
    }
}
