package com.test.www.myapplication.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Html;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.www.myapplication.R;
import com.test.www.myapplication.activity.BaseActivity;
import com.test.www.myapplication.model.ArticleModel;
import com.test.www.myapplication.model.NewsModel;
import com.test.www.myapplication.util.LogUtil;
import com.test.www.myapplication.util.imageLoad.ImageCacheManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by weijingtong20 on 2016/6/23.
 */
public class ArticleAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private int resourceId;
    private ArrayList<String> dataList = new ArrayList<>();
    private int PhoneWidth;

    public ArticleAdapter(Context c, int textViewResourceId, ArrayList<String> dataList) {
        super(c, textViewResourceId, dataList);
        this.mContext = c;
        this.resourceId = textViewResourceId;
        this.dataList = dataList;
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        PhoneWidth = wm.getDefaultDisplay().getWidth();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String content = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.about_article_tv);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.about_article_iv);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        //判断是图片还是文字

        if (content.indexOf("|img=") != -1) {
            //图片类型的
            Matcher m = Pattern.compile("\\|img=(.+?)\\-width=(\\d+)\\-height=(\\d+)\\|").matcher(content);
            String url = "";
            Integer height = 500;
            Integer width = 640;
            while (m.find()) {
                url = m.group(1);
                width = Integer.parseInt(m.group(2));
                height = Integer.parseInt(m.group(3));
            }
            if (!url.equals("")) {
                ImageCacheManager.loadImage(url, viewHolder.imageView,
                        BaseActivity.getPreLoadImg(),
                        BaseActivity.getPreLoadImg());
                //根据屏幕宽度等比例计算图片高度

                //计算高度
                height = (int) Math.ceil((double) PhoneWidth / width * height);
                ViewGroup.LayoutParams params = viewHolder.imageView.getLayoutParams();
                params.width = PhoneWidth;
                params.height = height;
                viewHolder.imageView.setLayoutParams(params);
                viewHolder.imageView.setVisibility(View.VISIBLE);
            }
        } else {

            //文字类型的
            //替换自定义标签
            content = content.replaceAll("\\|b\\|(.*?)\\|/b\\|", "<b>$1</b>");
            content = content.replaceAll("\\|enter\\|", "<br/>");
            viewHolder.textView.setText(Html.fromHtml(content));
            //计算文字所占textview的高度
            int widthSpec = View.MeasureSpec.makeMeasureSpec(PhoneWidth-40, View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            viewHolder.textView.measure(widthSpec, heightSpec);
            viewHolder.textView.setHeight(viewHolder.textView.getMeasuredHeight());
            viewHolder.textView.setVisibility(View.VISIBLE);

//            ViewTreeObserver observer = viewHolder.textView.getViewTreeObserver();
//            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    viewHolder.textView.getViewTreeObserver().removeGlobalOnLayoutListener(this);//避免重复监听
//                    int height = viewHolder.textView.getMeasuredHeight();//获文本高度
//                    viewHolder.textView.setHeight(height);
//                    LogUtil.d(BaseActivity.TAG,height+ " - &");
//                }
//            });


        }

        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

}
