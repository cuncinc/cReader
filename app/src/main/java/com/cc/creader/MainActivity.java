package com.cc.creader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cc.creader.fragment.BookcaseFragment;
import com.cc.creader.fragment.PersonInfoFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ActionBar actionbar = getSupportActionBar();
//        if (actionbar != null)
//            actionbar.hide();
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
}
