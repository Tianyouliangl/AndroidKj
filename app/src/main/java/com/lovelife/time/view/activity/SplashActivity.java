package com.lovelife.time.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.lovelife.time.R;
import com.lovelife.time.app.DHApplication;
import com.lovelife.time.base.BaseActivity;
import com.lovelife.time.utlis.SPUtils;
import com.lovelife.time.weight.SPKey;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SplashActivity extends BaseActivity {

    private ImageView iv_gif;
    private String[] permissions= new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                intentActivity();
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        iv_gif = findViewById(R.id.iv_gif);
        iv_gif.setScaleType(ImageView.ScaleType.FIT_XY);
        goLogin();
    }

    private void goLogin() {
        Glide.with(this).load(R.mipmap.gif)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<Integer, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception arg0, Integer arg1, Target<GlideDrawable> arg2, boolean arg3) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Integer model,
                                                   Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // 计算动画时长
                        GifDrawable drawable = (GifDrawable) resource;
                        GifDecoder decoder = drawable.getDecoder();
                        int duration = 0;
                        for (int i = 0; i < drawable.getFrameCount(); i++) {
                            duration += decoder.getDelay(i);
                        }
                        handler.sendEmptyMessageDelayed(1, duration);
                        return false;
                    }
                }) //仅仅加载一次gif动画
                .into(new GlideDrawableImageViewTarget(iv_gif, 1));
    }

    private void intentActivity() {
        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                Intent mIntent ;
                boolean b = checkFloatPermission(SplashActivity.this);
                if (!b){
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent,10);
                }else {
                    String s = SPUtils.getInstance(SplashActivity.this).getString(SPKey.USER_ID);
                    if (TextUtils.isEmpty(s)){
                        mIntent = new Intent(SplashActivity.this,LoginActivity.class);
                    }else {
                        mIntent = new Intent(SplashActivity.this,MainActivity.class);
                    }
                    startActivity(mIntent);
                    DHApplication.getDao().createTab();
                    finish();
                    overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                }


            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                Toast.makeText(SplashActivity.this,"请设置权限!",Toast.LENGTH_SHORT).show();
            }
        },permissions,false,null);
    }

    public static boolean checkFloatPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return true;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                Class cls = Class.forName("android.content.Context");
                Field declaredField = cls.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (!(obj instanceof String)) {
                    return false;
                }
                String str2 = (String) obj;
                obj = cls.getMethod("getSystemService", String.class).invoke(context, str2);
                cls = Class.forName("android.app.AppOpsManager");
                Field declaredField2 = cls.getDeclaredField("MODE_ALLOWED");
                declaredField2.setAccessible(true);
                Method checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
                int result = (Integer) checkOp.invoke(obj, 24, Binder.getCallingUid(), context.getPackageName());
                return result == declaredField2.getInt(cls);
            } catch (Exception e) {
                return false;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                if (appOpsMgr == null)
                    return false;
                int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), context
                        .getPackageName());
                return mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED;
            } else {
                return Settings.canDrawOverlays(context);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                    Toast.makeText(SplashActivity.this,"请授予权限",Toast.LENGTH_SHORT);
                    finish();
                }
            }
        }
    }
}
