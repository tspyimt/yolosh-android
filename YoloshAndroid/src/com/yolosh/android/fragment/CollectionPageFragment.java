package com.yolosh.android.fragment;

import java.util.ArrayList;
import java.util.List;

import model.CollectionObject;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.yolosh.android.R;
import com.yolosh.android.adapter.CollectionGridAdapter;

public class CollectionPageFragment extends Fragment {
	private static final String KEY_CONTENT = "TestFragment:Content";
	private List<CollectionObject> dataList;
	private GridView gridView;

	public static CollectionPageFragment newInstance(String content) {
		CollectionPageFragment fragment = new CollectionPageFragment();
		return fragment;
	}

	private String mContent = "???";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_pagemain_collection,
				container, false);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		gridView = (GridView) view.findViewById(R.id.id_gridview_Collection);
		dataList = new ArrayList<CollectionObject>();
		getdata();

		CollectionGridAdapter adapter = new CollectionGridAdapter(
				getActivity(), dataList);
		gridView.setAdapter(adapter);
	}

	void getdata() {
		CollectionObject object1 = new CollectionObject(null,
				R.drawable.img_test1, "111", "222");

		dataList.add(object1);
		dataList.add(object1);
		dataList.add(object1);
		dataList.add(object1);
		dataList.add(object1);
		dataList.add(object1);
		dataList.add(object1);
		dataList.add(object1);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}
}
