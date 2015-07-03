package com.example.hlg.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

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
import java.util.prefs.PreferenceChangeEvent;

import shoushimima.ShoushimimaActivity;

public class MyService extends Service {
    //网络心跳间隔时间
    public static final long HTTP_HEART_TIME = 30 * 1000;

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


    public MyService() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
                        SharedPreferences preference=getSharedPreferences("person", Context.MODE_PRIVATE);
                        if(preference.contains("_s")){
                            Log.i("~~~~~~~~~~~~~~~~","111111111111111");
                            _s=preference.getString("_s","");
                            heartbeatTouch();
                        }else if(preference.contains("Psw")&&preference.contains("User")){

                            Log.i("~~~~~~~~~~~~~~~~","2222222222222222");


                            String despassword=preference.getString("Psw","");
                            Log.i("jiemi", despassword);
                            //对密码进行AES解密
                            String result=null;
                            try {
                                result=DES.decryptDES(despassword,"12345678");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.i(".......................",result);
                            String account=preference.getString("User","");//preference.getString(标示符,默认值<这里为空>）
                            Login login=new Login(account,result);

                            Log.i("后台登陆",login.getStatus());
                            if(login.getStatus().equals("OK")) {
                                Log.i("后台登陆", login.getStatus());
                                /*
                                MyApplication app=(MyApplication)getApplication();
                                app.set_s(login.get_s());*/
                                Log.i("后台登陆！！！！！！！！！", "!!!!!!!!!!!!!!");
                                _s=login.get_s();
                                SharedPreferences preference2=getSharedPreferences("person", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = preference2.edit();
                                edit.putString("_s",_s);
                                edit.commit();
                                Log.i("!!!!!!!!!!!!!","~~~~~~~~~~~~~~~~");
                                System.out.println("_S:" + _s);
                            }else{
                                Log.i("=========","后台登陆失败！！！！");
                            }
                        }
                        sleep(HTTP_HEART_TIME);
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
                    Log.i("$$$$$$$$$$$$$$$$$$$$$$","$$$_s已过期得重新后台登录$$$");
                    Log.i("~~~~~~~~~~~~~~~~","3333333333333333");

                    SharedPreferences preference=getSharedPreferences("person",Context.MODE_PRIVATE);
                    String despassword=preference.getString("Psw","");
                    Log.i("jiemi", despassword);
                    //对密码进行AES解密
                    String result=null;
                    try {
                        result=DES.decryptDES(despassword,"12345678");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i(".......................",result);
                    String account=preference.getString("User","");//preference.getString(标示符,默认值<这里为空>）
                    Login login=new Login(account,result);

                    Log.i("后台登陆",login.getStatus());
                    if(login.getStatus().equals("OK")) {
                        Log.i("后台登陆", login.getStatus());
                                /*
                                MyApplication app=(MyApplication)getApplication();
                                app.set_s(login.get_s());*/
                        Log.i("后台登陆！！！！！！！！！", "!!!!!!!!!!!!!!");
                        _s=login.get_s();
                        SharedPreferences preference2=getSharedPreferences("person", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = preference2.edit();
                        edit.putString("_s",_s);
                        edit.commit();
                        Log.i("!!!!!!!!!!!!!","~~~~~~~~~~~~~~~~");
                        System.out.println("_S:" + _s);
                    }else{
                        Log.i("=========","后台登陆失败！！！！");
                    }
                }
            }

        } catch (Exception e) {
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
