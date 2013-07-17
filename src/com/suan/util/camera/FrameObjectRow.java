package com.suan.util.camera;

import java.util.ArrayList;

import android.util.Log;

public class FrameObjectRow {
	ArrayList<AreaItem> objectList = new ArrayList<AreaItem>();
	int leftBorder = -1, rightBorder = -1, topBorder = -1, bottomBorder = -1;
	public static int minAreaSize = 10;

	public FrameObjectRow() {

	}

	public void addObject(int x, int y, int[][] gridColor,int colorRadius) {
//		Log.e("add", "|");

		if (objectList.size() > 0) {
			for (int i = 0; i < objectList.size(); i++) {
				if (objectList.get(i).contain(x, y)) {
				
					
					return;
				}
			}

		}
		
		AreaItem areaItem = new AreaItem(x, y, gridColor,colorRadius);
//		Log.e("add", ""+areaItem.getAreaSize());
		if (areaItem.getAreaSize() > minAreaSize) {
			// not in original area
			if (areaItem.getLeftBorder() < leftBorder) {
				leftBorder = areaItem.getLeftBorder();
			}

			if (areaItem.getRightBorder() > rightBorder) {
				rightBorder = areaItem.getRightBorder();
			}
			if (areaItem.getTopBorder() < topBorder) {
				topBorder = areaItem.getTopBorder();
			}
			if (areaItem.getBottomBorder() > bottomBorder) {
				bottomBorder = areaItem.getBottomBorder();
			}
			objectList.add(areaItem);

		}

		// boolean contain = false;
		// if (x >= leftBorder && x <= rightBorder && y >= topBorder
		// && y <= bottomBorder) {
		// // in the border
		// for (int i = 0; i < objectList.size(); i++) {
		// if (objectList.get(i).contain(x, y)) {
		// contain = true;
		// }
		// }
		//
		// } else {
		// contain = false;
		// }
		// if (contain) {
		//
		// } else {
		//
		// AreaItem areaItem = new AreaItem(x, y, gridColor);
		// if (areaItem.getAreaSize() < minAreaSize) {
		// return;
		// }
		// if (leftBorder == -1) {// first time
		// objectList.add(areaItem);
		// leftBorder = areaItem.getLeftBorder();
		// rightBorder = areaItem.getRightBorder();
		// topBorder = areaItem.getTopBorder();
		// bottomBorder = areaItem.getBottomBorder();
		//
		// } else {
		// // not first time
		//
		// if (areaItem.getLeftBorder() >= leftBorder
		// && areaItem.getRightBorder() <= rightBorder
		// && areaItem.getTopBorder() >= topBorder
		// && areaItem.getBottomBorder() <= bottomBorder) {
		// // in the origin area
		// return;
		// } else {
		// // not in original area
		// if (areaItem.getLeftBorder() < leftBorder) {
		// leftBorder = areaItem.getLeftBorder();
		// }
		//
		// if (areaItem.getRightBorder() > rightBorder) {
		// rightBorder = areaItem.getRightBorder();
		// }
		// if (areaItem.getTopBorder() < topBorder) {
		// topBorder = areaItem.getTopBorder();
		// }
		// if (areaItem.getBottomBorder() > bottomBorder) {
		// bottomBorder = areaItem.getBottomBorder();
		// }
		//
		// objectList.add(areaItem);
		//
		// }
		// }
		//
		// }
	}

}
