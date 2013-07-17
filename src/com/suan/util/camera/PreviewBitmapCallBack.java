package com.suan.util.camera;

import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;

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
