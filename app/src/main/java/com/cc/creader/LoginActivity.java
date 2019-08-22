package com.cc.creader;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
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
//        getSupportActionBar().hide();
        Button b_login = (Button) findViewById(R.id.button_login);
        Button b_tologup = (Button) findViewById(R.id.button_tologup);

        et_account = (EditText) findViewById(R.id.editText_account);
        et_password = (EditText) findViewById(R.id.editText_password);
        b_login.setOnClickListener(this);
        b_tologup.setOnClickListener(this);

        dbmanager = new DBManager(this);
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
                CheckBox checkBox = (CheckBox)findViewById(R.id.checkbox_remember_pass);

                if (null==str_account || 0==str_account.length())
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                    dialog.setMessage("账号不能为空！");
                    dialog.show();
                    break;
                }
                String command_find = "SELECT * FROM AccountInfo WHERE AccountNumber = \'" + str_account +"\';";
                Cursor cursor = dbmanager.findDB(command_find);
                if (!cursor.moveToFirst())
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                    dialog.setMessage("账号未注册！");
                    et_account.setText(null);
                    et_password.setText(null);
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
                String command_update_remember;
                if (checkBox.isChecked()) //如果记住了密码
                    command_update_remember = "UPDATE AccountInfo SET IsRememberAccount = 1 WHERE AccountNumber = \'" + str_account + "\';";
                else    //没有记住密码
                    command_update_remember = "UPDATE AccountInfo SET IsRememberAccount = 0 WHERE AccountNumber = \'" + str_account + "\';";
                dbmanager.updateDB(command_update_remember);
                String command_upToOnline = "UPDATE AccountInfo SET IsOnline = 1 WHERE AccountNumber = \'" + str_account + "\'";
                dbmanager.updateDB(command_upToOnline);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                finish();
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
                    et_account.setText(return_account);
                    et_password.setText(null);
                }
            }
            default:
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.e("Login", "onDestroy");
        dbmanager.closeDB();
    }

    @Override
    public void onBackPressed()
    {
        //退出时再启动不会再显示splash界面
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

//    @Override
//    public void onRestart()
//    {
//        super.onRestart();
////        dbmanager = new DBManager(this);
//    }

}
