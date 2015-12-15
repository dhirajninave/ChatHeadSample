package com.appsthentic.chatheadsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * @author Dhiraj Chathead Sample MainActivity.java
 */
public class MainActivity extends Activity {
	Button startService, stopService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		startService(new Intent(getApplication(), ChatHeadService.class));
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopService(new Intent(getApplication(), ChatHeadService.class));
	}

}
