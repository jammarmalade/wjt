package com.app.www.weijingtong.model;

import java.util.ArrayList;

/**
 * Created by weijingtong20 on 2016/6/17.
 */
public class BusinessModel {
    private String dataType;//数据类型，区分楼层
    //第一栏要用的字段属性
    private String description = null;

    //第二栏字段属性
    private ArrayList<TagIconModel> ydznty = new ArrayList<>();//移动智能体验
    private ArrayList<TagIconModel> shhyx = new ArrayList<>();//社会化营销
    private ArrayList<TagIconModel> sjhyx = new ArrayList<>();//数据化营销
    private ArrayList<TagIconModel> ydds = new ArrayList<>();//移动电商


    public String getDataType() {return dataType;}
    public void setDataType(String dataType) {this.dataType = dataType;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public ArrayList<TagIconModel> getYdznty() {return ydznty;}
    public void setYdznty(ArrayList<TagIconModel> ydznty) {this.ydznty = ydznty;}
    public ArrayList<TagIconModel> getShhyx() {return shhyx;}
    public void setShhyx(ArrayList<TagIconModel> shhyx) {this.shhyx = shhyx;}
    public ArrayList<TagIconModel> getSjhyx() {return sjhyx;}
    public void setSjhyx(ArrayList<TagIconModel> sjhyx) {this.sjhyx = sjhyx;}
    public ArrayList<TagIconModel> getYdds() {return ydds;}
    public void setYdds(ArrayList<TagIconModel> ydds) {this.ydds = ydds;}

}
