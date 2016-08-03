package com.app.www.weijingtong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.www.weijingtong.R;
import com.app.www.weijingtong.adapter.BusinessListAdapter;
import com.app.www.weijingtong.model.BaseApplication;
import com.app.www.weijingtong.model.BusinessModel;
import com.app.www.weijingtong.model.TagIconModel;
import com.app.www.weijingtong.util.CacheUtil;
import com.app.www.weijingtong.util.HttpCallbackListener;
import com.app.www.weijingtong.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 主要业务
 */
public class BusinessActivity extends BaseActivity{

    private static final String REQUEST_URL = REQUEST_HOST +"?c=api&a=business";
    private String cacheName = "activity_business";//缓存名字
    private RecyclerView mRecyclerView;
//    private int cacheTime = 10;//缓存时间

    private ArrayList<BusinessModel> businessData;
    private BusinessListAdapter mAdapter;

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

        onQueryData();
    }

    //设置数据
    public void onSetData(){
        List cacheData = CacheUtil.readJson(BusinessActivity.this ,cacheName ,cacheTime);
        String jsonData ="";
        if(cacheData.size()!=0){
            jsonData = cacheData.get(0).toString();
            businessData = parseCacheData(jsonData);
            if(businessData.size()>0){
                //使用  recyclerView
                mRecyclerView = (RecyclerView) findViewById(R.id.content_main_RV);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                mAdapter = new BusinessListAdapter(this, businessData);
                mAdapter.setOnItemClickLitener(new BusinessListAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //获取第二个item 的视图，控制 tag 的显隐
                        View clssifyView = mRecyclerView.getChildAt(1);
                        View descView = mRecyclerView.getChildAt(0);
                        switch(view.getId()){
                            case R.id.btn_ydznty:
                                //改变按钮背景
                                descView.findViewById(R.id.btn_ydznty).setBackgroundResource(R.drawable.bus_ydznty_g);
                                descView.findViewById(R.id.btn_shhyx).setBackgroundResource(R.drawable.bus_shhyx);
                                descView.findViewById(R.id.btn_sjhyx).setBackgroundResource(R.drawable.bus_sjhyx);
                                descView.findViewById(R.id.btn_ydds).setBackgroundResource(R.drawable.bus_ydds);
                                //控制tag 显隐
                                clssifyView.findViewById(R.id.item_ydznty).setVisibility(View.VISIBLE);
                                clssifyView.findViewById(R.id.item_shhyx).setVisibility(View.GONE);
                                clssifyView.findViewById(R.id.item_sjhyx).setVisibility(View.GONE);
                                clssifyView.findViewById(R.id.item_ydds).setVisibility(View.GONE);
                                break;
                            case R.id.btn_shhyx:
                                //改变按钮背景
                                descView.findViewById(R.id.btn_ydznty).setBackgroundResource(R.drawable.bus_ydznty);
                                descView.findViewById(R.id.btn_shhyx).setBackgroundResource(R.drawable.bus_shhyx_g);
                                descView.findViewById(R.id.btn_sjhyx).setBackgroundResource(R.drawable.bus_sjhyx);
                                descView.findViewById(R.id.btn_ydds).setBackgroundResource(R.drawable.bus_ydds);

                                clssifyView.findViewById(R.id.item_ydznty).setVisibility(View.GONE);
                                clssifyView.findViewById(R.id.item_shhyx).setVisibility(View.VISIBLE);
                                clssifyView.findViewById(R.id.item_sjhyx).setVisibility(View.GONE);
                                clssifyView.findViewById(R.id.item_ydds).setVisibility(View.GONE);
                                break;
                            case R.id.btn_sjhyx:
                                //改变按钮背景
                                descView.findViewById(R.id.btn_ydznty).setBackgroundResource(R.drawable.bus_ydznty);
                                descView.findViewById(R.id.btn_shhyx).setBackgroundResource(R.drawable.bus_shhyx);
                                descView.findViewById(R.id.btn_sjhyx).setBackgroundResource(R.drawable.bus_sjhyx_g);
                                descView.findViewById(R.id.btn_ydds).setBackgroundResource(R.drawable.bus_ydds);

                                clssifyView.findViewById(R.id.item_ydznty).setVisibility(View.GONE);
                                clssifyView.findViewById(R.id.item_shhyx).setVisibility(View.GONE);
                                clssifyView.findViewById(R.id.item_sjhyx).setVisibility(View.VISIBLE);
                                clssifyView.findViewById(R.id.item_ydds).setVisibility(View.GONE);
                                break;
                            case R.id.btn_ydds:
                                //改变按钮背景
                                descView.findViewById(R.id.btn_ydznty).setBackgroundResource(R.drawable.bus_ydznty);
                                descView.findViewById(R.id.btn_shhyx).setBackgroundResource(R.drawable.bus_shhyx);
                                descView.findViewById(R.id.btn_sjhyx).setBackgroundResource(R.drawable.bus_sjhyx);
                                descView.findViewById(R.id.btn_ydds).setBackgroundResource(R.drawable.bus_ydds_g);

                                clssifyView.findViewById(R.id.item_ydznty).setVisibility(View.GONE);
                                clssifyView.findViewById(R.id.item_shhyx).setVisibility(View.GONE);
                                clssifyView.findViewById(R.id.item_sjhyx).setVisibility(View.GONE);
                                clssifyView.findViewById(R.id.item_ydds).setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                });
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
                            mToastStatic(msg);
                        }
                    });
                }
            });
        }else{
            onSetData();
        }
    }

    //解析缓存的数据
    private ArrayList<BusinessModel> parseCacheData(String cacheData){
        ArrayList<BusinessModel> tmpList = new ArrayList<>();
        try{
            JSONArray tmpBusinessList = new JSONArray(cacheData);
            Integer dataType ;
            for(int i=0;i <tmpBusinessList.length();i++){
                JSONObject tmpData = tmpBusinessList.getJSONObject(i);
                BusinessModel businessModel = new BusinessModel();
                //判断类型
                dataType = tmpData.getInt("dataType");
                businessModel.setDataType(dataType.toString());
                if(dataType.equals(BusinessListAdapter.TYPE_1)){
                    businessModel.setDescription(tmpData.getString("description"));
                }else if(dataType.equals(BusinessListAdapter.TYPE_2)){
                    businessModel.setYdznty(getTagIconModel(tmpData.getJSONArray("ydznty")));
                    businessModel.setSjhyx(getTagIconModel(tmpData.getJSONArray("sjhyx")));
                    businessModel.setShhyx(getTagIconModel(tmpData.getJSONArray("shhyx")));
                    businessModel.setYdds(getTagIconModel(tmpData.getJSONArray("ydds")));
                }
                tmpList.add(businessModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tmpList;
    }
    private ArrayList<TagIconModel> getTagIconModel(JSONArray jsonArray){
        ArrayList<TagIconModel> tmpList = new ArrayList<>();
        try{
            for(int i=0;i <jsonArray.length();i++){
                JSONObject tmpData = jsonArray.getJSONObject(i);
                TagIconModel tagIconModel = new TagIconModel();
                tagIconModel.setIcon(tmpData.getString("icon"));
                tagIconModel.setTitle(tmpData.getString("title"));
                tmpList.add(tagIconModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tmpList;
    }

    //启动本 活动
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, BusinessActivity.class);
        context.startActivity(intent);
    }
}
