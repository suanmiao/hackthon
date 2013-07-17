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

public class AudioActivity extends Activity {

	private RelativeLayout audioLayout;
	private MyAudioView myAudioView;
	private RecordThread recordThread;
	private PostThread postThread;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide status-bar
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Hide title-bar, must be before setContentView
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.audio_layout);

		postThread = new PostThread();
		recordThread = new RecordThread();

		initWidget();
	}

	private void initWidget() {
		audioLayout = (RelativeLayout) findViewById(R.id.audio_layout);

	}

	@Override
	protected void onResume() {
		super.onResume();

		recordThread.start();
		
//		postThread.start();
		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
		myAudioView = new MyAudioView(this, new LayoutParams(audioLayout.getWidth(),
				audioLayout.getHeight()));
		RelativeLayout.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		audioLayout.addView(myAudioView, layoutParams);
	}

	@Override
	protected void onPause() {
		super.onPause();
		audioLayout.removeView(myAudioView);
		recordThread.pause();
		postThread.pause();
		// mLayout.removeView(mPreview); // This is necessary.
	}

}
