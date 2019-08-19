package com.cc.creader;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText et_account;
    private EditText et_password;
    private DBManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        Button b_login = (Button) findViewById(R.id.button_login);
        Button b_tologup = (Button) findViewById(R.id.button_tologup);

        et_account = (EditText) findViewById(R.id.editText_account);
        et_password = (EditText) findViewById(R.id.editText_password);
        b_login.setOnClickListener(this);
        b_tologup.setOnClickListener(this);

        dbmanager = new DBManager(this);
        //首次执行导入.db文件
        dbmanager.openDB();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        dbmanager = new DBManager(this);
        dbmanager.openDB();
        Log.e("Login", "onRestart");
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_login:
            {
                String str_account = et_account.getText().toString();
                String str_password = et_password.getText().toString();

                if (null==str_account || 0==str_account.length())
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                    dialog.setMessage("账号不能为空！");
                    dialog.show();
                    break;
                }
                String command_find = "SELECT PassWord FROM AccountInfo WHERE AccountNumber = \'" + str_account +"\';";
                Cursor cursor = dbmanager.findDB(command_find);
                if (!cursor.moveToFirst())
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                    dialog.setMessage("账号未注册！");
                    dialog.show();
                    cursor.close();
                    break;
                }
                String password_in_DB = cursor.getString(cursor.getColumnIndex("PassWord"));
                if (!password_in_DB.equals(str_password))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                    dialog.setMessage("账号或密码错误！");
                    dialog.show();
                    cursor.close();
                    break;
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button_tologup:
            {
                Intent intent_2 = new Intent(LoginActivity.this, LogupActivity.class);
                startActivityForResult(intent_2, 1); //请求码唯一
                //如果注册成功，返回账号，密码（或者直接从数据库读取），不用再输入，直接登录
                break;
            }
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case 1:
            {
                if (resultCode==RESULT_OK)
                {
                    String return_account = data.getStringExtra("str_logup_account");
                    Log.e("return_account", return_account);
                    et_account.setText(return_account);
                    et_password.setText("");
                }
            }
            default:
                break;
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        dbmanager.closeDB();
        Log.e("Login","onStop");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        dbmanager.closeDB();
        Log.e("Login", "onDestroy");
    }
}
