package com.lovelife.time.view.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lovelife.time.R;
import com.lovelife.time.base.BaseActivity;
import com.lovelife.time.utlis.StatusBarUtil;

import butterknife.BindView;

public class HistoryActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.bar_iv_back)
    ImageView m_back;
    @BindView(R.id.bar_tv_title)
    TextView m_title;
    LinearLayout m_bar_bg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        StatusBarUtil.setWindowStatusBarColor(this,R.color.musicPlay);
        m_bar_bg=findViewById(R.id.bar);
        m_back.setOnClickListener(this);
        m_title.setText("历史记录");
        m_bar_bg.setBackgroundColor(Color.rgb(139,139,122));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bar_iv_back){
            this.finish();
        }
    }
}
