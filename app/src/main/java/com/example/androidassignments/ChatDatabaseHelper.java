package com.example.androidassignments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static final String ACTIVITY_NAME = "ChatDatabaseHelper.java";
    public static final String DATABASE_NAME ="Messages.db";
    public static final int VERSION_NUM =4 ;
    public static final String KEY_ID ="_id" ;
    public static final String KEY_MESSAGE ="Message" ;
    public static final String TABLE_NAME = "TableofMessages" ;
    public final static String  DATABASE_CREATE = "create table " +
            TABLE_NAME + "(" + KEY_ID +
            " integer primary key autoincrement, " +
            KEY_MESSAGE + " text not null);";

    public ChatDatabaseHelper(Context ctx) {

        super(ctx,DATABASE_CREATE,null,VERSION_NUM);
    }


    public void onCreate(SQLiteDatabase db) {
        Log.i(ACTIVITY_NAME, "Calling onCreate");
        db.execSQL(DATABASE_CREATE);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + i + "newVersion=" + i1);


    }


}
