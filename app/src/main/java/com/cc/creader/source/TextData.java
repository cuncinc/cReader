package com.cc.creader.source;

import android.view.View;

/**
 * Created by CC on 2019/8/31.
 */

public class TextData
{
    View view;
    String text;

    public TextData(View v, String t)
    {
        this.view = v;
        text = t;
    }

    public View getView()
    {
        return view;
    }

    public String getText()
    {
        return text;
    }

    public void setView(View view)
    {
        this.view = view;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
