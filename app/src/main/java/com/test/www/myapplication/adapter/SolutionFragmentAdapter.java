package com.test.www.myapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.test.www.myapplication.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class SolutionFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<String> titles;

    private List<BaseFragment> fragments;

    public SolutionFragmentAdapter(FragmentManager fm, ArrayList<String> list, List<BaseFragment> fragments) {
        super(fm);
        this.titles = list;
        this.fragments=fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
