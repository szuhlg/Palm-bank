package com.example.hlg.myapplication;

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
 * Created by hlg on 2015/5/31.
 */
public class Login {
    //服务端的url地址
    private static final String url = "http://devace.sinaapp.com/ace_mobile/";
    //创建一个HttpClient连接
    private HttpClient httpCilent;
    //创建一个HttpResponse用于存放响应的数据
    private HttpResponse response;
    //创建一个HttpPost请求
    private HttpPost httpPost;
    //存储服务器返回的_s
    private String _s;
    //创建一个HttpEntity用于存放请求的实体数据
    private HttpEntity entity;
    private String status;
    private String errmsg;
    /**
     * 登录方法
     * 该方法用于将用户输入的账号和密码发送到服务端，并对服务端返回的数据进行处理
     * @param account 账号
     * @param password 密码
     */
    public Login(String account, String password) {
        //实例化httpCilent
        httpCilent = new DefaultHttpClient();
        try {
            //设置请求的路径
            httpPost = new HttpPost(url);
            //创建一个用户，用于向服务端发送数据时，存放的实体
            JSONObject post=new JSONObject();
            JSONObject data = new JSONObject();
            try {
                //将用户填写的账号和密码存放到JSONObject中
                data.put("user", account);
                data.put("pass", password);
                data.put("lang","en");
                post.put("_c","A");
                post.put("_m","TmpLogin");
                post.put("_p",data);
                Log.i("===========", post.toString());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.d("=========","lalala");
            //设置请求体aa
            httpPost.setEntity(new StringEntity(post.toString()));
            Log.d("=========","22222");
            //执行请求获取响应
            response = httpCilent.execute(httpPost);
            Log.d("=======","3333333333");

            //如果响应的状态码为200时，表示请求响应成功
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                Log.d("========","请求响应成功");
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
                status = datas.getString("STS");
                System.out.println(datas.toString());
                if(status.equals("KO"))
                    errmsg=datas.getString("errmsg");
                System.out.println("errmsg:"+errmsg);
                if(status.equals("OK"))
                    _s=datas.getString("_s");
                System.out.println("_s:"+_s);
            }
        } catch (Exception e) {Log.d("======","error");
        }
    }

    public String getErrmsg() {
        return errmsg;
    }

    public String getStatus() {
        return status;
    }
    public String get_s(){
        return _s;
    }
}
