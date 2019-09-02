package com.cc.creader.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.creader.R;
import com.cc.creader.lib.DBManager;
import com.cc.creader.lib.FileUtils;
import com.cc.creader.source.BookData;
import com.cc.creader.source.ScannedBookAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

/**
 * Created by CC on 2019/8/27.
 */

public class AddBookActivity extends AppCompatActivity implements View.OnClickListener
{
    private ArrayList<BookData> scannedBook;
    private ArrayList<BookData> books_info;
    private ListView listView;
    private Button bt_scanbook;
    private Button bt_importbook;
    private static List<Integer> positions = new ArrayList<Integer>();
    private int onlineID;
    DBManager dbManager = new DBManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_book);

        onlineID = getOnlineID();
        books_info = new ArrayList<>();
        bt_scanbook = (Button)findViewById(R.id.button_scanbook);
        bt_importbook = (Button) findViewById(R.id.button_import_book);
        listView = (ListView)findViewById(R.id.listView_scanned_book);
        bt_scanbook.setOnClickListener(this);
        bt_importbook.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (positions.contains(position))
                {
                    positions.remove(positions.indexOf(position));
                }
                else
                {
                    positions.add(position);
                }
                bt_importbook.setText("导入书架("+positions.size()+")");
            }
        });
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        dbManager.closeDB();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_scanbook:
            {
                bt_scanbook.setVisibility(View.INVISIBLE);
                books_info = FileUtils.getSpecificTypeOfFile(AddBookActivity.this, new String[]{".txt"});
                ScannedBookAdapter adapter = new ScannedBookAdapter(AddBookActivity.this, R.layout.layout_item_scanned_book, books_info);
                listView.setAdapter(adapter);
                break;
            }
            case R.id.button_import_book:
            {
                String name;
                String route;
                String command_insert_book;
                double size;
                for (int i = 0; i<positions.size(); ++i)
                {
                    name = books_info.get(positions.get(i)).getTitle();
                    route = books_info.get(positions.get(i)).getRoute();
                    size = books_info.get(positions.get(i)).getSize();
                    command_insert_book = "INSERT INTO BookInfo(UserID, BookName, BookPath, BookSize) VALUES(" + onlineID + ", \'" + name + "\', \'" + route + "\'," + size + ")";
                    dbManager.createDB(command_insert_book);
                }
                finish();
                positions.clear();
                break;
            }
        }
    }

    private int getOnlineID()
    {
        String command_get_onlineAccount = "SELECT ID FROM AccountInfo WHERE IsOnline = 1";
        Cursor cursor = dbManager.findDB(command_get_onlineAccount);
        cursor.moveToFirst();
        int ID = cursor.getInt(0);
        cursor.close();
        return ID;
    }
}
