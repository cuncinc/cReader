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
import android.widget.Toast;

import com.cc.creader.MainActivity;
import com.cc.creader.R;

public class BookcaseFragment extends Fragment
{
    private View view;
    private EditText editText;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.bookcase_fragment, container, false);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        editText = (EditText) getActivity().findViewById(R.id.edittext_bookcase);
        editText.setText("是大大笨猪");
        button = (Button) getActivity().findViewById(R.id.button_getstring);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editText.setText("点击了按钮");
            }
        });
    }

//    @Override
//    public void onAttach(Context context)
//    {
//        super.onAttach(context);
//        Log.e("书架", "onAttach");
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        Log.e("书架", "onCreate");
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState)
//    {
//        super.onActivityCreated(savedInstanceState);
//        Log.e("书架", "onActivityCreated");
//    }
//
//    @Override
//    public void onPause()
//    {
//        super.onPause();
//        Log.e("书架", "onPause");
//    }
//
//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        Log.e("书架", "onResume");
//    }
//
//    @Override
//    public void onStop()
//    {
//        super.onStop();
//        Log.e("书架", "onStop");
//    }
//
//    @Override
//    public void onDestroyView()
//    {
//        super.onDestroyView();
//        Log.e("书架", "onDestroyView");
//    }
//
//    @Override
//    public void onDestroy()
//    {
//        super.onDestroy();
//        Log.e("书架", "onDestroy");
//    }
//
//    @Override
//    public void onDetach()
//    {
//        super.onDetach();
//        Log.e("书架", "onDetach");
//    }

}
