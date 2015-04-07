package com.yolosh.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CollectionGroup implements Serializable {
    private long id;
    private int imageId;
    private String groupName;
    private List<CollectionItem> itemList = new ArrayList<CollectionItem>();

    public CollectionGroup(String groupName, int imageId, long id) {
        super();
        this.id = id;
        this.imageId = imageId;
        this.groupName = groupName;
    }

    public List<CollectionItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<CollectionItem> itemList) {
        this.itemList = itemList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}