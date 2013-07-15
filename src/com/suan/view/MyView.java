package com.suan.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.suan.util.BitmapOperator;

@SuppressLint("HandlerLeak")
public class MyView extends View {

	/*
	 * draw mode 0:sketch 1:draw line 2:draw circle 3:draw rectangle
	 */

	private Paint paint = null;

	public MyView(Context context, LayoutParams layoutParams) {
		super(context, null);
		this.setLayoutParams(layoutParams);
		paint = new Paint();
		new Thread() {
			public void run() {
				while(true){
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
			MyView.this.invalidate();

		}
	};

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);

	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		Log.e("ondraw", "draw" + BitmapOperator.gridColor);

				if (BitmapOperator.gridColor != null) {
			for (int x = 0; x < BitmapOperator.gridColor.length; x++) {
				for (int y = 0; y < BitmapOperator.gridColor[0].length; y++) {
					paint.setColor(BitmapOperator.gridColor[x][y]);

					canvas.drawRect(x * 10,y * 10, x * 10 + 10, y * 10 + 10,
							paint);
				}
			}
		}

	}

}
