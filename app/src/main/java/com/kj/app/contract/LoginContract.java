package com.kj.app.contract;


import com.kj.app.base.BaseContract;
import com.kj.app.bean.HomeCarBean;
import com.kj.app.bean.RegisterAndLoginBean;

public interface LoginContract {
    interface View extends BaseContract.BaseView {

        void onSuccess();

        void onError();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getData();

    }
}
