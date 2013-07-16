package com.suan.util;

import java.util.ArrayList;

import android.graphics.Color;
import android.hardware.Camera;
import android.util.Log;

public class BitmapOperator {

	public static int colorRadius = 1500;
	private static int minAreaWith = 30;
	private static int minAreaHeight = 30;
	private static int xDivision = 5;
	private static int yDivision = 5;
	private static int height, width;

	private static int xAmount = 30;
	private static int yAmount = 18;
	public static int[][] gridColor;
	private static ReconitionManager reconitionManager;
	private static int[] colors;
	ThreadPool threadPool;
	private int nowTaskId = 0;
	private long lastTime;

	public BitmapOperator() {
		gridColor = new int[xAmount][yAmount];

		reconitionManager = new ReconitionManager();
		threadPool = new ThreadPool(3);
	}

	public void doCalculate(byte[] data, Camera camera) {
		lastTime = System.currentTimeMillis();
		colors = convertTointArray(data, camera);

//		Log.e("time", "" + (System.currentTimeMillis() - lastTime));
		threadPool.execute(createTask(nowTaskId++));

		// if (nowTaskId / 2 == 0) {
		// initGridColor(bitmap);
		// reconitionManager.recoginse(gridColor);
		// } else {
		//
		// }
		nowTaskId++;
		// Log.e("bitmap", gridColor[0][0] + "|" + gridColor[1][1] + "||"
		// + (System.currentTimeMillis() - startTime));

	}

	private static Runnable createTask(final int taskID) {
		return new Runnable() {
			public int[][] mGridColor;

			public void run() {
				mGridColor = new int[xAmount][yAmount];
				long time = System.currentTimeMillis();
				
				initGridColor();
//				Log.e("time", ""+(System.currentTimeMillis()-time));
				long time1 = System.currentTimeMillis();
				gridColor = mGridColor;

				reconitionManager.recoginse(mGridColor);
//				Log.e("time1", ""+(System.currentTimeMillis()-time1));

			}

			private void initGridColor() {
//				long time = System.currentTimeMillis();
				int xLength = width / xAmount;
				int yLength = height / yAmount;
				for (int x = 0; x < xAmount; x++) {
					for (int y = 0; y < yAmount; y++) {

						int[][] pixelMatrix = new int[xLength][yLength];
						for (int i = 0; i < xLength; i++) {
							for (int j = 0; j < yLength; j++) {
								pixelMatrix[i][j] = colors[(y * yLength + j)
										* width + x * xLength + i];
							}
						}
						

						mGridColor[x][y] = getAreaColor(pixelMatrix);

					}

				}
//				Log.e("time", ""+(System.currentTimeMillis()-time));

			}

		};
	}

	public int[] convertTointArray(byte[] data, Camera camera) {
		height = camera.getParameters().getPreviewSize().height;
		width = camera.getParameters().getPreviewSize().width;

		int[] argb8888 = new int[height * width];

		decodeYUV(argb8888, data, width, height);

		return argb8888;

	}

	// private void initGridColor(Bitmap bitmap) {
	// int xLength = bitmap.getWidth() / xAmount;
	// int yLength = bitmap.getHeight() / yAmount;
	// for (int x = 0; x < xAmount; x++) {
	// for (int y = 0; y < yAmount; y++) {
	// int[] pixels = new int[(xLength + 3) * (yLength + 10)];
	//
	// // if(nowX>=bitmap.getWidth()-xLength){
	// // nowX = bitmap.getWidth() - xLength;
	// // }
	// // if(nowY >= bitmap.getHeight() - yLength){
	// // nowY = bitmap.getHeight() - yLength;
	// // }
	// Bitmap nowBitmap = Bitmap.createBitmap(bitmap, x * xLength, y
	// * yLength, xLength, yLength);
	// nowBitmap.getPixels(pixels, 0, nowBitmap.getWidth(), 0, 0,
	// xLength, yLength);
	//
	// int[][] pixelMatrix = new int[xLength][yLength];
	// for (int i = 0; i < xLength; i++) {
	// for (int j = 0; j < yLength; j++) {
	// pixelMatrix[i][j] = pixels[i * xLength + j];
	// }
	// }
	//
	// gridColor[x][y] = getAreaColor(pixelMatrix);
	//
	// }
	//
	// }
	//
	// }

	public static int getAreaColor(int[][] area) {

		if (area.length <= minAreaWith || area[0].length <= minAreaHeight) {
			return getMinAreaColor(area);
		} else {

			// too large:resize
			int[][] resizedArea = new int[xDivision][yDivision];
			int xLength = area.length / xDivision;
			int yLength = area[0].length / yDivision;
			for (int x = 0; x < xDivision; x++) {
				for (int y = 0; y < yDivision; y++) {

					int[][] nowArea = new int[xLength][yLength];
					for (int i = 0; i < xLength; i++) {
						for (int j = 0; j < yLength; j++) {
							int nowX = x * xDivision + i;
							int nowY = y * yDivision + j;
							if (nowX > area.length) {
								nowX = area.length;
							}
							if (nowY > area[0].length) {
								nowY = area[0].length;
							}
							nowArea[i][j] = area[nowX][nowY];
						}
					}
					resizedArea[x][y] = getAreaColor(nowArea);
				}
			}

			return getAreaColor(resizedArea);
		}

	}

	public static int getMinAreaColor(int[][] minArea) {

		// long average0 = 0;
		// long average1 = 0;
		// long average2 = 0;
		// long average3 = 0;
		//
		// for (int i = 0; i < minArea.length; i++) {
		// for (int j = 0; j < minArea[0].length; j++) {
		// average1 += Color.red(minArea[i][j]);
		// average2 += Color.green(minArea[i][j]);
		// average3 += Color.blue(minArea[i][j]);
		// average0 += Color.alpha(minArea[i][j]);
		//
		// }
		// }
		//
		// average0 = (int) (average0 / (minArea.length * minArea[0].length));
		//
		// average1 = (int) (average1 / (minArea.length * minArea[0].length));
		//
		// average2 = (int) (average2 / (minArea.length * minArea[0].length));
		//
		// average3 = (int) (average3 / (minArea.length * minArea[0].length));
		//
		// return Color.argb((int) average0, (int) average1, (int) average2,
		// (int) average3);

		ArrayList<int[]> colorArrayList = new ArrayList<int[]>();
		for (int x = 0; x < minArea.length; x += 4) {
			for (int y = 0; y < minArea[0].length; y += 4) {
				int index = exist(minArea[x][y], colorArrayList);
				if (index == -1) {

					colorArrayList.add(new int[] { minArea[x][y], 1 });
				} else {

					int average0 = (int) (Color.alpha(minArea[x][y]) + Color
							.alpha(colorArrayList.get(index)[0])) / 2;
					int average1 = (int) (Color.red(minArea[x][y]) + Color
							.red(colorArrayList.get(index)[0])) / 2;
					int average2 = (int) (Color.green(minArea[x][y]) + Color
							.green(colorArrayList.get(index)[0])) / 2;
					int average3 = (int) (Color.blue(minArea[x][y]) + Color
							.blue(colorArrayList.get(index)[0])) / 2;

					int averageColor = Color.argb(average0, average1, average2,
							average3);

					colorArrayList.set(index, new int[] { averageColor,
							colorArrayList.get(index)[1] + 1 });
				}
			}
		}

		int maxIndex = 0;
		for (int i = 0; i < colorArrayList.size(); i++) {
			if (colorArrayList.get(maxIndex)[1] <= colorArrayList.get(i)[1]) {
				maxIndex = i;
			}
		}

		return colorArrayList.get(maxIndex)[0];

	}

	private static int exist(int a, ArrayList<int[]> list) {
		int index = -1;

		for (int i = 0; i < list.size(); i++) {
			if (colorSimilar(a, list.get(i)[0],colorRadius)) {
				return i;
			}
		}

		return index;
	}

	public static boolean colorSimilar(int a, int b,int colorRadius) {
		return Math.abs(Color.alpha(a) - Color.alpha(b))
				* Math.abs(Color.alpha(a) - Color.alpha(b))
				+ Math.abs(Color.red(a) - Color.red(b))
				* Math.abs(Color.red(a) - Color.red(b))
				+ Math.abs(Color.green(a) - Color.green(b))
				* Math.abs(Color.green(a) - Color.green(b))
				+ Math.abs(Color.blue(a) - Color.blue(b))
				* Math.abs(Color.blue(a) - Color.blue(b)) <= colorRadius;
	}

	public void decodeYUV(int[] out, byte[] fg, int width, int height)
			throws NullPointerException, IllegalArgumentException {
		int sz = width * height;
		if (out == null)
			throw new NullPointerException("buffer out is null");
		if (out.length < sz)
			throw new IllegalArgumentException("buffer out size " + out.length
					+ " < minimum " + sz);
		if (fg == null)
			throw new NullPointerException("buffer 'fg' is null");
		if (fg.length < sz)
			throw new IllegalArgumentException("buffer fg size " + fg.length
					+ " < minimum " + sz * 3 / 2);
		int i, j;
		int Y, Cr = 0, Cb = 0;
		for (j = 0; j < height; j++) {
			int pixPtr = j * width;
			final int jDiv2 = j >> 1;
			for (i = 0; i < width; i++) {
				Y = fg[pixPtr];
				if (Y < 0)
					Y += 255;
				if ((i & 0x1) != 1) {
					final int cOff = sz + jDiv2 * width + (i >> 1) * 2;
					Cb = fg[cOff];
					if (Cb < 0)
						Cb += 127;
					else
						Cb -= 128;
					Cr = fg[cOff + 1];
					if (Cr < 0)
						Cr += 127;
					else
						Cr -= 128;
				}
				int R = Y + Cr + (Cr >> 2) + (Cr >> 3) + (Cr >> 5);
				if (R < 0)
					R = 0;
				else if (R > 255)
					R = 255;
				int G = Y - (Cb >> 2) + (Cb >> 4) + (Cb >> 5) - (Cr >> 1)
						+ (Cr >> 3) + (Cr >> 4) + (Cr >> 5);
				if (G < 0)
					G = 0;
				else if (G > 255)
					G = 255;
				int B = Y + Cb + (Cb >> 1) + (Cb >> 2) + (Cb >> 6);
				if (B < 0)
					B = 0;
				else if (B > 255)
					B = 255;
				out[pixPtr++] = 0xff000000 + (B << 16) + (G << 8) + R;
			}
		}

	}

}
