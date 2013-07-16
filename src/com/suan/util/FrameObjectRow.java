package com.suan.util;

import java.util.ArrayList;

public class FrameObjectRow {
	ArrayList<AreaItem> objectList = new ArrayList<AreaItem>();
	int leftBorder = -1, rightBorder = -1, topBorder = -1, bottomBorder = -1;
	public static int minAreaSize = 10;

	public FrameObjectRow() {

	}

	public void addObject(int x, int y, int[][] gridColor) {
		boolean contain = false;
		if (x >= leftBorder && x <= rightBorder && y >= topBorder
				&& y <= bottomBorder) {
			// in the border
			for (int i = 0; i < objectList.size(); i++) {
				if (objectList.get(i).contain(x, y)) {
					contain = true;
				}
			}

		} else {
			contain = false;
		}
		if (contain) {

		} else {

			AreaItem areaItem = new AreaItem(x, y, gridColor);
			if (areaItem.getAreaSize() < minAreaSize) {
				return;
			}
			if (leftBorder == -1) {// first time
				objectList.add(areaItem);
				leftBorder = areaItem.getLeftBorder();
				rightBorder = areaItem.getRightBorder();
				topBorder = areaItem.getTopBorder();
				bottomBorder = areaItem.getBottomBorder();

			} else {
				// not first time

				if (areaItem.getLeftBorder() >= leftBorder
						&& areaItem.getRightBorder() <= rightBorder
						&& areaItem.getTopBorder() >= topBorder
						&& areaItem.getBottomBorder() <= bottomBorder) {
					// in the origin area
					return;
				} else {
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
			}

		}
	}

}
