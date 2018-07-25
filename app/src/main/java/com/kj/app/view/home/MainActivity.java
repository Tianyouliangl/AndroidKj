package com.kj.app.view.home;


import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.RelativeLayout;

import com.kj.app.R;
import com.kj.app.base.BaseActivity;
import com.kj.app.bean.HomeCarBean;
import com.kj.app.bean.RegisterAndLoginBean;
import com.kj.app.presenter.RegisterPresenter;
import com.kj.app.contract.LoginContract;
import com.kj.app.view.home.fragment.HomePageFragment;
import com.kj.app.view.home.fragment.LiveFragment;
import com.kj.app.view.home.fragment.MineFragment;
import com.hjm.bottomtabbar.BottomTabBar;
import com.hjm.bottomtabbar.custom.CustomFragmentTabHost;

public class MainActivity extends BaseActivity<RegisterPresenter> implements LoginContract.View {

    private String TAG = "MainActivity";
    private static DrawerLayout mDrawerLayout;
    private static RelativeLayout main_menu;
    private static BottomTabBar bottomTabBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {
    // mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        bottomTabBar = findViewById(R.id.bottomTabBar);
        mDrawerLayout = findViewById(R.id.main_drawerLayout);
        main_menu = findViewById(R.id.main_menu);
        initData(bottomTabBar);
    }

    private void initData(BottomTabBar bottomTabBar) {
        bottomTabBar.init(getSupportFragmentManager())    //bottomTabBar是找到的控件的名字
                .setImgSize(40,40)
                .setFontSize(12)//字体的大小
                .setTabPadding(4,6,8)
                .setChangeColor(Color.BLUE,Color.DKGRAY)
                //导航栏文字描述，图片id，fragment
                .addTabItem("首页", R.mipmap.ic_launcher_round,HomePageFragment.class)
                .addTabItem("直播", R.mipmap.ic_launcher_round,LiveFragment.class)
                .addTabItem("我的",R.mipmap.ic_launcher_round, MineFragment.class)
                .isShowDivider(false);

    }

    public static int getBottomTabBarItemId(){
        int id = bottomTabBar.getId();
        return id;
    }
    @Override
    public void onSuccess(HomeCarBean msg) {

    }

    @Override
    public void onError(String a) {

    }

    public static void openMenu(){
        mDrawerLayout.openDrawer(main_menu);
    }
}
