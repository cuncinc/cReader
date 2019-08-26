package com.cc.creader.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.cc.creader.R;
import com.cc.creader.lib.FileUtils;

import java.util.ArrayList;

public class BookshelfFragment extends Fragment
{
    private View view;
    private Button button;
    private ArrayList<String> file_routes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        ImageView add_book = (ImageView) getActivity().findViewById(R.id.imageView_add_book);
        add_book.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //加书籍的fragment
            }
        });

        button = (Button) getActivity().findViewById(R.id.button_test);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        file_routes = FileUtils.getSpecificTypeOfFile(getActivity(), new String[]{".txt"});
                        for (int i=0; i<file_routes.size(); ++i)
                        {
                            Log.e("file_route", file_routes.get(i));
                        }
                    }
                }).start();
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
