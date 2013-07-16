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
	 * 0:color 1:size 2:height 3:width
	 */
	private Line centerRecord = new Line(25);

	public void recoginse(int[][] gridColor) {
		this.gridColor = gridColor;

		switch (nowRecogState) {
		case -1:
			centerAreaItem = new AreaItem(centerX, centerY, gridColor);
			centerAreaSize = centerAreaItem.getAreaSize();
			centerRecord.push(new int[] { gridColor[centerX][centerY],
					centerAreaSize, centerAreaItem.getAreaHeight(),
					centerAreaItem.getAreaWidth() });

//			Log.e("centerSize",
//					centerAreaSize + "|" + centerAreaItem.getAreaHeight() + "|"
//							+ centerAreaItem.getAreaWidth());

			switch (handRecogState) {
			case -1:
				if (centerRecord.getTop()[1] > 70) {
					if (!BitmapOperator.colorSimilar(centerRecord.getTop()[0],
							Color.argb(255, 255, 255, 255),
							BitmapOperator.colorRadius)) {// not white
//						Log.e("reco", "not white");

						for (int i = 0; i < centerRecord.content.size(); i++) {
							if (centerRecord.content.get(i)[1] > 70
									&& BitmapOperator.colorSimilar(
											centerRecord.content.get(i)[0],
											centerRecord.getTop()[0],
											BitmapOperator.colorRadius)){
//								Log.e("reco", "hit");
							}
								
						}
					}

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
