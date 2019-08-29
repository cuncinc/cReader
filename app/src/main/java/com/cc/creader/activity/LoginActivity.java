package com.cc.creader.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.cc.creader.lib.DBManager;
import com.cc.creader.R;

import java.sql.Time;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText et_account;
    private EditText et_password;
    private DBManager dbmanager;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        dbmanager = new DBManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button b_login = (Button) findViewById(R.id.button_login);
        Button b_tosignup = (Button) findViewById(R.id.button_tosignup);
        et_account = (EditText) findViewById(R.id.editText_account);
        et_password = (EditText) findViewById(R.id.editText_password);
        checkBox = (CheckBox)findViewById(R.id.checkbox_remember_pass);
        initAccountEdit();

        b_login.setOnClickListener(this);
        b_tosignup.setOnClickListener(this);
    }

    private void initAccountEdit()
    {
        String command_get_lastLoginAccount = "SELECT * FROM AccountInfo ORDER BY LastLoginTime DESC LIMIT 1;";
        Cursor cursor = dbmanager.findDB(command_get_lastLoginAccount);
        cursor.moveToFirst();
        if (cursor.isNull(cursor.getColumnIndex("LastLoginTime")))
        {
            return;
        }
        else
        {
            et_account.setText(cursor.getString(cursor.getColumnIndex("AccountNumber")));
            if (cursor.getInt(cursor.getColumnIndex("IsRememberPassword")) == 1)
            {
                et_password.setText(cursor.getString(cursor.getColumnIndex("PassWord")));
                checkBox.setChecked(true);
            }
        }
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
                int remenberpass;
                if (checkBox.isChecked()) //如果记住了密码
                        remenberpass = 1;
                else    //没有记住密码
                        remenberpass = 0;
                String command_upToOnline =  "UPDATE AccountInfo SET IsOnline = 1, IsRememberPassword = "
                        + remenberpass +", LastLoginTime = " + System.currentTimeMillis()
                        + " WHERE AccountNumber = '" + str_account + "\'";
                Log.e("Command", command_upToOnline);
                dbmanager.updateDB(command_upToOnline);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
                break;
            }
            case R.id.button_tosignup:
            {
                Intent intent_2 = new Intent(LoginActivity.this, SignupActivity.class);
                startActivityForResult(intent_2, 1); //请求码唯一
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
}
