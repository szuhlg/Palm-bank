package shoushimima;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.hlg.myapplication.MainActivity;
import com.example.hlg.myapplication.R;

import shoushimima.Drawl.GestureCallBack;

public class SetPasswordActivity extends Activity {

	private FrameLayout setpassword_layout;
	private ContentView content1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setpassword);
		setpassword_layout = (FrameLayout) findViewById(R.id.setpassword_layout);

		// 初始化一个显示各个点的viewGroup
		content1 = new ContentView(this, new GestureCallBack() {

			@Override
			public void CheckedSuccess() {
				Toast.makeText(getApplicationContext(), "设置手势密码成功！",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(SetPasswordActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}

			public void CheckedFail() {
				Toast.makeText(getApplicationContext(), "绘制的密码与上一次的不一致！",
						Toast.LENGTH_SHORT).show();
			}

		});

		// 设置手势解锁显示到哪个布局里面
		content1.setParentView(setpassword_layout);
				
	}

}
