package com.cc.creader.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cc.creader.lib.DBManager;
import com.cc.creader.R;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText et_signup_account;
    private EditText et_signup_password;
    private EditText et_signup_password_again;
    private String SignupAccount;
    private String SignupPassword;
    private String SignupPasswordAgain;
    private DBManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_signup_account = (EditText) findViewById(R.id.editText_signup_account);
        et_signup_password = (EditText) findViewById(R.id.editText_signup_password);
        et_signup_password_again = (EditText) findViewById(R.id.editText_sigup_password_again);
        Button bt_signup = (Button) findViewById(R.id.button_signup);
        bt_signup.setOnClickListener(this);

        dbmanager = new DBManager(this);
    }

    @Override
    //其实在填完信息之后按钮亮起时比较好的，不用提示账号、密码不为空
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_signup:
            {
                SignupAccount = et_signup_account.getText().toString();
                SignupPassword = et_signup_password.getText().toString();
                SignupPasswordAgain = et_signup_password_again.getText().toString();

                if (null==SignupAccount || 0==SignupAccount.length())
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SignupActivity.this);
                    dialog.setMessage("账号不能为空");
                    dialog.show();
                    break;
                }

                String command_find = "SELECT AccountNumber FROM AccountInfo WHERE AccountNumber = \'" + SignupAccount+ "\';";
                Cursor cursor = dbmanager.findDB(command_find);

                //要检查账号的合法性，如不能有汉字，长度限定等等
                //若此账号已被注册
                if (cursor.moveToFirst())
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SignupActivity.this);
                    dialog.setMessage("此账号已被注册");
                    dialog.show();
                    et_signup_account.setText(null);
                }
                else if (null==SignupPassword || 0==SignupPassword.length()) //有一个方法，能同时判断非null非空
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SignupActivity.this);
                    dialog.setMessage("密码不能为空");
                    dialog.show();
                }
                //若两次输入的密码不同
                else if (!SignupPassword.equals(SignupPasswordAgain))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SignupActivity.this);
                    dialog.setMessage("两次输入的密码不相同，请再次输入！");
                    dialog.show();
                    et_signup_password_again.setText(null);
                }
                else
                {
                    String command_create = "INSERT INTO AccountInfo(AccountNumber, PassWord) VALUES(\'"
                                            + SignupAccount + "\' ,\'" + SignupPassword + "\');";
                    dbmanager.createDB(command_create);

                    AlertDialog.Builder dialog = new AlertDialog.Builder(SignupActivity.this);
                    dialog.setMessage("注册成功！");
                    dialog.setCancelable(true);
                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
                    {
                        @Override
                        public void onCancel(DialogInterface dialog)
                        {
                            toLogin();
                        }
                    });
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
        intent.putExtra("str_logup_account", SignupAccount);
        setResult(RESULT_OK, intent);
        finish();
    }
}
