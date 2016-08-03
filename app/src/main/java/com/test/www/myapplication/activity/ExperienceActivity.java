package com.test.www.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.test.www.myapplication.R;
import com.test.www.myapplication.adapter.AboutAdapter;
import com.test.www.myapplication.adapter.ExperienceAdapter;
import com.test.www.myapplication.adapter.HomeListAdapter;
import com.test.www.myapplication.model.AboutModel;
import com.test.www.myapplication.model.ArticleModel;
import com.test.www.myapplication.model.BaseApplication;
import com.test.www.myapplication.model.ExperienceModel;
import com.test.www.myapplication.model.ImageModel;
import com.test.www.myapplication.model.NavsModel;
import com.test.www.myapplication.util.CacheUtil;
import com.test.www.myapplication.util.HttpCallbackListener;
import com.test.www.myapplication.util.HttpUtil;
import com.test.www.myapplication.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品体验
 */
public class ExperienceActivity extends BaseActivity{

    private static final String REQUEST_URL = BaseActivity.REQUEST_HOST+"?c=api&a=experience";
    private String cacheName = "activity_experience";//缓存名字
//    private int cacheTime = 10;//缓存时间
    private RecyclerView mRecyclerView;
    private ExperienceAdapter mAdapter;
    private ArrayList<ExperienceModel> experienceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //选中状态
        checkedItemId = 4;
        toolBarTitle = getResources().getString(R.string.experience);
        //初始化导航
        initNav();

        onQueryData();
    }

    //设置数据
    public void onSetData(){
        List cacheData = CacheUtil.readJson(ExperienceActivity.this ,cacheName ,cacheTime);
        String jsonData ="";
        if(cacheData.size()!=0){
            jsonData = cacheData.get(0).toString();
            experienceData = parseCacheData(jsonData);
            if(experienceData.size()>0){
                //使用  recyclerView
                mRecyclerView = (RecyclerView) findViewById(R.id.content_main_RV);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                mAdapter = new ExperienceAdapter(this, experienceData);
                mRecyclerView.setAdapter(mAdapter);
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
                            Toast.makeText(ExperienceActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else{
            onSetData();
        }
    }

    //解析缓存的数据
    private ArrayList<ExperienceModel> parseCacheData(String cacheData){
        ArrayList<ExperienceModel> tmpList = new ArrayList<>();
        try{
            JSONArray tmpExpList = new JSONArray(cacheData);
            Integer dataType ;
            for(int i=0;i <tmpExpList.length();i++){
                JSONObject tmpData = tmpExpList.getJSONObject(i);
                //判断类型
                dataType = tmpData.getInt("dataType");
                ExperienceModel experienceModel = new ExperienceModel();
                experienceModel.setDataType(dataType.toString());
                ArrayList<NavsModel> navsModelList = new ArrayList<>();
                if(dataType.equals(ExperienceAdapter.TYPE_1)){
                    JSONArray navsArr = tmpData.getJSONArray("navs");
                    for(int j=0;j <navsArr.length();j++){
                        JSONObject tmpNav = navsArr.getJSONObject(j);
                        NavsModel navsModel = new NavsModel();
                        navsModel.setId(tmpNav.getString("id"));
                        navsModel.setName(tmpNav.getString("name"));
                        navsModel.setIconType(tmpNav.getString("iconType"));
                        //下级导航数据列表
                        JSONArray cnavList = tmpNav.getJSONArray("cnav");
                        ArrayList<NavsModel> CnavsModelList = new ArrayList<>();
                        for(int k=0;k <cnavList.length();k++){
                            JSONObject tmpNav1 = cnavList.getJSONObject(k);
                            NavsModel navsModel1 = new NavsModel();
                            navsModel1.setId(tmpNav1.getString("id"));
                            navsModel1.setName(tmpNav1.getString("name"));
                            CnavsModelList.add(navsModel1);
                        }
                        navsModel.setCnavs(CnavsModelList);
                        navsModelList.add(navsModel);
                    }
                    experienceModel.setNavs(navsModelList);
                }
                tmpList.add(experienceModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tmpList;
    }

    //启动本 活动
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ExperienceActivity.class);
        context.startActivity(intent);
    }
}
