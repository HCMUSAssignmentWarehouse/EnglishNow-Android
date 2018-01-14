package com.iceteaviet.englishnow.ui.newsfeed;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.iceteaviet.englishnow.ui.newsfeed.status.view.StatusFragment;
import com.iceteaviet.englishnow.ui.newsfeed.videostatus.view.VideoStatusFragment;

/**
 * Created by Genius Doan on 14/01/2018.
 */

public class NewsFeedPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;

    public NewsFeedPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mTabCount = 0;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return StatusFragment.newInstance();
            case 1:
                return VideoStatusFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    public void setCount(int count) {
        mTabCount = count;
    }
}