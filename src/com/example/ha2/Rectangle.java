package com.example.ha2;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Rectangle {
	public int left;
	public int right;
	public int top;
	public int bottom;
	
	public Rectangle(int left, int right, int top, int bottom){
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}
	
	public int width(){
		return right - left;
	}
	
	public int height(){
		return bottom - top;
	}

	public void draw(Canvas canvas, Paint p) {
		canvas.drawRect(left, top, right, bottom, p);	
	}

}
