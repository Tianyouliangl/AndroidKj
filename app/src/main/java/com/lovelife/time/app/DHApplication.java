package com.lovelife.time.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.lovelife.time.component_dagger.ApplicationComponent;
import com.lovelife.time.component_dagger.DaggerApplicationComponent;
import com.lovelife.time.module_dagger.ApplicationModule;
import com.lovelife.time.sqdb.SQLiteDao;

import java.util.ArrayList;
import java.util.List;

//import cn.bmob.v3.Bmob;

/**
 * Created by fucp on 2018-4-10.
 * Description :
 */

public class DHApplication extends Application {

    private static DHApplication mApp;
    private static SQLiteDao dao;
    private ApplicationComponent mApplicationComponent;
    private static DHApplication mInstance;
    private static Context context;
    private static List<Activity> mActivities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mInstance = this;
        initApplicationComponent();
        Utils.init(this);
    }

    /**
     * 初始化ApplicationComponent
     */
    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    public static DHApplication getInstance() {
        return mInstance;
    }
    public static synchronized SQLiteDao getDao() {
        if (dao == null) {
            synchronized (DHApplication.class) {
                if (dao == null) {
                    dao = new SQLiteDao(context);
                }
            }
        }
        return dao;
    }

    /**
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }


    public static void logout() {
        Activity activity = getTopActivity();

        if (activity != null) {

          //  Intent intent = new Intent(activity, LoginContainerActivity.class);
         //   activity.startActivity(intent);

            DHApplication.clearActivities();
        }
    }
    public static void clearActivities() {
        for (Activity activity : mApp.mActivities) {
            if (activity != null) {
                activity.finish();
            }
        }
        mApp.mActivities.clear();
    }

    public static void exit() {
        clearActivities();
        System.exit(0);
    }
    public static Activity getTopActivity() {
        if (mApp != null && mApp.mActivities.size() > 0) {
            return mApp.mActivities.get(mApp.mActivities.size() - 1);
        } else {
            return null;
        }
    }

}
