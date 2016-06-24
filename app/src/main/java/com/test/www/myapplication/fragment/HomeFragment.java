package com.test.www.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.www.myapplication.R;

;

/**
 * Created by weijingtong20 on 2016/6/16.
 * 首页
 */
public class HomeFragment extends BaseFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onQueryData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        return view;
    }

}
