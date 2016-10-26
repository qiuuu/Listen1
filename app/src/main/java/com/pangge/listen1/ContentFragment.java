package com.pangge.listen1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by iuuu on 16/10/26.
 */
public class ContentFragment extends Fragment implements View.OnClickListener {
    private View contentView;
    private MainActivity mainActivity;
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private String book;
    private String content_path;
    private String video_path;
    private Button play;
    private Button pause;
    private Button stop;
    private int pathResourceId;

    private MediaPlayer mediaPlayer = new MediaPlayer();

    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        contentView = inflater.inflate(R.layout.three_fragment,container,false);
        mainActivity = (MainActivity)getActivity();
        play = (Button)contentView.findViewById(R.id.play);
        pause = (Button)contentView.findViewById(R.id.pause);
        stop = (Button)contentView.findViewById(R.id.stop);
        textView = (TextView) contentView.findViewById(R.id.content);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        dbHelper= mainActivity.getDbHelper();
        db = dbHelper.getWritableDatabase();
        addContent();

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        initMediaPlayer();

        return contentView;

    }

    private void addContent(){
        String course = mainActivity.getCourse();
        book = mainActivity.getBook();
        // String columns = "content";
        Cursor cursor = db.query(book, null, "title=?", new String[] {course}, null, null, null, null);
        while (cursor.moveToNext()) {
            //String content = cursor.getString(cursor.getColumnIndex("content"));
            content_path = cursor.getString(cursor.getColumnIndex("content"));
            video_path = cursor.getString(cursor.getColumnIndex("path"));

            // content_path = "content1_1";
            //Course course = new Course(content);

            Log.v("error: ","--"+mainActivity.getPackageName());
            try {

                int contentResourceId = contentView.getResources().getIdentifier("raw/" + content_path,
                        null, mainActivity.getPackageName());
                pathResourceId = contentView.getResources().getIdentifier("raw/" + video_path,
                        null, mainActivity.getPackageName());
                BufferedReader contentBufferReader = new BufferedReader(
                        new InputStreamReader(contentView.getResources().openRawResource(contentResourceId)));

                String result = "";
                String line = "";
                while ((line = contentBufferReader.readLine()) != null){
                    result += line ;

                }


                textView.setText(result);
                Log.v("inn","content:  "+ result);

            }catch (Exception e){
                e.printStackTrace();
            }

            //textView.setText(content);
        }
        cursor.close();
        //   String result = "";
      /*  try{
            InputStream inputStream = getResources().openRawResource(content);

            InputStreamReader inputReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputReader);

            // int length = inputStream.available();
           // byte[] buffer = new byte[length];
           // inputStream.read(buffer);
            textView.setText(bufferedReader.readLine());

        }catch (Exception e){
            e.printStackTrace();
        }*/

    }

    private void initMediaPlayer(){
        try {
            mediaPlayer = MediaPlayer.create(mainActivity,pathResourceId);
            // contentView.getResources().openRawResource(pathResourceId);
            mediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
                break;
            case R.id.pause:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case R.id.stop:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    initMediaPlayer();
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
