package com.app.www.weijingtong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.www.weijingtong.R;
import com.app.www.weijingtong.activity.BaseActivity;
import com.app.www.weijingtong.model.CaseModel;
import com.app.www.weijingtong.util.imageLoad.ImageCacheManager;

import java.util.ArrayList;

/**
 * Created by weijingtong20 on 2016/6/23.
 * 方案列表
 */
public class SolutionSchemeListAdapter extends ArrayAdapter<CaseModel> {

    private Context mContext;
    private int resourceId;

    public SolutionSchemeListAdapter(Context c , int textViewResourceId, ArrayList<CaseModel> dataList){
        super(c ,textViewResourceId ,dataList);
        this.mContext = c;
        this.resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CaseModel caseModel = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.itemImage = (ImageView) view.findViewById(R.id.scheme_listview_iv);
            viewHolder.itemSubTitle = (TextView) view.findViewById(R.id.scheme_listview_subTitle);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        ImageCacheManager.loadImage(caseModel.getImgUrl(), viewHolder.itemImage,
                BaseActivity.getPreLoadImg(),
                BaseActivity.getPreLoadImg());
        viewHolder.itemSubTitle.setText(caseModel.getSubTitle());
        return view;
    }
    class ViewHolder{
        ImageView itemImage;
        TextView itemSubTitle;
    }
}
