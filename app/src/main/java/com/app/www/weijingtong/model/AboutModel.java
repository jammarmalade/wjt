package com.app.www.weijingtong.model;

import java.util.ArrayList;

/**
 * Created by weijingtong20 on 2016/6/17.
 */
public class AboutModel {
    private String dataType;//数据类型，区分楼层
    //第一栏要用的字段属性
    private ArrayList<ImageModel> imgs = new ArrayList<>();

    //第二栏字段属性
    private ArticleModel articleModel = new ArticleModel();

    public String getDataType() {return dataType;}
    public void setDataType(String dataType) {this.dataType = dataType;}

    public ArrayList<ImageModel> getImgs() {return imgs;}
    public void setImgs(ArrayList<ImageModel> imgs) {this.imgs = imgs;}

    public ArticleModel getArticle() {return articleModel;}
    public void setArticle(ArticleModel articleModel) {this.articleModel = articleModel;}


}
