package com.app.www.weijingtong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.www.weijingtong.activity.NavArticleActivity;
import com.app.www.weijingtong.model.ExperienceModel;
import com.app.www.weijingtong.model.NavsModel;
import com.app.www.weijingtong.util.Unity;
import com.app.www.weijingtong.R;

import java.util.ArrayList;

/**
 * Created by weijingtong20 on 2016/6/17.
 * 产品体验数据适配器
 */
public class ExperienceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int TYPE_1 = 1;//第一栏 分类
    private ArrayList<ExperienceModel> dataList = new ArrayList<>();//数据集合
    private Context mContext;

    public ExperienceAdapter(Context context, ArrayList<ExperienceModel> dataList) {
        super();
        this.mContext = context;
        this.dataList = dataList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TYPE_1:
                NavHolder type1Holder = new NavHolder(LayoutInflater.from(mContext).inflate(R.layout._experience_navs_item, parent, false));
                holder = type1Holder;
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        ExperienceModel experienceModel = dataList.get(position);
        switch (getItemViewType(position)) {
            case TYPE_1:
                final NavHolder navHolder = (NavHolder)holder;
                final ArrayList<NavsModel> navsList = experienceModel.getNavs();

                ExperiencePreNavAdapter experiencePreNavAdapter = new ExperiencePreNavAdapter(mContext ,
                        R.layout._experience_navs_listview_item, navsList);
                navHolder.navLv.setAdapter(experiencePreNavAdapter);
                Unity.setListViewHeight(navHolder.navLv);
                navHolder.navLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        NavsModel navsModel = navsList.get(position);
                        NavArticleActivity.actionStart(mContext,navsModel.getId(),navsModel.getName());
                    }
                });
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        if ("1".equals(dataList.get(position).getDataType())) {
            return TYPE_1;// 第一栏
        }else{
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
    
    class NavHolder extends RecyclerView.ViewHolder{
        ListView navLv;
        public NavHolder(View itemView) {
            super(itemView);
            navLv = (ListView) itemView.findViewById(R.id.exp_nav_lv);
        }
    }

}

