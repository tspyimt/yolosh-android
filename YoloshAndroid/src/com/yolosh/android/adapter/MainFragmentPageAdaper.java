package com.yolosh.android.adapter;

import com.yolosh.android.fragment.CollectionPageFragment;
import com.yolosh.android.fragment.ExplorerPageFragment;
import com.yolosh.android.fragment.TimelinePageFragment;
import com.yolosh.android.util.ConstantValues;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainFragmentPageAdaper extends FragmentPagerAdapter {
	public MainFragmentPageAdaper(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return ExplorerPageFragment
					.newInstance(ConstantValues.TITLE_FRAGMENT_MAIN[position
							% ConstantValues.TITLE_FRAGMENT_MAIN.length]);
		case 1:
			return CollectionPageFragment
					.newInstance(ConstantValues.TITLE_FRAGMENT_MAIN[position
							% ConstantValues.TITLE_FRAGMENT_MAIN.length]);
		case 2:
			return TimelinePageFragment
					.newInstance(ConstantValues.TITLE_FRAGMENT_MAIN[position
							% ConstantValues.TITLE_FRAGMENT_MAIN.length]);
		}
		return CollectionPageFragment
				.newInstance(ConstantValues.TITLE_FRAGMENT_MAIN[position
						% ConstantValues.TITLE_FRAGMENT_MAIN.length]);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return ConstantValues.TITLE_FRAGMENT_MAIN[position
				% ConstantValues.TITLE_FRAGMENT_MAIN.length];
	}

	@Override
	public int getCount() {
		return ConstantValues.TITLE_FRAGMENT_MAIN.length;
	}

}
