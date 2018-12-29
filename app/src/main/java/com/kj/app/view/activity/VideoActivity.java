package com.kj.app.view.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kj.app.R;
import com.kj.app.base.BaseActivity;
import com.kj.app.utlis.StatusBarUtil;

import butterknife.BindView;

public class VideoActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.bar_iv_back)
    ImageView m_back;
    @BindView(R.id.bar_tv_title)
    TextView m_title;
    LinearLayout m_bar_bg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        StatusBarUtil.setWindowStatusBarColor(this,R.color.colorAccent);
        m_bar_bg=findViewById(R.id.bar);
        m_back.setOnClickListener(this);
        m_title.setText("本地视频");
        m_bar_bg.setBackgroundColor(Color.rgb(41,127,202));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bar_iv_back){
            this.finish();
        }
    }
}
