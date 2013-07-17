package com.suan.util.camera;

import java.util.ArrayList;

public class Tracker {
	private AreaItem targetAreaItem;
	private int[][] gridColor, lastGridColor;

	private ArrayList<AreaItem> possibleList = new ArrayList<AreaItem>();
	FrameObjectRow nowFrameObjectRow, lastFrameObjectRow;

	public void track(AreaItem targetAreaItem, int[][] nowGridColor,
			int[][] lastGridColor) {
		this.targetAreaItem = targetAreaItem;
		this.gridColor = nowGridColor;
		this.lastGridColor = lastGridColor;
		possibleList = new ArrayList<AreaItem>();
		nowFrameObjectRow = new FrameObjectRow();
		lastFrameObjectRow = new FrameObjectRow();

		findStillArea();
	}

	private void findStillArea() {

		for (int i = 0; i < BitmapOperator.xAmount; i += 4) {
			for (int j = 12; j < BitmapOperator.yAmount; j += 2) {

				nowFrameObjectRow.addObject(i, j, gridColor, 1000);
				lastFrameObjectRow.addObject(i, j, lastGridColor, 1000);
			}

		}

		for (int i = 0; i < nowFrameObjectRow.objectList.size(); i++) {
			AreaItem nowAreaItem = nowFrameObjectRow.objectList.get(i);

			for (int j = 0; j < lastFrameObjectRow.objectList.size(); j++) {
				AreaItem lastAreaItem = lastFrameObjectRow.objectList.get(j);
				// 遍历所有区域 找到可能区域
				if (BitmapOperator.colorSimilar(nowAreaItem.getColor(),
						lastAreaItem.getColor(), 2000)) {
					if (((double) nowAreaItem.getAreaSize() / (double) lastAreaItem
							.getAreaSize()) < 3
							&& ((double) nowAreaItem.getAreaSize() / (double) lastAreaItem
									.getAreaSize()) > 0.3)
						//size the same
						possibleList.add(nowAreaItem);

				}

			}

		}
	}

}
