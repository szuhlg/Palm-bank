package shoushimima;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.hlg.myapplication.MainActivity;
import com.example.hlg.myapplication.R;

import shoushimima.Drawl.GestureCallBack;

public class Verify extends Activity implements OnClickListener {

	private FrameLayout verify_layout;
	private ContentView content2;

	private Button mBtnPasswordManager;
	private Button mBtnOtherLoginer;

	SharedPreferences.Editor editor;
	SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activty_verify);
		

		mBtnPasswordManager = (Button) findViewById(R.id.bt_password_manager);
		mBtnOtherLoginer = (Button) findViewById(R.id.bt_other_login);
		
		mBtnOtherLoginer.setOnClickListener(this);
		mBtnPasswordManager.setOnClickListener(this);


		verify_layout = (FrameLayout) findViewById(R.id.verify_layout);

		editor = getSharedPreferences("admin", MODE_PRIVATE).edit();
		pref = getSharedPreferences("admin", MODE_PRIVATE);

		content2 = new ContentView(this, new GestureCallBack() {

			@Override
			public void CheckedSuccess() {
				Toast.makeText(getApplicationContext(), "解锁成功！",
						Toast.LENGTH_SHORT).show();
				if (pref.getBoolean("AppOnflag", false)) {
					// 进入应用程序验证成功跳转
					Intent intent = new Intent(Verify.this, MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					// 锁屏验证成功返回
					finish();
				}
			}

			public void CheckedFail() {
				Toast.makeText(getApplicationContext(), "解锁失败！",
						Toast.LENGTH_SHORT).show();
			}
		});

		content2.setParentView(verify_layout);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_password_manager :
			Intent mJumpintent = new Intent(Verify.this,
					PasswordSettingActivity.class);
			startActivity(mJumpintent);
			finish();
			break;

		case R.id.bt_other_login:
			//登陆其他账号，跳到登陆界面
			break;
		default:
			break;
		}
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
