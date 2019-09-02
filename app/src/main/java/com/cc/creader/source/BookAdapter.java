package com.cc.creader.source;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cc.creader.R;

import java.util.ArrayList;

/**
 * Created by CC on 2019/8/30.
 */

public class BookAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<BookData> data;

    public BookAdapter(Context context, ArrayList<BookData> data)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    //指定的索引对应的数据项
    public Object getItem(int position)
    {
        return data.get(position);
    }

    @Override
    //ListView需要显示的数据数量
    public int getCount()
    {
        return data.size();//传过来的list的长度
    }

    @Override
    //指定的索引对应的数据项ID
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    //返回每一项的显示内容
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.layout_item_book, null);
        }
        TextView textView= (TextView) convertView.findViewById(R.id.tv_book_name);
        TextView textView2 = (TextView) convertView.findViewById(R.id.tv_book_name2);
        textView.setText(data.get(position).getTitle());
        int size = (int) (data.get(position).getSize()/1024/1024);
        int res = getImage(size);

        textView.setBackgroundResource(res);
        textView2.setText(data.get(position).getTitle());
        return convertView;
    }

    int getImage(int size)
    {
        int res;
        switch (size)
        {
            case 0:
            case 1:
                res = R.drawable.c1;
                break;
            case 2:
                res = R.drawable.c2;
                break;
            case 3:
                res = R.drawable.c3;
                break;
            case 4:
                res = R.drawable.c4;
                break;
            case 5:
                res = R.drawable.c5;
                break;
            case 6:
                res = R.drawable.c6;
                break;
            case 7:
                res = R.drawable.c7;
                break;
            case 8:
                res = R.drawable.c8;
                break;
            case 9:
                res = R.drawable.c9;
                break;
            case 10:
                res = R.drawable.c10;
                break;
            default:
                res = R.drawable.c11;
                break;
        }
        return res;
    }
}
