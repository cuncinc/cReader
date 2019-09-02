package com.cc.creader.source;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.creader.R;

import java.util.ArrayList;

/**
 * Created by CC on 2019/9/2.
 */

    public class ScannedBookAdapter extends ArrayAdapter<BookData>
{
//    private Context context;
//    private ArrayList<BookData> data;
    private int resourceId;

    public ScannedBookAdapter(Context context, int textViewResourceId, ArrayList<BookData> data)
    {
        super(context, textViewResourceId, data);
        resourceId = textViewResourceId;
//        this.context = context;
//        this.data = data;
    }

    @Override
    //返回每一项的显示内容
    public View getView(int position, View v, ViewGroup parent)
    {
        BookData data = getItem(position);
        v = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView_cover_scanned_book);
        TextView tv_title = (TextView) v.findViewById(R.id.textView_title_scanned_book);
        TextView tv_size = (TextView) v.findViewById(R.id.textView_size_scanned_book);
        double size = data.getSize();
        int res = getImage((int)(size/1024/1024));
        String str_size;
        if (size/1024/1024 < 1.0)
        {
            str_size = ""+size/1024+" K";
        }
        else
        {
            str_size = ""+size/1024/1024+" M";
        }
        imageView.setImageResource(res);
        tv_title.setText(data.getTitle());
        tv_size.setText(str_size);
        return v;
    }

    int getImage(int size)
    {
        int res;
        switch (size)
        {
            case 0:
                res = R.drawable.c1;
                break;
            case 1:
                res = R.drawable.c2;
                break;

            case 2:
                res = R.drawable.c3;
                break;
            case 3:
                res = R.drawable.c4;
                break;
            case 4:
                res = R.drawable.c5;
                break;
            case 5:
                res = R.drawable.c6;
                break;
            case 6:
                res = R.drawable.c7;
                break;
            case 7:
                res = R.drawable.c8;
                break;
            case 8:
                res = R.drawable.c9;
                break;
            case 9:
                res = R.drawable.c10;
                break;
            default:
                res = R.drawable.c11;
                break;
        }
        return res;
    }
}
