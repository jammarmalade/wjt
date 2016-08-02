package com.test.www.myapplication.model;

import java.util.ArrayList;

/**
 * Created by weijingtong20 on 2016/6/17.
 */
public class ExperienceModel {

    private String dataType;

    private ArrayList<NavsModel> navs = new ArrayList<>();


    public String getDataType() {return dataType;}
    public void setDataType(String dataType) {this.dataType = dataType;}


    public ArrayList<NavsModel> getNavs() {return navs;}
    public void setNavs(ArrayList<NavsModel> navs) {this.navs = navs;}

}
