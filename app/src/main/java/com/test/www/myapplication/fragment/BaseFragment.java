package com.test.www.myapplication.fragment;

import android.widget.Toast;

import com.test.www.myapplication.model.BaseApplication;
import android.support.v4.app.Fragment;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by weijingtong20 on 2016/6/17.
 */
public class BaseFragment extends Fragment {

    //为 fragment 设置数据
    public void onSetData(){

    }
    //获取数据
    public void onQueryData(){

    }
    //解析返回的数据
    public Object parseResult(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData).getJSONObject("result");
            boolean status = jsonObject.getBoolean("status");
            String message = jsonObject.getString("message");
            if (status == false) {
                Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
                return "";
            }
            JSONArray result = jsonObject.getJSONArray("data");
            if(result.length()==0){
                return "empty";
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
