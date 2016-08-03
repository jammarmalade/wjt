package com.test.www.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.test.www.myapplication.R;
import com.test.www.myapplication.adapter.AboutAdapter;
import com.test.www.myapplication.model.BaseApplication;
import com.test.www.myapplication.util.CacheUtil;
import com.test.www.myapplication.util.HttpCallbackListener;
import com.test.www.myapplication.util.HttpUtil;
import com.test.www.myapplication.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * 与我们合作
 */
public class CooperationActivity extends BaseActivity{

    private static final String REQUEST_URL = BaseActivity.REQUEST_HOST+"?c=api&a=cooperation";
    private String cacheName = "activity_cooperation";//缓存名字
//    private int cacheTime = 10;//缓存时间
    private ArrayMap<String ,String> cooperationData = new ArrayMap<>();
    private TextView cooperation1;
    private TextView coon_setp1;
    private TextView coon_setp2;
    private TextView coon_setp3;
    private TextView coon_setp4;
    private TextView coon_setp5;
    private TextView cooperation2;
    private TextView qdswhz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //选中状态
        checkedItemId = 6;
        toolBarTitle = getResources().getString(R.string.cooperation);
        //初始化导航
        initNav();

        //使用layoutInflater布局
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.content_main);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View homeLayout = layoutInflater.inflate(R.layout.activity_cooperation, null);
        mainLayout.addView(homeLayout);

        cooperation1 = (TextView)findViewById(R.id.cooperation1);
        coon_setp1 = (TextView)findViewById(R.id.coon_setp1);
        coon_setp2 = (TextView)findViewById(R.id.coon_setp2);
        coon_setp3 = (TextView)findViewById(R.id.coon_setp3);
        coon_setp4 = (TextView)findViewById(R.id.coon_setp4);
        coon_setp5 = (TextView)findViewById(R.id.coon_setp5);
        cooperation2 = (TextView)findViewById(R.id.cooperation2);
        qdswhz = (TextView)findViewById(R.id.qdswhz);
        onQueryData();
    }

    //设置数据
    public void onSetData(){
        List cacheData = CacheUtil.readJson(CooperationActivity.this ,cacheName ,cacheTime);
        String jsonData ="";
        if(cacheData.size()!=0){
            jsonData = cacheData.get(0).toString();
            cooperationData = parseCacheData(jsonData);
            if(cooperationData.size()>0){
                cooperation1.setText(Html.fromHtml(cooperationData.get("cooperation1")));
                coon_setp1.setText(Html.fromHtml(cooperationData.get("step1")));
                coon_setp2.setText(Html.fromHtml(cooperationData.get("step2")));
                coon_setp3.setText(Html.fromHtml(cooperationData.get("step3")));
                coon_setp4.setText(Html.fromHtml(cooperationData.get("step4")));
                coon_setp5.setText(Html.fromHtml(cooperationData.get("step5")));
                cooperation2.setText(Html.fromHtml(cooperationData.get("cooperation2")));
                qdswhz.setText(Html.fromHtml(cooperationData.get("contact")));
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
                            Toast.makeText(CooperationActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else{
            onSetData();
        }
    }
    private ArrayMap<String ,String> parseCacheData (String cacheData){
        ArrayMap<String ,String> tmpData = new ArrayMap<>();
        try{
            JSONArray tmpCoopArr = new JSONArray(cacheData);
            JSONObject tmpCoopData = tmpCoopArr.getJSONObject(0);
            tmpData.put("cooperation1",tmpCoopData.getString("cooperation1"));
            tmpData.put("step1",tmpCoopData.getString("step1"));
            tmpData.put("step2",tmpCoopData.getString("step2"));
            tmpData.put("step3",tmpCoopData.getString("step3"));
            tmpData.put("step4",tmpCoopData.getString("step4"));
            tmpData.put("step5",tmpCoopData.getString("step5"));
            tmpData.put("cooperation2",tmpCoopData.getString("cooperation2"));
            tmpData.put("contact",tmpCoopData.getString("contact"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return tmpData;
    }
    //启动本 活动
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CooperationActivity.class);
        context.startActivity(intent);
    }
}
