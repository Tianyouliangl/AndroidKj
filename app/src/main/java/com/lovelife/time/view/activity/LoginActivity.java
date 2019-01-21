package com.lovelife.time.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.lovelife.time.R;
import com.lovelife.time.app.DHApplication;
import com.lovelife.time.base.BaseActivity;
import com.lovelife.time.bean.UserInfoBean;
import com.lovelife.time.contract.LoginContract;
import com.lovelife.time.presenter.LoginPresenter;
import com.lovelife.time.utlis.SPUtils;
import com.lovelife.time.weight.SPKey;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;

import org.json.JSONObject;

public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener,LoginContract.View {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login){
            mPresenter.getData(this,"all");
        }
    }

    @Override
    public void onSuccess(JSONObject obj,String openID) {
        Gson gson = new Gson();
        final UserInfoBean bean = gson.fromJson(obj.toString(), UserInfoBean.class);
        DHApplication.getDao().insetUserInfo(bean);
        SPUtils.getInstance(this).put(SPKey.USER_ID,openID);
        SPUtils.getInstance(this).put(SPKey.USER_INFO,obj.toString());
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void onError() {

    }

    @Override
    public void onCancel() {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mPresenter.getListener());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
