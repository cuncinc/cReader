package com.cc.creader;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogupActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText et_logup_account;
    private EditText et_logup_password;
    private EditText et_logup_password_again;
    private String str_logup_account;
    private String str_logup_password;
    private String str_logup_password_again;
    private DBManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);
//        getSupportActionBar().hide();
        et_logup_account = (EditText) findViewById(R.id.editText_logup_account);
        et_logup_password = (EditText) findViewById(R.id.editText_logup_password);
        et_logup_password_again = (EditText) findViewById(R.id.editText_logup_password_again);
        Button bt_logup = (Button) findViewById(R.id.button_logup);
        bt_logup.setOnClickListener(this);

        dbmanager = new DBManager(this);
    }

    @Override
    //其实在填完信息之后按钮亮起时比较好的，不用提示账号、密码不为空
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_logup:
            {
                str_logup_account = et_logup_account.getText().toString();
                str_logup_password = et_logup_password.getText().toString();
                str_logup_password_again = et_logup_password_again.getText().toString();

                if (null==str_logup_account || 0==str_logup_account.length())
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LogupActivity.this);
                    dialog.setMessage("账号不能为空");
                    dialog.show();
                    break;
                }

                String command_find = "SELECT AccountNumber FROM AccountInfo WHERE AccountNumber = \'" + str_logup_account + "\';";
                //注意str_logup_account不能为空，不然SQL语句错误
                Cursor cursor = dbmanager.findDB(command_find);

                //要检查账号的合法性，如不能有汉字，长度限定等等

                //若此账号已被注册
                if (cursor.moveToFirst())
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LogupActivity.this);
                    dialog.setMessage("此账号已被注册");
                    dialog.show();
                    et_logup_account.setText(null);
                }
                else if (null==str_logup_password || 0==str_logup_password.length()) //有一个方法，能同时判断非null非空
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LogupActivity.this);
                    dialog.setMessage("密码不能为空");
                    dialog.show();
                }
                //若两次输入的密码不同
                else if (!str_logup_password.equals(str_logup_password_again))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LogupActivity.this);
                    dialog.setMessage("两次输入的密码不相同，请再次输入！");
                    dialog.show();
                    et_logup_password_again.setText(null);
                }
                else
                {
                    String command_create = "INSERT INTO AccountInfo(AccountNumber, PassWord) VALUES(\'"
                                            + str_logup_account + "\' ,\'" + str_logup_password + "\');";
                    dbmanager.createDB(command_create);

                    AlertDialog.Builder dialog = new AlertDialog.Builder(LogupActivity.this);
                    dialog.setMessage("注册成功！");
                    dialog.setCancelable(true);
                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
                    {
                        @Override
                        public void onCancel(DialogInterface dialog)
                        {
                            toLogin();
                        }
                    });  //这个怎么用
                    dialog.setPositiveButton("去登录", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            toLogin();
                        }
                    });
                    dialog.show();
                }
                cursor.close();
                break;
            }
            default:
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        dbmanager.closeDB();
    }

    private void toLogin()
    {
        Intent intent = new Intent();
        intent.putExtra("str_logup_account", str_logup_account);
        setResult(RESULT_OK, intent);
        finish();
    }
}
