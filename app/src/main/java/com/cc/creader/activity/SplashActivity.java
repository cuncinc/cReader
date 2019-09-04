package com.cc.creader.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cc.creader.lib.DBManager;
import com.cc.creader.R;

import static java.security.AccessController.getContext;

public class SplashActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Intent intent;
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        DBManager dbmanager = new DBManager(this);
        String command_get_online = "SELECT * FROM AccountInfo WHERE IsOnline = 1;";
        Cursor cursor = dbmanager.findDB(command_get_online);
        boolean isOnline = cursor.moveToFirst();
        dbmanager.closeDB();
        cursor.close();
        if (isOnline)   //若有账号在线的
            intent = new Intent(SplashActivity.this, MainActivity.class);
        else    //没有账号在线的
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}