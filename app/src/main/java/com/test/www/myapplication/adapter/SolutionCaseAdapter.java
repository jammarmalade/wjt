package com.test.www.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.test.www.myapplication.R;
import com.test.www.myapplication.activity.BaseActivity;
import com.test.www.myapplication.model.CaseModel;
import com.test.www.myapplication.model.NewsModel;
import com.test.www.myapplication.model.SchemeModel;
import com.test.www.myapplication.util.Unity;
import com.test.www.myapplication.util.imageLoad.ImageCacheManager;

import java.util.ArrayList;

/**
 * Created by weijingtong20 on 2016/6/17.
 * 案例数据适配器
 */
public class SolutionCaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private ArrayList<CaseModel> dataList = new ArrayList<>();//数据集合
    private Context mContext;


    public SolutionCaseAdapter(Context context, ArrayList<CaseModel> dataList) {
        super();
        this.mContext = context;
        this.dataList = dataList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        Type1Holder type1Holder = new Type1Holder(LayoutInflater.from(mContext).inflate(R.layout._solution_case_item, parent, false));
        holder = type1Holder;
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        CaseModel caseModel = dataList.get(position);
        final Type1Holder type1Holder = (Type1Holder)holder;
        type1Holder.schemeTitle.setText(caseModel.getSubTitle());
        ImageCacheManager.loadImage(caseModel.getImgUrl(), type1Holder.backgroundImage,
                BaseActivity.getPreLoadImg(),
                BaseActivity.getPreLoadImg());
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return 100;
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
        TextView schemeTitle;
        ImageView backgroundImage;
        public Type1Holder(View itemView) {
            super(itemView);
            backgroundImage = (ImageView) itemView.findViewById(R.id.case_listview_iv);
            schemeTitle = (TextView) itemView.findViewById(R.id.case_listview_subTitle);
        }
    }
}

