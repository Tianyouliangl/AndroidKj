package com.lovelife.time.internet;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fucp on 2018-5-23.
 * Description :
 */

public class OkHttpManager {

    private static OkHttpClient okHttpClient;
    private static long CONNECT_TIMEOUT = 60L;
    private static long READ_TIMEOUT = 10L;
    private static long WRITE_TIMEOUT = 10L;

    /**
     * 获取OkHttp单例，线程安全.
     *
     * @return 返回OkHttpClient单例
     */
    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpManager.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
//                            .cache(cache)
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)
//                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(addHeaderInterceptor)
                            .addInterceptor(mLoggingInterceptor)
//                            .cookieJar(new CookiesManager())
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    /**
     * 设置头
     */
    private static final Interceptor addHeaderInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder requestBuilder = originalRequest.newBuilder()
                    // Provide your custom header here
                    .header("Content-Type", "multipart/form-data")
                    //  .header("client-info", "uid=-1,mobile_model=,mobile_system=,app_version=1.0.0")
                    .method(originalRequest.method(), originalRequest.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    };

    /**
     * 日志拦截器
     */
    private static final Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            return chain.proceed(request);
        }
    };

}
