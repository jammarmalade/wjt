package com.app.www.weijingtong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.www.weijingtong.activity.SolutionActivity;
import com.app.www.weijingtong.adapter.SolutionSchemeAdapter;
import com.app.www.weijingtong.util.HttpCallbackListener;
import com.app.www.weijingtong.R;
import com.app.www.weijingtong.activity.BaseActivity;
import com.app.www.weijingtong.model.BaseApplication;
import com.app.www.weijingtong.model.CaseModel;
import com.app.www.weijingtong.model.SchemeModel;
import com.app.www.weijingtong.util.CacheUtil;
import com.app.www.weijingtong.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SolutionSchemeFragment extends BaseFragment {
    private String cacheName = "fragment_solution_scheme";//缓存名字
    private int cacheTime = BaseActivity.getCacheTime();//缓存时间
    private View view;
    private RecyclerView mRecyclerView;
    private SolutionSchemeAdapter mAdapter;
    private ArrayList<SchemeModel> schemeData;

    public static BaseFragment getInstance(Bundle bundle) {
        SolutionSchemeFragment fragment = new SolutionSchemeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.solution_fragmnet_scheme, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onQueryData();
    }

//    private void initView(View view) {
//        TextView tv = (TextView) view.findViewById(R.id.solution_scheme_tv);
//        tv.setText(getArguments().getString("title"));
//    }
    //获取数据
    public void onQueryData(){
        //1 小时缓存
        List cacheData = CacheUtil.readJson(getActivity(),cacheName ,cacheTime);
        if(cacheData.size()==0){
            HttpUtil.sendHttpRequest(SolutionActivity.REQUEST_URL, "type=scheme",new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Object res = parseResult(response);
                    if (res.equals("")) {
                        onError("解析数据失败",new Exception());
                    }else{
                        //存储数据
                        CacheUtil.writeJson(BaseApplication.getContext(), res.toString(), cacheName, false);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onSetData();
                            }
                        });
                    }
                }

                @Override
                public void onError(final String msg,Exception e) {
                    //在主线中执行
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else{
            onSetData();
        }
    }
    //设置数据
    public void onSetData(){
        //1 小时缓存
        List cacheData = CacheUtil.readJson(getActivity(),cacheName ,cacheTime);
        String jsonData ="";
        if(cacheData.size()!=0){
            jsonData = cacheData.get(0).toString();
            schemeData = parseCacheData(jsonData);
            if(schemeData.size()>0){
                //使用  recyclerView
                mRecyclerView = (RecyclerView) view.findViewById(R.id.solution_scheme_RV);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAdapter = new SolutionSchemeAdapter(getActivity(), schemeData);
                mRecyclerView.setAdapter(mAdapter);
            }else{
                BaseActivity.mToastStatic("没有数据 - 102");
            }
        }else if(jsonData.equals("\"empty\"")){
            BaseActivity.mToastStatic("没有数据 - 105");
        }else{
            BaseActivity.mToastStatic("没有数据 - 107");
        }
    }
    //解析缓存的数据
    private ArrayList<SchemeModel> parseCacheData(String cacheData){
        ArrayList<SchemeModel> tmpList = new ArrayList<>();
        try{
            JSONArray tmpSchemeList = new JSONArray(cacheData);
            for(int i=0;i <tmpSchemeList.length();i++){
                JSONObject tmpData = tmpSchemeList.getJSONObject(i);
                SchemeModel schemeModel = new SchemeModel();
                schemeModel.setTitle(tmpData.getString("title"));
                JSONArray tmpCaseListArr = tmpData.getJSONArray("list");

                ArrayList<CaseModel> caseModelList = new ArrayList<>();
                for(int j=0;j<tmpCaseListArr.length();j++){
                    JSONObject tmpCaseListObj = tmpCaseListArr.getJSONObject(j);
                    CaseModel caseModel = new CaseModel();
                    caseModel.setSubTitle(tmpCaseListObj.getString("subTitle"));
                    caseModel.setImgUrl(tmpCaseListObj.getString("imgUrl"));
                    caseModelList.add(caseModel);
                }
                schemeModel.setCaseList(caseModelList);
                tmpList.add(schemeModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tmpList;
    }

}
