package com.cc.creader.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.cc.creader.activity.MainActivity;
import com.cc.creader.activity.ReadBookActivity;
import com.cc.creader.activity.SignupActivity;
import com.cc.creader.lib.TxtFileUtil;
import com.cc.creader.source.BookAdapter;
import com.cc.creader.source.BookData;
import com.cc.creader.activity.AddBookActivity;
import com.cc.creader.R;
import com.gyf.immersionbar.ImmersionBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import static com.cc.creader.activity.MainActivity.dbmanager;

public class BookshelfFragment extends Fragment
{
    private View view;
    private Cursor cursor;
    private GridView gridView;
    private ArrayList <BookData> books;
    private String command_get_online_book;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        int height = ImmersionBar.getStatusBarHeight(getActivity());
        Log.e("height", ""+height);
        view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        books = new ArrayList<>();
//        Log.e("书架", "onActivityCreated");
        command_get_online_book = "SELECT * FROM BookInfo WHERE UserID IS (SELECT ID FROM AccountInfo WHERE IsOnline = 1)";

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
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(getActivity(), "点击了"+id, Toast.LENGTH_SHORT).show();
                toReadBook(position);
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(getActivity(), "长按"+position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void toReadBook(final int position)
    {
        final String route = books.get(position).getRoute();
        File file = new File(route);
        if (!file.exists())
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setMessage("文件不存在，是否删除文件？");
            dialog.setCancelable(true);
            dialog.setPositiveButton("删除", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    String command_delete_book = "DELETE FROM BookInfo WHERE BookPath = \'"+ route
                                               + "\' AND UserID IS (SELECT ID FROM AccountInfo WHERE IsOnline = 1)";
                    dbmanager.updateDB(command_delete_book);
                    books.remove(position);
                    initBookShelf();
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });
            dialog.show();
            return;
        }
        else
        {
            Intent intent = new Intent(getActivity(), ReadBookActivity.class);
            intent.putExtra("route", route);
            startActivity(intent);
        }
//        byte[] b = new byte[10240];
//        RandomAccessFile raf;
//        try
//        {
//            raf = new RandomAccessFile(file, "r");
//            raf.read(b);
//            String showContent = new String(b);
//            Log.e("内容", showContent);
//        }
//        catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }

    }

    public void initBookShelf()
    {
        books.clear();
        cursor = dbmanager.findDB(command_get_online_book);
        while (cursor.moveToNext())
        {
            BookData data = new BookData();
            data.setRoute(cursor.getString(cursor.getColumnIndex("BookPath")));
            data.setTitle(cursor.getString(cursor.getColumnIndex("BookName")));
            data.setSize(cursor.getDouble(cursor.getColumnIndex("BookSize")));
            books.add(data);
        }
        BookAdapter adapter = new BookAdapter(getActivity(), books);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        initBookShelf();
//        Log.e("书架", "onStart");
    }

//    @Override
//    public void onStop()
//    {
//        super.onStop();
//        Log.e("书架", "onStop");
//    }
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
