package com.cc.creader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by CC on 2019/8/17.
 */

public class DBManager
{
    private final int BUFFER_SIZE = 400;
    private static final String DB_NAME = "creader.db"; //保存的数据库文件名
    private static final String DB_PATH = "/data/data/com.cc.creader";

    private SQLiteDatabase database;
    private Context context;

    public DBManager(Context context)
    {
        this.context = context;
        this.database = this.openDB(DB_PATH + "/" + DB_NAME);
    }

//    public void openDB()
//    {
//        this.database = this.openDB(DB_PATH + "/" + DB_NAME);
//    }

    private SQLiteDatabase openDB(String dbfile)
    {
        try
        {
            if (!(new File(dbfile).exists()))
            {
                //判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                InputStream inputstream = this.context.getResources().openRawResource(R.raw.creader); //欲导入的数据库
                FileOutputStream fileoutputstream = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = inputstream.read(buffer)) > 0)
                {
                    fileoutputstream.write(buffer, 0, count);
                }
                fileoutputstream.close();
                inputstream.close();
            }
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,null);
            return db;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void createDB(String command_create)
    {
        database.execSQL(command_create);
    }

    public Cursor findDB(String command_find)
    {
        return ( database.rawQuery(command_find, null) );
    }

    public void updateDB(String command_update)
    {
        database.execSQL(command_update);
    }

    public void closeDB()
    {
        this.database.close();
    }

    //    public SQLiteDatabase getDB()
//    {
//        return database;
//    }
//    public void setDB(SQLiteDatabase database)
//    {
//        this.database = database;
//    }
}