package com.pangge.listen1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by iuuu on 16/10/26.
 */
public class DbHelper extends SQLiteOpenHelper {
    private Context myContext;
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version){
        super(context,name,factory,version);
        myContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE book1" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, path VARCHAR)");
        db.execSQL("CREATE TABLE book2" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, path VARCHAR)");
        Log.v("INFO","successful");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
