package com.yolosh.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.yolosh.android.R;
import com.yolosh.android.model.CollectionGroup;
import com.yolosh.android.model.CollectionItem;

import java.util.List;

public class CollectionAdapter extends BaseExpandableListAdapter {

    private List<CollectionGroup> collectionList;
    private int itemLayoutId;
    private int groupLayoutId;
    private Context ctx;

    public CollectionAdapter(List<CollectionGroup> collectionList, Context ctx) {
        this.collectionList = collectionList;
        this.ctx = ctx;
        this.itemLayoutId = R.layout.layout_collection_item;
        this.groupLayoutId = R.layout.layout_collection_group;
    }

    @Override
    public int getGroupCount() {
        return collectionList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size = collectionList.get(groupPosition).getItemList().size();
//        System.out.println("Child for group ["+groupPosition+"] is ["+size+"]");
        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return collectionList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return collectionList.get(groupPosition).getItemList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return collectionList.get(groupPosition).hashCode();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return collectionList.get(groupPosition).getItemList().get(childPosition).hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.layout_collection_group, parent, false);

            TextView groupName = (TextView) v.findViewById(R.id.groupName);

            CollectionGroup group = collectionList.get(groupPosition);

            groupName.setText(group.getGroupName());
            groupName.setCompoundDrawablesWithIntrinsicBounds(group.getImageId(), 0, 0, 0);
        }
        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.layout_collection_item, parent, false);
        }
        TextView itemName = (TextView) v.findViewById(R.id.itemName);
        TextView itemDescr = (TextView) v.findViewById(R.id.itemDescr);

        CollectionItem item = collectionList.get(groupPosition).getItemList().get(childPosition);

        itemName.setText(item.getName());
        itemName.setCompoundDrawablesWithIntrinsicBounds(item.getImgId(), 0, 0, 0);
        itemDescr.setText(item.getStartNumber());

        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
