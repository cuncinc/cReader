package com.cc.creader.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cc.creader.lib.DBManager;
import com.cc.creader.R;
import com.cc.creader.fragment.BookcaseFragment;
import com.cc.creader.fragment.BookmaketFragment;
import com.cc.creader.fragment.PersonInfoFragment;

import static android.R.attr.path;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener
{
    public static DBManager dbmanager;
    private RadioGroup rg_tab_bar;
    private RadioButton rb_home;

    //Fragment Object
    private Fragment fg_bookmaket, fg_bookshelf, fg_personinfo;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbmanager = new DBManager(this);
        Log.e("Main", "onCreate");

        fManager = getSupportFragmentManager();
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置其为选中状态
        rb_home = (RadioButton) findViewById(R.id.radio_button_bookmaket);
        rb_home.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId)
        {
            case R.id.radio_button_bookmaket:
                if(fg_bookmaket == null)
                {
                    fg_bookmaket = new BookmaketFragment();
                    fTransaction.add(R.id.fragment_layout,fg_bookmaket);
                }
                else
                {
                    fTransaction.show(fg_bookmaket);
                }
                break;
            case R.id.radio_button_bookshelf:
                if(fg_bookshelf == null)
                {
                    fg_bookshelf = new BookcaseFragment();
                    fTransaction.add(R.id.fragment_layout,fg_bookshelf);
                }
                else
                {
                    fTransaction.show(fg_bookshelf);
                }
                break;
            case R.id.radio_button_personinfo:
                if(fg_personinfo == null){
                    fg_personinfo = new PersonInfoFragment();
                    fTransaction.add(R.id.fragment_layout,fg_personinfo);
                }
                else
                {
                    fTransaction.show(fg_personinfo);
                }
                break;
        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction)
    {
        if(fg_bookmaket != null)fragmentTransaction.hide(fg_bookmaket);
        if(fg_bookshelf != null)fragmentTransaction.hide(fg_bookshelf);
        if(fg_personinfo != null)fragmentTransaction.hide(fg_personinfo);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        dbmanager.closeDB();
        Log.e("Main", "onDestroy");
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
//    public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//        //在相册里面选择好相片之后调回到现在的这个activity中
//        switch (requestCode)
//        {
//            case 2:
//                if (resultCode == RESULT_OK)
//                {
//                    try
//                    {
//                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
//                        cursor.moveToFirst();
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        String path = cursor.getString(columnIndex);  //获取照片路径
//                        cursor.close();
//                    }
//                    catch (Exception e)
//                    {
//                        // TODO Auto-generatedcatch block
//                        e.printStackTrace();
//                    }
//                }
//                break;
//            default:
//                break;
//        }
//    }

//    @Override
//    public void onStart()
//    {
//        super.onStart();
//        Log.e("Main", "onStart");
//    }
//
//    @Override
//    public void onStop()
//    {
//        super.onStop();
//        Log.e("Main", "onStop");
//    }
//
//    @Override
//    public void onPause()
//    {
//        super.onPause();
//        Log.e("Main", "onPause");
//    }

}
