package com.test.www.myapplication.model;

/**
 * Created by weijingtong20 on 2016/6/17.
 */
public class NewsModel {
    private int id;
    private String subject = null;
    private String thumbImg = null;
    private String description = null;
    private String time = null;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getSubject() {return subject;}
    public void setSubject(String subject) {this.subject = subject;}
    public String getThumbImg() {return thumbImg;}
    public void setThumbImg(String thumbImg) {this.thumbImg = thumbImg;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public String getTime() {return time;}
    public void setTime(String time) {this.time = time;}
}
