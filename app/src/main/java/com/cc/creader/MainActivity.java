package com.cc.creader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText et_account;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b_login = (Button) findViewById(R.id.button_login);
        et_account = (EditText) findViewById(R.id.editText_account);
        et_password = (EditText) findViewById(R.id.editText_password);
        b_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_login :
                String str_account = et_account.getText().toString();
                String str_password = et_password.getText().toString();
                Toast.makeText(MainActivity.this, "登录成功"+str_account, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
