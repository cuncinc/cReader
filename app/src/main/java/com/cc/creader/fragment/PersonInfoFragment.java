package com.cc.creader.fragment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.creader.DBManager;
import com.cc.creader.R;
import static com.cc.creader.MainActivity.dbmanager;

public class PersonInfoFragment extends Fragment
{
    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.e("个人中心", "onCreateView");
        View view = inflater.inflate(R.layout.person_infor_fragment, container, false);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.e("个人中心", "onStart");
        String command_find_profile = "";
        cursor = dbmanager.findDB(command_find_profile);

        String command_getOnlineAccount = "SELECT * FROM AccountInfo WHERE IsOnline = 1";
        cursor = dbmanager.findDB(command_getOnlineAccount);
        cursor.moveToFirst();
        String profileroute = cursor.getString(cursor.getColumnIndex("ProfileRoute"));
        //设置头像
        if (profileroute == null || profileroute.length()==0) //如果ProfileRoute为null或空，即默认头像没有被替换
        {
            ImageView ima_profile = (ImageView) getActivity().findViewById(R.id.image_profile);
            ima_profile.setImageResource(R.drawable.default_profile);
        }
        else
        {
            //如果默认头像被替换了，用被替换掉的头像的路径
            //如果按照此路径找不到头像，则还是用默认头像
        }

        //设置ID
        String onlineID = cursor.getString(cursor.getColumnIndex("ID"));
        TextView tv_id = (TextView) getActivity().findViewById(R.id.textview_online_id);
        tv_id.setText(onlineID);

        //设置账号
        String onlineAccount = cursor.getString(cursor.getColumnIndex("AccountNumber"));
        TextView tv_account = (TextView) getActivity().findViewById(R.id.textview_online_account);
        tv_account.setText(onlineAccount);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.e("个人中心", "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.e("个人中心", "onCreate");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.e("个人中心", "onActivityCreated");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.e("个人中心", "onPause");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.e("个人中心", "onResume");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.e("个人中心", "onStop");
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        Log.e("个人中心", "onDestroyView");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.e("个人中心", "onDestroy");
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        Log.e("个人中心", "onDetach");
    }
}
