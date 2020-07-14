package com.ule.uhj.app.zgd.model;

import java.io.Serializable;

public class Face implements Serializable {
	
	private static final long serialVersionUID = -2656049481667364613L;
	private String faceId;
	private FaceRectangle faceRectangle;
	public String getFaceId() {
		return faceId;
	}
	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}
	public FaceRectangle getFaceRectangle() {
		return faceRectangle;
	}
	public void setFaceRectangle(FaceRectangle faceRectangle) {
		this.faceRectangle = faceRectangle;
	}
	
	
}
