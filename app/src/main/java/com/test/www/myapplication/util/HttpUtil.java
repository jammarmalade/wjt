package com.test.www.myapplication.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.test.www.myapplication.activity.BaseActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/6/10.
 * http 访问类
 */
public class HttpUtil {
    public static final String TAG = "HttpUtil";
    public static void sendHttpRequest(final String address,final String data, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();

                    //若数据不为空则发送 post 请求
                    if(data.equals("")){
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Accept", "application/json");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                    }else{
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Accept", "application/json");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        connection.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
                        //默认为false
                        connection.setDoOutput(true);
                        //4.向服务器写入数据
                        connection.getOutputStream().write(data.getBytes());
                    }
                    
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response  = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    in.close();
                    reader.close();
                    if(listener != null){
                        //回调 onFinish() 方法
                        listener.onFinish(response.toString());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(listener != null){
                        listener.onError("加载失败",e);
                    }
                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 获取网落图片资源
     * @param url
     * @param listener
     * @return
     */
    public static void getHttpBitmap(final String url, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL myFileURL;
                Bitmap bitmap=null;
                HttpURLConnection conn = null;
                try{
                    myFileURL = new URL(url);
                    //获得连接
                    conn = (HttpURLConnection)myFileURL.openConnection();
                    //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
                    conn.setConnectTimeout(6000);
                    //连接设置获得数据流
                    conn.setDoInput(true);
                    //不使用缓存
                    conn.setUseCaches(false);
                    //这句可有可无，没有影响
                    conn.connect();
                    //得到数据流
                    InputStream is = conn.getInputStream();
                    //解析得到图片
                    bitmap = BitmapFactory.decodeStream(is);
                    //关闭数据流
                    is.close();
                    if(listener != null){
                        //回调 onFinish() 方法
                        listener.onFinish(Unity.bitmaptoString(bitmap,80));
                    }
                }catch(Exception e){
                    if(listener != null){
                        listener.onError("加载失败",e);
                    }
                }finally {
                    if(conn != null){
                        conn.disconnect();
                    }
                }

            }
        }).start();

    }
}
