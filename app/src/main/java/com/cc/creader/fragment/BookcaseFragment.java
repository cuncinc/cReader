package com.cc.creader.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cc.creader.R;

public class BookcaseFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.bookcase_fragment, container, false);
        EditText et_string = (EditText) view.findViewById(R.id.edittext_bookcase);
        Button bt_string = (Button) view.findViewById(R.id.button_bookcase);

        return view;
    }

}
