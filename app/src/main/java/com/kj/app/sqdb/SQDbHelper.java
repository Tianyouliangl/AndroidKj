package com.kj.app.sqdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQDbHelper extends SQLiteOpenHelper {

    private static final String name = "db_dm_mc_1_0";
    private static final int version = 1;

    public SQDbHelper(Context context) {
        super(context,name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + "if not exists "
                + SQManager.SQLITE_MC_MSG
                + " (id Integer primary key autoincrement, name VARCHAR, path VARCHAR, " +
                " album VARCHAR, artist VARCHAR, size VARCHAR, duration VARCHAR, pinyin VARCHAR, time VARCHAR, title VARCHAR, mid VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
