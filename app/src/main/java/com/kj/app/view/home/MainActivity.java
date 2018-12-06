package com.kj.app.view.home;



import com.kj.app.R;
import com.kj.app.base.BaseActivity;
import com.kj.app.bean.HomeCarBean;
import com.kj.app.presenter.RegisterPresenter;
import com.kj.app.contract.LoginContract;
import com.hjm.bottomtabbar.BottomTabBar;

public class MainActivity extends BaseActivity<RegisterPresenter> implements LoginContract.View {




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

    }

    @Override
    public void onSuccess(HomeCarBean msg) {

    }

    @Override
    public void onError(String a) {

    }

}
