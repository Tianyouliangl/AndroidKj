package com.lovelife.time.internet;

import android.app.Activity;
import android.net.ParseException;

import com.lovelife.time.R;
import com.lovelife.time.app.DHApplication;
import com.lovelife.time.base.BaseActivity;
import com.lovelife.time.bean.BaseCallModel;
import com.google.gson.JsonParseException;
import com.trycatch.mysnackbar.Prompt;
import com.trycatch.mysnackbar.TSnackbar;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class HttpCallBack<T> extends BaseObserver<BaseCallModel<T>> {
    protected HttpCallBack() {

    }

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    private static final int LOGIN_TOKEN_ERROR = 1201;
    private static final int TOKEN_EXPIRE = 1202;

    @Override
    public void onComplete() {
        super.onComplete();
        hideLoading();
    }

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        showLoading();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        hideLoading();
        Throwable throwable = e;
        //获取最根源的异常
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case LOGIN_TOKEN_ERROR:
                case TOKEN_EXPIRE:
                case FORBIDDEN:
                    if (getContext() != null) {
                        TSnackbar.make(getContext().getWindow().getDecorView(), "当前token已过期,请重新登录!", TSnackbar.LENGTH_SHORT)
                                .setPromptThemBackground(Prompt.ERROR)
                                .show();
                        //Toast.makeText(MyApp.getApp(),"当前token已过期,请重新登录!",Toast.LENGTH_SHORT).show();
                        //SharedPreferencesHelper.getInstance().saveData("token", "");
                        DHApplication.logout();
                    }
                    break;
                case UNAUTHORIZED:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    if (getContext() != null){
                        TSnackbar.make(getContext().getWindow().getDecorView(), R.string.server_http_error,TSnackbar.LENGTH_SHORT)
                                .setPromptThemBackground(Prompt.ERROR)
                                .show();
                    }

                    //Toast.makeText(MyApp.getApp(), R.string.server_http_error, Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (e instanceof SocketTimeoutException) {
            if (getContext() != null){
                TSnackbar.make(getContext().getWindow().getDecorView(),R.string.network_error,TSnackbar.LENGTH_SHORT)
                        .setPromptThemBackground(Prompt.ERROR)
                        .show();
            }

            //Toast.makeText(MyApp.getApp(), R.string.network_error, Toast.LENGTH_SHORT).show();
        }else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            //Toast.makeText(MyApp.getApp(), R.string.data_error, Toast.LENGTH_SHORT).show(); //均视为解析错误
            if (getContext() != null){
                TSnackbar.make(getContext().getWindow().getDecorView(),R.string.data_error,TSnackbar.LENGTH_SHORT)
                        .setPromptThemBackground(Prompt.ERROR)
                        .show();
            }

        } else if (e instanceof ConnectException) {
            if (getContext() != null){
                TSnackbar.make(getContext().getWindow().getDecorView(), R.string.server_http_error,TSnackbar.LENGTH_SHORT)
                        .setPromptThemBackground(Prompt.ERROR)
                        .show();
            }

            //Toast.makeText(MyApp.getApp(), R.string.server_http_error, Toast.LENGTH_SHORT).show();
        } else {//未知错误
            if (getContext() != null){
                TSnackbar.make(getContext().getWindow().getDecorView(),R.string.no_error,TSnackbar.LENGTH_SHORT)
                        .setPromptThemBackground(Prompt.ERROR)
                        .show();
            }

            //Toast.makeText(MyApp.getApp(),R.string.no_error+":"+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNext(BaseCallModel<T> tBaseCallModel) {
        if (tBaseCallModel.status == 0) {
            onDone(tBaseCallModel.data);
            onComplete();
            return;
        }else {
            showError(tBaseCallModel.errorInfo+"");
            Matcher matcher = Pattern.compile("[\u4e00-\u9fa5]").matcher(tBaseCallModel.errorInfo + "");
            if (matcher.find()) {
                if (getContext() != null) {
                    TSnackbar.make(getContext().getWindow().getDecorView(), tBaseCallModel.errorInfo + "", TSnackbar.LENGTH_SHORT)
                            .setPromptThemBackground(Prompt.WARNING)
                            .show();
                }
            }

        }
    }

    protected abstract void onDone(T t);

    protected abstract String LoadingMessage();

    protected BaseActivity getContext() {
        Activity activity = DHApplication.getTopActivity();
        if (activity instanceof BaseActivity) {
            return (BaseActivity) activity;
        }
        return null;
    }

    protected void showLoading() {
        BaseActivity activity = getContext();
        if (activity != null) {
            //activity.showLoading(LoadingMessage());
        }
    }

    protected void hideLoading() {
        BaseActivity activity = getContext();
        if (activity != null) {
            activity.hideLoading();
        }
    }

    protected abstract void showError(String error);
}
