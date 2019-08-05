package cn.moecity.virtualpet;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;


public class WelcomeActivity extends Activity {

	private Boolean isOpened=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);

		new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message arg0) {
				if(isOpened==false)
				{startActivity(new Intent(getApplicationContext(),LoginActivity.class));
				finish();}
				return false;
			}
		}).sendEmptyMessageDelayed(0,3000);//延时3秒

	}
	public void onClicked(View v){
		switch(v.getId()){
		case R.id.editBtn:
			Intent i1=new Intent();
			ComponentName component=new ComponentName(getApplicationContext(), LoginActivity.class);
			i1.setComponent(component);
			startActivity(i1);
			isOpened=true;
			finish();
			break;
		}

	}
}
