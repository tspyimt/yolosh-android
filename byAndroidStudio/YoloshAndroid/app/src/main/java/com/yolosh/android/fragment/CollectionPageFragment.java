package com.yolosh.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.software.shell.fab.ActionButton;
import com.yolosh.android.R;
import com.yolosh.android.adapter.CollectionAdapter;
import com.yolosh.android.model.CollectionGroup;
import com.yolosh.android.model.CollectionItem;
import com.yolosh.android.util.UriValues;

import java.util.ArrayList;
import java.util.List;

public class CollectionPageFragment extends Fragment {

    private static final String KEY_CONTENT = "TestFragment:Content";

    private List<CollectionGroup> dataList;
    //    private MyGridView gridView;
    private ExpandableListView listView;
    private CollectionAdapter adapterList;
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
        dataList = new ArrayList<CollectionGroup>();
        initData();

        listView = (ExpandableListView) view.findViewById(R.id.id_collection_explistview);
        adapterList = new CollectionAdapter(dataList, getActivity());
        listView.setAdapter(adapterList);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                } else {
                    boolean animateExpansion = false;
                    parent.expandGroup(groupPosition, animateExpansion);
                }
                //telling the listView we have handled the group click, and don't want the default actions.
                return true;
            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent();
                intent.setAction(UriValues.ACTION_STEP_ACTIVITY);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                return true;
            }
        });
        // open group when open first
        listView.expandGroup(0);
        listView.expandGroup(1);

        fab = (ActionButton) view.findViewById(R.id.id_fab_collection);

        fab.setShowAnimation(ActionButton.Animations.JUMP_FROM_DOWN);
        fab.setHideAnimation(ActionButton.Animations.JUMP_TO_DOWN);

        fab.setButtonColor(getResources().getColor(R.color.fab_material_cyan_500));
        fab.setButtonColorPressed(getResources().getColor(R.color.fab_material_cyan_900));


//        CollectionGridAdapter adapter = new CollectionGridAdapter(
//                getActivity(), dataList);


    }

    void initData() {
        CollectionGroup object1 = new CollectionGroup("Bookmark Collection", R.drawable.bookmark_collection28, 1);
        CollectionGroup object2 = new CollectionGroup("My Collection", R.drawable.my_collection28, 2);

        List<CollectionItem> list1 = new ArrayList<>();

        CollectionItem item1 = new CollectionItem("2015 Goals", "27", R.drawable.collection1, 1);
        CollectionItem item2 = new CollectionItem("vBBAS (Vsoft Backend As ...", "57", R.drawable.collection2, 2);
        CollectionItem item3 = new CollectionItem("Outsourcing Object", "104", R.drawable.collection3, 3);
        CollectionItem item4 = new CollectionItem("Yolosh", "4957", R.drawable.collection4, 4);
        CollectionItem item5 = new CollectionItem("Artlock", "253", R.drawable.collection5, 5);

        list1.add(item1);
        list1.add(item2);
        list1.add(item3);
        list1.add(item4);
        list1.add(item5);
        list1.add(item1);
        list1.add(item2);
        list1.add(item3);
        list1.add(item4);
        list1.add(item5);

        object1.setItemList(list1);
        object2.setItemList(list1);

        dataList.add(object1);
        dataList.add(object2);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
//
//gridView.setAdapter(adapter);
//
//        gridView.setOnDetectScrollListener(new OnDetectScrollListener() {
//@Override
//public void onUpScrolling() {
//        if (dataList.size() > 0 && gridView.getLastVisiblePosition() <
//        dataList.size() - ConstantValues.GRIDVIEW_DELAY_ANIMATION) {
//        if (fab.isHidden()) {
//        new Handler().postDelayed(new Runnable() {
//@Override
//public void run() {
//        fab.show();
//        }
//        }, ConstantValues.FAB_SHOW_DELAY_ANIMATION);
//        }
////                    if (!((ActionBarActivity) getActivity()).getSupportActionBar().isShowing()) {
////                        new Handler().postDelayed(new Runnable() {
////                            @Override
////                            public void run() {
////                                ((ActionBarActivity) getActivity()).getSupportActionBar().show();
////                            }
////                        }, ConstantValues.ACTIONBAR_SHOW_DELAY_ANIMATION);
////                    }
//        }
//        }
//
//@Override
//public void onDownScrolling() {
//        if (fab.isShown()) {
//        new Handler().postDelayed(new Runnable() {
//@Override
//public void run() {
//        fab.hide();
//        }
//        }, ConstantValues.FAB_HIDE_DELAY_ANIMATION);
//        }
////                if (((ActionBarActivity) getActivity()).getSupportActionBar().isShowing()) {
////                    new Handler().postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
////                            ((ActionBarActivity) getActivity()).getSupportActionBar().hide();
////                        }
////                    }, ConstantValues.ACTIONBAR_HIDE_DELAY_ANIMATION);
////                }
//        }
//        });
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//@Override
//public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        }
//        });