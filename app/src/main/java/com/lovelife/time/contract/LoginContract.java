package com.lovelife.time.contract;


import com.lovelife.time.base.BaseContract;
import com.lovelife.time.bean.HomeCarBean;
import com.lovelife.time.bean.RegisterAndLoginBean;

public interface LoginContract {
    interface View extends BaseContract.BaseView {

        void onSuccess();

        void onError();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getData();

    }
}
