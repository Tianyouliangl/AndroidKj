package com.lovelife.time.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lovelife.time.R;
import com.lovelife.time.adapter.MusicAdapter;
import com.lovelife.time.base.BaseActivity;
import com.lovelife.time.bean.MusicInfo;
import com.lovelife.time.contract.MusicContract;
import com.lovelife.time.presenter.MusicPresenter;
import com.lovelife.time.utlis.SnackbarUtils;
import com.lovelife.time.utlis.StatusBarUtil;
import com.lovelife.time.weight.ActivityManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.BindView;

public class MusicActivity extends BaseActivity<MusicPresenter> implements View.OnClickListener,MusicContract.MusicView, MusicAdapter.onClickInterface {

    @BindView(R.id.bar_iv_back)
    ImageView m_back;
    @BindView(R.id.bar_tv_title)
    TextView m_title;
    @BindView(R.id.bar)
    LinearLayout m_bar_bg;
    @BindView(R.id.null_data)
    RelativeLayout m_null_data;
    @BindView(R.id.rv_music)
    RecyclerView m_rv_music;
    @BindView(R.id.btn_null_data)
    Button m_btn_null_data;
    @BindView(R.id.iv_music_dw)
    ImageView iv_music_dw;
    private MusicAdapter adapter;
    private CheckBox ck_pop_check;
    private MusicInfo mMusicInfo;
    private View mView;
    private PopupWindow popupWindow;
    private List<MusicInfo> mList;
    private LinearLayoutManager layoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music;

    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        StatusBarUtil.setWindowStatusBarColor(this,R.color.color_music);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(false);
        m_back.setOnClickListener(this);
        m_bar_bg.setBackgroundColor(Color.rgb(139,230,100));
        m_title.setText("本地音乐");
        m_title.setTextColor(Color.WHITE);
        m_btn_null_data.setText("扫描本地音乐(>30秒)");
        m_btn_null_data.setOnClickListener(this);
        iv_music_dw.setOnClickListener(this);

        m_rv_music.setLayoutManager(layoutManager);
        m_rv_music.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        m_rv_music.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0){
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {
                                sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (iv_music_dw != null){
                                        iv_music_dw.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    }.start();
                }else {
                    if (iv_music_dw != null){
                        iv_music_dw.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        mPresenter.getMusicDbAll();
    }

    private void isNull(List<MusicInfo> mList) {
        if (mList.size() > 0){
            m_null_data.setVisibility(View.GONE);
            m_rv_music.setVisibility(View.VISIBLE);
            setAdapter(mList);
            adapter.notifyDataSetChanged();
        }else {
            m_null_data.setVisibility(View.VISIBLE);
            m_rv_music.setVisibility(View.GONE);
        }

    }

    private int getPlayingMusic() {
        String musicInfo = MainActivity.getPlayMusicInfo();
        int index = 0;
        if (TextUtils.isEmpty(musicInfo)){return index;}
        if (mList != null && mList.size() > 0){
            for (int i=0;i< mList.size();i++){
                if (musicInfo.equals(mList.get(i).getPath())){
                    mList.get(i).setPlayState(1);
                    index = i;
                }else {
                    mList.get(i).setPlayState(0);
                }
            }
            setAdapter(mList);
            adapter.notifyItemChanged(index);
        }
        return index;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPlayingMusic();
    }

    private void setAdapter(List<MusicInfo> mList) {
        adapter = new MusicAdapter(mList,this);
        m_rv_music.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bar_iv_back){ // 返回
            this.finish();
        }
        if (v.getId() == R.id.btn_null_data){ // 没有数据
            mPresenter.getMusic(this);
        }
        if (v.getId() == R.id.tv_pop_delete){ // 删除
            popupWindow.dismiss();
            isReSetMusic();
            mPresenter.deleteMusic(mMusicInfo.getMid(),mMusicInfo.getPath(), ck_pop_check.isChecked());
            if (!TextUtils.isEmpty(mMusicInfo.getPath())){sXin(mMusicInfo.getPath());}
        }
        if (v.getId() == R.id.tv_pop_share){ // 分享
            popupWindow.dismiss();
            SnackbarUtils.Short(m_title,"此功能正在开发中...")
                    .Colors(Color.YELLOW)
                    .gravityFrameLayout(Gravity.TOP)
                    .messageCenter()
                    .messageColor(Color.BLACK)
                    .above(m_title,0,0,0)
                    .show();
        }
        if (v.getId() == R.id.iv_music_dw){ // 定位
            int i = getPlayingMusic();
            if (layoutManager != null){
                layoutManager.scrollToPositionWithOffset(i,0);
            }
        }
    }

    private void isReSetMusic() {
        String s = MainActivity.getPlayMusicInfo();
        if (s != null){
            if (s.equals( mMusicInfo.getPath())){
                MainActivity.reSet(s);
            }
        }
    }

    @Override
    public void MusicMsg(List<MusicInfo> list) {
        this.mList = list;
        isNull(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void deleteSuccess(String msg) {
        SnackbarUtils.Short(m_title,msg)
                .Colors(Color.GREEN)
                .gravityFrameLayout(Gravity.TOP)
                .messageCenter()
                .messageColor(Color.WHITE)
                .above(m_title,0,0,0)
                .show();
    }

    @Override
    public void deleteError(String msg) {
        SnackbarUtils.Short(m_title,msg)
                .Colors(Color.RED)
                .gravityFrameLayout(Gravity.TOP)
                .messageCenter()
                .messageColor(Color.WHITE)
                .above(m_title,0,0,0)
                .show();
    }

    @Override
    public void onItemClickListener(MusicInfo musicInfo,View view1,int index) {
        /**
         * 共享元素 打开方式
         */
        Intent intent = new Intent(this,MusicPlayActivity.class);
//        ActivityOptionsCompat options = ActivityOptionsCompat
//                .makeSceneTransitionAnimation(
//                        MusicActivity.this,view1,
//                        "musicTitle");
//        ActivityCompat.startActivity(MusicActivity.this,intent,options.toBundle());  // 有问题
        mPlayMusic(musicInfo);
        boolean isNull = mPresenter.getIsNull(musicInfo.getPath());
        if (isNull){
            startActivity(intent);
            ActivityManager.in(this);
        }


    }

    private void mPlayMusic(MusicInfo musicInfo) {
        if (MainActivity.getPlayMusicInfo() != null){
            boolean state = MainActivity.isPlaying();
                if (state){
                    if (MainActivity.getPlayMusicInfo().equals(musicInfo.getPath())){
                        MainActivity.playMusic();
                    }else {
                        MainActivity.playMusic(musicInfo.getPath());
                    }

                }else{
                    MainActivity.playMusic(musicInfo.getPath());
                }
        }else {
            MainActivity.playMusic(musicInfo.getPath());
        }
    }

    @Override
    public void onItemRightMenu(MusicInfo musicInfo) {
        this.mMusicInfo = musicInfo;
        View view = showPopWindow();
        TextView tv_delete = view.findViewById(R.id.tv_pop_delete);
        TextView tv_share = view.findViewById(R.id.tv_pop_share);
        ck_pop_check = view.findViewById(R.id.rb_pop_check);
        tv_delete.setOnClickListener(this);
        tv_share.setOnClickListener(this);
    }

    @SuppressLint("WrongConstant")
    private View showPopWindow() {
        mView = LayoutInflater.from(this).inflate(R.layout.custom_right_menu_pop, null);
        popupWindow = new PopupWindow(mView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.4f;
        getWindow().setAttributes(params);
        popupWindow.setAnimationStyle(R.style.popwindow_anim_style);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = ((Activity) mView.getContext()).getWindow().getAttributes();
                params.alpha = 1f;
                ((Activity) mView.getContext()).getWindow().setAttributes(params);
                if (popupWindow != null){
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow.showAtLocation(mView, Gravity.BOTTOM,0,0);
        return mView;
    }

    /**
     * 删除本地文件,通知媒体库刷新   不刷新就会出现删除后还能查询到的情况
     * @param path
     */
    private void sXin(String path) {
        String saveAs = path;
        Uri contentUri = Uri.fromFile(new File(saveAs));
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,contentUri);
        sendBroadcast(mediaScanIntent);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(MusicInfo event){
       getPlayingMusic();
    }

}
