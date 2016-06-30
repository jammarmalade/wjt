package com.test.www.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.test.www.myapplication.R;
import com.test.www.myapplication.adapter.SolutionFragmentAdapter;
import com.test.www.myapplication.fragment.SolutionCaseFragment;
import com.test.www.myapplication.fragment.SolutionSchemeFragment;
import com.test.www.myapplication.util.PagerSlidingTabStrip;

import java.util.ArrayList;

/**
 * 解决方案
 */
public class SolutionActivity extends BaseActivity{

    private static final String REQUEST_URL = BaseActivity.REQUEST_HOST+"?c=api&a=solution";
    private String cacheName = "activity_solution";//缓存名字
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //选中状态
        checkedItemId = 3;
        //标题
        toolBarTitle = getResources().getString(R.string.solution);
        //初始化导航
        initNav();

        //使用layoutInflater布局
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.content_main);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View homeLayout = layoutInflater.inflate(R.layout.activity_solution, null);
        mainLayout.addView(homeLayout);

        //设置tab导航
        pager = (ViewPager) findViewById(R.id.solution_pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.solution_tabs);
        ArrayList<String> titles = new ArrayList<>();
        titles.add("解决方案");
        titles.add("案例");
        ArrayList<Fragment> fragments = new ArrayList<>();
        //解决方案
        Bundle bundle1 = new Bundle();
        bundle1.putString("title", titles.get(0));
        fragments.add(SolutionSchemeFragment.getInstance(bundle1));
        //案例
        Bundle bundle2 = new Bundle();
        bundle2.putString("title", titles.get(1));
        fragments.add(SolutionCaseFragment.getInstance(bundle2));

        pager.setAdapter(new SolutionFragmentAdapter(getSupportFragmentManager(), titles, fragments));
        tabs.setViewPager(pager);
        pager.setCurrentItem(0);

    }

    //设置数据
    public void onSetData(){

    }
    //获取数据
    public void onQueryData(){

    }
    //启动本 活动
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SolutionActivity.class);
        context.startActivity(intent);
    }
}
