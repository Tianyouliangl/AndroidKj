package com.lovelife.time.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class HomeAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList;
    private String[] title;
    public HomeAdapter(FragmentManager fm) {
        super(fm);
    }

    public HomeAdapter(FragmentManager fm, List<Fragment> mList) {
        super(fm);
        this.mList = mList;
    }

    public HomeAdapter(FragmentManager fm, List<Fragment> mList, String[] title) {
        super(fm);
        this.mList = mList;
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position < mList.size()) {
            fragment = mList.get(position);
        } else {
            fragment = mList.get(0);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (title != null && title.length > 0)
            return title[position];
        return null;
    }
}
