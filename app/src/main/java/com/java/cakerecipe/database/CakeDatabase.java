package com.java.cakerecipe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class CakeDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CAKE.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;

    public CakeDatabase(@Nullable Context context, @Nullable String name,
                        @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tableMain = "Create table " + "CAKE" + "("
                + "id integer primary key autoincrement, "
                + "image blob,"
                + "name text,"
                + "description text" + ")";
        sqLiteDatabase.execSQL(tableMain);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("Drop Table if exists " + "CAKE");
        onCreate(sqLiteDatabase);
    }

    public boolean insert(byte[] img, String name, String des) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Image", img);
        values.put("Name", name);
        values.put("Description", des);

        long row = sqLiteDatabase.insert("CAKE", null, values);
        Toast.makeText(context, row + "", Toast.LENGTH_SHORT).show();
        if (row == -1) {
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
        }
        return (row > 0);
    }

    // doc du lieu dùng để gọi
    public Cursor ReadAllData() {
        String query = "SELECT * FROM " + "CAKE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);

        } else {
            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
        }
        return cursor;
    }

    public void Delete(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("CAKE", " ID=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Nope", Toast.LENGTH_SHORT).show();
        }
    }

    public void UpdateDaTa(String row_id, byte[] img,  String name, String des) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Image", img);
        values.put("Name", name);
        values.put("Description", des);
        long resurt = db.update("CAKE", values, "ID=?", new String[]{row_id});
        if (resurt == -1) {
            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }
}
