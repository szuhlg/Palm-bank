package com.example.hlg.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import shoushimima.ShoushimimaActivity;

/**
 * Created by kwen on 15/6/21.
 */
public class MyApplication extends Application {
    //网络心跳间隔时间
    public static final long HTTP_HEART_TIME = 3 * 1000;

    //服务端的url地址
    private static final String url = "http://devace.sinaapp.com/ace_mobile/";
    //创建一个HttpClient连接
    private HttpClient httpCilent;
    //创建一个HttpResponse用于存放响应的数据
    private HttpResponse response;
    //创建一个HttpPost请求
    private HttpPost httpPost;
    //存储服务器返回的_s
    private static  String _s;
    //创建一个HttpEntity用于存放请求的实体数据
    private HttpEntity entity;

    private MyApplication mMyApplication;



    public static void set_s(String s){
        _s=s;
    }
    public static String get_s(){
        return _s;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        mMyApplication = new MyApplication();
        initData();
    }

    private void initData(){
        //心跳机制
        new Thread(){
            @Override
            public void run(){
             /*   SharedPreferences preference=getSharedPreferences("person", Context.MODE_PRIVATE);
                if(preference.contains("Psw")&&preference.contains("User")){

                }*/
                while (true) {
                    try {
                        sleep(HTTP_HEART_TIME);
                        //heartbeatTouch();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }

    /**
     * 心跳
     */
    public void heartbeatTouch() {

        httpCilent = new DefaultHttpClient();
        try {
            //设置请求的路径
            httpPost = new HttpPost(url);
            //创建一个用户，用于向服务端发送数据时，存放的实体
            JSONObject post = new JSONObject();
            JSONObject data = new JSONObject();
            try {
                data.put("_c","A");
                data.put("_m","GetLoginStatus");
                data.put("_s",_s);
/*                post.put("_c", "A");
                post.put("_m", "CheckLoginStatus");
                post.put("_p", data);*/
                Log.i("===========", data.toString());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.d("=========","lalala");
            //设置请求体aa
            httpPost.setEntity(new StringEntity(data.toString()));
            Log.d("=========","22222");
            //执行请求获取响应
            response = httpCilent.execute(httpPost);
            Log.d("=======","3333333333");

            //如果响应的状态码为200时，表示请求响应成功
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                Log.d("========","请求响应成功!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                //获取响应的实体数据
                entity = response.getEntity();
                StringBuffer sb = new StringBuffer();
                // 通过reader读取实体对象包含的数据
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
                //循环读取实体里面的数据
                String s = null;

                while((s = reader.readLine()) != null){
                    sb.append(s);
                }
                //创建一个JSONObject对象存放从服务端获取到的JSONObject数据
                JSONObject datas = new JSONObject(sb.toString());
                //创建一个boolean变量用于存放服务端的处理结果状态
                String status = datas.getString("STS");
                System.out.println(datas.toString());

                if(status.equals("OK")){
                    //在线时
                    _s=datas.getString("_s");
                    Log.i("===online===", "_s:" + _s);
                }else{//如果服务端的处理结果状态为KO时
                    //掉线时
                    Log.i("$$$$$$$$$$$$$$$$$$$$$$","$$$$$$$$$$$$");
                  /*  String errmsg=datas.getString("errmsg");*/
                    //Toast.makeText(this, "请重新登录", Toast.LENGTH_SHORT).show();
            /*        Intent intent=new Intent(this,LoginActivity.class);
                    startActivity(intent);*/
                }
            }

        } catch (Exception e) {
        }
    }
}
