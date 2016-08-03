package com.test.www.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.test.www.myapplication.R;
import com.test.www.myapplication.adapter.AboutAdapter;
import com.test.www.myapplication.adapter.HomeListAdapter;
import com.test.www.myapplication.model.AboutModel;
import com.test.www.myapplication.model.ArticleModel;
import com.test.www.myapplication.model.BaseApplication;
import com.test.www.myapplication.model.ClientModel;
import com.test.www.myapplication.model.HomeModel;
import com.test.www.myapplication.model.IconModel;
import com.test.www.myapplication.model.ImageModel;
import com.test.www.myapplication.model.NewsModel;
import com.test.www.myapplication.util.CacheUtil;
import com.test.www.myapplication.util.HttpCallbackListener;
import com.test.www.myapplication.util.HttpUtil;
import com.test.www.myapplication.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 关于我们
 */
public class AboutActivity extends BaseActivity{

    private static final String REQUEST_URL = BaseActivity.REQUEST_HOST+"?c=api&a=about";
    private String cacheName = "activity_about";//缓存名字
//    private int cacheTime = 10;//缓存时间
    private RecyclerView mRecyclerView;
    private AboutAdapter mAdapter;
    private ArrayList<AboutModel> aboutData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //选中状态
        checkedItemId = 5;
        //标题
        toolBarTitle = getResources().getString(R.string.about);
        //初始化导航
        initNav();

        //使用layoutInflater布局
//        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.content_main);
//        LayoutInflater layoutInflater = LayoutInflater.from(this);
//        View homeLayout = layoutInflater.inflate(R.layout.activity_about, null);
//        mainLayout.addView(homeLayout);

        onQueryData();
    }

    //设置数据
    public void onSetData(){
        List cacheData = CacheUtil.readJson(AboutActivity.this ,cacheName ,cacheTime);
        String jsonData ="";
        if(cacheData.size()!=0){
            jsonData = cacheData.get(0).toString();
            aboutData = parseCacheData(jsonData);
            if(aboutData.size()>0){
                //使用  recyclerView
                mRecyclerView = (RecyclerView) findViewById(R.id.content_main_RV);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                mAdapter = new AboutAdapter(this, aboutData);
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
                            Toast.makeText(AboutActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else{
            onSetData();
        }
    }
    //解析缓存的数据
    private ArrayList<AboutModel> parseCacheData(String cacheData){
        ArrayList<AboutModel> tmpList = new ArrayList<>();
        try{
            JSONArray tmpHomeList = new JSONArray(cacheData);
            Integer dataType ;
            for(int i=0;i <tmpHomeList.length();i++){
                JSONObject tmpData = tmpHomeList.getJSONObject(i);
                //判断类型
                dataType = tmpData.getInt("dataType");
                AboutModel aboutModel = new AboutModel();
                aboutModel.setDataType(dataType.toString());
                if(dataType.equals(HomeListAdapter.TYPE_1)){
                    ArrayList<ImageModel> tmpImgs = new ArrayList<>();
                    JSONArray imgList = tmpData.getJSONArray("imgUrls");
                    for(int j=0;j <imgList.length();j++){
                        JSONObject tmpIcon = imgList.getJSONObject(j);
                        ImageModel imageModel = new ImageModel();
                        imageModel.setUrl(tmpIcon.getString("url"));
                        tmpImgs.add(imageModel);
                    }
                    aboutModel.setImgs(tmpImgs);
                }else if(dataType.equals(HomeListAdapter.TYPE_2)){
                    JSONObject articleData = tmpData.getJSONObject("article");
                    ArticleModel articleModel = new ArticleModel();
                    articleModel.setId(articleData.getString("id"));
                    JSONArray tmpContentList = articleData.getJSONArray("content");
                    ArrayList<String> contentList = new ArrayList<>();
                    for(int j=0;j <tmpContentList.length();j++){
                        contentList.add(tmpContentList.getString(j));
                    }
                    articleModel.setContentList(contentList);
                    aboutModel.setArticle(articleModel);
                }
                tmpList.add(aboutModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tmpList;
    }

    //启动本 活动
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    public void test(){
        TextView aboutTv = (TextView)findViewById(R.id.about_tv);
        String str = "<a href='http://www.baidu.com/'>重庆微景通科技有限公司</a>（简称：<b>微景通</b>）成立于2013年，是国|article=1|文章1|/article|内领先的移动营销解决方案服务商，本着“专注深耕细作”的企业理念，致力于旅游景区移动营销产品的研发与运营。\\n成立至今，依靠优秀的产品和服务能力，|article=2|文章2|/article|微景通已成为国内最大的旅游行业微信第三方平台，接入服务景区超过1000家，包括秦始皇帝陵博物院、雁荡山、华清宫、白水洋鸳鸯溪、金佛山、天柱山、天目湖、台儿庄古城等一大批5A级景区。";
        //使用SpannableString存放字符串
        SpannableString spannableString = new SpannableString(str);
        //通过setSpan设定文本块响应的点击事件
        Matcher m = Pattern.compile("\\|article=(\\d+)\\|([^\\|]+?)\\|/article\\|").matcher(spannableString);

        while(m.find()){
            final String aid = m.group(1);
            final String text = m.group(2);
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    BaseActivity.mToastStatic("start activity "+ aid + " - "+ text);
                }
            },m.start() , m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }


        aboutTv.setText(spannableString);
        //设置单击链接调用浏览器打开网址
        aboutTv.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
