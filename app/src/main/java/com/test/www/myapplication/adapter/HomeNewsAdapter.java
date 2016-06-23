package com.test.www.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.www.myapplication.R;
import com.test.www.myapplication.activity.BaseActivity;
import com.test.www.myapplication.model.ClientModel;
import com.test.www.myapplication.model.NewsModel;
import com.test.www.myapplication.util.imageLoad.ImageCacheManager;

import java.util.ArrayList;

/**
 * Created by weijingtong20 on 2016/6/23.
 */
public class HomeNewsAdapter extends ArrayAdapter<NewsModel> {

    private Context mContext;
    private int resourceId;

    public HomeNewsAdapter(Context c , int textViewResourceId, ArrayList<NewsModel> dataList){
        super(c ,textViewResourceId ,dataList);
        this.mContext = c;
        this.resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsModel newsModel = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.home_news_img);
            viewHolder.subject = (TextView) view.findViewById(R.id.home_news_subject);
            viewHolder.desc = (TextView) view.findViewById(R.id.home_news_desc);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        ImageCacheManager.loadImage(newsModel.getThumbImg(), viewHolder.imageView,
                BaseActivity.getPreLoadImg(),
                BaseActivity.getPreLoadImg());
        viewHolder.subject.setText(newsModel.getSubject());
        viewHolder.desc.setText(newsModel.getDescription());
        return view;
    }
    class ViewHolder{
        ImageView imageView;
        TextView subject;
        TextView desc;
    }
}
