package com.cc.creader.lib;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Files;
import android.provider.MediaStore.Files.FileColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by CC on 2019/8/25.
 * Copy from https://blog.csdn.net/Programming2012/article/details/50060099
 */

public class FileUtils
{
    public static ArrayList<String> getSpecificTypeOfFile(Context context, String[] extension)
    {
        ArrayList<String> file_routes = new ArrayList<String>();
        //从外存中获取
        Uri fileUri = Files.getContentUri("external");
        //筛选列，这里只筛选了：文件路径和不含后缀的文件名
        String[] projection = new String[]
        {
            FileColumns.DATA, FileColumns.TITLE
        };
        //构造筛选语句
        String selection = "";
        for (int i = 0; i < extension.length; i++)
        {
            if (i != 0)
            {
                selection = selection + " OR ";
            }
            selection = selection + FileColumns.DATA + " LIKE '%" + extension[i] + "'";
        }
        //按时间递增顺序对结果进行排序;待会从后往前移动游标就可实现时间递减
        String sortOrder = FileColumns.DATE_MODIFIED;
        //获取内容解析器对象
        ContentResolver resolver = context.getContentResolver();
        //获取游标
        Cursor cursor = resolver.query(fileUri, projection, selection, null, sortOrder);
        if (cursor == null)
            return null;
        //游标从最后开始往前递减，以此实现时间递减顺序（最近访问的文件，优先显示）
        if (cursor.moveToLast())
        {
            do
            {
                //输出文件的完整路径
                String data = cursor.getString(0);
                file_routes.add(data);
//                Log.e("tag", data);
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        return file_routes;
    }
}
