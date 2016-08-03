package com.app.www.weijingtong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.www.weijingtong.model.BusinessModel;
import com.app.www.weijingtong.R;
import com.app.www.weijingtong.model.TagIconModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by weijingtong20 on 2016/6/17.
 * 首页数据适配器
 */
public class BusinessListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int TYPE_1 = 1;//第一栏
    public static final int TYPE_2 = 2;//第二栏 功能tag
    private ArrayList<BusinessModel> dataList = new ArrayList<>();//数据集合
    private Context mContext;

    //第二栏对应的tag图标 16个
    private Map<String ,Integer> iconMap = new HashMap<String ,Integer>(){{
        put("zzspry", R.drawable.bus_icon_zzspry);//自助售票入园
        put("zzyydl", R.drawable.bus_icon_zzyydl);//自助语音导览
        put("yddtdh", R.drawable.bus_icon_yddtdh);//移动地图导航
        put("znkf", R.drawable.bus_icon_znkf);//智能客服
        put("jqzmtptjs", R.drawable.bus_icon_ptjs);//景区自媒体平台建设
        put("sjyxhdch", R.drawable.bus_icon_sjyx);//社交营销活动策划
        put("yhyyclzc", R.drawable.bus_icon_yycl);//用户运营策略支持
        put("yhyjfxfw", R.drawable.bus_icon_yhyjfx);//用户研究分析服务
        put("yhlyjzfx", R.drawable.bus_icon_yhly);//用户来源精准分析
        put("zmtyhhx", R.drawable.bus_icon_yhhx);//自媒体用户画像
        put("yyxgpg", R.drawable.bus_icon_yyxgpg);//运营效果评估
        put("jqklljc", R.drawable.bus_icon_lljc);//景区客流量监测
        put("ydpwxs", R.drawable.bus_icon_ydpw);//移动票务销售
        put("yddzsw", R.drawable.bus_icon_yddzsw);//移动电子商务
        put("jqsyzyzh", R.drawable.bus_icon_zyzh);//景区商业资源整合
        put("sjhxszc", R.drawable.bus_icon_sjhxszc);//社交化销售支持
    }};

    public BusinessListAdapter(Context context, ArrayList<BusinessModel> dataList) {
        super();
        this.mContext = context;
        this.dataList = dataList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TYPE_1:
                Type1Holder type1Holder = new Type1Holder(LayoutInflater.from(mContext).inflate(R.layout._business_desc_item, parent, false));
                holder = type1Holder;
                break;
            case TYPE_2:
                Type2Holder type2Holder = new Type2Holder(LayoutInflater.from(mContext).inflate(R.layout._business_classify_item, parent, false));
                holder = type2Holder;
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        BusinessModel businessModel = dataList.get(position); // 获取当前项的 homeModel 实例
        switch (getItemViewType(position)){
            case TYPE_1:
                final Type1Holder type1Holder = (Type1Holder)holder;
                type1Holder.description.setText(businessModel.getDescription());
                //监听点击事件
                type1Holder.btn_ydds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(v,position);
                    }
                });
                type1Holder.btn_shhyx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(v,position);
                    }
                });
                type1Holder.btn_sjhyx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(v,position);
                    }
                });
                type1Holder.btn_ydznty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(v,position);
                    }
                });
                break;
            case TYPE_2:
                final Type2Holder type2Holder = (Type2Holder)holder;
                int i = 1;
                String tmpIcon,tmpTitle;
                //移动智能体验
                ArrayList<TagIconModel> ydzntyList = businessModel.getYdznty();
                for (TagIconModel ydznty : ydzntyList){
                    tmpIcon = ydznty.getIcon();
                    tmpTitle = ydznty.getTitle();
                    switch (i){
                        case 1:
                            type2Holder.ydznty_icon_1.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.ydznty_title_1.setText(tmpTitle);
                            break;
                        case 2:
                            type2Holder.ydznty_icon_2.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.ydznty_title_2.setText(tmpTitle);
                            break;
                        case 3:
                            type2Holder.ydznty_icon_3.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.ydznty_title_3.setText(tmpTitle);
                            break;
                        case 4:
                            type2Holder.ydznty_icon_4.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.ydznty_title_4.setText(tmpTitle);
                            break;
                    }
                    i++;
                }
                //设为可见
                type2Holder.item_ydznty.setVisibility(View.VISIBLE);
                //社会化营销
                i = 1;
                ArrayList<TagIconModel> shhyxList = businessModel.getShhyx();
                for (TagIconModel shhyx : shhyxList){
                    tmpIcon = shhyx.getIcon();
                    tmpTitle = shhyx.getTitle();
                    switch (i){
                        case 1:
                            type2Holder.shhyx_icon_1.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.shhyx_title_1.setText(tmpTitle);
                            break;
                        case 2:
                            type2Holder.shhyx_icon_2.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.shhyx_title_2.setText(tmpTitle);
                            break;
                        case 3:
                            type2Holder.shhyx_icon_3.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.shhyx_title_3.setText(tmpTitle);
                            break;
                        case 4:
                            type2Holder.shhyx_icon_4.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.shhyx_title_4.setText(tmpTitle);
                            break;

                    }
                    i++;
                }
                //设为不可见
                type2Holder.item_shhyx.setVisibility(View.GONE);
                //数据化营销
                i = 1;
                ArrayList<TagIconModel> sjhyxList = businessModel.getSjhyx();
                for (TagIconModel sjhyx : sjhyxList){
                    tmpIcon = sjhyx.getIcon();
                    tmpTitle = sjhyx.getTitle();
                    switch (i){
                        case 1:
                            type2Holder.sjhyx_icon_1.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.sjhyx_title_1.setText(tmpTitle);
                            break;
                        case 2:
                            type2Holder.sjhyx_icon_2.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.sjhyx_title_2.setText(tmpTitle);
                            break;
                        case 3:
                            type2Holder.sjhyx_icon_3.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.sjhyx_title_2.setText(tmpTitle);
                            break;
                        case 4:
                            type2Holder.sjhyx_icon_4.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.sjhyx_title_4.setText(tmpTitle);
                            break;

                    }
                    i++;
                }
                //设为不可见
                type2Holder.item_sjhyx.setVisibility(View.GONE);
                //移动电商
                i = 1;
                ArrayList<TagIconModel> yddsList = businessModel.getYdds();
                for (TagIconModel ydds : yddsList){
                    tmpIcon = ydds.getIcon();
                    tmpTitle = ydds.getTitle();
                    switch (i){
                        case 1:
                            type2Holder.ydds_icon_1.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.ydds_title_1.setText(tmpTitle);
                            break;
                        case 2:
                            type2Holder.ydds_icon_2.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.ydds_title_2.setText(tmpTitle);
                            break;
                        case 3:
                            type2Holder.ydds_icon_3.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.ydds_title_3.setText(tmpTitle);
                            break;
                        case 4:
                            type2Holder.ydds_icon_4.setImageResource(iconMap.get(tmpIcon));
                            type2Holder.ydds_title_4.setText(tmpTitle);
                            break;

                    }
                    i++;
                }
                //设为不可见
                type2Holder.item_ydds.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        if ("1".equals(dataList.get(position).getDataType())) {
            return TYPE_1;// 第一栏
        } else if ("2".equals(dataList.get(position).getDataType())) {
            return TYPE_2;// 第二栏
        } else {
            return 0;
        }
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
        TextView description;
        ImageButton btn_ydznty,btn_shhyx,btn_sjhyx,btn_ydds;
        public Type1Holder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.business_desc);
            btn_ydznty = (ImageButton) itemView.findViewById(R.id.btn_ydznty);
            btn_shhyx = (ImageButton) itemView.findViewById(R.id.btn_shhyx);
            btn_sjhyx = (ImageButton) itemView.findViewById(R.id.btn_sjhyx);
            btn_ydds = (ImageButton) itemView.findViewById(R.id.btn_ydds);
        }
    }
    class Type2Holder extends RecyclerView.ViewHolder{
        LinearLayout item_ydznty,item_shhyx,item_sjhyx,item_ydds;//块级视图
        TextView ydznty_title_1,ydznty_title_2,ydznty_title_3,ydznty_title_4;
        TextView shhyx_title_1,shhyx_title_2,shhyx_title_3,shhyx_title_4;
        TextView sjhyx_title_1,sjhyx_title_2,sjhyx_title_3,sjhyx_title_4;
        TextView ydds_title_1,ydds_title_2,ydds_title_3,ydds_title_4;
        ImageView ydznty_icon_1,ydznty_icon_2,ydznty_icon_3, ydznty_icon_4;
        ImageView shhyx_icon_1,shhyx_icon_2,shhyx_icon_3, shhyx_icon_4;
        ImageView sjhyx_icon_1,sjhyx_icon_2,sjhyx_icon_3, sjhyx_icon_4;
        ImageView ydds_icon_1,ydds_icon_2,ydds_icon_3, ydds_icon_4;
        public Type2Holder(View itemView) {
            super(itemView);
            item_ydznty = (LinearLayout) itemView.findViewById(R.id.item_ydznty);
            item_shhyx = (LinearLayout) itemView.findViewById(R.id.item_shhyx);
            item_sjhyx = (LinearLayout) itemView.findViewById(R.id.item_sjhyx);
            item_ydds = (LinearLayout) itemView.findViewById(R.id.item_ydds);

            ydznty_title_1 = (TextView) itemView.findViewById(R.id.ydznty_title_1);
            ydznty_title_2 = (TextView) itemView.findViewById(R.id.ydznty_title_2);
            ydznty_title_3 = (TextView) itemView.findViewById(R.id.ydznty_title_3);
            ydznty_title_4 = (TextView) itemView.findViewById(R.id.ydznty_title_4);

            shhyx_title_1 = (TextView) itemView.findViewById(R.id.shhyx_title_1);
            shhyx_title_2 = (TextView) itemView.findViewById(R.id.shhyx_title_2);
            shhyx_title_3 = (TextView) itemView.findViewById(R.id.shhyx_title_3);
            shhyx_title_4 = (TextView) itemView.findViewById(R.id.shhyx_title_4);

            sjhyx_title_1 = (TextView) itemView.findViewById(R.id.sjhyx_title_1);
            sjhyx_title_2 = (TextView) itemView.findViewById(R.id.sjhyx_title_2);
            sjhyx_title_3 = (TextView) itemView.findViewById(R.id.sjhyx_title_3);
            sjhyx_title_4 = (TextView) itemView.findViewById(R.id.sjhyx_title_4);

            ydds_title_1 = (TextView) itemView.findViewById(R.id.ydds_title_1);
            ydds_title_2 = (TextView) itemView.findViewById(R.id.ydds_title_2);
            ydds_title_3 = (TextView) itemView.findViewById(R.id.ydds_title_3);
            ydds_title_4 = (TextView) itemView.findViewById(R.id.ydds_title_4);

            ydznty_icon_1 = (ImageView) itemView.findViewById(R.id.ydznty_icon_1);
            ydznty_icon_2 = (ImageView) itemView.findViewById(R.id.ydznty_icon_2);
            ydznty_icon_3 = (ImageView) itemView.findViewById(R.id.ydznty_icon_3);
            ydznty_icon_4 = (ImageView) itemView.findViewById(R.id.ydznty_icon_4);

            shhyx_icon_1 = (ImageView) itemView.findViewById(R.id.shhyx_icon_1);
            shhyx_icon_2 = (ImageView) itemView.findViewById(R.id.shhyx_icon_2);
            shhyx_icon_3 = (ImageView) itemView.findViewById(R.id.shhyx_icon_3);
            shhyx_icon_4 = (ImageView) itemView.findViewById(R.id.shhyx_icon_4);

            sjhyx_icon_1 = (ImageView) itemView.findViewById(R.id.sjhyx_icon_1);
            sjhyx_icon_2 = (ImageView) itemView.findViewById(R.id.sjhyx_icon_2);
            sjhyx_icon_3 = (ImageView) itemView.findViewById(R.id.sjhyx_icon_3);
            sjhyx_icon_4 = (ImageView) itemView.findViewById(R.id.sjhyx_icon_4);

            ydds_icon_1 = (ImageView) itemView.findViewById(R.id.ydds_icon_1);
            ydds_icon_2 = (ImageView) itemView.findViewById(R.id.ydds_icon_2);
            ydds_icon_3 = (ImageView) itemView.findViewById(R.id.ydds_icon_3);
            ydds_icon_4 = (ImageView) itemView.findViewById(R.id.ydds_icon_4);
        }
    }
}

