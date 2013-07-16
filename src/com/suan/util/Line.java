package com.suan.util;

import java.util.ArrayList;


public class Line {
	
	
	public ArrayList<long []> content = new ArrayList< long []>();
	private int amount;
	public Line(int amount){
		this.amount = amount;
	}
	
	public long [] getTop(){
		if(content.size()>0){
			return content.get(content.size()-1);
		}
		return null;
	}
	
	public void resort(){
		for(int i = 0;i<content.size();i++){
			if(System.currentTimeMillis()-content.get(0)[4]>8000){
				content.remove(0);
				
			}
		}
	}
	
	public void push(long [] a){
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
