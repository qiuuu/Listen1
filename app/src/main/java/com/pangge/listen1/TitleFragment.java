package com.pangge.listen1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iuuu on 16/10/26.
 */
public class TitleFragment extends Fragment {

    private View titleView;
    private CourseAdapter adapter;

    private MainActivity mainActivity;
    private String book;
    private List<Course> courseList = new ArrayList<Course>();
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        titleView = inflater.inflate(R.layout.two_fragment,container,false);
        adapter = new CourseAdapter(getActivity(),
                R.layout.item_course,courseList);
        Log.v("Title begin successful","777hhaa");
        ListView listView = (ListView)titleView.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        mainActivity = (MainActivity)getActivity();

        dbHelper= mainActivity.getDbHelper();
        db = dbHelper.getWritableDatabase();
        //   Log.v("Title DBDBDB successful","777hhaa");
        addCourse();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                Course course = courseList.get(position);
                Log.v("course",course.getTitle());
                mainActivity.setCourse(course.getTitle());
                changeView();
            }
        });
        return titleView;
    }


    private void addCourse() {
        // new Thread(new Runnable() {
        //      @Override
        //     public void run() {
        book = mainActivity.getBook();

        Cursor cursor = db.query(book, null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            Course course = new Course(title);
            courseList.add(course);
        }
        cursor.close();
        //   }
        //   }).start();
    }

    private void changeView(){
        ContentFragment contentFragment = new ContentFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(this);
        transaction.add(R.id.title_fragment, contentFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }



}
