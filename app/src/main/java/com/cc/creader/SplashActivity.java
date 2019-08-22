package com.cc.creader;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity
{
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//    }
//
//    @Override
//    protected void onStart()
//    {
//        super.onStart();
        DBManager dbmanager = new DBManager(this);
        String command_get_online = "SELECT * FROM AccountInfo WHERE IsOnline = 1;";
        Cursor cursor = dbmanager.findDB(command_get_online);

        if (cursor.moveToFirst())   //若有账号在线的
            intent = new Intent(SplashActivity.this, MainActivity.class);
        else    //没有账号在线的
            intent = new Intent(SplashActivity.this, LoginActivity.class);

        dbmanager.closeDB();
        cursor.close();
//        finish();
//        startActivity(intent);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}