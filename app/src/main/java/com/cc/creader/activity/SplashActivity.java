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
    Intent intent;
    String[] permissions = {Manifest.permission_group.STORAGE};
    boolean isOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        DBManager dbmanager = new DBManager(this);
        String command_get_online = "SELECT * FROM AccountInfo WHERE IsOnline = 1;";
        Cursor cursor = dbmanager.findDB(command_get_online);
        isOnline = cursor.moveToFirst();
        dbmanager.closeDB();
        cursor.close();
        initPermission();
        toCall();
    }

    private void initPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(SplashActivity.this, permissions[0]))
            {
                //小米手机根本不执行下面这一句
                ActivityCompat.requestPermissions(SplashActivity.this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        Log.e("已经执行回调", "permission");  //不执行
        switch (requestCode)
        {
            case 1:
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                toCall();
                break;
            }
            else
            {
                Toast.makeText(this, "权限不足", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(SplashActivity.this)
                        .setTitle("权限不足")
                        .setMessage("应用需要获取读取存储空间权限以保证软件能够正常运行")
                        .setPositiveButton("重新授权", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                initPermission();
                            }
                        })
                        .setNegativeButton("退出", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
            break;
        }
    }

    private void toCall()
    {
        if (isOnline)   //若有账号在线的
            intent = new Intent(SplashActivity.this, MainActivity.class);
        else    //没有账号在线的
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

//    private void getPermissions()
//    {
//        if (Build.VERSION.SDK_INT < 23)
//        {
//            return;
//        }
//        if (ContextCompat.checkSelfPermission(getApplication(), permissions[0]) != PackageManager.PERMISSION_GRANTED)
//        {
//            //unget
//            ActivityCompat.requestPermissions(SplashActivity.this, permissions, 3);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @Nullable String[] permissions,@Nullable int[] grantResults)
//    {
//        //通过requestCode来识别是否同一个请求
//        if (requestCode == 3)
//        {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            {
//                //用户同意，执行操作
//                return;
//            }
//            else
//            {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0]))
//                {
//                    //用户不同意，向用户展示该权限作用
//                    Log.e("permission", "user reject permission");
//                    finish();
//                }
//            }
//        }
//    }
}