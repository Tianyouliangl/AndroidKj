package com.lovelife.time.view.activity;



import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lovelife.time.R;
import com.lovelife.time.adapter.HomeAdapter;
import com.lovelife.time.base.BaseActivity;
import com.lovelife.time.bean.MusicInfo;
import com.lovelife.time.service.MusicService;
import com.lovelife.time.utlis.SnackbarUtils;
import com.lovelife.time.utlis.StatusBarUtil;
import com.lovelife.time.view.fragment.HomeFragmentMe;
import com.lovelife.time.view.fragment.HomeFragmentRed;

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
    @BindView(R.id.tv_pop_title)
    TextView tv_pop_title;
    @BindView(R.id.tv_pop_author)
    TextView tv_pop_author;
    @BindView(R.id.main_bottom)
    FrameLayout main_bottom;
    @BindView(R.id.iv_pause_music)
    ImageView iv_pause_music;
    static TextView tv_pop_time;
    private List<Fragment> mFragmentList = new ArrayList<>(2);
    private String[] titles;
    private HomeAdapter adapter;
    private static MusicService.mBinder mMyBinder;
    //“绑定”服务的intent
    Intent MediaServiceIntent;
    private static SimpleDateFormat time = new SimpleDateFormat("m:ss");
    public static Handler mHandler = new Handler();
    private AlertDialog.Builder builder;
    private boolean isPlayMusic = false;

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
        tv_pop_time = findViewById(R.id.tv_pop_time);
        iv_left_menu.setOnClickListener(this);
        iv_right_menu.setOnClickListener(this);
        main_bottom.setOnClickListener(this);
        iv_pause_music.setOnClickListener(this);
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
        if (v.getId()==R.id.main_bottom){
            startActivity(new Intent(this,MusicPlayActivity.class));
        }
        if (v.getId() == R.id.iv_pause_music){
            if (isPlayMusic){
                iv_pause_music.setImageResource(R.mipmap.start_l);
                isPlayMusic = false;
                mMyBinder.pauseMusic();
            }else if (!isPlayMusic){
                iv_pause_music.setImageResource(R.mipmap.pause_l);
                isPlayMusic = true;
                mMyBinder.playMusic();
            }
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
    protected void onResume() {
        super.onResume();
        String info = getPlayMusicInfo();
        MusicInfo musicMsg = getPlayMusicMsg();
        if (!TextUtils.isEmpty(info)){
            main_bottom.setVisibility(View.VISIBLE);
            String title = musicMsg.getTitle();
            String name = musicMsg.getArtist();
            tv_pop_title.setText(title);
            tv_pop_author.setText(name);
            if (mMyBinder.isPlaying()){
                iv_pause_music.setImageResource(R.mipmap.pause_l);
                isPlayMusic = true;
            }else {
                iv_pause_music.setImageResource(R.mipmap.start_l);
                isPlayMusic = false;
            }
        }else {
            main_bottom.setVisibility(View.GONE);
        }
    }

    public static void setProgress(String playProgress,String progress){
        if (tv_pop_time != null){
            tv_pop_time.setText(playProgress+"/"+progress);
        }
    }

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
                MainActivity.setProgress(time.format(mMyBinder.getPlayPosition()),time.format(mMyBinder.getProgress()));
                mHandler.postDelayed(mRunnable, 1000);
            }
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            showFinishDialog();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void showFinishDialog() {
            builder = new AlertDialog.Builder(this).setIcon(R.mipmap.logo).setTitle("爱生活")
                    .setMessage("你确定要退出吗?").setPositiveButton("不想听了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).setNegativeButton("继续听", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.create().show();
    }

}
