package com.suan.util.audio;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.suan.util.audio.Sender.AudioSendCallBack;

public class PostThread extends Thread {
	private  boolean isRun = true;

	public PostThread() {
		super();

	}
	
	public void run() {
		super.run();
		while (isRun) {
			
			try{
				
				sleep(1000);
			}catch(Exception exception){
				
			}
			Log.e("post", "start");
			Message message = new Message();
			sendHandler.sendMessage(message);
			
		}
	}
	
	@SuppressLint("HandlerLeak")
	private Handler sendHandler = new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			
			Sender.sendMMS(new AudioSendCallBack() {
				
				@Override
				public void onBack(String resultString) {
					// TODO Auto-generated method stub
				
					Log.e("send return", "result:"+resultString);
					
				}
			});
			
		}
		
	};


	public void pause() {
		// 在调用本线程的 Activity 的 onPause 里调用，以便 Activity 暂停时释放麦克风
		isRun = false;
	}

	public  void start() {
		// 在调用本线程的 Activity 的 onResume 里调用，以便 Activity 恢复后继续获取麦克风输入音量
		super.start();
	}
}