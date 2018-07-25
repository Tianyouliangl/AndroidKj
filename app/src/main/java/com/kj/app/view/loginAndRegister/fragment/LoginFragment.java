package com.kj.app.view.loginAndRegister.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kj.app.R;
import com.kj.app.base.BaseFragment;
import com.kj.app.bean.HomeCarBean;
import com.kj.app.bean.RegisterAndLoginBean;
import com.kj.app.contract.LoginContract;
import com.kj.app.presenter.LoginPresenter;
import com.kj.app.view.home.MainActivity;
import com.kj.app.view.loginAndRegister.LoginAndRegisterActivity;


public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginContract.View, View.OnClickListener {

    private TextView showreg;
    private Button btn_login;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initInjector() {
      mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        showreg = view.findViewById(R.id.showreg);
        btn_login = view.findViewById(R.id.btn_login);
    }
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        showreg.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onSuccess(HomeCarBean msg) {

        Log.i("SSSSS", "onSuccess: "+msg);
        Toast.makeText(getActivity(),msg.isHasAnyArchives()+"",Toast.LENGTH_SHORT).show();
        if (!"".equals(msg) && msg!=null){

//            Intent intent=new Intent(getActivity(), MainActivity.class);
//            getActivity().startActivity(intent);
//            getActivity().finish();
        }

    }

    @Override
    public void onError(String a) {
        Log.i("SSSSS", "onError: "+a);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showreg:
                 LoginAndRegisterActivity.setViewPagerItem(1);
                 break;
            case R.id.btn_login:
                mPresenter.getData();
                break;
                    default:
                        break;
                }
    }
}
