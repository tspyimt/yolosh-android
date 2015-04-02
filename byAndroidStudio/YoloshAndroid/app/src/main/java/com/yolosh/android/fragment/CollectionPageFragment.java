package com.yolosh.android.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.software.shell.fab.ActionButton;
import com.yolosh.android.R;
import com.yolosh.android.adapter.CollectionGridAdapter;
import com.yolosh.android.model.CollectionObject;
import com.yolosh.android.myview.MyGridView;
import com.yolosh.android.myview.OnDetectScrollListener;

public class CollectionPageFragment extends Fragment {

    private static final String KEY_CONTENT = "TestFragment:Content";

    private List<CollectionObject> dataList;

    private MyGridView gridView;
    private ActionButton fab;

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
        View rootView = inflater.inflate(R.layout.fragment_page_main_collection,
                container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        gridView = (MyGridView) view.findViewById(R.id.id_gridview_Collection);
        fab = (ActionButton) view.findViewById(R.id.id_fab_collection);

        fab.setShowAnimation(ActionButton.Animations.ROLL_FROM_DOWN);
        fab.setHideAnimation(ActionButton.Animations.ROLL_TO_DOWN);

        fab.setButtonColor(getResources().getColor(R.color.fab_material_cyan_500));
        fab.setButtonColorPressed(getResources().getColor(R.color.fab_material_cyan_900));

        dataList = new ArrayList<CollectionObject>();
        getdata();

        CollectionGridAdapter adapter = new CollectionGridAdapter(
                getActivity(), dataList);
        gridView.setAdapter(adapter);

        gridView.setOnDetectScrollListener(new OnDetectScrollListener() {
            @Override
            public void onUpScrolling() {
                if (fab.isHidden()) {
                    fab.show();
                }
            }

            @Override
            public void onDownScrolling() {
                if (fab.isShown()) {
                    fab.hide();
                }
            }
        });
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
        dataList.add(object1);
        dataList.add(object1);
        dataList.add(object1);
        dataList.add(object1);
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
