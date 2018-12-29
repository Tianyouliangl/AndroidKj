package com.lovelife.time.presenter;


import com.lovelife.time.base.BasePresenter;
import com.lovelife.time.bean.BaseCallModel;
import com.lovelife.time.bean.HomeCarBean;
import com.lovelife.time.contract.LoginContract;
import com.lovelife.time.internet.ApiService;
import com.lovelife.time.internet.RetrofitManager;
import com.lovelife.time.utlis.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {


    @Inject
    LoginPresenter(){

    }


    @Override
    public void getData() {
        RetrofitManager.create(ApiService.class)
                .getCarData(1)
                .compose(RxSchedulers.<BaseCallModel<HomeCarBean>>applySchedulers())
                .compose(mView.<BaseCallModel<HomeCarBean>>bindToLife())
                .subscribe(new Consumer<BaseCallModel<HomeCarBean>>() {
                    @Override
                    public void accept(BaseCallModel<HomeCarBean> homeCarBeanBaseCallModel) throws Exception {
                             mView.onSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                             mView.onError();

                    }
                });



    }
}
