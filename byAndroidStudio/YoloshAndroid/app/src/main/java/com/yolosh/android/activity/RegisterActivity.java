package com.yolosh.android.activity;

import com.yolosh.android.R;
import com.yolosh.android.R.id;
import com.yolosh.android.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class RegisterActivity extends Activity {

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_register);

        initView();
    }

    private void initView() {
        btnRegister = (Button) findViewById(id.id_btn_register);

    }
}
