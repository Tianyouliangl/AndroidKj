package com.kj.app.contract;


import com.kj.app.base.BaseContract;
import com.kj.app.bean.RegisterAndLoginBean;

public interface RegisterContract {
    interface View extends BaseContract.BaseView {

        void onSuccess(RegisterAndLoginBean param);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getData();

    }
}
