package com.cc.creader.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.cc.creader.source.BookAdapter;
import com.cc.creader.source.BookData;
import com.cc.creader.activity.AddBookActivity;
import com.cc.creader.R;

import java.util.ArrayList;

import static com.cc.creader.activity.MainActivity.dbmanager;

public class BookshelfFragment extends Fragment
{
    private View view;
    private Cursor cursor;
    private GridView gridView;
    private ArrayList <BookData> books;
    private ArrayList<String> files_routes;
    private ArrayList<String> files_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        books = new ArrayList<>();
        Log.e("书架", "onActivityCreated");
        intiDB();
        super.onActivityCreated(savedInstanceState);
        ImageView add_book = (ImageView) getActivity().findViewById(R.id.imageView_add_book);
        gridView = (GridView) getActivity().findViewById(R.id.grid_book);
        add_book.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), AddBookActivity.class);
                startActivity(intent);
            }
        });
    }

    private void intiDB()
    {
        //SQL联结语句
//        String command_get_onlineAccount = "SELECT ID FROM AccountInfo WHERE IsOnline = 1";
//        Cursor cur = dbmanager.findDB(command_get_onlineAccount);
//        cur.moveToFirst();
//        int onlineID = cur.getInt(0);
//        String command_get_onlineBook = "SELECT * FROM BookInfo WHERE UserID = " + onlineID;
//        cursor = dbmanager.findDB(command_get_onlineBook);
        String command_get_online_book = "SELECT * FROM BookInfo WHERE UserID IS (SELECT ID FROM AccountInfo WHERE IsOnline = 1)";
        cursor = dbmanager.findDB(command_get_online_book);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.e("书架", "onStart");

        while (cursor.moveToNext())
        {
            BookData data =new BookData();
            data.setRoute(cursor.getString(cursor.getColumnIndex("BookPath")));
            data.setTitle(cursor.getString(cursor.getColumnIndex("BookName")));
            books.add(data);
        }

        BookAdapter adapter = new BookAdapter(getActivity(), books);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.e("书架", "onStop");
        cursor.moveToFirst();
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
