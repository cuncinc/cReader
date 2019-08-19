package com.cc.creader.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cc.creader.R;


public class PersonInfoFragment extends Fragment
{
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
