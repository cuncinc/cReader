package com.cc.creader.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cc.creader.R;
import com.cc.creader.lib.DBManager;
import com.cc.creader.lib.FileUtils;
import com.cc.creader.source.BookData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CC on 2019/8/27.
 */

public class AddBookActivity extends AppCompatActivity implements View.OnClickListener
{
    private ArrayList<BookData> scannedBook;
    private ArrayList<String> file_routes;
    private ArrayList<String> file_title;
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

        String command_get_onlineAccount = "SELECT ID FROM AccountInfo WHERE IsOnline = 1";
        Cursor cursor = dbManager.findDB(command_get_onlineAccount);
        cursor.moveToFirst();
        onlineID = cursor.getInt(0);

        bt_scanbook = (Button)findViewById(R.id.button_scanbook);
        bt_importbook = (Button) findViewById(R.id.button_import_book);
        listView = (ListView)findViewById(R.id.listView_scanned_book);
        file_routes=new ArrayList<>();
//        scannedBook = new ArrayList<>();
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
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_scanbook:
            {
                bt_scanbook.setVisibility(View.INVISIBLE);
                file_routes = FileUtils.getSpecificTypeOfFile(AddBookActivity.this, new String[]{".txt"});
                file_title = getTitle(file_routes);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddBookActivity.this, android.R.layout.simple_list_item_multiple_choice, file_title);
                listView.setAdapter(adapter);
                break;
            }
            case R.id.button_import_book:
            {
                String name;
                String route;
                String command_insert_book;
                for (int i = 0; i<positions.size(); ++i)
                {
                    name = file_title.get(positions.get(i));
                    route = file_routes.get(positions.get(i));
                    command_insert_book = "INSERT INTO BookInfo(UserID, BookName, BookPath) VALUES(" + onlineID + ", \'" + name + "\', \'" + route + "\')";
                    dbManager.createDB(command_insert_book);
                }
                finish();
                break;
            }
        }
    }

    private ArrayList<String> getTitle(ArrayList<String> routes)
    {
        ArrayList<String> title = new ArrayList<String>();
        for(int i=0; i<routes.size(); ++i)
        {
            int x = routes.get(i).lastIndexOf('/');
            title.add(routes.get(i).substring(x+1, routes.get(i).length()-4));
        }
        return title;
    }

    public void onDestroy()
    {
        super.onDestroy();
        dbManager.closeDB();
    }
}
