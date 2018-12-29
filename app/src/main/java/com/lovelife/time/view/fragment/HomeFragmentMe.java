package com.lovelife.time.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lovelife.time.R;
import com.lovelife.time.adapter.HomeMeAdapter;
import com.lovelife.time.base.BaseFragment;
import com.lovelife.time.bean.ThisType;
import com.lovelife.time.utlis.ProgressDialogUtil;
import com.lovelife.time.view.activity.HistoryActivity;
import com.lovelife.time.view.activity.IlikeActivity;
import com.lovelife.time.view.activity.MainActivity;
import com.lovelife.time.view.activity.MusicActivity;
import com.lovelife.time.view.activity.VideoActivity;
import com.lovelife.time.weight.ActivityManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragmentMe extends BaseFragment implements HomeMeAdapter.onItemClickInterface {


    @BindView(R.id.rv_home_me)
    RecyclerView rv_home_me;
    private HomeMeAdapter meAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_me;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView(View view) {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        rv_home_me.setLayoutManager(manager);
        rv_home_me.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));
        List<ThisType> setData = setData();
        setAdapter(setData);

    }

    private void setAdapter(List<ThisType> setData) {
        meAdapter = new HomeMeAdapter(getContext(), setData);
        rv_home_me.setAdapter(meAdapter);
        meAdapter.setOnClickItemListener(this);
    }

    private List<ThisType> setData() {
        ThisType type1 = new ThisType();
        type1.setImage(R.mipmap.music);
        type1.setTitle("本地音乐");
        ThisType type2 = new ThisType();
        type2.setImage(R.mipmap.video);
        type2.setTitle("本地视频");
        ThisType type3 = new ThisType();
        type3.setImage(R.mipmap.like);
        type3.setTitle("我喜欢");
        ThisType type4 = new ThisType();
        type4.setImage(R.mipmap.history);
        type4.setTitle("历史记录");
        List<ThisType> list = new ArrayList<>(3);
        list.add(type1);
        list.add(type2);
        list.add(type3);
        list.add(type4);
        return list;
    }


    @Override
    public void onClickItem(String title) {
        Intent mIntent = null;
        if (title.contains("本地音乐")){
            mIntent = new Intent(getActivity(),MusicActivity.class);
        }
        if (title.contains("本地视频")){
            mIntent = new Intent(getActivity(), VideoActivity.class);
        }
        if (title.contains("我喜欢")){
            mIntent = new Intent(getActivity(), IlikeActivity.class);
        }
        if (title.contains("历史记录")){
            mIntent = new Intent(getActivity(), HistoryActivity.class);
        }
        startActivity(mIntent);
        ActivityManager.in(getActivity());
    }
}
