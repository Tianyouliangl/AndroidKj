package com.kj.app.view.activity;

import android.view.View;
import android.widget.Button;

import com.kj.app.R;
import com.kj.app.base.BaseActivity;
import com.kj.app.utlis.LoadingUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login){
            LoadingUtil.showLoading(this);
        }
    }
}
