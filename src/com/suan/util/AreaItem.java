package com.suan.util;

import java.util.ArrayList;

public class AreaItem {
	private int x, y;
	private int[][] gridColor;
	/*
	 * 0:x 1:y 2:color
	 */
	public ArrayList<int[]> blockArrayList = new ArrayList<int[]>();
	/*
	 * 0:x 1:time
	 */
	ArrayList<int[]> xRecord = new ArrayList<int[]>();
	/*
	 * 0:y 1:time
	 */
	ArrayList<int[]> yRecord = new ArrayList<int[]>();

	public AreaItem(int x, int y, int[][] gridColor) {
		this.x = x;
		this.y = y;
		this.gridColor = gridColor;
		detectArea(x, y, x, y, gridColor);

		record();

	}
	
	public int getColor(){
		return gridColor[x][y];
	}

	public void detectArea(int startX, int startY, int detectX, int detectY,
			int[][] gridColor) {
		if (detectX < gridColor.length && detectX >= 0
				&& detectY < gridColor[0].length && detectY >= 0) {
			if (BitmapOperator.colorSimilar(gridColor[x][y],
					gridColor[detectX][detectY],
					(int) (BitmapOperator.colorRadius * 2))
					&& BitmapOperator.colorSimilar(gridColor[startX][startY],
							gridColor[detectX][detectY],
							(int) (BitmapOperator.colorRadius*1.5))) {
				if (exist(detectX, detectY)) {
					return;
				} else {
					blockArrayList.add(new int[] { detectX, detectY,
							gridColor[detectX][detectY] });
				}

			} else {
				return;
			}

			detectArea(detectX, detectY, detectX, detectY - 1, gridColor);
			detectArea(detectX, detectY, detectX, detectY + 1, gridColor);
			detectArea(detectX, detectY, detectX + 1, detectY, gridColor);
			detectArea(detectX, detectY, detectX - 1, detectY, gridColor);
		}

	}

	private boolean exist(int x, int y) {
		boolean exist = false;
		for (int i = 0; i < blockArrayList.size(); i++) {
			if (blockArrayList.get(i)[0] == x && blockArrayList.get(i)[1] == y) {
				exist = true;
			}
		}

		return exist;
	}

	private void record() {
		for (int i = 0; i < blockArrayList.size(); i++) {
			int xIndex = xExist(blockArrayList.get(i)[0]);
			if (xIndex != -1) {
				xRecord.set(xIndex,
						new int[] { xRecord.get(xIndex)[0],
								xRecord.get(xIndex)[1] + 1 });
			} else {
				xRecord.add(new int[] { blockArrayList.get(i)[0], 1 });
			}
			int yIndex = yExist(blockArrayList.get(i)[1]);
			if (yIndex != -1) {
				yRecord.set(yIndex,
						new int[] { yRecord.get(yIndex)[0],
								yRecord.get(yIndex)[1] + 1 });
			} else {
				yRecord.add(new int[] { blockArrayList.get(i)[1], 1 });
			}

		}

	}

	private int xExist(int x) {
		int index = -1;
		for (int i = 0; i < xRecord.size(); i++) {
			if (xRecord.get(i)[0] == x) {
				index = i;
			}
		}
		return index;
	}

	private int yExist(int y) {
		int index = -1;
		for (int i = 0; i < yRecord.size(); i++) {
			if (yRecord.get(i)[0] == y) {
				index = i;
			}
		}
		return index;
	}

	public int getAreaSize() {
		return blockArrayList.size();
	}

	public int getAreaWidth() {
		if (xRecord.size() == 0) {
			return 0;
		}
		int maxTime = xRecord.get(0)[1];
		int minTime = xRecord.get(0)[1];
		for (int i = 0; i < xRecord.size(); i++) {
			for (int j = i; j < xRecord.size() - 1; j++) {
				if (xRecord.get(j)[0] < xRecord.get(j + 1)[0]) {
					// replace
					int[] temp = xRecord.get(j + 1);
					xRecord.set(j + 1, xRecord.get(j));
					xRecord.set(j, temp);
				} else {

				}
			}
			if (xRecord.get(i)[1] < minTime) {
				minTime = xRecord.get(i)[1];
			}

			if (xRecord.get(i)[1] > maxTime) {
				maxTime = xRecord.get(i)[1];
			}

		}
		// border clip
		if (xRecord.size() > 4) {
			for (int i = 0; i < xRecord.size() / 3; i++) {
				if (xRecord.get(0)[0] < maxTime / 5) {
					xRecord.remove(0);
				}

			}
			for (int i = 0; i < xRecord.size() / 3; i++) {
				if (xRecord.get(xRecord.size() - 1)[0] < maxTime / 5) {
					xRecord.remove(xRecord.size() - 1);
				}

			}

		}

		return xRecord.size();
	}

	public int getAreaHeight() {

		if (yRecord.size() == 0) {
			return 0;
		}

		int maxTime = yRecord.get(0)[1];
		int minTime = yRecord.get(0)[1];
		for (int i = 0; i < yRecord.size(); i++) {
			for (int j = i; j < yRecord.size() - 1; j++) {
				if (yRecord.get(j)[0] < yRecord.get(j + 1)[0]) {
					// replace
					int[] temp = yRecord.get(j + 1);
					yRecord.set(j + 1, yRecord.get(j));
					yRecord.set(j, temp);
				} else {

				}
			}
			if (yRecord.get(i)[1] < minTime) {
				minTime = yRecord.get(i)[1];
			}

			if (yRecord.get(i)[1] > maxTime) {
				maxTime = yRecord.get(i)[1];
			}

		}
		// border clip
		if (yRecord.size() > 4) {
			for (int i = 0; i < yRecord.size() / 3; i++) {
				if (yRecord.get(0)[0] < maxTime / 5) {
					yRecord.remove(0);
				}

			}
			for (int i = 0; i < yRecord.size() / 3; i++) {
				if (yRecord.get(yRecord.size() - 1)[0] < maxTime / 5) {
					yRecord.remove(yRecord.size() - 1);
				}

			}

		}

		return yRecord.size();

	}

}
