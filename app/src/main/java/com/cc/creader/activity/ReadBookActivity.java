package com.cc.creader.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cc.creader.R;
import com.cc.creader.lib.DBManager;
import com.cc.creader.lib.ReadView;
import com.cc.creader.lib.TxtFileUtil;
import com.cc.creader.source.TextAdapter;
import com.cc.creader.source.TextData;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class ReadBookActivity extends AppCompatActivity
{
    DBManager dbManager = new DBManager(this);
    LayoutInflater inflater;
    TextData data;
    ArrayList<TextData> datas;
    private View v;
    private TxtFileUtil txtfile;
    private ViewPager viewPager;
    private String route;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init();
        Intent intent = getIntent();
        route = intent.getStringExtra("route");
        title = route.substring(route.lastIndexOf('/')+1, route.length()-4);

        String command_getLastReadPosition = "SELECT LastReadingPosition FROM BookInfo WHERE BookPath = \'"
                + route + "\' AND UserID = (SELECT ID FROM AccountInfo WHERE IsOnline = 1);";
        Cursor cursor = dbManager.findDB(command_getLastReadPosition);
        cursor.moveToFirst();
        long skip_length = cursor.getLong(0);

        inflater = getLayoutInflater().from(ReadBookActivity.this);
        int buff_length = initBuffLength();
        txtfile = new TxtFileUtil(route, buff_length, skip_length);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        datas = new ArrayList<TextData>();
        for (long i=0; i<3; ++i)
        {
            View view = inflater.inflate(R.layout.layout_text, null);
            data = new TextData(view, txtfile.loadNextTxt(), title);
            datas.add(data);
        }
        v = inflater.inflate(R.layout.layout_text, null);
        final TextAdapter adapter = new TextAdapter(datas);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                //字符读完，空白页继续读，无止境
                View view = inflater.inflate(R.layout.layout_text, null);
                data = new TextData(view, txtfile.loadNextTxt(), title);
                datas.add(data);
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }
            @Override
            public void onPageScrollStateChanged(int state)
            {
            }
        });
    }

    private int initBuffLength()
    {
        //这个方法没有用
        int length = 801;
        byte[] b = new byte[9000];
        try
        {
            FileInputStream fin = new FileInputStream(new File(route));
            String s = new String(b, "UTF-8");
            ReadView readView = (ReadView)(TextView)(v.findViewById(R.id.textView_txt));
            readView.setText(s);
            length = readView.getCharNum();
            Log.e("每页字数", "x"+length);
            return length;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return length;
    }
}
