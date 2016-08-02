package com.test.www.myapplication.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.test.www.myapplication.R;
import com.test.www.myapplication.adapter.ExperienceAdapter;
import com.test.www.myapplication.model.ExperienceModel;
import com.test.www.myapplication.model.NavsModel;
import com.test.www.myapplication.util.CacheUtil;
import com.test.www.myapplication.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品体验二级导航列表
 */
public class NavArticleActivity extends BaseActivity{

    private String cacheName = "activity_experience";//缓存名字
    private String id;
    private String title ;
    private ArrayList<NavsModel> navsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navarticle);
        //传递过来的值
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");

        if(!title.equals("") && title!=null){
            setTitle(title);
        }else{
            title = getResources().getString(R.string.experience);
            setTitle(title);
        }

        onSetData();
        setOverflowShowingAlways();
    }
    //Overflow 按钮不显示的情况
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        //调用NavUtils.getParentActivityIntent()方法可以获取到跳转至父Activity的Intent
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            //如果不是在同一个Task中的，则需要借助TaskStackBuilder来创建一个新的Task
            TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
        } else {
            //如果父Activity和当前Activity是在同一个Task中的，则直接调用navigateUpTo()方法进行跳转
            upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NavUtils.navigateUpTo(this, upIntent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // 配置SearchView的属性
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(true);
        return true;
    }

    //设置数据
    public void onSetData(){
        List cacheData = CacheUtil.readJson(this ,cacheName ,0);
        String jsonData ="";
        if(cacheData.size()!=0){
            jsonData = cacheData.get(0).toString();
            navsData = parseCacheData(jsonData);
            if(navsData.size()>0){
                List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
                for(NavsModel navsModel : navsData){
                    Map<String , Object> listItem = new HashMap<>();
                    listItem.put("id",navsModel.getId());
                    listItem.put("name",navsModel.getName());
                    listItems.add(listItem);
                }
                SimpleAdapter simpleAdapter = new SimpleAdapter(this,listItems ,R.layout._navarticle_listview_item ,
                        new String[]{"name"},
                        new int[]{ R.id.navarticle_title});
                ListView listView = (ListView)findViewById(R.id.navarticle_lv);
                listView.setAdapter(simpleAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        NavsModel navsModel = navsData.get(position);
                        ArticleActivity.actionStart(NavArticleActivity.this,navsModel.getId(),title,"product");
                    }
                });
            }else{
                mToast("没有数据 - 102");
            }
        }else if(jsonData.equals("\"empty\"")){
            mToast("没有数据 - 105");
        }else{
            mToast("没有数据 - 107");
        }
    }

    //解析缓存的数据
    private ArrayList<NavsModel> parseCacheData(String cacheData){
        ArrayList<NavsModel> tmpList = new ArrayList<>();
        try{
            JSONArray tmpExpList = new JSONArray(cacheData);
            Integer dataType ;
            for(int i=0;i <tmpExpList.length();i++){
                JSONObject tmpData = tmpExpList.getJSONObject(i);
                //判断类型
                dataType = tmpData.getInt("dataType");
                if(dataType.equals(ExperienceAdapter.TYPE_1)){
                    JSONArray navsArr = tmpData.getJSONArray("navs");
                    for(int j=0;j <navsArr.length();j++){
                        JSONObject tmpNav = navsArr.getJSONObject(j);
                        //说明是对应导航下面的数据
                        if(tmpNav.getString("id").equals(id)){
                            //下级导航数据列表
                            JSONArray cnavList = tmpNav.getJSONArray("cnav");
                            for(int k=0;k <cnavList.length();k++){
                                JSONObject tmpNav1 = cnavList.getJSONObject(k);
                                NavsModel navsModel = new NavsModel();
                                navsModel.setId(tmpNav1.getString("id"));
                                navsModel.setName(tmpNav1.getString("name"));
                                tmpList.add(navsModel);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tmpList;
    }
    //启动本 活动
    public static void actionStart(Context context,String id,String title) {
        Intent intent = new Intent(context, NavArticleActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }
}
