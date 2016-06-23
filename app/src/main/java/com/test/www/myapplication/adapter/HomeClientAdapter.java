package com.test.www.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.test.www.myapplication.R;
import com.test.www.myapplication.activity.BaseActivity;
import com.test.www.myapplication.model.ClientModel;
import com.test.www.myapplication.util.imageLoad.ImageCacheManager;

import java.util.ArrayList;

/**
 * Created by weijingtong20 on 2016/6/23.
 */
public class HomeClientAdapter extends ArrayAdapter<ClientModel> {

    private Context mContext;
    private int resourceId;

    public HomeClientAdapter(Context c , int textViewResourceId, ArrayList<ClientModel> dataList){
        super(c ,textViewResourceId ,dataList);
        this.mContext = c;
        this.resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClientModel clientModel = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.client_logo);
            view.setTag(viewHolder); //  将ViewHolder 存储在View 中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); //  重新获取ViewHolder
        }
        ImageCacheManager.loadImage(clientModel.getImgUrl(), viewHolder.imageView,
                BaseActivity.getPreLoadImg(),
                BaseActivity.getPreLoadImg());
        return view;
    }
    class ViewHolder{
        ImageView imageView;
    }
}
