package com.kj.app.presenter;


import com.kj.app.base.BasePresenter;
import com.kj.app.bean.BaseCallModel;
import com.kj.app.bean.HomeCarBean;
import com.kj.app.contract.LoginContract;
import com.kj.app.internet.ApiService;
import com.kj.app.internet.RetrofitManager;
import com.kj.app.utlis.RxSchedulers;

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
                             mView.onSuccess(homeCarBeanBaseCallModel.data);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                             mView.onError(throwable+"");

                    }
                });



    }
}
