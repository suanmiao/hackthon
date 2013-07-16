package com.suan.util;


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
	private FrameManager frameManager;
	/*
	 * 0:color 1:size 2:height 3:width 4:record time
	 */

	public ReconitionManager(){
		frameManager = new FrameManager();
		
	}
	
	public void recoginse(int[][] gridColor) {
		this.gridColor = gridColor;
		frameManager.addFrame(gridColor);
		
		switch (nowRecogState) {
		case -1:

			/*
			 * size >90 ->save
			 */
			
//			centerAreaItem = new AreaItem(centerX, centerY, gridColor);
//			centerAreaSize = centerAreaItem.getAreaSize();

			// Log.e("centerSize",
			// centerAreaSize + "|" + centerAreaItem.getAreaHeight() + "|"
			// + centerAreaItem.getAreaWidth());

			switch (handRecogState) {
			case -1:

				
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
