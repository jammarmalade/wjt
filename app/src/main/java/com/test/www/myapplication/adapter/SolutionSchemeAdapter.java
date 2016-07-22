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

import com.test.www.myapplication.R;
import com.test.www.myapplication.activity.BaseActivity;
import com.test.www.myapplication.model.CaseModel;
import com.test.www.myapplication.model.ClientModel;
import com.test.www.myapplication.model.HomeModel;
import com.test.www.myapplication.model.IconModel;
import com.test.www.myapplication.model.NewsModel;
import com.test.www.myapplication.model.SchemeModel;
import com.test.www.myapplication.util.LogUtil;
import com.test.www.myapplication.util.Unity;
import com.test.www.myapplication.util.imageLoad.ImageCacheManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by weijingtong20 on 2016/6/17.
 * 解决方案数据适配器
 */
public class SolutionSchemeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private ArrayList<SchemeModel> dataList = new ArrayList<>();//数据集合
    private Context mContext;


    public SolutionSchemeAdapter(Context context, ArrayList<SchemeModel> dataList) {
        super();
        this.mContext = context;
        this.dataList = dataList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Type1Holder(LayoutInflater.from(mContext).inflate(R.layout._solution_scheme_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        SchemeModel schemeModel = dataList.get(position);
        Type1Holder type1Holder = (Type1Holder)holder;
        type1Holder.schemeTitle.setText(schemeModel.getTitle());
        //数据
        ArrayList<CaseModel> caseList = schemeModel.getCaseList();
        SolutionSchemeListAdapter ssAdapter = new SolutionSchemeListAdapter(mContext , R.layout._solution_scheme_listview_item, caseList);
        type1Holder.schemeLv.setAdapter(ssAdapter);
        Unity.setListViewHeight(type1Holder.schemeLv);
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
        ListView schemeLv;
        public Type1Holder(View itemView) {
            super(itemView);
            schemeTitle = (TextView) itemView.findViewById(R.id.scheme_title);
            schemeLv = (ListView) itemView.findViewById(R.id.scheme_lv);
        }
    }
}

