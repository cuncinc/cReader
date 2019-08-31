package com.cc.creader.source;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.cc.creader.R;

import java.util.ArrayList;

/**
 * Created by CC on 2019/8/31.
 */

public class TextAdapter extends PagerAdapter
{
    String text;
    private ArrayList<TextData> datas;

    public TextAdapter(ArrayList<TextData> datas)
    {
        this.datas = datas;
    }

    // viewpager中的要显示的View的总数量
    @Override
    public int getCount()
    {
        return Integer.MAX_VALUE;
//        return datas.size();
    }

    public void setText(String text)
    {
        this.text = text;
    }

    // 滑动切换的时候销毁当前的View
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object)
    {
        container.removeView(datas.get(position).getView());
    }

    // 每次滑动的时候生成的View
    public Object instantiateItem(ViewGroup container, int position)
    {
        container.addView(datas.get(position).getView());
        String s = datas.get(position).getText();
        ((TextView)(datas.get(position).getView().findViewById(R.id.textView_txt))).setText(s);
        return datas.get(position).getView();
    }
    // 官方建议这样写
    @Override
    public boolean isViewFromObject(View arg0, Object arg1)
    {
        return arg0 == arg1;
    }
}
