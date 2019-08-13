package com.cc.creader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DisplayActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Button b_d_c = (Button) findViewById(R.id.button_d_create);
        b_d_c.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(DisplayActivity.this, "成功返回", Toast.LENGTH_SHORT).show();
                finish();
                //
            }
        });
    }
}
