package com.pangge.listen1;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private List<Fragment> mList;
    private String book;
    private String course;


    private BookFragment bookFragment;
    private DbHelper dbHelper;
    private TitleFragment titleFragment;
    private ContentFragment contentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this,"ListenTest.db",null,1);
        setDbHelper(dbHelper);
        mList = new ArrayList<Fragment>();
        bookFragment = new BookFragment();
        titleFragment = new TitleFragment();
        contentFragment = new ContentFragment();
        mList.add(bookFragment);
        mList.add(titleFragment);
        mList.add(contentFragment);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.book_fragment,bookFragment);
        transaction.commit();

        if(isFirstTime()){
            addContent();
            displayContent();
        }
    }

    public void addContent(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    BufferedReader localBufferReader = new BufferedReader(
                            new InputStreamReader(getResources().openRawResource(
                                    R.raw.title1)));
                    BufferedReader localBufferReader1 = new BufferedReader(
                            new InputStreamReader(getResources().openRawResource(
                                    R.raw.content_path1)));
                    BufferedReader localBufferReader2 = new BufferedReader(
                            new InputStreamReader(getResources().openRawResource(
                                    R.raw.audio_path1)));

                    for (int i = 1; i < 97; i++) {
                        String title = localBufferReader.readLine();
                        // int content_path =Integer.parseInt(localBufferReader1.readLine());
                        String content_path = localBufferReader1.readLine();

                        //  int content_path = Integer.parseInt(content_path1);
                        String video_path = localBufferReader2.readLine();
                        if (title == null) {
                            localBufferReader.close();
                            localBufferReader1.close();
                            localBufferReader2.close();
                            break;
                        }
                        Log.v("insert", "success! " + title + "cooo" + content_path);
                        ContentValues cv = new ContentValues();
                        cv.put("id", i);
                        cv.put("title", title);
                        cv.put("content", content_path);
                        cv.put("path", video_path);
                        db.insert("book1", null, cv);
                    }



                    //book2
                    localBufferReader = new BufferedReader(
                            new InputStreamReader(getResources().openRawResource(
                                    R.raw.title2)));
                    localBufferReader1 = new BufferedReader(
                            new InputStreamReader(getResources().openRawResource(
                                    R.raw.content_path2)));
                    localBufferReader2 = new BufferedReader(
                            new InputStreamReader(getResources().openRawResource(
                                    R.raw.audio_path2)));
                    for (int i = 1; i < 61; i++) {
                        String title = localBufferReader.readLine();
                        String content_path = localBufferReader1.readLine();
                        String video_path = localBufferReader2.readLine();
                        if (title == null) {
                            localBufferReader.close();
                            localBufferReader1.close();
                            localBufferReader2.close();
                            break;
                        }
                        Log.v("insert", "success! " + title);
                        ContentValues cv = new ContentValues();
                        cv.put("id", i);
                        cv.put("title", title);
                        cv.put("content",content_path);
                        cv.put("path", video_path);
                        db.insert("book2", null, cv);
                    }
                    Log.v("add2 successful","777hhaa");
                    db.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void displayContent(){


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("book1",null,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String path = cursor.getString(cursor.getColumnIndex("path"));
            Log.v("Hello World","Title: "+ title + " Content: " + content + " Path: " + path);
        }
        cursor.close();

    }

    private boolean isFirstTime(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore",false);
        if(!ranBefore){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore",true);
            editor.commit();
        }
        return !ranBefore;

    }

    public void setBook(String book){
        this.book = book;
    }

    public String getBook(){
        return book;
    }

    public void setCourse(String course){
        this.course = course;
    }

    public String getCourse(){
        return course;
    }



    public void setDbHelper(DbHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public DbHelper getDbHelper(){
        return dbHelper;
    }
}
