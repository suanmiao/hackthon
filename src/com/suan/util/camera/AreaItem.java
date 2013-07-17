package com.suan.util.camera;

import java.util.ArrayList;

import android.util.Log;

public class AreaItem {
	public int x, y;
	private int[][] gridColor;
	public int[][] content;
	private int[] xRecord;
	private int[] yRecord;
	private int size = 0;
	private int leftBorder, rightBorder, topBorder, bottomBorder;
	private int xMax,yMax;
	private int colorRadius;

	/*
	 * 0:x 1:y 2:type
	 * 
	 * type: 1:content 0:not discovered -1:other
	 */

	public AreaItem(int x, int y, int[][] gridColor,int colorRadius) {
		this.x = x;
		this.y = y;
		this.gridColor = gridColor;
		this.colorRadius = colorRadius;
		content = new int[BitmapOperator.xAmount][BitmapOperator.yAmount];
		xRecord = new int[BitmapOperator.xAmount];
		yRecord = new int[BitmapOperator.yAmount];
		leftBorder = x;
		rightBorder = x;
		topBorder = y;
		bottomBorder = y;

		ArrayList<int[]> nextPointArrayList = new ArrayList<int[]>();
		nextPointArrayList.add(new int[] { x, y, x, y });
		while (nextPointArrayList.size() > 0) {
			detectArea(nextPointArrayList.get(0)[0],
					nextPointArrayList.get(0)[1], nextPointArrayList.get(0)[2],
					nextPointArrayList.get(0)[3], nextPointArrayList);
			nextPointArrayList.remove(0);

		}

		record();

		// Log.e("size", size + "");
	}

	public int getColor() {
		return gridColor[x][y];
	}

	public void detectArea(int startX, int startY, int detectX, int detectY,
			ArrayList<int[]> nextPointArrayList) {

		if (legal(detectX, detectY)) {
			if (content[detectX][detectY] == 0) {
				// color the same
				if (BitmapOperator.colorSimilar(gridColor[x][y],
						gridColor[detectX][detectY],
						(int) (colorRadius * 1.5))
						&& BitmapOperator.colorSimilar(
								gridColor[startX][startY],
								gridColor[detectX][detectY],
								(int) (colorRadius * 1))) {
					content[detectX][detectY] = 1;
					size++;

				} else {

					content[detectX][detectY] = -1;
				}

				if (legal(detectX - 1, detectY)) {
					if (content[detectX - 1][detectY] == 0) {
						nextPointArrayList.add(new int[] { detectX, detectY,
								detectX - 1, detectY });
					}
				}

				if (legal(detectX + 1, detectY)) {
					if (content[detectX + 1][detectY] == 0) {
						nextPointArrayList.add(new int[] { detectX, detectY,
								detectX + 1, detectY });
					}
				}
				if (legal(detectX, detectY - 1)) {
					if (content[detectX][detectY - 1] == 0) {
						nextPointArrayList.add(new int[] { detectX, detectY,
								detectX, detectY - 1 });
					}
				}
				if (legal(detectX, detectY + 1)) {
					if (content[detectX][detectY + 1] == 0) {
						nextPointArrayList.add(new int[] { detectX, detectY,
								detectX, detectY + 1 });
					}
				}

			}

		}

	}

	private boolean legal(int detectX, int detectY) {
		return detectX < BitmapOperator.xAmount && detectX >= 0
				&& detectY < BitmapOperator.yAmount && detectY >= 0;
	}

	private void record() {
		for (int x = 0; x < BitmapOperator.xAmount; x++) {
			for (int y = 0; y < BitmapOperator.yAmount; y++) {
				if (content[x][y] == 1 || content[x][y] == 2) {
					xRecord[x]++;
					yRecord[y]++;
				}
			}
		}
		int minXIndex = x;
		int maxXIndex = x;
		int minYIndex = y;
		int maxYIndex = y;
		boolean leftBorderFound = false;
		boolean rightBorderFound = false;
		boolean topBorderFound = false;
		boolean bottomBorderFound = false;

		for (int i = 0; i < BitmapOperator.xAmount; i++) {
			if (xRecord[i] != 0 && !leftBorderFound) {
				leftBorderFound = true;
				leftBorder = i;
			}
			if (xRecord[i] == 0 && leftBorderFound && !rightBorderFound) {
				rightBorderFound = true;
				rightBorder = i;
			}

			if (xRecord[i] > xRecord[maxXIndex]) {
				maxXIndex = i;
			}
			if (xRecord[i] < xRecord[minXIndex] && xRecord[i] != 0) {
				minXIndex = i;
			}

		}
		
		xMax = maxXIndex;
		yMax = maxYIndex;

		for (int j = 0; j < BitmapOperator.yAmount; j++) {
			if (yRecord[j] != 0 && !topBorderFound) {
				topBorderFound = true;
				topBorder = j;
			}
			if (yRecord[j] == 0 && topBorderFound && !bottomBorderFound) {
				bottomBorderFound = true;
				bottomBorder = j;
			}

			if (yRecord[j] > yRecord[maxYIndex]) {
				maxYIndex = j;
			}
			if (yRecord[j] < yRecord[minYIndex] && yRecord[j] != 0) {
				minYIndex = j;
			}

		}

	}

	public int getAreaSize() {
		return size;
	}

	public int getAreaWidth() {

		return rightBorder - leftBorder + 1;
	}

	public int getAreaHeight() {

		return bottomBorder - topBorder + 1;

	}

	public int getRightBorder() {
		return rightBorder;
	}

	public int getLeftBorder() {

		return leftBorder;
	}

	public int getTopBorder() {
		return topBorder;

	}

	public int getBottomBorder() {
		return bottomBorder;

	}
	
	public int getXMax(){
		return xMax;
	}
	
	public int getYMax(){
		return yMax;
	}

	public boolean contain(int x, int y) {
		return content[x][y] == 1 || content[x][y] == 2;

	}

	public double similarity(AreaItem areaItem) {
		if (BitmapOperator.colorSimilar(getColor(), areaItem.getColor(),
				BitmapOperator.colorRadius * 2)) {
			int similarAmount = 0;

			for (int x = 0; x < BitmapOperator.xAmount; x++) {
				for (int y = 0; y < BitmapOperator.yAmount; y++) {
					if ((content[x][y] == 1) && areaItem.content[x][y] == 1) {
						similarAmount++;

					}
				}
			}
			// Log.e("amount", similarAmount+"|"+(size+areaItem.getAreaSize()));
			return (double) (similarAmount * 2)
					/ (double) (size + areaItem.getAreaSize());

		} else {
			return 0;
		}
	}

	public double getFillPercent() {
		return (double) (size)
				/ (double) ((getRightBorder() - getLeftBorder()) * (getBottomBorder() - getTopBorder()));
	}
}
