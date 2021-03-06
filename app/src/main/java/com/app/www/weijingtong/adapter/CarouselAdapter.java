package com.app.www.weijingtong.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.www.weijingtong.util.imageLoad.ImageCacheManager;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.app.www.weijingtong.activity.BaseActivity;

import java.util.ArrayList;

/**
 * Created by weijingtong20 on 2016/7/21.
 */
//轮播图的adapter
public class CarouselAdapter extends StaticPagerAdapter {
    private ArrayList<String> imgs = new ArrayList();
    public CarouselAdapter(ArrayList<String> imgs) {
        this.imgs = imgs;
    }
    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ImageCacheManager.loadImage(imgs.get(position), view,
                BaseActivity.getPreLoadImg(),
                BaseActivity.getPreLoadImg());
        return view;
    }


    @Override
    public int getCount() {
        return imgs.size();
    }
}
