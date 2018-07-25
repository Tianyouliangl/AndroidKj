package com.kj.app.view.loginAndRegister.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private final List<RxFragment> list;

    public MyFragmentAdapter(FragmentManager fm, List<RxFragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
