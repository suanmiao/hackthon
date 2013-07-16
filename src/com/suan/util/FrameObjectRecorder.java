package com.suan.util;


public class FrameObjectRecorder {
	private FrameObjectRow frameObjectRow;
	private int[][] gridColor;
	public static int footFrame = 4;
	private long bornTime;

	public FrameObjectRecorder(int[][] gridColor) {
		frameObjectRow = new FrameObjectRow();
		this.gridColor = gridColor;
		bornTime = System.currentTimeMillis();
		init();

	}

	private void init() {
		if (gridColor != null) {

			for(int i = 0;i<gridColor.length;i+=footFrame){
				frameObjectRow.addObject(i, 1, gridColor);
				frameObjectRow.addObject(i, 10, gridColor);
				frameObjectRow.addObject(i, 17, gridColor);
				
			}
			
//			for (int i = 0; i < gridColor.length; i += footFrame) {
//				for (int j = 0; j < gridColor[0].length; j += footFrame) {
//					AreaItem nowAreaItem = new AreaItem(i, j, gridColor);
//					if (nowAreaItem.getAreaSize() >= minAreaSize) {
//						frameObjectRow.addObject(i,j,gridColor);
//
//					}
//				}
//			}
		}

	}

	public long getFrameBornTime() {
		return bornTime;

	}

	public FrameObjectRow getFrameObjectRow() {
		return frameObjectRow;
	}
}
