package com.lovelife.time.contract;


import android.content.Context;

import com.lovelife.time.base.BaseContract;
import com.lovelife.time.bean.HomeCarBean;
import com.lovelife.time.bean.RegisterAndLoginBean;
import com.lovelife.time.presenter.LoginPresenter;

import org.json.JSONObject;

public interface LoginContract {
    interface View extends BaseContract.BaseView {

        void onSuccess(JSONObject obj,String open_id);

        void onError();

        void onCancel();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getData(Context context,String p);

        LoginPresenter.BaseUiListener getListener();

    }
}
