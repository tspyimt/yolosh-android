package com.yolosh.android.fragment.mainfragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yolosh.android.R;
import com.yolosh.android.adapter.StepAdapter;
import com.yolosh.android.util.UriValues;

import java.util.ArrayList;
import java.util.List;

public class StepFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private List<String> dataList;
    private ListView listView;
    private StepAdapter adapter;

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static StepFragment newInstance(int sectionNumber) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public StepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step,
                container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataList = new ArrayList<>();
        initData();

        listView = (ListView) view.findViewById(R.id.id_listview_step);
        adapter = new StepAdapter(dataList, getActivity());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction(UriValues.ACTION_DETAIL_ACTIVITY);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(getArguments().getInt(
//                ARG_SECTION_NUMBER));
    }

    void initData() {
        dataList.add("Làm quen với Node.js");
        dataList.add("Hello word");
        dataList.add("Cách viết asyn - callback");
        dataList.add("Viết 1 Website đơn giản");
        dataList.add("Yolosh Project");
        dataList.add("Java cơ bản");
        dataList.add("Android cookbook");
        dataList.add("Ios căn bản");

        dataList.add("Làm quen với Node.js");
        dataList.add("Hello word");
        dataList.add("Cách viết asyn - callback");
        dataList.add("Viết 1 Website đơn giản");
        dataList.add("Yolosh Project");
        dataList.add("Java cơ bản");
        dataList.add("Android cookbook");
        dataList.add("Ios căn bản");
    }
}