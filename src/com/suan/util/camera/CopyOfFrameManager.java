package com.suan.util.camera;

import java.util.ArrayList;

import com.suan.view.MyCameraView;

public class CopyOfFrameManager {
	private ArrayList<AreaItem[]> objectList = new ArrayList<AreaItem[]>();
	private ArrayList<AreaItem[]> changedList = new ArrayList<AreaItem[]>();

	private ArrayList<AreaItem[]> stillList = new ArrayList<AreaItem[]>();
	FrameObjectRow nowFrameObjectRow, lastFrameObjectRow;

	private static int maxMovingFootFrame = 8;
	private static int minMovingFootFrame = 2;
	private int[][] gridColor;
	private int[][] lastGridColor;

	public CopyOfFrameManager() {

	}

	public void addFrame(int[][] gridColor) {
		this.gridColor = gridColor;
		if (lastGridColor == null) {
			lastGridColor = gridColor;
		}

		nowFrameObjectRow = new FrameObjectRow();
		lastFrameObjectRow = new FrameObjectRow();
		changedList = new ArrayList<AreaItem[]>();
		stillList = new ArrayList<AreaItem[]>();
		objectList = new ArrayList<AreaItem[]>();
		findPossibleArea();
		resortChangedArea();

		lastGridColor = gridColor;
		// Log.e("moving", movingObjectList.size() + "");
	}

	private void findPossibleArea() {

		for (int i = 0; i < BitmapOperator.xAmount; i += 4) {
			for (int j = 12; j < BitmapOperator.yAmount; j +=4) {

				nowFrameObjectRow.addObject(i, j, gridColor, 1000);
				lastFrameObjectRow.addObject(i, j, lastGridColor, 1000);
			}

		}

		for (int i = 0; i < nowFrameObjectRow.objectList.size(); i++) {
			AreaItem nowAreaItem = nowFrameObjectRow.objectList.get(i);
			ArrayList<AreaItem>  possibleAreaItems = new ArrayList<AreaItem>();
			
			for (int j = 0; j < lastFrameObjectRow.objectList.size(); j++) {
				AreaItem lastAreaItem = lastFrameObjectRow.objectList.get(j);
				//遍历所有区域 找到可能区域
				if (BitmapOperator.colorSimilar(nowAreaItem.getColor(),
						lastAreaItem.getColor(), 3000)) {

					if(nowAreaItem.similarity(lastAreaItem)>0.1){
						possibleAreaItems.add(lastAreaItem);
					}
					
				}

			}
			if(possibleAreaItems.size()>0){
				int totalSize = possibleAreaItems.size()+1;
				AreaItem[] objectAreaItems = new AreaItem[totalSize];
				objectAreaItems[0] = nowAreaItem;
				for(int k = 1;k<totalSize;k++){
					objectAreaItems[k] = possibleAreaItems.get(k-1);
				}
				objectList.add(objectAreaItems);
			}
			
		}

	}

	public void resortChangedArea() {
		for (int i = 0; i < objectList.size(); i++) {
			AreaItem nowSimilarAreaItem = objectList.get(i)[0];
			for(int j = 1;j<objectList.get(i).length;j++){
				AreaItem lastAreaItem = objectList.get(i)[j];
//				double similar = nowSimilarAreaItem.similarity(lastAreaItem);

				// if (similar > 0.5) {
				// changedList.set(i, null);
				// }
				if (nowSimilarAreaItem.getFillPercent() < 0.5
						|| nowSimilarAreaItem.getAreaSize() < 20) {
					objectList.set(i, null);
				}
				
				if (!BitmapOperator.colorSimilar(nowSimilarAreaItem.getColor(),
						lastAreaItem.getColor(), BitmapOperator.colorRadius * 3)) {
					objectList.set(i, null);
				}
				
			}

		}
		// Log.e("similar", "" + similarAreaItems.size());
		ArrayList<AreaItem> clipedAreaItems = new ArrayList<AreaItem>();
		for (int x = 0; x < objectList.size(); x++) {
			if (objectList.get(x) != null) {
				clipedAreaItems.add(objectList.get(x)[0]);

			}
		}

		MyCameraView.nowAreaItems = clipedAreaItems;

	}

	private boolean newBorn(AreaItem areaItem) {
		if (lastGridColor == null) {
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

		double difPercent = (double) difference
				/ (double) areaItem.getAreaSize();

		return difPercent >= 0.6;
	}

}
