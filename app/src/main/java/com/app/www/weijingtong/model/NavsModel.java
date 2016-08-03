package com.app.www.weijingtong.model;

import java.util.ArrayList;

/**
 * Created by weijingtong20 on 2016/6/17.
 * 产品体验的导航
 */
public class NavsModel {

    private String id = null;
    private String name = null;
    private String iconType = null;

    private ArrayList<NavsModel> navs = new ArrayList<>();

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getIconType() {return iconType;}
    public void setIconType(String iconType) {this.iconType = iconType;}

    public ArrayList<NavsModel> getCnavs() {return navs;}
    public void setCnavs(ArrayList<NavsModel> Cnavs) {this.navs = Cnavs;}

}
