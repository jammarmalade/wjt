package com.test.www.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.test.www.myapplication.R;

/**
 * 主要业务
 */
public class BusinessActivity extends BaseActivity{

    private static final String REQUEST_URL = BaseActivity.REQUEST_HOST+"?c=api&a=business";
    private String cacheName = "activity_business";//缓存名字


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //选中状态
        checkedItemId = 1;
        //标题
        toolBarTitle = getResources().getString(R.string.business);
        //初始化导航
        initNav();

        //使用layoutInflater布局
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.content_main);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View homeLayout = layoutInflater.inflate(R.layout.activity_business, null);
        mainLayout.addView(homeLayout);



    }

    //设置数据
    public void onSetData(){

    }
    //获取数据
    public void onQueryData(){

    }
    //启动本 活动
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, BusinessActivity.class);
        context.startActivity(intent);
    }
}
