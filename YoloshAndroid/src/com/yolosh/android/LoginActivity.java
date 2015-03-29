package com.yolosh.android;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.yolosh.android.adapter.WelcomePageAdapter;
import com.yolosh.android.fragment.LoginFragment;
import com.yolosh.android.fragment.WelcomePageFragment;

public class LoginActivity extends FragmentActivity {
	WelcomePageAdapter pageAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		List<Fragment> fragments = getFragments();
		pageAdapter = new WelcomePageAdapter(getSupportFragmentManager(),
				fragments);
		ViewPager pager = (ViewPager) findViewById(R.id.id_welcome_viewpager);
		pager.setAdapter(pageAdapter);
	}

	// init list pages welcome screen
	private List<Fragment> getFragments() {
		List<Fragment> fList = new ArrayList<Fragment>();

		fList.add(WelcomePageFragment.newInstance(R.drawable.img_1));
		fList.add(WelcomePageFragment.newInstance(R.drawable.img_2));
		fList.add(WelcomePageFragment.newInstance(R.drawable.img_3));
		fList.add(LoginFragment.newInstance());
		return fList;
	}
}