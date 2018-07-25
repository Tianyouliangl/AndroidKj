package com.kj.app.presenter;



import com.kj.app.base.BasePresenter;
import com.kj.app.contract.RegisterContract;

import javax.inject.Inject;

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {


    @Inject
    RegisterPresenter(){

           }

    @Override
    public void getData() {

    }
}
