package shoushimima;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.hlg.myapplication.R;

@SuppressLint("NewApi")
public class PasswordSettingActivity extends Activity {

	private Switch mSwPasswordSwitch;
	private Button mBtnPasswordChangeButton;
	private Button mPBtnasswordForgotButton;
	private LinearLayout mPwChangeLayout;
	private LinearLayout mPwForgotLayout;

	private boolean mPasswordFlag;
	private SharedPreferences.Editor editor;
	private SharedPreferences pwSwitchPref;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_manage);

		mSwPasswordSwitch = (Switch) findViewById(R.id.password_switch);
		mBtnPasswordChangeButton = (Button) findViewById(R.id.password_change);
		mPBtnasswordForgotButton = (Button) findViewById(R.id.password_forgot);
		mPwChangeLayout = (LinearLayout) findViewById(R.id.pw_change);
		mPwForgotLayout = (LinearLayout) findViewById(R.id.pw_forgot);

		editor = getSharedPreferences("admin", MODE_PRIVATE).edit();
		pwSwitchPref = getSharedPreferences("admin", MODE_PRIVATE);		

		mSwPasswordSwitch
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							// 选中时 do some thing
							Intent intent = new Intent(
									PasswordSettingActivity.this,
									SetPasswordActivity.class);
							startActivity(intent);
							mPwChangeLayout.setVisibility(View.VISIBLE);
							mPwForgotLayout.setVisibility(View.VISIBLE);
							editor.putBoolean("pwflag", true);
							editor.commit();

						} else {
							// 非选中时 do some thing
							Intent intent = new Intent(
									PasswordSettingActivity.this, Verify.class);
							startActivity(intent);
							mPwChangeLayout.setVisibility(View.INVISIBLE);
							mPwForgotLayout.setVisibility(View.INVISIBLE);
							editor.putBoolean("pwflag", false);
							editor.commit();
						}
					}
				});
//		 手势开关页面设置
		if (pwSwitchPref.getBoolean("pwflag", false)) {
			mPwChangeLayout.setVisibility(View.VISIBLE);
			mPwForgotLayout.setVisibility(View.VISIBLE);
		} else {
			mPwChangeLayout.setVisibility(View.INVISIBLE);
			mPwForgotLayout.setVisibility(View.INVISIBLE);
		}

		mBtnPasswordChangeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PasswordSettingActivity.this,
						ChangePasswordActivity.class);
				startActivity(intent);
				//原密码置零
				SharedPreferences sharedPreferences = getSharedPreferences(
						"passwordInfo", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				//editor.putString("pawd", "");
				editor.putString("tpawd", "");
				editor.commit();
//				Intent intent1 = new Intent(PasswordSettingActivity.this,
//						SetPasswordActivity.class);
//				startActivity(intent1);
				finish();
			}
		});
	}
}
