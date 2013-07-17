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
import com.suan.view.CameraPreview;
import com.suan.view.MyView;

public class MainActivity extends Activity {

	private RelativeLayout previewLayout;
	private CameraPreview mPreview;
	private MyView myView;
	private static Camera mCamera;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide status-bar
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Hide title-bar, must be before setContentView
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.main);

		initWidget();

	}

	private void initWidget() {
		previewLayout = (RelativeLayout) findViewById(R.id.preview_layout);

	}

	public static void autoFocus() {
		mCamera.autoFocus(new AutoFocusCallback() {

			@Override
			public void onAutoFocus(boolean success, Camera camera) {
				// TODO Auto-generated method stub

			}
		});

	}

	public static void takePhoto() {
		mCamera.takePicture(new ShutterCallback() {

			@Override
			public void onShutter() {
				// TODO Auto-generated method stub

			}
		}, new Camera.PictureCallback() {

			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				// TODO Auto-generated method stub

			}
		}, new Camera.PictureCallback() {

			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				// TODO Auto-generated method stub

				mCamera.startPreview();
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		// Set the second argument by your choice.
		// Usually, 0 for back-facing camera, 1 for front-facing camera.
		// If the OS is pre-gingerbreak, this does not have any effect.

		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
		// if (Camera.getNumberOfCameras() > cameraId) {
		// mCameraId = cameraId;
		// } else {
		// mCameraId = 0;
		// }
		// } else {
		// mCameraId = 0;
		// }
		//
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
		// mCamera = Camera.open(mCameraId);
		// } else {
		// mCamera = Camera.open();
		// }

		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
		mCamera = Camera.open();
		mCamera.startPreview();

		mPreview = new CameraPreview(new LayoutParams(previewLayout.getWidth(),
				previewLayout.getHeight()), this, mCamera,
				CameraPreview.LayoutMode.FitToParent);
		myView = new MyView(this, new LayoutParams(previewLayout.getWidth(),
				previewLayout.getHeight()));
		RelativeLayout.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		previewLayout.addView(mPreview, layoutParams);
		previewLayout.addView(myView, layoutParams);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mPreview.stop();
		previewLayout.removeView(mPreview);
		previewLayout.removeView(myView);
		// mLayout.removeView(mPreview); // This is necessary.
		mPreview = null;
	}

}
