package com.cc.creader.activity;

import android.Manifest;
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
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.SplashTheme);
        initPermission();
        super.onCreate(savedInstanceState);
        DBManager dbmanager = new DBManager(this);
        String command_get_online = "SELECT * FROM AccountInfo WHERE IsOnline = 1;";
        Cursor cursor = dbmanager.findDB(command_get_online);

        if (cursor.moveToFirst())   //若有账号在线的
            intent = new Intent(SplashActivity.this, MainActivity.class);
        else    //没有账号在线的
            intent = new Intent(SplashActivity.this, LoginActivity.class);

        dbmanager.closeDB();
        cursor.close();

        startActivity(intent);
        finish();
    }

    private void initPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(PackageManager.PERMISSION_GRANTED !=checkSelfPermission(permissions[0]))
                requestPermissions(permissions, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0]==-1)
        {
            Toast.makeText(this, "权限不足", Toast.LENGTH_SHORT).show();
//            new AlertDialog.Builder(getContext())
//                    .setTitle("权限不足")
//                    .setMessage("应用需要获取读取存储空间权限以保证软件能够正常运行")
//                    .setPositiveButton("重新授权", new DialogInterface.OnClickListener()
//                    {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            initPermission();
//                        }
//                    })
//                    .setNegativeButton("退出", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which)
//                        {
//                            finish();
//                        }
//                    })
//                    .setCancelable(false)
//                    .show();
        }
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