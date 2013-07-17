package com.suan.util.camera;

import java.util.ArrayList;

import com.suan.view.MyView;

import android.util.Log;

public class FrameManager {
	public ArrayList<FrameObjectRecorder> allFrameArrayList = new ArrayList<FrameObjectRecorder>();
	public ArrayList<AreaItem> movingObjectList = new ArrayList<AreaItem>();
	public ArrayList<AreaItem[]> similarAreaItems = new ArrayList<AreaItem[]>();
	private static int maxMovingFootFrame = 8;
	private static int minMovingFootFrame = 2;
	private int [] [] gridColor;
	private int [][]lastGridColor;

	public FrameManager() {

	}

	public void addFrame(int[][] gridColor) {
		this.gridColor = gridColor;
		movingObjectList = null;
		allFrameArrayList.add(new FrameObjectRecorder(gridColor));
		resort();
		similarAreaItems = new ArrayList<AreaItem[]>();
		movingObjectList = new ArrayList<AreaItem>();

		recognise();
		lastGridColor = gridColor;
		// Log.e("moving", movingObjectList.size() + "");
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

					// Log.e("differen",
					// (difference/nowAreaItem.getAreaSize()) + "");

					if (BitmapOperator
							.colorSimilar(nowAreaItem.getColor(),
									lastAreaItem.getColor(),
									BitmapOperator.colorRadius)) {

						if ((Math.abs(nowAreaItem.getLeftBorder()
								- lastAreaItem.getLeftBorder()) < maxMovingFootFrame
								&& Math.abs(nowAreaItem.getRightBorder()
										- lastAreaItem.getRightBorder()) < maxMovingFootFrame
								&& Math.abs(nowAreaItem.getTopBorder()
										- lastAreaItem.getTopBorder()) < maxMovingFootFrame && Math
								.abs(nowAreaItem.getBottomBorder()
										- lastAreaItem.getBottomBorder()) < maxMovingFootFrame)) {

							similarAreaItems.add(new AreaItem[] { nowAreaItem,
									lastAreaItem });

						}

					}

				}
			}

			//
			removeStillObject(lastFrameAreaItems);
			MyView.nowAreaItems = movingObjectList;

		}

	}

	public void removeStillObject(ArrayList<AreaItem> lastFrameAreaItems) {
		for (int i = 0; i < similarAreaItems.size(); i++) {
			AreaItem nowSimilarAreaItem = similarAreaItems.get(i)[0];
			AreaItem lastAreaItem = similarAreaItems.get(i)[1];
			double similar = nowSimilarAreaItem.similarity(lastAreaItem);
			if (similar > 0.5) {
				similarAreaItems.set(i, null);
			}
			if (nowSimilarAreaItem.getFillPercent() < 0.5
					|| nowSimilarAreaItem.getAreaSize() < 20) {
				similarAreaItems.set(i, null);
			}

			if (Math.abs(nowSimilarAreaItem.getXMax() - lastAreaItem.getXMax()) < minMovingFootFrame
					&& Math.abs(nowSimilarAreaItem.getYMax()
							- lastAreaItem.getYMax()) < minMovingFootFrame) {
				similarAreaItems.set(i, null);
			}

//			if(!newBorn(nowSimilarAreaItem)){
//				similarAreaItems.set(i, null);
//			}
		}
		// Log.e("similar", "" + similarAreaItems.size());

		for (int x = 0; x < similarAreaItems.size(); x++) {
			if (similarAreaItems.get(x) != null) {
				movingObjectList.add(similarAreaItems.get(x)[0]);

			}
		}

	}

	private boolean newBorn(AreaItem areaItem) {
		if(lastGridColor==null){
			return true;
		}
		
		int difference = 0;
		for (int i = areaItem.getLeftBorder(); i <= areaItem.getRightBorder(); i++) {
			for (int j = areaItem.getTopBorder(); j <= areaItem
					.getBottomBorder(); j++) {
				if (!BitmapOperator.colorSimilar(areaItem.getColor(),
						lastGridColor[i][j], BitmapOperator.colorRadius)) {
					difference++;
				}
			}
		}

		double difPercent = (double)difference / (double)areaItem.getAreaSize();

		return difPercent >= 0.6;
	}

}
