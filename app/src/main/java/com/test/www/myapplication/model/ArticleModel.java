package com.test.www.myapplication.model;

import java.util.ArrayList;

/**
 * Created by weijingtong20 on 2016/6/17.
 * 文章模型 主要用于存储文章的图片和文本
 */
public class ArticleModel {

    private String id;
    private String subject = null;
    //图片或文字为一个item 的数据
    private ArrayList<String> contentList = new ArrayList<>();

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getSubject() {return subject;}
    public void setSubject(String subject) {this.subject = subject;}

    public ArrayList<String> getContentList() {return contentList;}
    public void setContentList(ArrayList<String> contentList) {this.contentList = contentList;}


}
