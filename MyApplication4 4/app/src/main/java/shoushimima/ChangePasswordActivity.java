package shoushimima;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.hlg.myapplication.R;

import shoushimima.Drawl.GestureCallBack;

public class ChangePasswordActivity extends Activity {

	private FrameLayout verify_layout;
	private ContentView contentView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepassword);

		verify_layout = (FrameLayout) findViewById(R.id.verify_layout);

		contentView = new ContentView(this, new GestureCallBack() {

			@Override
			public void CheckedSuccess() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ChangePasswordActivity.this,
						SetPasswordActivity.class);
				SharedPreferences sharedPreferences = getSharedPreferences(
						"passwordInfo", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putBoolean("change", true);
				editor.commit();
				startActivity(intent);
				finish();
			}

			@Override
			public void CheckedFail() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Password Wrong!",
						Toast.LENGTH_SHORT).show();
			}
		});

		contentView.setParentView(verify_layout);

		// SharedPreferences sharedPreferences = getSharedPreferences(
		// "passwordInfo", Context.MODE_PRIVATE);
		// SharedPreferences.Editor editor = sharedPreferences.edit();
		// editor.putBoolean("change", true);
	}

}
