package com.cc.creader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cc.creader.fragment.BookcaseFragment;
import com.cc.creader.fragment.PersonInfoFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public static DBManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Button button1 = (Button)findViewById(R.id.button_bookcase);
        Button button2 = (Button)findViewById(R.id.button_person_info);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        replaceFragment(new BookcaseFragment());
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            //这里有点不对。如果bookcase已经显示在界面上，按下切换到bookcase按钮时，
            // bookcase碎片就会重新加载，所有信息重置，会经历一个完整的生命周期。person也是一样
            case R.id.button_bookcase:
                replaceFragment(new BookcaseFragment());
                break;
            case R.id.button_person_info:
                replaceFragment(new PersonInfoFragment());
                break;
            default:
                break;
        }
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.commit();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        dbmanager = new DBManager(this);
        dbmanager.openDB();
//        Log.e("Login", "onRestart");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        dbmanager.closeDB();
    }
}
