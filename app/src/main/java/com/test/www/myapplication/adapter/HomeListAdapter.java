package com.test.www.myapplication.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.www.myapplication.R;
import com.test.www.myapplication.activity.BaseActivity;
import com.test.www.myapplication.activity.HomeActivity;
import com.test.www.myapplication.model.BaseApplication;
import com.test.www.myapplication.model.ClientModel;
import com.test.www.myapplication.model.HomeModel;
import com.test.www.myapplication.model.IconModel;
import com.test.www.myapplication.model.NewsModel;
import com.test.www.myapplication.util.HttpCallbackListener;
import com.test.www.myapplication.util.HttpUtil;
import com.test.www.myapplication.util.LogUtil;
import com.test.www.myapplication.util.Unity;
import com.test.www.myapplication.util.imageLoad.ImageCacheManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weijingtong20 on 2016/6/17.
 * 首页数据适配器
 */
public class HomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int TYPE_1 = 1;//第一栏 banner
    public static final int TYPE_2 = 2;//第二栏列表 功能
    public static final int TYPE_3 = 3;//第三栏列表 我们的客户
    public static final int TYPE_4 = 4;//第四栏列表 公司动态
    private ArrayList<HomeModel> dataList = new ArrayList<>();//数据集合
    private Context mContext;
    private Handler handler;

    //第二栏对应的图标
    private Map<String ,Integer> iconMap = new HashMap<String ,Integer>(){{
        put("business", R.drawable.business);
        put("advantage", R.drawable.advantage);
        put("solution", R.drawable.solution);
        put("experience", R.drawable.experience);
        put("about", R.drawable.about);
        put("cooperation", R.drawable.cooperation);
    }};

    public HomeListAdapter(Context context, ArrayList<HomeModel> dataList) {
        super();
        this.mContext = context;
        this.dataList = dataList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TYPE_1:
                Type1Holder type1Holder = new Type1Holder(LayoutInflater.from(mContext).inflate(R.layout._home_banner_item, parent, false));
                holder = type1Holder;
                break;
            case TYPE_2:
                Type2Holder type2Holder = new Type2Holder(LayoutInflater.from(mContext).inflate(R.layout._home_icon_item, parent, false));
                holder = type2Holder;
                break;
            case TYPE_3:
                Type3Holder type3Holder = new Type3Holder(LayoutInflater.from(mContext).inflate(R.layout._home_client_item, parent, false));
                holder = type3Holder;
                break;
            case TYPE_4:
                Type4Holder type4Holder = new Type4Holder(LayoutInflater.from(mContext).inflate(R.layout._home_news_item, parent, false));
                holder = type4Holder;
                break;

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        HomeModel homeModel = dataList.get(position); // 获取当前项的 homeModel 实例
        switch (getItemViewType(position)){
            case TYPE_1:
                final Type1Holder type1Holder = (Type1Holder)holder;
                type1Holder.title.setText(homeModel.getTitle());
                if(homeModel.getSubTitle()!=null){
                    type1Holder.subtitle.setText(homeModel.getSubTitle());
                    type1Holder.subtitle.setVisibility(View.VISIBLE);
                }
                LogUtil.d(BaseActivity.TAG,"adt - 96 "+homeModel.getImgUrl());
                //若是图片链接为空，就使用本地的banner图
                if("".equals(homeModel.getImgUrl())){
                    type1Holder.banner.setImageResource(R.drawable.banner);
                }else{
                    ImageCacheManager.loadImage(homeModel.getImgUrl(), type1Holder.banner,
                            BaseActivity.getPreLoadImg(),
                            BaseActivity.getPreLoadImg());
                }
                break;
            case TYPE_2:
                final Type2Holder type2Holder = (Type2Holder)holder;
                //数据
                ArrayList<IconModel> iconsList = homeModel.getIcons();
                int i = 0 ;
                String tmpTitle,tmpIconType;
                for(IconModel icon : iconsList){
                    tmpTitle = icon.getTitle();
                    tmpIconType = icon.getIconType();
                    switch (i){
                        case 0:
                            type2Holder.image1.setImageResource(iconMap.get(tmpIconType));
                            type2Holder.title1.setText(tmpTitle);
                            type2Holder.ll1.setVisibility(View.VISIBLE);
                            type2Holder.ll1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mOnItemClickLitener.onItemClick(v , position);
                                }
                            });
                            break;
                        case 1:
                            type2Holder.image2.setImageResource(iconMap.get(tmpIconType));
                            type2Holder.title2.setText(tmpTitle);
                            type2Holder.ll2.setVisibility(View.VISIBLE);
                            type2Holder.ll2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mOnItemClickLitener.onItemClick(v , position);
                                }
                            });
                            break;
                        case 2:
                            type2Holder.image3.setImageResource(iconMap.get(tmpIconType));
                            type2Holder.title3.setText(tmpTitle);
                            type2Holder.ll3.setVisibility(View.VISIBLE);
                            type2Holder.ll3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mOnItemClickLitener.onItemClick(v , position);
                                }
                            });
                            break;
                        case 3:
                            type2Holder.image4.setImageResource(iconMap.get(tmpIconType));
                            type2Holder.title4.setText(tmpTitle);
                            type2Holder.ll4.setVisibility(View.VISIBLE);
                            type2Holder.ll4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mOnItemClickLitener.onItemClick(v , position);
                                }
                            });
                            break;
                        case 4:
                            type2Holder.image5.setImageResource(iconMap.get(tmpIconType));
                            type2Holder.title5.setText(tmpTitle);
                            type2Holder.ll5.setVisibility(View.VISIBLE);
                            type2Holder.ll5.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mOnItemClickLitener.onItemClick(v , position);
                                }
                            });
                            break;
                        case 5:
                            type2Holder.image6.setImageResource(iconMap.get(tmpIconType));
                            type2Holder.title6.setText(tmpTitle);
                            type2Holder.ll6.setVisibility(View.VISIBLE);
                            type2Holder.ll6.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mOnItemClickLitener.onItemClick(v , position);
                                }
                            });
                            break;
                    }
                    i++;
                }
                if(i>4){
                    type2Holder.icon_ll2.setVisibility(View.VISIBLE);
                }
                break;
            case TYPE_3:
                final Type3Holder type3Holder = (Type3Holder)holder;
                //数据
                ArrayList<ClientModel> clientList = homeModel.getClients();
                HomeClientAdapter homeClientAdapter = new HomeClientAdapter(mContext , R.layout._home_client_gridview_item, clientList);
                type3Holder.clientGV.setAdapter(homeClientAdapter);
                //重新计算高度
                Unity.setGrideViewHeight(type3Holder.clientGV);
                break;
            case TYPE_4:
                final Type4Holder type4Holder = (Type4Holder)holder;
                //数据
                ArrayList<NewsModel> newsList = homeModel.getNews();
                HomeNewsAdapter homeNewsAdapter = new HomeNewsAdapter(mContext , R.layout._home_news_listview_item, newsList);
                type4Holder.newsListview.setAdapter(homeNewsAdapter);
                Unity.setListViewHeight(type4Holder.newsListview);
                //加载更多事件
                type4Holder.newsLoadmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(v , position);
                    }
                });
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        if ("1".equals(dataList.get(position).getDataType())) {
            return TYPE_1;// 第一栏banner 数据
        } else if ("2".equals(dataList.get(position).getDataType())) {
            return TYPE_2;// 第二栏 导航图标
        } else if ("3".equals(dataList.get(position).getDataType())) {
            return TYPE_3;// 第三栏 客户logo
        } else if ("4".equals(dataList.get(position).getDataType())) {
            return TYPE_4;// 第四栏 公司动态
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
    
    class Type1Holder extends RecyclerView.ViewHolder{
        TextView title;
        TextView subtitle;
        ImageView banner;
        public Type1Holder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.home_title);
            subtitle = (TextView) itemView.findViewById(R.id.home_subtitle);
            banner = (ImageView) itemView.findViewById(R.id.home_banner);
        }
    }
    class Type2Holder extends RecyclerView.ViewHolder{
        LinearLayout ll1,ll2,ll3,ll4,ll5,ll6;//块级视图
        LinearLayout icon_ll1;//第一横栏
        LinearLayout icon_ll2;//第二横栏
        TextView title1,title2,title3,title4,title5,title6;
        ImageView image1,image2,image3, image4,image5,image6;
        public Type2Holder(View itemView) {
            super(itemView);
            icon_ll1 = (LinearLayout) itemView.findViewById(R.id.frag_home_icon_ll_1);
            icon_ll2 = (LinearLayout) itemView.findViewById(R.id.frag_home_icon_ll_2);
            ll1 = (LinearLayout) itemView.findViewById(R.id.frag_home_block_1);
            ll2 = (LinearLayout) itemView.findViewById(R.id.frag_home_block_2);
            ll3 = (LinearLayout) itemView.findViewById(R.id.frag_home_block_3);
            ll4 = (LinearLayout) itemView.findViewById(R.id.frag_home_block_4);
            ll5 = (LinearLayout) itemView.findViewById(R.id.frag_home_block_5);
            ll6 = (LinearLayout) itemView.findViewById(R.id.frag_home_block_6);
            image1 = (ImageView) itemView.findViewById(R.id.frag_home_icon_1);
            image2 = (ImageView) itemView.findViewById(R.id.frag_home_icon_2);
            image3 = (ImageView) itemView.findViewById(R.id.frag_home_icon_3);
            image4 = (ImageView) itemView.findViewById(R.id.frag_home_icon_4);
            image5 = (ImageView) itemView.findViewById(R.id.frag_home_icon_5);
            image6 = (ImageView) itemView.findViewById(R.id.frag_home_icon_6);
            title1 = (TextView) itemView.findViewById(R.id.frag_home_title_1);
            title2 = (TextView) itemView.findViewById(R.id.frag_home_title_2);
            title3 = (TextView) itemView.findViewById(R.id.frag_home_title_3);
            title4 = (TextView) itemView.findViewById(R.id.frag_home_title_4);
            title5 = (TextView) itemView.findViewById(R.id.frag_home_title_5);
            title6 = (TextView) itemView.findViewById(R.id.frag_home_title_6);
        }
    }

    class Type3Holder extends RecyclerView.ViewHolder{
        GridView clientGV;
        public Type3Holder(View itemView) {
            super(itemView);
            clientGV = (GridView)itemView.findViewById(R.id.home_client_list);
        }
    }
    class Type4Holder extends RecyclerView.ViewHolder{
        ListView newsListview;
        Button newsLoadmore;
        public Type4Holder(View itemView) {
            super(itemView);
            newsListview = (ListView)itemView.findViewById(R.id.home_new_list);
            newsLoadmore = (Button) itemView.findViewById(R.id.news_loadmore);
        }
    }
}
