package com.app.www.weijingtong.adapter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.www.weijingtong.model.NavsModel;
import com.app.www.weijingtong.R;

import java.util.ArrayList;

/**
 * Created by weijingtong20 on 2016/6/17.
 * 产品体验上级导航数据适配器
 */
public class ExperiencePreNavAdapter extends ArrayAdapter<NavsModel> {

    private Context mContext;
    private int resourceId;
    //对应的分类图标
    private ArrayMap<String ,Integer> navIconMap = new ArrayMap<String ,Integer>(){{
        put("zntyl", R.drawable.exp_zntyl);
        put("ydds", R.drawable.exp_ydds);
        put("yxtgl", R.drawable.exp_yxtgl);
        put("sjhdl", R.drawable.exp_sjhdl);
        put("xxzs", R.drawable.exp_xxzs);
    }};

    public ExperiencePreNavAdapter(Context c , int textViewResourceId, ArrayList<NavsModel> dataList){
        super(c ,textViewResourceId ,dataList);
        this.mContext = c;
        this.resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NavsModel navsModel = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.exp_icon);
            viewHolder.textView = (TextView) view.findViewById(R.id.exp_nav_title);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(navsModel.getName());
        viewHolder.imageView.setImageResource(navIconMap.get(navsModel.getIconType()));

        return view;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }

}

