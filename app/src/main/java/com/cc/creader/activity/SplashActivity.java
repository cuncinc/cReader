package com.cc.creader.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cc.creader.lib.DBManager;
import com.cc.creader.R;

public class SplashActivity extends AppCompatActivity
{
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        DBManager dbmanager = new DBManager(this);
//        String command_get_online = "SELECT * FROM AccountInfo WHERE IsOnline = 1;";
//        Cursor cursor = dbmanager.findDB(command_get_online);
        Cursor cursor = dbmanager.findDB("SELECT * FROM AccountInfo WHERE IsOnline = 1;");

        if (cursor.moveToFirst())   //若有账号在线的
            intent = new Intent(SplashActivity.this, MainActivity.class);
        else    //没有账号在线的
            intent = new Intent(SplashActivity.this, LoginActivity.class);

        dbmanager.closeDB();
        cursor.close();

        startActivity(intent);
        finish();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                startActivity(intent);
//                finish();
//            }
//        }, 200);   //bug：不能延时，不然会有黑屏
//                    //bug解决，图片太大，压缩一下
    }
}