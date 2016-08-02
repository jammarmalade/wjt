package com.test.www.myapplication.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.TextHintView;
import com.test.www.myapplication.R;
import com.test.www.myapplication.activity.BaseActivity;
import com.test.www.myapplication.model.AboutModel;
import com.test.www.myapplication.model.ArticleModel;
import com.test.www.myapplication.model.BaseApplication;
import com.test.www.myapplication.model.ClientModel;
import com.test.www.myapplication.model.HomeModel;
import com.test.www.myapplication.model.IconModel;
import com.test.www.myapplication.model.ImageModel;
import com.test.www.myapplication.model.NewsModel;
import com.test.www.myapplication.util.LogUtil;
import com.test.www.myapplication.util.Unity;
import com.test.www.myapplication.util.imageLoad.ImageCacheManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by weijingtong20 on 2016/6/17.
 * 关于我们数据适配器
 */
public class AboutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int TYPE_1 = 1;//第一栏 banner
    public static final int TYPE_2 = 2;//第二栏列表 文章

    private ArrayList<AboutModel> dataList = new ArrayList<>();//数据集合
    private Context mContext;

    public AboutAdapter(Context context, ArrayList<AboutModel> dataList) {
        super();
        this.mContext = context;
        this.dataList = dataList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TYPE_1:
                BannerHolder type1Holder = new BannerHolder(LayoutInflater.from(mContext).inflate(R.layout._about_banner_item, parent, false));
                holder = type1Holder;
                break;
            case TYPE_2:
                ArticleHolder type2Holder = new ArticleHolder(LayoutInflater.from(mContext).inflate(R.layout._about_article_item, parent, false));
                holder = type2Holder;
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        AboutModel aboutModel = dataList.get(position); // 获取当前项的 homeModel 实例
        switch (getItemViewType(position)){
            case TYPE_1:
                final BannerHolder type1Holder = (BannerHolder)holder;
                ArrayList<ImageModel> banners = aboutModel.getImgs();
                ArrayList<String> imgs = new ArrayList<>();
                for(ImageModel tmpBanner : banners){
                    imgs.add(tmpBanner.getUrl());
                }
                RollPagerView mRollViewPager = type1Holder.carouselView;
                //设置播放时间间隔
                mRollViewPager.setPlayDelay(3000);
                //设置透明度
                mRollViewPager.setAnimationDurtion(500);
//                //设置文字指示器
//                mRollViewPager.setHintView(new TextHintView(BaseApplication.getContext()));
                //设置适配器
                mRollViewPager.setAdapter(new CarouselAdapter(imgs));

                break;
            case TYPE_2:
                final ArticleHolder type2Holder = (ArticleHolder)holder;
                //数据
                ArticleModel articleModel = aboutModel.getArticle();
                ArrayList<String> dateList = articleModel.getContentList();

                ArticleAdapter articleAdapter = new ArticleAdapter(mContext , R.layout._about_article_listview_item, dateList);
                type2Holder.aboutArticleLV.setAdapter(articleAdapter);
                //重新计算listview高度
                Unity.setListViewHeight(type2Holder.aboutArticleLV);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        if ("1".equals(dataList.get(position).getDataType())) {
            return TYPE_1;// 第一栏banner 数据
        } else if ("2".equals(dataList.get(position).getDataType())) {
            return TYPE_2;// 第二栏 文章
        } else {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    //监听事件的回调方法接口
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }
    private OnItemClickLitener mOnItemClickLitener;
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    
    class BannerHolder extends RecyclerView.ViewHolder{
        RollPagerView carouselView;
        public BannerHolder(View itemView) {
            super(itemView);
            carouselView = (RollPagerView) itemView.findViewById(R.id.about_roll_view_pager);
        }
    }
    class ArticleHolder extends RecyclerView.ViewHolder{
        ListView aboutArticleLV;
        public ArticleHolder(View itemView) {
            super(itemView);
            aboutArticleLV = (ListView)itemView.findViewById(R.id.about_article_lv);
        }
    }

}

