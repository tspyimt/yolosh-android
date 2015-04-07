package com.yolosh.android.model;

import java.io.Serializable;

public class CollectionItem implements Serializable {

    private long id;
    private int imgId;
    private String name;
    private String startNumber;


    public CollectionItem(String name, String startNumber, int imgId, long id) {
        this.id = id;
        this.imgId = imgId;
        this.startNumber = startNumber;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(String startNumber) {
        this.startNumber = startNumber;
    }

}
