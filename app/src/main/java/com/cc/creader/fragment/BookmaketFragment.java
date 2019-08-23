package com.cc.creader.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cc.creader.R;

/**
 * Created by CC on 2019/8/23.
 */

public class BookmaketFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.bookmaket_fragment, container, false);
        return view;
    }
}
