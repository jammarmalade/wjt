package com.test.www.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.test.www.myapplication.R;
import com.test.www.myapplication.adapter.ArticleAdapter;
import com.test.www.myapplication.adapter.ExperienceAdapter;
import com.test.www.myapplication.model.ArticleModel;
import com.test.www.myapplication.model.BaseApplication;
import com.test.www.myapplication.model.NavsModel;
import com.test.www.myapplication.util.CacheUtil;
import com.test.www.myapplication.util.HttpCallbackListener;
import com.test.www.myapplication.util.HttpUtil;
import com.test.www.myapplication.util.LogUtil;
import com.test.www.myapplication.util.Unity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章界面
 */
public class ArticleActivity extends BaseActivity{
    private static final String REQUEST_URL = BaseActivity.REQUEST_HOST+"?c=api&a=article";
    private String cacheName = "article";//缓存名字
    private String id;//文章id , 若 from  为 product ,则是 栏目id
    private ArticleModel articleData = new ArticleModel();
    private ListView contentLV;
    private TextView subject;
    private TextView time;
    private String from = "";//来源
    private String title = "";
    private LinearLayout article_content ;
    private LinearLayout article_loading ;
//    private int cacheTime = 10;//缓存时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        //传递过来的值
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        from = getIntent().getStringExtra("from");

        if(!title.equals("") && title!=null){
            setTitle(title);
        }else{
            setTitle(getResources().getString(R.string.app_name));
        }
        contentLV = (ListView)findViewById(R.id.article_lv);
        subject = (TextView) findViewById(R.id.article_subject);
        time = (TextView)findViewById(R.id.article_time);
        article_content = (LinearLayout) findViewById(R.id.article_content);
        article_loading = (LinearLayout)findViewById(R.id.article_loading);

        onQueryData();
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
    //按键返回
    @Override
    public void onBackPressed() {
        backListen();
    }
    //顶部返回按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        backListen();
        return true;
    }
    //监听返回事件
    public void backListen(){
        switch (from){
            case "home":
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
                break;
            case "solution":
                SolutionActivity.actionStart(ArticleActivity.this);
                finish();
                break;
            case "product":
                NavArticleActivity.actionStart(ArticleActivity.this,id,title);
                finish();
                break;
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
        String tmpCacheName = cacheName+id+from;
        List cacheData = CacheUtil.readJson(this ,tmpCacheName ,0);
        String jsonData ="";
        if(cacheData.size()!=0){
            jsonData = cacheData.get(0).toString();

            articleData = parseCacheData(jsonData);
            if(articleData.getId()!=null){
                //设置标题
                subject.setText(articleData.getSubject());
                //设置发表时间
                time.setText(articleData.getTime());
                //设置文章内容
                ArrayList<String> dateList = articleData.getContentList();
                ArticleAdapter articleAdapter = new ArticleAdapter(this , R.layout._article_listview_item, dateList);
                contentLV.setAdapter(articleAdapter);
                //重新计算listview高度
                Unity.setListViewHeight(contentLV);
                article_content.setVisibility(View.VISIBLE);
                article_loading.setVisibility(View.GONE);
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
        final String tmpCacheName = cacheName+id+from;
        List cacheData = CacheUtil.readJson(this ,tmpCacheName ,cacheTime);
        if(cacheData.size()==0){
            String requestData = "id="+id+"&from="+from;
            HttpUtil.sendHttpRequest(REQUEST_URL, requestData,new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Object res = parseResult(response);
                    if (res.equals("")) {
                        onError("解析数据失败",new Exception());
                    }else{
                        //存储数据
                        CacheUtil.writeJson(BaseApplication.getContext(), res.toString(), tmpCacheName, false);
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
                            Toast.makeText(ArticleActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else{
            onSetData();
        }
    }
    //解析缓存的数据
    private ArticleModel parseCacheData(String cacheData){
        ArticleModel articleModel = new ArticleModel();
        try{
            JSONArray tmpArticleArr = new JSONArray(cacheData);
            JSONObject tmpArticle = tmpArticleArr.getJSONObject(0);
            articleModel.setId(tmpArticle.getString("id"));
            articleModel.setSubject(tmpArticle.getString("subject"));
            articleModel.setTime(tmpArticle.getString("time"));
            JSONArray tmpContentList = tmpArticle.getJSONArray("content");
            ArrayList<String> contentList = new ArrayList<>();
            for(int j=0;j <tmpContentList.length();j++){
                contentList.add(tmpContentList.getString(j));
            }
            articleModel.setContentList(contentList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return articleModel;
    }
    //启动本 活动
    public static void actionStart(Context context,String id,String title,String from) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("from", from);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
