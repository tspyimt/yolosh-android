package com.yolosh.android.fragment.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yolosh.android.R;
import com.yolosh.android.util.MessageKeyValues;

// Fragment hold image welcome when app run first
public class WelcomePageFragment extends Fragment {

	public static final WelcomePageFragment newInstance(int message) {
		WelcomePageFragment f = new WelcomePageFragment();
		Bundle bdl = new Bundle();
		bdl.putInt(MessageKeyValues.EXTRA_IMAGE, message);
		f.setArguments(bdl);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int message = getArguments().getInt(MessageKeyValues.EXTRA_IMAGE);
		View v = inflater.inflate(R.layout.fragment_page_welcome, container, false);
		ImageView imageView = (ImageView) v
				.findViewById(R.id.id_welcome_imageview);
		// Log.d("LOG", "set image");
		imageView.setImageResource(message);
		return v;
	}
}
