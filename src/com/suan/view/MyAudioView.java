package com.suan.view;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera.Area;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.suan.util.audio.RecordThread;
import com.suan.util.camera.AreaItem;
import com.suan.util.camera.BitmapOperator;
import com.suan.util.camera.ReconitionManager;

@SuppressLint("HandlerLeak")
public class MyAudioView extends View {

	/*
	 * draw mode 0:sketch 1:draw line 2:draw circle 3:draw rectangle
	 */
	public static ArrayList<AreaItem> nowAreaItems = null;

	private Paint paint = null;

	public MyAudioView(Context context, LayoutParams layoutParams) {
		super(context, null);
		this.setLayoutParams(layoutParams);
		paint = new Paint();
		new Thread() {
			public void run() {
				while (true) {
					try {
						sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
					}
					Message message = new Message();
					invalidateHandler.sendMessage(message);

				}
			}

		}.start();

	}

	@SuppressLint("HandlerLeak")
	final Handler invalidateHandler = new Handler() {

		// 子类必须重写此方法,接受数据
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			super.handleMessage(msg);
			// 此处可以更新UI
			MyAudioView.this.invalidate();

		}
	};

	public MyAudioView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public MyAudioView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);

	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// Log.e("ondraw", "draw" + BitmapOperator.gridColor);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawColor(Color.WHITE);
		paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.STROKE);
		float maxHeight = 500;
		float xLength = (float) (20);
//		Log.e("xlength", xLength+"");
		for (int i = 0; i < 50; i++) {
			Rect nowRect = new Rect((int) (i * xLength),
					(int) (maxHeight * (1-(RecordThread.record[i][1]-34000) / 5000.0)),
					(int) (i * xLength + xLength), (int) maxHeight);
			canvas.drawRect(nowRect, paint);
		}

	}

}
