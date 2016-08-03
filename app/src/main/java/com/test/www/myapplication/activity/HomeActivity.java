package com.test.www.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.test.www.myapplication.R;
import com.test.www.myapplication.adapter.HomeListAdapter;
import com.test.www.myapplication.model.BaseApplication;
import com.test.www.myapplication.model.ClientModel;
import com.test.www.myapplication.model.HomeModel;
import com.test.www.myapplication.model.IconModel;
import com.test.www.myapplication.model.NewsModel;
import com.test.www.myapplication.util.CacheUtil;
import com.test.www.myapplication.util.HttpCallbackListener;
import com.test.www.myapplication.util.HttpUtil;
import com.test.www.myapplication.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 首页
 */
public class HomeActivity extends BaseActivity {
    //获取主页数据
    private static final String REQUEST_URL = REQUEST_HOST+"?c=api&a=home";
    //获取文章列表的数据
    private static final String REQUEST_URL_ARTICLE = REQUEST_HOST+"?c=api&a=getArticle";

    private String cacheName = "activity_home";//缓存名字
    private RecyclerView mRecyclerView;
    private final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private HomeListAdapter mAdapter;
    private Integer newsListNextPage = 2;//公司动态列表初始页数
//    private int cacheTime = 10;//缓存时间
    private int newsId = 23;//公司动态的导航id
    private ArrayList<HomeModel> homeData;
    private final int UPDATE_NEWS = 0;//更新公司动态列表
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_NEWS:
                    updateNewsList((String) msg.obj);
                    break;
            }
        }
    };
    private Boolean isLoading = false;//是否正在加载公司动态数据
    private Boolean isAlertMsg = false;//是否提示过没有数据了

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //选中状态
        checkedItemId = 0;
        //标题
        toolBarTitle = getResources().getString(R.string.app_name);
        //初始化导航
        initNav();

        //设置数据
        onQueryData();
    }

    //设置数据
    public void onSetData(){
        //1 小时缓存
        List cacheData = CacheUtil.readJson(HomeActivity.this ,cacheName ,cacheTime);
        String jsonData ="";
        if(cacheData.size()!=0){
            jsonData = cacheData.get(0).toString();
            homeData = parseCacheData(jsonData);
            if(homeData.size()>0){
                //使用  recyclerView
                mRecyclerView = (RecyclerView) findViewById(R.id.content_main_RV);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                //加入一个加载更多的视图
                HomeModel loadMore = new HomeModel();
                loadMore.setDataType("99");
                homeData.add(loadMore);
                mAdapter = new HomeListAdapter(HomeActivity.this, homeData);
                mAdapter.setOnItemClickLitener(new HomeListAdapter.OnItemClickLitener(){
                    @Override
                    public void onItemClick(View view, int position){
                        switch(view.getId()){
                            case R.id.frag_home_block_1:
                                BusinessActivity.actionStart(HomeActivity.this);
                                break;
                            case R.id.frag_home_block_2:
                                AdvantageActivity.actionStart(HomeActivity.this);
                                break;
                            case R.id.frag_home_block_3:
                                SolutionActivity.actionStart(HomeActivity.this);
                                break;
                            case R.id.frag_home_block_4:
                                AboutActivity.actionStart(HomeActivity.this);
                                break;
                            case R.id.frag_home_block_5:
                                ExperienceActivity.actionStart(HomeActivity.this);
                                break;
                            case R.id.frag_home_block_6:
                                CooperationActivity.actionStart(HomeActivity.this);
                                break;
                        }
                    }
                });
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if(newState == RecyclerView.SCROLL_STATE_IDLE){
                            int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
                            if(lastVisiblePosition >= linearLayoutManager.getItemCount() - 1){
                                getNewsList();
                            }
                        }
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
    //获取公司动态列表
    public void getNewsList(){
        //若是正在加载，则返回
        if(isLoading){
            return ;
        }
        if(newsListNextPage.equals(0)){
            if(!isAlertMsg) {
                mToast("没有数据啦~");
                isAlertMsg = true;
            }
            mRecyclerView.findViewById(R.id.loadmore_ll).setVisibility(View.GONE);
            return ;
        }
        isLoading = true;//标记为正在加载
        mRecyclerView.findViewById(R.id.loadmore_ll).setVisibility(View.VISIBLE);
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        final String newsCacheName = "newsList"+newsListNextPage;
        List cacheData = CacheUtil.readJson(BaseApplication.getContext() ,newsCacheName ,cacheTime);
        String requestData = "id="+newsId+"&page="+newsListNextPage;
        if(cacheData.size()==0){
            HttpUtil.sendHttpRequest(REQUEST_URL_ARTICLE,requestData ,new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Object res = parseResult(response);
                    if (res.equals("")) {
                        onError("解析数据失败",new Exception());
                    }else{
                        //存储数据
                        CacheUtil.writeJson(HomeActivity.this, res.toString(), newsCacheName, false);
                        Message message = new Message();
                        message.what = UPDATE_NEWS;
                        message.obj = newsCacheName;
                        mHandler.sendMessage(message);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                updateNewsList(newsCacheName);
//                            }
//                        });
                    }
                }

                @Override
                public void onError(final String msg,Exception e) {
                    //在主线中执行
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else{
            updateNewsList(newsCacheName);
        }
    }
    //更新公司动态列表
    public void updateNewsList(String newsCacheName){
        List cacheData = CacheUtil.readJson(this ,newsCacheName ,cacheTime);
        if(cacheData.size()!=0){
            ArrayList<NewsModel> newsList = homeData.get(3).getNews();
            try{
                JSONArray tmpRes = new JSONArray(cacheData.get(0).toString());
                JSONObject tmpNewsObj = tmpRes.getJSONObject(0);
                //更改下一页
                newsListNextPage = tmpNewsObj.getInt("nextPage");

                JSONArray tmpNewsList = tmpNewsObj.getJSONArray("newsList");
                for(int i=0;i <tmpNewsList.length();i++) {
                    JSONObject tmpData = tmpNewsList.getJSONObject(i);
                    NewsModel newsModel = new NewsModel();
                    newsModel.setId(tmpData.getInt("id"));
                    newsModel.setSubject(tmpData.getString("subject"));
                    newsModel.setThumbImg(tmpData.getString("thumb_img"));
                    newsModel.setDescription(tmpData.getString("description"));
                    newsModel.setTime(tmpData.getString("time"));
                    newsList.add(newsModel);
                }
                //通知更新 item
                homeData.get(3).setNews(newsList);
                mAdapter.notifyItemChanged(3);
//                mRecyclerView.smoothScrollBy(0,mRecyclerView.getMeasuredHeight());//滑动到底部
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            mToast("没有公司动态数据");
        }
        isLoading = false;//标记为结束加载
//        mRecyclerView.findViewById(R.id.loadmore_ll).setVisibility(View.GONE);
    }
    //获取数据
    public void onQueryData(){
        //1 小时缓存
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
                            Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else{
            onSetData();
        }
    }

    //解析缓存的数据
    private ArrayList<HomeModel> parseCacheData(String cacheData){
        ArrayList<HomeModel> tmpList = new ArrayList<>();
        try{
            JSONArray tmpHomeList = new JSONArray(cacheData);
            Integer dataType ;
            for(int i=0;i <tmpHomeList.length();i++){
                JSONObject tmpData = tmpHomeList.getJSONObject(i);
                //判断类型
                dataType = tmpData.getInt("dataType");
                HomeModel homeModel = new HomeModel();
                homeModel.setDataType(dataType.toString());
                if(dataType.equals(HomeListAdapter.TYPE_1)){
                    homeModel.setImgUrl(tmpData.getString("imgUrl"));
                    homeModel.setTitle(tmpData.getString("title"));
                    homeModel.setSubTitle(tmpData.getString("subTitle"));
                }else if(dataType.equals(HomeListAdapter.TYPE_2)){
                    ArrayList<IconModel> tmpIcons = new ArrayList<>();
                    JSONArray incoList = tmpData.getJSONArray("list");
                    for(int j=0;j <incoList.length();j++){
                        JSONObject tmpIcon = incoList.getJSONObject(j);
                        IconModel iconModel = new IconModel();
                        iconModel.setTitle(tmpIcon.getString("title2"));
                        iconModel.setIconType(tmpIcon.getString("iconType"));
                        tmpIcons.add(iconModel);
                    }
                    homeModel.setIcons(tmpIcons);
                }else if(dataType.equals(HomeListAdapter.TYPE_3)){
                    ArrayList<ClientModel> tmpClients = new ArrayList<>();
                    JSONArray clientList = tmpData.getJSONArray("list");
                    for(int j=0;j <clientList.length();j++){
                        JSONObject tmpClient = clientList.getJSONObject(j);
                        ClientModel clientModel = new ClientModel();
                        clientModel.setImgUrl(tmpClient.getString("logoUrl"));
                        clientModel.setName(tmpClient.getString("name"));
                        tmpClients.add(clientModel);
                    }
                    homeModel.setClients(tmpClients);
                }else if(dataType.equals(HomeListAdapter.TYPE_4)) {
                    ArrayList<NewsModel> tmpNews = new ArrayList<>();
                    JSONArray newList = tmpData.getJSONArray("articleList");
                    //下一页 和 栏目id
                    newsListNextPage = tmpData.getInt("nextPage");
                    newsId = tmpData.getInt("id");
                    for(int j=0;j <newList.length();j++){
                        JSONObject tmpNew = newList.getJSONObject(j);
                        NewsModel newsModel = new NewsModel();
                        newsModel.setId(tmpNew.getInt("id"));
                        newsModel.setSubject(tmpNew.getString("subject"));
                        newsModel.setThumbImg(tmpNew.getString("thumb_img"));
                        newsModel.setDescription(tmpNew.getString("description"));
                        newsModel.setTime(tmpNew.getString("time"));
                        tmpNews.add(newsModel);
                    }
                    homeModel.setNews(tmpNews);
                }
                tmpList.add(homeModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tmpList;
    }
    //启动本 活动
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

}
