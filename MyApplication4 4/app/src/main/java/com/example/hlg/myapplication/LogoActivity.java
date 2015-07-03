package com.example.hlg.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import shoushimima.ShoushimimaActivity;
import shoushimima.Verify;


public class LogoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_logo);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preference=getSharedPreferences("person", Context.MODE_PRIVATE);
                if(preference.contains("Psw")&&preference.contains("User")){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(LogoActivity.this,ShoushimimaActivity.class);
                    startActivity(intent);
                    finish();
/*                    String despassword=preference.getString("Psw","");
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
                        Log.i("后台登陆",login.getStatus());
                        MyApplication app=(MyApplication)getApplication();
                        app.set_s(login.get_s());
                        Log.i("后台登陆！！！！！！！！！","!!!!!!!!!!!!!!");
                        Intent intent=new Intent(LogoActivity.this, ShoushimimaActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(LogoActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }*/
                }
                else{
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(LogoActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
