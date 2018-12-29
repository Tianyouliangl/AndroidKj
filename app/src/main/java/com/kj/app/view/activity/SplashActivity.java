package com.kj.app.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
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
import com.kj.app.R;
import com.kj.app.app.DHApplication;
import com.kj.app.base.BaseActivity;

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
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                DHApplication.getDao().createTab();
                finish();
                overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                Toast.makeText(SplashActivity.this,"请设置权限!",Toast.LENGTH_SHORT).show();
            }
        },permissions,false,null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
