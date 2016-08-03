package com.test.www.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.test.www.myapplication.R;
import com.test.www.myapplication.activity.ArticleActivity;
import com.test.www.myapplication.activity.BaseActivity;
import com.test.www.myapplication.activity.SolutionActivity;
import com.test.www.myapplication.adapter.SolutionCaseAdapter;
import com.test.www.myapplication.model.BaseApplication;
import com.test.www.myapplication.model.CaseModel;
import com.test.www.myapplication.util.CacheUtil;
import com.test.www.myapplication.util.HttpCallbackListener;
import com.test.www.myapplication.util.HttpUtil;
import com.test.www.myapplication.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SolutionCaseFragment extends BaseFragment {
    private String cacheName = "fragment_solution_case";//缓存名字
    private int cacheTime = BaseActivity.getCacheTime();//缓存时间
    private RecyclerView mRecyclerView;
    private SolutionCaseAdapter mAdapter;
    private ArrayList<CaseModel> caseData;
    private View view;

    public static BaseFragment getInstance(Bundle bundle) {
        SolutionCaseFragment fragment = new SolutionCaseFragment();
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
        view = inflater.inflate(R.layout.solution_fragmnet_case, container, false);
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
    //获取数据
    public void onQueryData(){
        //1 小时缓存
        List cacheData = CacheUtil.readJson(getActivity(),cacheName ,cacheTime);
        if(cacheData.size()==0){
            HttpUtil.sendHttpRequest(SolutionActivity.REQUEST_URL, "type=case",new HttpCallbackListener() {
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
            caseData = parseCacheData(jsonData);
            if(caseData.size()>0){
                //使用  recyclerView
                mRecyclerView = (RecyclerView) view.findViewById(R.id.solution_case_RV);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAdapter = new SolutionCaseAdapter(getActivity(), caseData);
                mAdapter.setOnItemClickLitener(new SolutionCaseAdapter.OnItemClickLitener(){
                    @Override
                    public void onItemClick(View view, int position){
                        CaseModel caseModel = caseData.get(position);
                        ArticleActivity.actionStart(BaseApplication.getContext(),caseModel.getId()+"","解决方案","solution");
                    }
                });
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
    private ArrayList<CaseModel> parseCacheData(String cacheData){
        ArrayList<CaseModel> tmpList = new ArrayList<>();
        try{
            JSONArray tmpCaseList = new JSONArray(cacheData);
            for(int i=0;i <tmpCaseList.length();i++){
                JSONObject tmpData = tmpCaseList.getJSONObject(i);
                CaseModel caseModel = new CaseModel();
                caseModel.setSubTitle(tmpData.getString("subTitle"));
                caseModel.setImgUrl(tmpData.getString("imgUrl"));
                caseModel.setId(tmpData.getInt("aid"));
                tmpList.add(caseModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tmpList;
    }
}
