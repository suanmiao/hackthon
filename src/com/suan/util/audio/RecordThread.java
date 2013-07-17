package com.suan.util.audio;

import java.nio.ByteBuffer;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class RecordThread extends Thread {
	private AudioRecord ar;
	private int bs;
	private static int SAMPLE_RATE_IN_HZ = 8000;
	private boolean isRun = true;
	public static long[][] record;
	private static int nowRecordLength = 0;
	private static int recordLength = 50;
	private long lastRecordTime = 0;
	public static int footStep = 50;
	FFT fft;

	@SuppressWarnings("deprecation")
	public RecordThread() {
		super();

		record = new long[recordLength][4];
		bs = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);

		bs = 1024*2;

		ar = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_IN_HZ,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, bs);
		fft = new FFT(512*2, ar.getSampleRate());
		Log.e("bs", bs + "");
	}

	public void run() {
		super.run();
		// boolean a = true;
		// if(a){
		// return;
		// }

		ar.startRecording();
		// 用于读取的 buffer
		byte[] buffer = new byte[bs];
		while (isRun) {
			int r = ar.read(buffer, 0, bs);
			// 将 buffer 内容取出，进行平方和运算
			float[] micBufferData = new float[512*2];
			final int bytesPerSample = 2; // As it is 16bit PCM
			final double amplification = 100.0; // choose a number as you like
			for (int index = 0, floatIndex = 0; index < r - bytesPerSample + 1; index += bytesPerSample, floatIndex++) {
				float sample = 0;
				for (int b = 0; b < bytesPerSample; b++) {
					int v = buffer[index + b];
					if (b < bytesPerSample - 1 || bytesPerSample == 1) {
						v &= 0xFF;
					}
					sample += v << (b * 8);
				}
				float sample32 = (float) (amplification * (sample / 32768.0));
				micBufferData[floatIndex] = sample32;
			}

			fft.forward(micBufferData);
			if (System.currentTimeMillis() - lastRecordTime > footStep) {

				for (int i = 0; i < recordLength; i++) {
					if (i < recordLength) {
						record[i][1] = (int) (micBufferData[10*2 * i]);

					}
				}
				lastRecordTime = System.currentTimeMillis();
			}
		}

		ar.stop();
	}
	
	

	public void record(double db) {
		if (nowRecordLength < recordLength) {
			record[nowRecordLength][0] = System.currentTimeMillis();
			record[nowRecordLength][1] = (int) (db * 1000);
			nowRecordLength++;
		} else {
			for (int i = 0; i < recordLength - 1; i++) {
				record[i][0] = record[i + 1][0];
				record[i][1] = record[i + 1][1];
			}
			record[recordLength - 1][0] = System.currentTimeMillis();
			record[recordLength - 1][1] = (int) (db * 1000);

		}

	}

	public void pause() {
		// 在调用本线程的 Activity 的 onPause 里调用，以便 Activity 暂停时释放麦克风
		isRun = false;
	}

	public void start() {
		// 在调用本线程的 Activity 的 onResume 里调用，以便 Activity 恢复后继续获取麦克风输入音量
		super.start();
	}
}