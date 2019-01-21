package com.lovelife.time.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.lovelife.time.BuildConfig;
import com.lovelife.time.R;
import com.lovelife.time.app.DHApplication;
import com.lovelife.time.bean.MusicInfo;
import com.lovelife.time.sqdb.SQManager;
import com.lovelife.time.utlis.SPUtils;
import com.lovelife.time.weight.SPKey;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {

    private String TAG = "com/kj/app/service";

    private mBinder mBinder = new mBinder();

    //初始化MediaPlayer
    public MediaPlayer mMediaPlayer;

    // 播放歌曲的集合
    public List<MusicInfo> mList;

    // 下标
    private int index = 0;
    private Notification notification;
    private Bitmap icon;
    private String playPath;

    public MusicService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
        showNotification();
    }

    private void showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(BuildConfig.APPLICATION_ID,
                    "love life ", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);

             icon = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
             notification = new Notification.Builder(this)
                     .setChannelId(BuildConfig.APPLICATION_ID)
                    .setContentTitle("Music")
                    .setContentText("This is Music")
                    .setSmallIcon(R.mipmap.logo)
                    .setLargeIcon(icon)
                    .build();

            notification.flags = Notification.FLAG_NO_CLEAR;
            startForeground(0x1080, notification);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
       return mBinder;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // 1 顺序播放 2 单曲循环 3 随机播放
        int anInt = SPUtils.getInstance(this).getInt(SPKey.PLAYING_ORDER, 1);
        postState(-3);
        if (mMediaPlayer != null) {
            //切换歌曲reset()很重要很重要很重要，没有会报IllegalStateException
            mMediaPlayer.reset();
            if (anInt == 1){
                nexPlayMusic();
            }else if (anInt == 2){
                playMusic();
            }else if (anInt == 3){
                ronDomPlayMusic();
            }
        }
    }

    private void ronDomPlayMusic() {
        List<MusicInfo> all = DHApplication.getDao().getMusicAll(SQManager.SQLITE_MC_MSG);
        int num = (int) (Math.random() * (all.size()-1));
        String path = all.get(num).getPath();
        playPath = path;
        if (mMediaPlayer != null){
            mMediaPlayer.reset();
            iniMediaPlayerFile(playPath);
            mMediaPlayer.start();
            postState(1);
        }
    }


    /**
     * 顺序播放
     */
    private void nexPlayMusic() {
        nextMusicList(playPath,DHApplication.getDao().getMusicAll(SQManager.SQLITE_MC_MSG));
        postState(9);
        if (mMediaPlayer != null) {
            playMusic(playPath);
        }
    }

    private void playMusic(String playPath) {
        mMediaPlayer.reset();
        iniMediaPlayerFile(playPath);
        if (!mMediaPlayer.isPlaying()) {
            //如果还没开始播放，就开始
            mMediaPlayer.start();
            postState(1);
        }
    }
    private void playMusic() {
        mMediaPlayer.reset();
        iniMediaPlayerFile(playPath);
            //如果还没开始播放，就开始
        mMediaPlayer.start();
        postState(1);

    }

    public class mBinder extends Binder{

        public void playMusic(String path) {
            mMediaPlayer.reset();
            playPath = path;
            iniMediaPlayerFile(path);
            if (!mMediaPlayer.isPlaying()) {
                //如果还没开始播放，就开始
                mMediaPlayer.start();
                postState(1);
            }
        }

        public void playMusic(){
            if (!mMediaPlayer.isPlaying()) {
                //如果还没开始播放，就开始
                mMediaPlayer.start();
                postState(1);
            }
        }

        /**
         * 暂停播放
         */
        public void pauseMusic() {
            postState(0);
            if (mMediaPlayer.isPlaying()) {
                //如果播放，就暂停
                mMediaPlayer.pause();
            }

        }

        /**
         * reset 重置
         */
        public void resetMusicD(String path) {
            playPath = path;
            if (!mMediaPlayer.isPlaying()) {
                //如果还没开始播放，就开始
                mMediaPlayer.reset();
                iniMediaPlayerFile(path);
            }else {
                mMediaPlayer.pause();
                mMediaPlayer.reset();
                iniMediaPlayerFile(path);
            }
            postState(-1);
        }
        /**
         * reset 重置
         */
        public void resetMusicX(String pathX) {
            playPath = pathX;
            reSetList(pathX,DHApplication.getDao().getMusicAll(SQManager.SQLITE_MC_MSG));
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                mMediaPlayer.reset();
                postState(-1);
                iniMediaPlayerFile(playPath);
            }
        }

        /**
         * 关闭播放器
         */
        public void closeMedia() {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
                mMediaPlayer.release();
                System.exit(0);
            }
        }

        /**
         * 下一首
         */
        public void nextMusic(String path) {
            playPath = path;
            postState(9);
            nextMusicList(path,DHApplication.getDao().getMusicAll(SQManager.SQLITE_MC_MSG));
            if (mMediaPlayer != null) {
                playMusic(playPath);
            }
        }

        /**
         * 上一首
         */
        public void preciousMusic(String path) {
            playPath = path;
            postState(4);
            preciousMusicList(path,DHApplication.getDao().getMusicAll(SQManager.SQLITE_MC_MSG));
            if (mMediaPlayer != null) {
                playMusic(playPath);

            }
        }

        /**
         * 获取歌曲长度
         **/
        public int getProgress() {
            return mMediaPlayer.getDuration();
        }

        /**
         * 获取播放位置
         */
        public int getPlayPosition() {
            return mMediaPlayer.getCurrentPosition();
        }

        /**
         * 播放指定位置
         */
        public void seekToPositon(int msec) {
            mMediaPlayer.seekTo(msec);
        }

        /**
         * 获取播放信息
         * @return
         */
        public String getPlayMusicInfo(){
            return playPath;
        }

        public MusicInfo getPlayMusicMsg(){
            return getPlayPath();
        }
        public boolean isPlaying(){
            return mMediaPlayer.isPlaying();
        }
    }


    private void reSetList(String pathX, List<MusicInfo> musicAll) {
        int index = -1;
        for (int i =0;i<musicAll.size();i++){
            if (pathX.equals(musicAll.get(i).getPath())){
                index = i;
            }
        }
        if (index > -1){

            if ((index+1) < musicAll.size()){
                index ++;
            }else {
                index = 0;
            }
        }
        playPath = musicAll.get(index).getPath();
    }

    private void nextMusicList(String path, List<MusicInfo> musicAll) {
        int index = -1;
        for (int i =0;i<musicAll.size();i++){
            if (path.equals(musicAll.get(i).getPath())){
                index = i;
            }
        }
        if (index > -1){
            if ((index+1) < musicAll.size()){
                index +=1;
            }else {
                index = 0;
            }
            playPath = musicAll.get(index).getPath();
        }
    }

    /**
     * 上一首
     * @param path
     * @param musicAll
     */
    private void preciousMusicList(String path, List<MusicInfo> musicAll) {
        int index = -1;
        for (int i =0;i<musicAll.size();i++){
            if (path.equals(musicAll.get(i).getPath())){
                index = i;
            }
        }
        if (index > -1){
            if (index == 0){
                index = musicAll.size()-1;
            }else if (index == musicAll.size()-1){
                index  -= 1;
            }else {
                index -= 1;
            }
            playPath = musicAll.get(index).getPath();
        }
    }


    /**
     * 发送播放状态
     * @param
     * @param i
     */
    private void postState(int i) {

            MusicInfo info = getPlayPath();
            info.setState(i);
            EventBus.getDefault().post(info);
    }

    private MusicInfo getPlayPath() {
        List<MusicInfo> infos = DHApplication.getDao().getMusicAll(SQManager.SQLITE_MC_MSG);
        for (int i=0;i<infos.size();i++){
            if (!TextUtils.isEmpty(playPath)){
                if (playPath.equals(infos.get(i).getPath())){
                    return infos.get(i);
                }
            }
        }
        return null;
    }

    /**
     * 准备播放
     *
     */
    private void iniMediaPlayerFile(String path) {
        //获取文件路径
        try {
            //此处的两个方法需要捕获IO异常
            //设置音频文件到MediaPlayer对象中
            postState(-2);
            mMediaPlayer.setDataSource(path);
            //让MediaPlayer对象准备
            mMediaPlayer.prepare();
        } catch (IOException e) {
            Log.d(TAG, "设置资源，准备阶段出错");
            e.printStackTrace();
        }
    }
}
