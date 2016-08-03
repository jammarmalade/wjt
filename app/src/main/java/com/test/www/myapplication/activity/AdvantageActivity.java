package com.test.www.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.www.myapplication.R;
import com.test.www.myapplication.adapter.BusinessListAdapter;
import com.test.www.myapplication.model.BaseApplication;
import com.test.www.myapplication.model.BusinessModel;
import com.test.www.myapplication.util.CacheUtil;
import com.test.www.myapplication.util.HttpCallbackListener;
import com.test.www.myapplication.util.HttpUtil;
import com.test.www.myapplication.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 核心优势
 */
public class AdvantageActivity extends BaseActivity{

    private static final String REQUEST_URL = BaseActivity.REQUEST_HOST+"?c=api&a=advantage";
    private String cacheName = "activity_advantage";//缓存名字
//    private int cacheTime = 10;//缓存时间
    private ArrayMap<String, String> advantageData ;
    private View advLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //选中状态
        checkedItemId = 2;
        //标题
        toolBarTitle = getResources().getString(R.string.advantage);
        //初始化导航
        initNav();

        //使用layoutInflater布局
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.content_main);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        advLayout = layoutInflater.inflate(R.layout.activity_advantage, null);
        mainLayout.addView(advLayout);
        //隐藏 RecyclerView
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.content_main_RV);
        mRecyclerView.setVisibility(View.GONE);

        onQueryData();
    }

    //设置数据
    public void onSetData(){
        List cacheData = CacheUtil.readJson(AdvantageActivity.this ,cacheName ,cacheTime);
        String jsonData ="";
        if(cacheData.size()!=0){
            jsonData = cacheData.get(0).toString();
            advantageData = parseCacheData(jsonData);

            if(advantageData.size()>0){
                TextView title1 = (TextView) advLayout.findViewById(R.id.adv_title1);
                title1.setText(advantageData.get("title1"));
                TextView title2 = (TextView) advLayout.findViewById(R.id.adv_title2);
                title2.setText(advantageData.get("title2"));
                TextView title3 = (TextView) advLayout.findViewById(R.id.adv_title3);
                title3.setText(advantageData.get("title3"));
                TextView title4 = (TextView) advLayout.findViewById(R.id.adv_title4);
                title4.setText(advantageData.get("title4"));
            }else{
                mToast("没有数据 - 102");
            }
        }else if(jsonData.equals("\"empty\"")){
            mToast("没有数据 - 105");
        }else{
            mToast("没有数据 - 107");
        }
    }
    //获取数据
    public void onQueryData(){
        List cacheData = CacheUtil.readJson(this ,cacheName ,cacheTime);
        if(cacheData.size()==0){
            HttpUtil.sendHttpRequest(REQUEST_URL, "",new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Object res = parseResult(response);
                    if (res.equals("")) {
                        onError("解析数据失败",new Exception());
                    }else{
                        //存储数据
                        CacheUtil.writeJson(BaseApplication.getContext(), res.toString(), cacheName, false);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onSetData();
                            }
                        });
                    }
                }

                @Override
                public void onError(final String msg,Exception e) {
                    //在主线中执行
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseActivity.mToastStatic(msg);
                        }
                    });
                }
            });
        }else{
            onSetData();
        }
    }
    //解析缓存的数据
    private ArrayMap<String, String> parseCacheData(String cacheData){
        ArrayMap<String, String> tmpList = new ArrayMap<>();
        try{
            JSONArray tmpAdvantageArr = new JSONArray(cacheData);
            JSONObject tmpAdvantageObj = tmpAdvantageArr.getJSONObject(0);
            tmpList.put("title1",tmpAdvantageObj.getString("title1"));
            tmpList.put("title2",tmpAdvantageObj.getString("title2"));
            tmpList.put("title3",tmpAdvantageObj.getString("title3"));
            tmpList.put("title4",tmpAdvantageObj.getString("title4"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return tmpList;
    }
    //启动本 活动
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AdvantageActivity.class);
        context.startActivity(intent);
    }
}
