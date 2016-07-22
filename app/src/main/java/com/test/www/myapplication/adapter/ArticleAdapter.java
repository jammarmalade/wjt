package com.test.www.myapplication.adapter;

import android.content.Context;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public ArticleAdapter(Context c , int textViewResourceId, ArrayList<String> dataList){
        super(c ,textViewResourceId ,dataList);
        this.mContext = c;
        this.resourceId = textViewResourceId;
        this.dataList = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String content = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.about_article_tv);
            viewHolder.imageView = (ImageView)view.findViewById(R.id.about_article_iv);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        //判断是图片还是文字

        if(content.indexOf("|img=")!=-1){
            //图片类型的
            Matcher m = Pattern.compile("\\|img=(.+?)\\-width=(\\d+)\\-height=(\\d+)\\|").matcher(content);
            String url = "";
            Integer height = 500;
            Integer width = 640;
            while(m.find()){
                url = m.group(1);
                width = Integer.parseInt(m.group(2));
                height = Integer.parseInt(m.group(3));
            }
            if(!url.equals("")){
                ImageCacheManager.loadImage(url, viewHolder.imageView,
                        BaseActivity.getPreLoadImg(),
                        BaseActivity.getPreLoadImg());
                //根据屏幕宽度等比例计算图片高度
                WindowManager wm = (WindowManager) getContext()
                        .getSystemService(Context.WINDOW_SERVICE);
                int Pwidth = wm.getDefaultDisplay().getWidth();
                //计算高度
                height = (int)Math.ceil((double)Pwidth / width * height);
                ViewGroup.LayoutParams params = viewHolder.imageView.getLayoutParams();
                params.width = Pwidth;
                params.height = height;
                viewHolder.imageView.setLayoutParams(params);
                viewHolder.imageView.setVisibility(View.VISIBLE);
            }
        }else{
            //文字类型的
            //替换自定义标签
            content = content.replaceAll("\\|b\\|(.*?)\\|/b\\|","<b>$1</b>");
            content = content.replaceAll("\\|enter\\|","<br/>");
            viewHolder.textView.setText(Html.fromHtml(content));
            viewHolder.textView.setVisibility(View.VISIBLE);
        }

        return view;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
