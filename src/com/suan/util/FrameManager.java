package com.suan.util;

import java.util.ArrayList;

import com.suan.view.MyView;

import android.util.Log;

public class FrameManager {
	public ArrayList<FrameObjectRecorder> allFrameArrayList = new ArrayList<FrameObjectRecorder>();
	public ArrayList<AreaItem> movingObjectList = new ArrayList<AreaItem>();
	private static int movingFootFrame = 4;

	public FrameManager() {

	}

	public void addFrame(int[][] gridColor) {
		movingObjectList = null;
		allFrameArrayList.add(new FrameObjectRecorder(gridColor));
		resort();
		movingObjectList = new ArrayList<AreaItem>();
		recognise();
		Log.e("moving", movingObjectList.size() + "");
	}

	private void resort() {
		if (System.currentTimeMillis()
				- allFrameArrayList.get(0).getFrameBornTime() > 6000) {
			allFrameArrayList.remove(0);
		}

	}

	private void recognise() {
		if (allFrameArrayList.size() > 1) {
			FrameObjectRecorder lastFrameObjectRecorder = allFrameArrayList
					.get(allFrameArrayList.size() - 2);
			FrameObjectRecorder nowFrameObjectRecorder = allFrameArrayList
					.get(allFrameArrayList.size() - 1);
			ArrayList<AreaItem> lastFrameAreaItems = lastFrameObjectRecorder
					.getFrameObjectRow().objectList;
			ArrayList<AreaItem> nowFrameAreaItems = nowFrameObjectRecorder
					.getFrameObjectRow().objectList;

			for (int i = 0; i < nowFrameAreaItems.size(); i++) {
				AreaItem nowAreaItem = nowFrameAreaItems.get(i);

				for (int j = 0; j < lastFrameAreaItems.size(); j++) {
					AreaItem lastAreaItem = lastFrameAreaItems.get(j);

					int difference = Math.abs(lastAreaItem.getAreaSize()
							- nowAreaItem.getAreaSize());
					// Log.e("differen",
					// (difference/nowAreaItem.getAreaSize()) + "");

					if (BitmapOperator.colorSimilar(nowAreaItem.getColor(),
							lastAreaItem.getColor(),
							BitmapOperator.colorRadius )) {

						movingObjectList.add(nowAreaItem);
						MyView.nowAreaItems = movingObjectList;


						if (difference / nowAreaItem.getAreaSize() < 3
								&& difference / nowAreaItem.getAreaSize() > 0.3) {

							if (Math.abs(nowAreaItem.getLeftBorder()
									- lastAreaItem.getLeftBorder()) < movingFootFrame
									&& Math.abs(nowAreaItem.getRightBorder()
											- lastAreaItem.getRightBorder()) < movingFootFrame
									&& Math.abs(nowAreaItem.getTopBorder()
											- lastAreaItem.getTopBorder()) < movingFootFrame
									&& Math.abs(nowAreaItem.getBottomBorder()
											- lastAreaItem.getBottomBorder()) < movingFootFrame) {

							}
						}

					}

				}

				// if (nowAreaItem.getLeftBorder() < lastFrameObjectRecorder
				// .getFrameObjectRow().leftBorder
				// || nowAreaItem.getRightBorder() > lastFrameObjectRecorder
				// .getFrameObjectRow().rightBorder
				// || nowAreaItem.getTopBorder() < lastFrameObjectRecorder
				// .getFrameObjectRow().topBorder
				// || nowAreaItem.getBottomBorder() > lastFrameObjectRecorder
				// .getFrameObjectRow().bottomBorder) {
				// // not contain
				// return;
				// } else {
				// for (int j = 0; j < lastFrameAreaItems.size(); j++) {
				// AreaItem lastAreaItem = lastFrameAreaItems.get(j);
				// int difference = Math.abs(lastAreaItem.getAreaSize()
				// - nowAreaItem.getAreaSize());
				// // Log.e("differen",
				// // (difference/nowAreaItem.getAreaSize()) + "");
				//
				// if (difference / nowAreaItem.getAreaSize() < 1.5
				// && difference / nowAreaItem.getAreaSize() > 0.7) {
				// if (Math.abs(nowAreaItem.getLeftBorder()
				// - lastAreaItem.getLeftBorder()) < 2
				// && Math.abs(nowAreaItem.getRightBorder()
				// - lastAreaItem.getRightBorder()) < 2
				// && Math.abs(nowAreaItem.getTopBorder()
				// - lastAreaItem.getTopBorder()) < 2
				// && Math.abs(nowAreaItem.getBottomBorder()
				// - lastAreaItem.getBottomBorder()) < 2) {
				// movingObjectList.add(nowAreaItem);
				//
				// }
				// }
				// }
				// }
			}

		}

	}

}
