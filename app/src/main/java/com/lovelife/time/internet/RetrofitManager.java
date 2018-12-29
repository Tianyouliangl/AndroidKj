package com.lovelife.time.internet;

import android.support.annotation.NonNull;
import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;
import com.lovelife.time.app.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static long CONNECT_TIMEOUT = 60L;
    private static long READ_TIMEOUT = 10L;
    private static long WRITE_TIMEOUT = 10L;
    //设缓存有效期为1天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    public static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=10";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    private static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";
    private static volatile OkHttpClient mOkHttpClient;

    /**
     * 获取OkHttpClient实例
     */
    private static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
//                Cache cache = new Cache(new File(DCApplication.getAppContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
                if (mOkHttpClient == null) {
                    //OkHttp3拦截器
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            Log.i("xxx",message);
                        }
                    });

                    //Okhttp3的拦截器日志分类 4种
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    mOkHttpClient = new OkHttpClient.Builder()
                            .addInterceptor(httpLoggingInterceptor)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                            .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzYxMTExMDAwMCIsInJvbGUiOiJST0xFX0NBUk9XTkVSIiwiZXhwIjoxNTMzMTc0ODU5LCJjcmVhdGVkIjoxNTMyMzEwODU5ODkzfQ.5zzCNXyTQCztMQ97X0-O6tsfYp6zv0aLxFFtHt-6Wcch81SOOTjRXfJuboYm3uu2Vd2roO2TLrlKEkoWBqbY1Q")
                                .build();
                        return chain.proceed(request);
                    }
                }).build();
                }
            }
        }
        return mOkHttpClient;
    }

    /**
     * 获取Service
     */
    public static <T> T create(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        return retrofit.create(clazz);
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
//                    .header("AppType", "TPOS")
//                    .header("Content-Type", "application/json")
                  //  .header("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzYxMTExMDAwMCIsInJvbGUiOiJST0xFX0NBUk9XTkVSIiwiZXhwIjoxNTMzMTc0ODU5LCJjcmVhdGVkIjoxNTMyMzEwODU5ODkzfQ.5zzCNXyTQCztMQ97X0-O6tsfYp6zv0aLxFFtHt-6Wcch81SOOTjRXfJuboYm3uu2Vd2roO2TLrlKEkoWBqbY1Q")
//                    .header("client-info", "uid=-1,mobile_model=,mobile_system=,app_version=1.0.0")
                    .method(originalRequest.method(), originalRequest.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    };


    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                //有网的时候读接口上的@Headers里的配置，可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
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
