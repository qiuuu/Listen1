package com.pangge.listen1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by iuuu on 16/10/26.
 */
public class CourseAdapter extends ArrayAdapter<Course>{
    private int resourceId;
    public CourseAdapter(Context context, int textViewResourceId, List<Course> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course course = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView courseTitle = (TextView)view.findViewById(R.id.course_text);

        courseTitle.setText(course.getTitle());
        return view;
    }
}
