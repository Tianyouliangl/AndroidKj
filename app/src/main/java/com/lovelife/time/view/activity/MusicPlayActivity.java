package com.lovelife.time.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lovelife.time.R;
import com.lovelife.time.app.DHApplication;
import com.lovelife.time.base.BaseActivity;
import com.lovelife.time.bean.MusicInfo;
import com.lovelife.time.sqdb.SQManager;
import com.lovelife.time.utlis.ProgressDialogUtil;
import com.lovelife.time.utlis.SPUtils;
import com.lovelife.time.utlis.SnackbarUtils;
import com.lovelife.time.utlis.StatusBarUtil;
import com.lovelife.time.weight.ActivityManager;
import com.lovelife.time.weight.SPKey;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MusicPlayActivity extends BaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {


    @BindView(R.id.bar_iv_back)
    ImageView m_back;
    @BindView(R.id.bar_tv_title)
    TextView m_title;
    @BindView(R.id.tv_play_name)
    TextView tv_play_name;
    @BindView(R.id.iv_music_shang)
    ImageView m_Up;
    @BindView(R.id.iv_music_xia)
    ImageView m_Below;
    @BindView(R.id.iv_music_play)
    ImageView m_Play;
    @BindView(R.id.iv_music_order)
    ImageView iv_music_order;


    private boolean isPlaying = false;
    public static Context mContext;
    static TextView m_left_time;
    static SeekBar m_pb_play;
    static TextView m_right_time;
    private int anInt ;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_play;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        mContext = this;
        EventBus.getDefault().register(this);
        StatusBarUtil.setWindowStatusBarColor(this,R.color.musicPlay);
        ProgressDialogUtil.startProgressDialog(this);
        m_left_time = findViewById(R.id.tv_play_left_time);
        m_pb_play   = findViewById(R.id.pb_play);
        m_right_time= findViewById(R.id.tv_play_right_time);
        initData();
        m_title.setTextColor(Color.WHITE);
        m_back.setOnClickListener(this);
        m_Up.setOnClickListener(this);
        m_Below.setOnClickListener(this);
        m_Play.setOnClickListener(this);
        iv_music_order.setOnClickListener(this);
    }


    private void initData() {
        MusicInfo musicMsg = MainActivity.getPlayMusicMsg();
        if (musicMsg != null){
            m_title.setText(musicMsg.getTitle());
            tv_play_name.setText(musicMsg.getArtist());
            getPlayOrder();
            boolean b = MainActivity.isPlaying();
            isPlaying = b;
            if (b){
                m_Play.setImageResource(R.mipmap.pause);
            }else {
                m_Play.setImageResource(R.mipmap.play);
            }
            Log.i("fzw","播放状态---" + musicMsg.getState());
            m_pb_play.setOnSeekBarChangeListener(this);
            MainActivity.mHandler.post(MainActivity.mRunnable);
            ProgressDialogUtil.stopProgressDialog();
        }else {
            initData();
        }

    }

    private void getPlayOrder() {
        int o =SPUtils.getInstance(this).getInt(SPKey.PLAYING_ORDER,1);
        anInt = o;
        if (o == 1){ // 顺序
            iv_music_order.setImageResource(R.mipmap.nex);
        }else if (o == 2){ // 单曲
            iv_music_order.setImageResource(R.mipmap.order);
        }else if (o == 3){ // 随机
            iv_music_order.setImageResource(R.mipmap.ordom);
        }
    }


    public static void setTvTime(String tvTime){
        if (m_left_time != null){
            m_left_time.setText(tvTime);
        }
    }

    public static void setSeekProgress(int progress){
        if (m_pb_play != null){
            m_pb_play.setProgress(progress);
        }
    }

    public static void setTvTimeRight(String timeRight){
        if (m_right_time != null){
            m_right_time.setText(timeRight);
        }
    }
    public static void setSeekMax(int max){
        if (m_pb_play != null){
            m_pb_play.setMax(max);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bar_iv_back){
         //   supportFinishAfterTransition();
            finish();
           // ActivityManager.out(this);
        }
        if (v.getId() == R.id.iv_music_shang){ // 上一首
            MainActivity.upMusic(MainActivity.getPlayMusicInfo());
            setO();
        }
        if (v.getId() == R.id.iv_music_play){ // 播放/暂停
            if (isPlaying){
                MainActivity.pauseMusic();
                isPlaying = false;
                m_Play.setImageResource(R.mipmap.play);
            }else {
                MainActivity.playMusic();
                isPlaying = true;
                m_Play.setImageResource(R.mipmap.pause);
            }

        }
        if (v.getId() == R.id.iv_music_xia){ // 下一首
            MainActivity.belowMusic(MainActivity.getPlayMusicInfo());
            setO();
        }
        if (v.getId() == R.id.iv_music_order){
            if (anInt == 1){
                showToast("切换至单曲循环");
                iv_music_order.setImageResource(R.mipmap.order);
                SPUtils.getInstance(this).put(SPKey.PLAYING_ORDER,2);
                anInt = 2;
            }else if (anInt == 2){
                iv_music_order.setImageResource(R.mipmap.ordom);
                showToast("切换至随机播放");
                SPUtils.getInstance(this).put(SPKey.PLAYING_ORDER,3);
                anInt = 3;
            }else if (anInt == 3){// 随机
                showToast("切换至顺序播放");
                iv_music_order.setImageResource(R.mipmap.nex);
                SPUtils.getInstance(this).put(SPKey.PLAYING_ORDER,1);
                anInt = 1;
            }
        }
    }

    private void showToast(String msg) {
        SnackbarUtils.Short(m_title,msg)
                .Colors(Color.WHITE)
                .gravityFrameLayout(Gravity.BOTTOM)
                .messageCenter()
                .alpha(0.2f)
                .messageColor(Color.BLACK)
                .show();
    }

    private void setO() {
        m_pb_play.setProgress(0);
        m_right_time.setText("0:00");
        m_left_time.setText("0:00");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(MusicInfo event){
        m_title.setText(event.getTitle());
        tv_play_name.setText(event.getArtist());
        switch (event.getState()){
            case 1: // 播放
                m_Play.setImageResource(R.mipmap.pause);
                break;
            case 0:  //暂停
                m_Play.setImageResource(R.mipmap.play);
                break;
            case 4:  // 上一首
                m_Play.setImageResource(R.mipmap.play);
                setO();
                break;
            case 9: // 下一首
                m_Play.setImageResource(R.mipmap.play);
                setO();
                break;
            case -1: // 重置
                m_Play.setImageResource(R.mipmap.play);
                isPlaying = true;
                setO();
                break;
            case -2: // 准备中
                m_Play.setImageResource(R.mipmap.play);
                break;
            case -3: // 播放完毕
                m_Play.setImageResource(R.mipmap.play);
                setO();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser){
            MainActivity.seekToPositon(m_pb_play.getProgress());
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
