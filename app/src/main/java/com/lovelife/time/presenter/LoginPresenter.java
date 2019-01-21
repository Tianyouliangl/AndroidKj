package com.lovelife.time.presenter;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lovelife.time.app.DHApplication;
import com.lovelife.time.base.BasePresenter;
import com.lovelife.time.contract.LoginContract;
import com.lovelife.time.bean.UserInfoBean;
import com.lovelife.time.utlis.LoadingUtil;
import com.lovelife.time.utlis.SPUtils;
import com.lovelife.time.weight.SPKey;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {


    private Tencent tencent;
    private String TAG = "qq";
    private Context mContext;
    private UserInfo mUserInfo;
    private BaseUiListener uiListener;

    @Inject
    LoginPresenter() {

    }


//    @Override
//    public void getData() {
//        RetrofitManager.create(ApiService.class)
//                .getCarData(1)
//                .compose(RxSchedulers.<BaseCallModel<HomeCarBean>>applySchedulers())
//                .compose(mView.<BaseCallModel<HomeCarBean>>bindToLife())
//                .subscribe(new Consumer<BaseCallModel<HomeCarBean>>() {
//                    @Override
//                    public void accept(BaseCallModel<HomeCarBean> homeCarBeanBaseCallModel) throws Exception {
//                             mView.onSuccess();
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                             mView.onError();
//
//                    }
//                });
//    }

    @Override
    public void getData(Context context, String p) {
        this.mContext = context;
        tencent = Tencent.createInstance("1105602574", context.getApplicationContext());
        if (TextUtils.isEmpty(p)) return;
        uiListener = new BaseUiListener();
        tencent.login((Activity) context, p, uiListener);
    }

    @Override
    public BaseUiListener getListener() {
        return uiListener;
    }

    public class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Log.e(TAG, "response:" + response + "----授权成功!");
            final JSONObject obj = (JSONObject) response;
            try {
                final String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                tencent.setOpenId(openID);
                tencent.setAccessToken(accessToken, expires);
                QQToken qqToken = tencent.getQQToken();
                mUserInfo = new UserInfo(mContext.getApplicationContext(), qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG, "登录成功" + response.toString());
                        Toast.makeText(mContext, "登录成功!", Toast.LENGTH_SHORT).show();
                        LoadingUtil.hideLoading();
                        mView.onSuccess((JSONObject) response,openID);
                    }

                    @Override
                    public void onError(UiError uiError) {
                        mView.onError();
                        Log.e(TAG, "登录失败" + uiError.toString());
                        Toast.makeText(mContext, "登录失败!", Toast.LENGTH_SHORT).show();
                        LoadingUtil.hideLoading();
                    }

                    @Override
                    public void onCancel() {
                        mView.onCancel();
                        Log.e(TAG, "登录取消");
                        Toast.makeText(mContext, "登录取消!", Toast.LENGTH_SHORT).show();
                        LoadingUtil.hideLoading();

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "登录失败!---异常" + e.toString());
            }
        }

        @Override
        public void onError(UiError uiError) {
            LoadingUtil.hideLoading();
            Log.e(TAG, "登录失败" + uiError.toString());
        }

        @Override
        public void onCancel() {
            LoadingUtil.hideLoading();
            Log.e(TAG, "登录取消");
        }
    }
}

