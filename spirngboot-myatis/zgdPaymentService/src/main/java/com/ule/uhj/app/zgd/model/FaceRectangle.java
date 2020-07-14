package com.ule.uhj.app.zgd.model;

import java.io.Serializable;

public class FaceRectangle implements Serializable {
	
	private static final long serialVersionUID = -2656049481667364613L;
	
	private int top;
	private int left;
	private int width;
	private int height;
	
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	@Override
	public String toString() {
		return "top:"+this.top+"\r\n"+"left:"+this.left+"\r\n"+"width:"+this.width+"\r\n"+"height:"+this.height;
	}
}
