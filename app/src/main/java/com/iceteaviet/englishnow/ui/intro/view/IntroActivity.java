package com.iceteaviet.englishnow.ui.intro.view;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.ActivityIntroBinding;
import com.iceteaviet.englishnow.ui.base.BaseActivity;
import com.iceteaviet.englishnow.ui.intro.IntroHandler;
import com.iceteaviet.englishnow.ui.intro.IntroPagerAdapter;
import com.iceteaviet.englishnow.ui.intro.viewmodel.IntroViewModel;
import com.iceteaviet.englishnow.ui.login.view.LoginActivity;
import com.iceteaviet.englishnow.utils.OsUtils;

import javax.inject.Inject;

public class IntroActivity extends BaseActivity<ActivityIntroBinding, IntroViewModel> implements IntroHandler {
    private final int[] mIntroBgColorIds = new int[]{
            R.color.intro_welcome_background_color,
            R.color.intro_informative_background_color,
            R.color.intro_social_networking_background_color
    };
    @Inject
    IntroViewModel introViewModel;

    ActivityIntroBinding activityIntroBinding;
    ArgbEvaluator mArgbEvaluator;
    ViewPager.OnPageChangeListener onPageChangeListener;
    private IntroPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        introViewModel.setHandler(this);
        activityIntroBinding = getViewDataBinding();
        mArgbEvaluator = new ArgbEvaluator();

        mPagerAdapter = new IntroPagerAdapter(this, activityIntroBinding.introPager);
        activityIntroBinding.introPager.setAdapter(mPagerAdapter);
        activityIntroBinding.introIndicator.setViewPager(activityIntroBinding.introPager);

        ViewCompat.setBackgroundTintList(activityIntroBinding.nextBtn, ColorStateList.valueOf(Color.WHITE));

        if (OsUtils.isAtLeastL()) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int bgColor = getResources().getColor(mIntroBgColorIds[position]);
                int nextBgColor = getResources().getColor(mIntroBgColorIds[position == mPagerAdapter.getCount() - 1 ? position : position + 1]);
                int colorUpdate = (Integer) mArgbEvaluator.evaluate(positionOffset, bgColor, nextBgColor);
                activityIntroBinding.introPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mPagerAdapter.getCount() - 1) {
                    activityIntroBinding.nextBtn.setText(R.string.intro_get_started);
                } else {
                    activityIntroBinding.nextBtn.setText(R.string.intro_next);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        activityIntroBinding.introPager.addOnPageChangeListener(onPageChangeListener);
    }

    @Override
    protected void onDestroy() {
        activityIntroBinding.introPager.removeOnPageChangeListener(onPageChangeListener);
        super.onDestroy();
    }

    @Override
    public IntroViewModel getViewModel() {
        return introViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_intro;
    }

    @Override
    public void goToNextScreen() {
        final int currentPos = activityIntroBinding.introPager.getCurrentItem();
        if (currentPos == mPagerAdapter.getCount() - 1) {
            onIntroFinished();
        } else {
            activityIntroBinding.introPager.setCurrentItem(currentPos + 1);
        }
    }

    @Override
    public void skip() {
        activityIntroBinding.introPager.setCurrentItem(mPagerAdapter.getCount() - 1, true);
    }

    private void onIntroFinished() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
