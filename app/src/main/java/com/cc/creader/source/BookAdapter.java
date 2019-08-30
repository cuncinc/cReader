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
//        Line line=lines.get(position);
//        imageView.setImageResource(line.getImg());
        textView.setText(data.get(position).getTitle());
        return convertView;
    }
}
