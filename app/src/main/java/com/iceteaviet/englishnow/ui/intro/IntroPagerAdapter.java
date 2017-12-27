package com.iceteaviet.englishnow.ui.intro;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.ui.intro.view.IntroPageFragment;

/**
 * Created by Genius Doan on 12/27/2017.
 */

public final class IntroPagerAdapter extends FragmentPagerAdapter {

    private final @StringRes
    int[] mIntroTitleIds = new int[]{
            R.string.intro_title_welcome,
            R.string.intro_title_always_learning,
            R.string.intro_title_social_networking,
    };
    private final @StringRes
    int[] mIntroDescriptionIds = new int[]{
            R.string.intro_description_welcome,
            R.string.speaking_english_anytime_anywhere,
            R.string.intro_description_social_networking,
    };
    private final @DrawableRes
    int[] mIntroDrawableIds = new int[]{
            R.drawable.ic_englishnow,
            R.drawable.bg_communication,
            R.drawable.bg_writing
    };

    private final @ColorRes
    int[] mIntroBgColorIds = new int[]{
            R.color.intro_welcome_background_color,
            R.color.intro_informative_background_color,
            R.color.intro_social_networking_background_color
    };

    AppCompatActivity activity;
    ViewPager mPager;


    public IntroPagerAdapter(AppCompatActivity activity, ViewPager pager) {
        super(activity.getFragmentManager());
        this.activity = activity;
        mPager = pager;
    }

    @Override
    public IntroPageFragment getItem(int position) {
        // Because this public method is called outside many times,
        // check if it exits first before creating a new one.
        final String name = makeFragmentName(mPager.getId(), position);
        IntroPageFragment fragment = (IntroPageFragment) activity.getFragmentManager().findFragmentByTag(name);
        if (fragment == null) {
            fragment = IntroPageFragment.newInstance(activity.getString(mIntroTitleIds[position]),
                    activity.getString(mIntroDescriptionIds[position]),
                    mIntroDrawableIds[position],
                    mIntroBgColorIds[position]);
        }
        return fragment;
    }


    private String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 3;
    }
}