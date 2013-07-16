package com.suan.util;

import android.graphics.Color;
import android.util.Log;

public class ReconitionManager {
	public int[][] gridColor;
	private int nowRecogState = -1;
	private int handRecogState = -1;
	private static final int HAND_COLOR_RECON = 0;
	private static final int ACTION_START = 1;
	private static final int ACTION_END = 2;
	public static int centerX = 15;
	public static int centerY = 9;
	private int centerAreaSize = 0;
	public static AreaItem centerAreaItem;
	/*
	 * 0:color 1:size 2:height 3:width 4:record time
	 */
	private Line centerRecord = new Line(25);

	public void recoginse(int[][] gridColor) {
		this.gridColor = gridColor;
		
		switch (nowRecogState) {
		case -1:

			/*
			 * size >90 ->save
			 */
			centerRecord.resort();
			centerAreaItem = new AreaItem(centerX, centerY, gridColor);
			centerAreaSize = centerAreaItem.getAreaSize();
			if (centerAreaSize > 90) {
				// if area size >90 and not the same as top color
				if (centerRecord.getTop() == null
						|| !BitmapOperator.colorSimilar(
								(int)centerRecord.getTop()[0],
								centerAreaItem.getColor(),
								(int) (BitmapOperator.colorRadius * 1.5))) {

					centerRecord.push(new long[] { gridColor[centerX][centerY],
							centerAreaSize, centerAreaItem.getAreaHeight(),
							centerAreaItem.getAreaWidth(),System.currentTimeMillis() });

				}

			}

			// Log.e("centerSize",
			// centerAreaSize + "|" + centerAreaItem.getAreaHeight() + "|"
			// + centerAreaItem.getAreaWidth());

			switch (handRecogState) {
			case -1:
				Log.e("size", centerRecord.content.size() + "");
				if (centerRecord.getTop()[1] > 10) {

				}
				break;

			case 0:

				break;

			case 1:

				break;

			}

			break;

		case HAND_COLOR_RECON:

			break;

		case ACTION_START:

			break;

		case ACTION_END:

			break;

		}

	}

}
