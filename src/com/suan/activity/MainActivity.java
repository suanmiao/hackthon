package com.suan.activity;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.suan.camerapreview.R;
import com.suan.util.audio.PostThread;
import com.suan.util.audio.RecordThread;
import com.suan.view.CameraPreview;
import com.suan.view.MyAudioView;
import com.suan.view.MyCameraView;
import com.suan.view.MyMainView;

public class MainActivity extends Activity {

	private RelativeLayout mainLayout;
	private MyMainView myMainView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide status-bar
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Hide title-bar, must be before setContentView
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.main_layout);


		initWidget();
	}

	private void initWidget() {
		mainLayout = (RelativeLayout) findViewById(R.id.main_layout);

	}

	@Override
	protected void onResume() {
		super.onResume();

		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
		myMainView = new MyMainView(this, new LayoutParams(mainLayout.getWidth(),
				mainLayout.getHeight()));
		RelativeLayout.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		mainLayout.addView(myMainView, layoutParams);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mainLayout.removeView(myMainView);

	}

}
