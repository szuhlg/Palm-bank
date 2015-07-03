package shoushimima;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.hlg.myapplication.MainActivity;
import com.example.hlg.myapplication.R;

public class ShoushimimaActivity extends Activity {

	private Button pSet;
	private Button pCancel;

	SharedPreferences.Editor editor;
	SharedPreferences pref;

	// private boolean firstIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoushimima);
		editor = getSharedPreferences("admin", MODE_PRIVATE).edit();
		pref = getSharedPreferences("admin", MODE_PRIVATE);
		// 保存设置状态,便于debug，合并时应去掉
		editor.putBoolean("pwflag", true);
		editor.commit();
		
		if (pref.getInt("firstIn", 0) == 1) {
			// 不是第一次进入应用程序了
			// 判断手势解锁有没有开启
			if (pref.getBoolean("pwflag", false)) {
				editor.putBoolean("AppOnflag", true);// 进入应用程序验证跳转标志
				editor.commit();
				Intent intent = new Intent(ShoushimimaActivity.this, Verify.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent(ShoushimimaActivity.this, MainActivity.class);
				startActivity(intent);
			}
			finish();
		}

		pSet = (Button) findViewById(R.id.set_pw);
		pSet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ShoushimimaActivity.this,
						SetPasswordActivity.class);
				startActivity(intent);
				finish();
			}
		});

		editor.putInt("firstIn", 1);
		editor.commit();
	}
}
