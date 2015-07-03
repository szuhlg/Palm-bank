package shoushimima;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class ScreenReceiver extends BroadcastReceiver {

	private SharedPreferences.Editor editor;
	private SharedPreferences pwSwitchPref;
	@Override
	public void onReceive(Context context, Intent intent) {
//	
//		editor = getSharedPreferences("admin", 0).edit();
//		pwSwitchPref = getSharedPreferences("admin", MODE_PRIVATE);
//		
		Toast.makeText(context, "ScreenOff", Toast.LENGTH_SHORT).show();
		Intent intent2 = new Intent(context, Verify.class);
		//if (pwSwitchPref.getBoolean("pwflag", false))
			context.startActivity(intent2);
	}

}
