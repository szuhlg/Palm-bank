package com.example.hlg.myapplication;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
        import android.widget.TextView;

public class TestActivity extends Activity {
    private TextView tvTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        //初始化界面
        initView();
        //初始化数据
        initData();
    }
    private void initData() {
//        //获取从上一个界面传递过来的数据
//        Bundle bundle = this.getIntent().getExtras();
//        //定义一个字符串用于显示用户的信息账号
//        String text = tvTest.getText().toString();
//        text = text + bundle.getString("account") + "登陆";
        //设置TextView显示的文本信息
        //tvTest.setText("Hello world!");
    }
    private void initView() {
        //通过id获取组件的实例
        tvTest = (TextView)findViewById(R.id.tv_test);
    }
}