package com.cc.creader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cc.creader.lib.FileUtils;

import java.util.ArrayList;

/**
 * Created by CC on 2019/8/27.
 */

public class AddBookActivity extends AppCompatActivity
{
    private ArrayList<String> file_routes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_book);
        Button bt_scanbook = (Button)findViewById(R.id.button_scanbook);
        file_routes=new ArrayList<>();
        bt_scanbook.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                file_routes = FileUtils.getSpecificTypeOfFile(AddBookActivity.this, new String[]{".txt"});
                for (int i=0; i<file_routes.size(); ++i)
                {
                    Log.e("file_route", file_routes.get(i));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddBookActivity.this, android.R.layout.simple_list_item_1, file_routes);
                ListView listView = (ListView)findViewById(R.id.listView_scanned_book);
                listView.setAdapter(adapter);
            }
        });
    }
}
