package com.yolosh.android.adapter;

import java.util.ArrayList;
import java.util.List;

import model.CollectionObject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yolosh.android.R;

/**
 * Adapter for GridView in collection page
 **/
public class CollectionGridAdapter extends BaseAdapter {
	private Context mContext;
	private List<CollectionObject> dataList = new ArrayList<CollectionObject>();

	public CollectionGridAdapter(Context c, List<CollectionObject> dataset) {
		mContext = c;
		dataList = dataset;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View grid;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			grid = new View(mContext);
			grid = inflater.inflate(R.layout.layout_girditem_collection, null);

			ImageView imageView = (ImageView) grid
					.findViewById(R.id.imageView1);
			TextView textView1 = (TextView) grid.findViewById(R.id.textView1);
			TextView textView2 = (TextView) grid.findViewById(R.id.textView2);

			imageView.setImageResource(dataList.get(position).getImageId());
			textView1.setText(dataList.get(position).getLikeNumber());
			textView2.setText(dataList.get(position).getViewNumber());
		} else {
			grid = (View) convertView;
		}
		return grid;
	}
}