package com.yolosh.android.model;

public class CollectionObject {
	private String urlImage;
	private int imageId;
	private String likeNumber;
	private String ViewNumber;

	public CollectionObject(String urlImage, int imageId, String likeNumber,
			String viewNumber) {
		super();
		this.urlImage = urlImage;
		this.imageId = imageId;
		this.likeNumber = likeNumber;
		this.ViewNumber = viewNumber;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(String likeNumber) {
		this.likeNumber = likeNumber;
	}

	public String getViewNumber() {
		return ViewNumber;
	}

	public void setViewNumber(String viewNumber) {
		this.ViewNumber = viewNumber;
	}

}
