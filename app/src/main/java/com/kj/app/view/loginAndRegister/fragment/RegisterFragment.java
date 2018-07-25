package com.kj.app.view.loginAndRegister.fragment;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.kj.app.R;
import com.kj.app.base.BaseFragment;
import com.kj.app.bean.RegisterAndLoginBean;
import com.kj.app.contract.RegisterContract;
import com.kj.app.view.loginAndRegister.LoginAndRegisterActivity;

public class RegisterFragment extends BaseFragment<RegisterContract.Presenter> implements RegisterContract.View, View.OnClickListener {

    private ImageView back;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initInjector() {
  // mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        back = view.findViewById(R.id.back);
    }

    @Override
    public void onResume() {
        super.onResume();
        back.setOnClickListener(this);
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onSuccess(RegisterAndLoginBean param) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
                    case R.id.back:
                        LoginAndRegisterActivity.setViewPagerItem(0);
                        break;
                    default:
                        break;
                }
    }
    }
