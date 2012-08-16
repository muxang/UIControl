package com.chairs.example;

import com.chairs.frame.interfc.Idata;

public class MyData implements Idata {
	Integer ii ;
	public MyData(int a){
		ii = a;
	}
	@Override
	public void setObj(Object obj) {
		ii = (Integer) obj;
	}
	@Override
	public Object getObj() {
		// TODO Auto-generated method stub
		return ii;
	}
	
}
