package com.kj.app.view.activity;



import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kj.app.R;
import com.kj.app.adapter.HomeAdapter;
import com.kj.app.base.BaseActivity;
import com.kj.app.bean.MusicInfo;
import com.kj.app.service.MusicService;
import com.kj.app.utlis.SnackbarUtils;
import com.kj.app.utlis.StatusBarUtil;
import com.kj.app.view.fragment.HomeFragmentMe;
import com.kj.app.view.fragment.HomeFragmentRed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tab_TabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.vp_ViewPager)
    ViewPager mViewPager;
    @BindView(R.id.iv_left_menu)
    ImageView iv_left_menu;
    @BindView(R.id.drawer)
    DrawerLayout m_drawer;
    @BindView(R.id.rl_left_menu)
    RelativeLayout rl_left_menu;
    @BindView(R.id.iv_right_menu)
    ImageView iv_right_menu;
    private List<Fragment> mFragmentList = new ArrayList<>(2);
    private String[] titles;
    private HomeAdapter adapter;
    private static MusicService.mBinder mMyBinder;
    //“绑定”服务的intent
    Intent MediaServiceIntent;
    private static SimpleDateFormat time = new SimpleDateFormat("m:ss");
    public static Handler mHandler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {
    // mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        StatusBarUtil.setWindowStatusBarColor(this,R.color.colorAccent);
        iv_left_menu.setOnClickListener(this);
        iv_right_menu.setOnClickListener(this);
        initService();
        initData();
        setAdapter();
    }
    private void initService() {
        MediaServiceIntent = new Intent(this,MusicService.class);
        bindService(MediaServiceIntent,mServiceConnection,BIND_AUTO_CREATE);
    }


    private void setAdapter() {
        adapter = new HomeAdapter(getSupportFragmentManager(), mFragmentList, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initData() {
        mFragmentList.add(new HomeFragmentMe());
        mFragmentList.add(new HomeFragmentRed());
        titles = new String[]{"我的","推荐"};
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_left_menu){
            m_drawer.openDrawer(rl_left_menu);
        }
        if (v.getId() == R.id.iv_right_menu){
            SnackbarUtils.Short(mTabLayout,"此功能正在开发中...")
                    .Colors(Color.YELLOW)
                    .gravityFrameLayout(Gravity.TOP)
                    .messageCenter()
                    .messageColor(Color.BLACK)
                    .above(mTabLayout,0,0,0)
                    .show();
        }
    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MusicService.mBinder) service;
            Log.d("fzw", "Service与Activity已连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyBinder.closeMedia();
        unbindService(mServiceConnection);
    }

    /**
     * 播放
     * @param index  播放音乐的下标
     */
    public static void playMusic(String index){
        if (mMyBinder != null){
            mMyBinder.playMusic(index);
        }
    }
    public static void playMusic(){
        if (mMyBinder != null){
            mMyBinder.playMusic();
        }
    }

    /**
     * 暂停
     */
    public static void pauseMusic(){
        if (mMyBinder != null){
            mMyBinder.pauseMusic();
        }
    }

    /**
     * 上一首
     */
    public static void upMusic(String path){
        if (mMyBinder != null){
            mMyBinder.preciousMusic(path);
        }
    }

    /**
     * 下一首
     */
    public static void belowMusic(String path){
        if (mMyBinder != null){
            mMyBinder.nextMusic(path);
        }
    }

    /**
     * 获取播放歌曲的信息
     * @return
     */
    public static String getPlayMusicInfo(){
        if (mMyBinder != null){
           return mMyBinder.getPlayMusicInfo();
        }
        return null;
    }
    public static MusicInfo getPlayMusicMsg(){
        if (mMyBinder != null){
            return mMyBinder.getPlayMusicMsg();
        }
        return null;
    }


    /**
     * 设置播放位置
     */
    public static void seekToPositon(int po){
        if (mMyBinder != null){
            mMyBinder.seekToPositon(po);
        }
    }

    /**
     * 重置
     * @param path
     */
    public static void reSet(String path){
        if (mMyBinder != null){
           mMyBinder.resetMusicX(path);
        }
    }
    /**
     * 获取是否在播放
     */
    public static boolean isPlaying(){
        if (mMyBinder != null){
          return   mMyBinder.isPlaying();
        }
        return false;
    }


    /**
     * 更新ui的runnable
     */
    public static Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (MusicPlayActivity.mContext !=null){
                MusicPlayActivity.setTvTime(time.format(mMyBinder.getPlayPosition()));
                MusicPlayActivity.setSeekProgress(mMyBinder.getPlayPosition());
                MusicPlayActivity.setTvTimeRight(time.format(mMyBinder.getProgress()));
                MusicPlayActivity.setSeekMax(mMyBinder.getProgress());
                mHandler.postDelayed(mRunnable, 1000);
            }
        }
    };

}
