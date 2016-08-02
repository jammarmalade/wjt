package com.test.www.myapplication.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.test.www.myapplication.activity.BaseActivity;

import java.io.ByteArrayOutputStream;

/**
 * Created by weijingtong20 on 2016/6/17.
 * 工具类
 */
public class Unity {
    /**
     * 将bitmap转换成base64字符串
     *
     * @param bitmap
     * @return base64 字符串
     */
    public static String bitmaptoString(Bitmap bitmap, int bitmapQuality) {
        // 将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, bitmapQuality, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * 将base64转换成bitmap图片
     *
     * @param string base64字符串
     * @return bitmap
     */
    public static Bitmap stringtoBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    //计算gridview 的高度
    public static void setGrideViewHeight(GridView grid) {
        ListAdapter adapter = grid.getAdapter();
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        View listItem = adapter.getView(0, null, grid);
        listItem.measure(0, 0);
        if (adapter.getCount() - 1 < 0) {
            totalHeight = listItem.getMeasuredHeight();
        } else {
            int line = adapter.getCount() / 4;
            if (adapter.getCount() % 4 != 0)
                line = line + 1;
            totalHeight = (listItem.getMeasuredHeight() ) * line;//+ 30
        }

        ViewGroup.LayoutParams params = grid.getLayoutParams();
        params.height = totalHeight + 30 ;//
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        grid.setLayoutParams(params);
    }
    //计算listView 的高度
    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
