package com.example.hlg.myapplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import shoushimima.ShoushimimaActivity;

/**
 * 思路：
 * 1、初始化组件
 * 2、添加按钮点击监听事件
 * 3、获取用户输入的数据并转换成JSON对象
 * 4、设置请求数据url和实体数据，并执行请求
 * 5、获取服务端响应状态
 * 6、若响应状态为200，响应成功，则获取响应实体
 * 7、通过读入对象获取响应的实体的字符串对象，并转换成JSON对象
 * 8、获取result值，并判断，若为true,表示登陆成功，false表示用户登录失败
 * 9、登陆成功则通过intent跳转到test界面（真正工程则跳转到主界面）
 * 10、登陆失败用Toast提醒用户名不存在或者密码错误
 * @author Administrator
 *
 */
public class LoginActivity extends Activity {
    /*//服务端的url地址
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
    private HttpEntity entity;*/
    // 用户的头像
    private ImageButton userHead;
    // 用户的账号
    private AutoCompleteTextView userAccount;
    // 用户密码
    private EditText userPwd;
    // 登陆按钮、取回密码按钮、登录设置按钮、注册登录按钮
    private Button btnLogining, btnGetBackPwd, btnSetLogin, btnToRegister;

    String _s=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

/*        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());*/
        //初始化界面控件
        initView();

        SharedPreferences preference = getSharedPreferences("person",Context.MODE_PRIVATE);
        String despassword=preference.getString("Psw","");
        Log.i("jiemi",despassword);
        //对密码进行AES解密
        try {
            String result=DES.decryptDES(despassword,"12345678");
            userPwd.setText(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //String result=AESEncryptor.parseByte2HexStr(desresult);
/*        //对密码进行AES解密
        try{
            despassword = AESEncryptor.decrypt("41227677", despassword);
            Toast.makeText(this, "解密中...", Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(this, "获取密码时产生解密错误!", Toast.LENGTH_SHORT).show();
            despassword = "";
        }*/
        userAccount.setText(preference.getString("User",""));//preference.getString(标示符,默认值<这里为空>）
//        login(userAccount.getText().toString(),userPwd.getText().toString());
        //添加监听响应事件
        addListener();
    }
    /**
     * 初始化所有控件
     */
    private void initView() {
        userHead = (ImageButton) findViewById(R.id.login_head);
        userAccount = (AutoCompleteTextView) findViewById(R.id.login_account);
        userPwd = (EditText) findViewById(R.id.login_pwd);
        btnLogining = (Button) findViewById(R.id.login_btn_logining);
        btnGetBackPwd = (Button) findViewById(R.id.login_btn_getbackpwd);
        btnSetLogin = (Button) findViewById(R.id.login_btn_setlogin);
        btnToRegister = (Button) findViewById(R.id.login_btn_to_register);
    }
    private void addListener() {
        //添加登陆按钮监听事件
        btnLogining.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户输入的账号
                final String account = userAccount.getText().toString();
                //获取用户输入的密码
                final String password = userPwd.getText().toString();
                //调用登录方法
                login(account, password);
               /* new Thread(new Runnable() {
                    @Override
                    public void run() {
                        login(account,password);
                    }
                }).start();*/

            }
        });
    }

    public void login(final String account, final String password){
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1){
                    _s=msg.obj.toString();
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    //创建一个intent用于页面之间的跳转
                    Intent intent = new Intent(LoginActivity.this,ShoushimimaActivity.class);
                    //开启跳转
                    startActivity(intent);
                    finish();
                    String result = null;
                    try {
                        //存放加密后的密码
                        result = DES.encryptDES(password, "12345678");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i("!!!!!!!!!!!!!!!!!!",result);
                    SharedPreferences preference = getSharedPreferences("person", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = preference.edit();
                    edit.putString("User",account);
                    edit.putString("Psw", result);
                    edit.putString("_s",_s);
                    edit.commit();
                }else if(msg.what==0){
                    Toast.makeText(LoginActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                }else if(msg.what==-1){
                    Log.i("===========",msg.toString());
                }
            }
        };

        new Thread(){
            @Override
            public void run() {
                Message msg=new Message();
                try{
                    Login login=new Login(account,password);
                    String Status=login.getStatus();
                    if(Status.equals("OK")){
                        msg.what=1;
                        msg.obj=login.get_s();
                    }else {
                        msg.what=0;
                        msg.obj=login.getErrmsg();
                    }
                }catch (Exception e){
                    msg.what=-1;
                    msg.obj=e;
                    e.printStackTrace();
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 登录方法
     * 该方法用于将用户输入的账号和密码发送到服务端，并对服务端返回的数据进行处理
     * @param account 账号
     * @param password 密码
     */
   /* public void login(String account, String password) {
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
            while(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
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
                String status = datas.getString("STS");
                System.out.println(datas.toString());

                if(status.equals("OK")){
                    _s=datas.getString("_s");
                    Log.i("======","_s:"+_s);
                    //创建一个intent用于页面之间的跳转
                    Intent intent = new Intent(this,ShoushimimaActivity.class);
                    //开启跳转
                    startActivity(intent);
                    finish();
                }else{//如果服务端的处理结果状态为false时
                    String errmsg=datas.getString("errmsg");
                    Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {Log.d("======","error");
        }
    }*/
}