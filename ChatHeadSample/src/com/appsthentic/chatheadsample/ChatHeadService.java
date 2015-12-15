package com.appsthentic.chatheadsample;


import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author Dhiraj
 * Chathead Sample ChatHeadService.java 
 */
public class ChatHeadService extends Service {

	private WindowManager windowManager;
	private ImageView chatHead;
	WindowManager.LayoutParams params;
	WindowManager.LayoutParams mDeleteAreaLayoutParams;
	public String TAG = "CHARHEAD";
	private ImageView closeArea;

	@Override
	public void onCreate() {
		super.onCreate();

		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

		DisplayMetrics metrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(metrics);

		int mDisplayHeight = metrics.heightPixels;
		int mDisplayWidth = metrics.widthPixels;

		chatHead = new ImageView(this);
		chatHead.setClickable(true);
		chatHead.setImageResource(R.drawable.ic_chat_float);

		params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = 0;
		params.y = 100;

		closeArea = new ImageView(this);
		closeArea.setClickable(true);
		closeArea.setImageResource(R.drawable.ic_chat_close);
		
		
		closeArea.setVisibility(View.INVISIBLE);
		mDeleteAreaLayoutParams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				PixelFormat.TRANSLUCENT);
		mDeleteAreaLayoutParams.gravity = Gravity.TOP /* | Gravity.LEFT */;
		mDeleteAreaLayoutParams.x = 0;
		mDeleteAreaLayoutParams.y = mDisplayHeight
				- (closeArea.getHeight() + 200);
		// mDisplayHeight
		windowManager.addView(closeArea, mDeleteAreaLayoutParams);

		// this code is for dragging the chat head
		chatHead.setOnTouchListener(new View.OnTouchListener() {
			private int initialX;
			private int initialY;
			private float initialTouchX;
			private float initialTouchY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				closeArea.setVisibility(View.VISIBLE);
				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					initialX = params.x;
					initialY = params.y;
					initialTouchX = event.getRawX();
					initialTouchY = event.getRawY();
					return true;
				case MotionEvent.ACTION_UP:
					closeArea.setVisibility(View.INVISIBLE);
					if ((Math.abs(initialTouchX - event.getRawX()) < 5)
							&& (Math.abs(initialTouchY - event.getRawY()) < 5)) {
						Log.e(TAG, "It's a click ! ");
						Toast.makeText(getApplicationContext(), "Clicked!!",
								Toast.LENGTH_SHORT).show();

					} else {
						Log.e(TAG, "you moved the head");
					}
					// ((params.x - mDeleteAreaLayoutParams.x) < 50)&&
					if ((params.y >= mDeleteAreaLayoutParams.y)) {
						if (chatHead != null) {
							chatHead.setVisibility(View.INVISIBLE);
							closeArea.setVisibility(View.INVISIBLE);
							//windowManager.removeView(chatHead);
							//windowManager.removeView(closeArea);
						}
					}
					return true;
				case MotionEvent.ACTION_MOVE:
					params.x = initialX
							+ (int) (event.getRawX() - initialTouchX);
					params.y = initialY
							+ (int) (event.getRawY() - initialTouchY);
					windowManager.updateViewLayout(chatHead, params);

					return true;
				}

				return false;
			}
		});

		windowManager.addView(chatHead, params);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (chatHead != null) {
			try {

				windowManager.removeView(chatHead);
				windowManager.removeView(closeArea);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "Destroy Exception ! ");
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}