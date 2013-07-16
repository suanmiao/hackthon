package com.suan.util;

import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;

public class PreviewBitmapCallBack implements PreviewCallback {
	BitmapOperator bitmapOperator;
	
	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		// TODO Auto-generated method stub
		if(bitmapOperator ==null){
			bitmapOperator = new BitmapOperator();
		}else{
			bitmapOperator.doCalculate(data, camera);
		}
		
	}
	
	
	

}
