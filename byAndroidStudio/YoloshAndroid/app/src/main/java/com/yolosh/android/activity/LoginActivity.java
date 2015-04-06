package com.yolosh.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;
import com.yolosh.android.R;
import com.yolosh.android.R.drawable;
import com.yolosh.android.R.id;
import com.yolosh.android.R.layout;
import com.yolosh.android.adapter.WelcomePageAdapter;
import com.yolosh.android.fragment.login.LoginFragment;
import com.yolosh.android.fragment.login.WelcomePageFragment;

public class LoginActivity extends FragmentActivity {
    private WelcomePageAdapter pageAdapter;
    private PageIndicator mIndicator;
    private ButtonRectangle buttonSigup, buttonLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login);

        List<Fragment> fragments = getFragments();
        pageAdapter = new WelcomePageAdapter(getSupportFragmentManager(),
                fragments);
        ViewPager pager = (ViewPager) findViewById(id.id_welcome_viewpager);
        pager.setAdapter(pageAdapter);

        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);

        buttonSigup = (ButtonRectangle) findViewById(id.id_btn_Sign_up);
        buttonLogin = (ButtonRectangle) findViewById(id.id_btn_Log_in);

        buttonSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
//                LoginActivity.this.finish();
            }
        });
    }

    // init list pages welcome screen
    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(WelcomePageFragment.newInstance(drawable.img_welcome));
        fList.add(WelcomePageFragment.newInstance(drawable.img_2_360));
        fList.add(WelcomePageFragment.newInstance(drawable.img_3_360));
        fList.add(WelcomePageFragment.newInstance(drawable.img_4_360));
        fList.add(WelcomePageFragment.newInstance(drawable.img_5_360));
//        fList.add(LoginFragment.newInstance());
        return fList;
    }
}