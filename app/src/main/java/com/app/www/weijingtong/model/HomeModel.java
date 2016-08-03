package com.app.www.weijingtong.model;

import java.util.ArrayList;

/**
 * Created by weijingtong20 on 2016/6/17.
 */
public class HomeModel {
    private String dataType;//数据类型，区分楼层
    //第一栏要用的字段属性
    private String imgUrl = null;
    private String title = null;
    private String subTitle = null;

    //第二栏字段属性
    private String title2 = null;
    private ArrayList<IconModel> icons = new ArrayList<>();

    //第三栏字段
    private ArrayList<ClientModel> clients = new ArrayList<>();
    //第四栏
    private ArrayList<NewsModel> news = new ArrayList<>();

    public String getDataType() {return dataType;}
    public void setDataType(String dataType) {this.dataType = dataType;}
    public String getImgUrl() {return imgUrl;}
    public void setImgUrl(String imgUrl) {this.imgUrl = imgUrl;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getSubTitle() {return subTitle;}
    public void setSubTitle(String subTitle) {this.subTitle = subTitle;}


    public String getTitle2() {return title2;}
    public void setTitle2(String title2) {this.title2 = title2;}
    public ArrayList getIcons(){return icons;};
    public void setIcons(ArrayList<IconModel> icons){this.icons = icons;}

    public ArrayList getClients(){return clients;};
    public void setClients(ArrayList<ClientModel> clients){this.clients = clients;}

    public ArrayList getNews(){return news;}
    public void setNews(ArrayList<NewsModel> news){this.news = news;}
}
