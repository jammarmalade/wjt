package com.test.www.myapplication.model;

import java.util.ArrayList;

/**
 * Created by weijingtong20 on 2016/6/17.
 */
public class SchemeModel {
    private String title = null;
    private ArrayList<CaseModel> caseList = new ArrayList<>();

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public ArrayList<CaseModel> getCaseList() {return caseList;}
    public void setCaseList(ArrayList<CaseModel> caseList) {this.caseList = caseList;}
}
