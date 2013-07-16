package com.suan.util;

import java.util.ArrayList;


public class Line {
	public ArrayList<int []> content = new ArrayList<int[]>();
	private int amount;
	public Line(int amount){
		this.amount = amount;
	}
	
	public int [] getTop(){
		if(content.size()>0){
			return content.get(content.size()-1);
		}
		return null;
	}
	
	public void push(int [] a){
		if(content.size()==amount){
			for(int i = content.size()-1;i>0;i--){
				content.set(i-1, content.get(i));
				content.set(content.size()-1, a);
			}
			
		}else{
			content.add(a);
		}
	}

}
