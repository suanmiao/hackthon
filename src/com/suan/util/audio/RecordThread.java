package com.suan.util.audio;

import java.nio.ByteBuffer;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

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

	@SuppressWarnings("deprecation")
	public RecordThread() {
		super();

		record = new long[recordLength][4];
		bs = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		ar = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_IN_HZ,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, bs);

	}

	public void run() {
		super.run();
		ar.startRecording();
		// 用于读取的 buffer
		byte[] buffer = new byte[bs];
		while (isRun) {
			int r = ar.read(buffer, 0, bs);
			int v = 0;
			// 将 buffer 内容取出，进行平方和运算
			for (int i = 0; i < buffer.length; i++) {
				// 这里没有做运算的优化，为了更加清晰的展示代码
				v += buffer[i] * buffer[i];
			}
			// value 的 值 控制 为 0 到 100 之间 0为最小 》= 100为最大！！
			int value = (int) (Math.abs((int) (v / (float) r) / 10000) >> 1);
			// Log.d("111", "v = " + v);
			// 平方和除以数据总长度，得到音量大小。可以获取白噪声值，然后对实际采样进行标准化。
			// 如果想利用这个数值进行操作，建议用 sendMessage 将其抛出，在 Handler 里进行处理。
			// Log.d("222", String.valueOf(v / (float) r));

			double dB = 10 * Math.log10(v / (double) r);
			// Log.d("333", "dB = " + dB);

			if (System.currentTimeMillis() - lastRecordTime > footStep) {
				record(dB);
				lastRecordTime = System.currentTimeMillis();
			}
		}

		ar.stop();
	}

	public static float[] floatMe(short[] pcms) {
		float[] floaters = new float[pcms.length];
		for (int i = 0; i < pcms.length; i++) {
			floaters[i] = pcms[i];
		}
		return floaters;
	}

	public static short[] shortMe(byte[] bytes) {
		short[] out = new short[bytes.length / 2]; // will drop last byte if odd
													// number
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		for (int i = 0; i < out.length; i++) {
			out[i] = bb.getShort();
		}
		return out;
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