package com.suan.util.camera;

import java.util.ArrayList;

import com.suan.view.MyCameraView;

import android.R.integer;
import android.graphics.Bitmap;
import android.util.Log;

public class FrameManager {
	ArrayList<int[][]> gridArrayList = new ArrayList<int[][]>();
	private ArrayList<AreaItem> firstAreaItems = new ArrayList<AreaItem>();
	private ArrayList<AreaItem> secondAreaItems = new ArrayList<AreaItem>();
	private ArrayList<AreaItem> thirdAreaItems = new ArrayList<AreaItem>();
	public static ArrayList<long[]> captuerColorList = new ArrayList<long[]>();
	private static int capturedColor = -1;
	public static boolean still = false;
	private int sampleFrame = 7;

	public FrameManager() {

	}

	public void addFrame(int[][] gridColor) {
		gridArrayList.add(gridColor);
		remove();
		if (gridArrayList.size() > 1) {
			detectCenter();

		}
		// Log.e("moving", movingObjectList.size() + "");
	}

	private void remove() {
		if (gridArrayList.size() > sampleFrame) {
			gridArrayList.remove(0);
		}
		if (firstAreaItems.size() > sampleFrame) {
			firstAreaItems.remove(0);
		}
		if (secondAreaItems.size() > sampleFrame) {
			secondAreaItems.remove(0);
		}
		if (thirdAreaItems.size() > sampleFrame) {
			thirdAreaItems.remove(0);
		}
		if(captuerColorList.size()>0){
			if(System.currentTimeMillis()-captuerColorList.get(0)[1]>3000){
				captuerColorList.remove(0);
			}
		}

	}

	private void detectCenter() {

		double stillPercent = (double) (detectGrid(ReconitionManager.centerX,
				ReconitionManager.centerY, firstAreaItems)
				+ detectGrid(ReconitionManager.centerX + 3,
						ReconitionManager.centerY, secondAreaItems) + detectGrid(
				ReconitionManager.centerX - 3, ReconitionManager.centerY,
				thirdAreaItems)) / 3.0;

//		Log.e("perconet", stillPercent + "");
		// 0.875
		// 0.812

		if (stillPercent > 0.68) {
			whenStill();
		} else {
			if (still) {
				AreaItem firstAreaItem = new AreaItem(
						ReconitionManager.centerX, ReconitionManager.centerY,
						gridArrayList.get(gridArrayList.size() - 1), 2000);
				AreaItem secondAreaItem = new AreaItem(
						ReconitionManager.centerX + 3,
						ReconitionManager.centerY,
						gridArrayList.get(gridArrayList.size() - 1), 2000);
				AreaItem thirdAreaItem = new AreaItem(
						ReconitionManager.centerX - 3,
						ReconitionManager.centerY,
						gridArrayList.get(gridArrayList.size() - 1), 2000);
				int similarAmount = 0;
				if (BitmapOperator.colorSimilar(firstAreaItem.getColor(),
						secondAreaItem.getColor(), 2000)) {
					similarAmount++;
				}
				if (BitmapOperator.colorSimilar(secondAreaItem.getColor(),
						thirdAreaItem.getColor(), 2000)) {
					similarAmount++;
				}
				Log.e("capthre", ""+"cap"+similarAmount);

				if (similarAmount > 1) {
					captureColor(firstAreaItem);
				}
			}
			still = false;
		}

	}

	private double detectGrid(int x, int y, ArrayList<AreaItem> areaItems) {
		AreaItem nowAreaItem = new AreaItem(x, y,
				gridArrayList.get(gridArrayList.size() - 1), 2000);
		areaItems.add(nowAreaItem);
		if (areaItems.size() > sampleFrame) {
			// whether center is still
			int stillAmount = 0;

			for (int i = areaItems.size() - 1; i > 1; i--) {

				if (BitmapOperator.colorSimilar(areaItems.get(i).getColor(),
						areaItems.get(i - 1).getColor(), 3000)) {
					stillAmount++;
				}
			}
			double stillPercent = ((double) (stillAmount / (double) (areaItems
					.size())));
			return stillPercent;

		}
		return 0;
	}

	private void captureColor(AreaItem areaItem) {
		Log.e("log", "fuck"+areaItem.getFillPercent()+"|"+areaItem.getAreaSize());
//		if (areaItem.getFillPercent() > 0.4 && areaItem.getAreaSize() > 20) {

			captuerColorList.add(new long[] { areaItem.getColor() ,System.currentTimeMillis()});

//		}

	}

	private void whenStill() {
		still = true;
		// Log.e("log", "still");

	}

}
