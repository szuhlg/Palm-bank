package shoushimima;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.hlg.myapplication.R;

public class AppActivity extends Activity {
 
	final private IntentFilter filter = new IntentFilter();
	final private ScreenReceiver receiver = new ScreenReceiver();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app);
		
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		registerReceiver(receiver, filter);

	}
}
