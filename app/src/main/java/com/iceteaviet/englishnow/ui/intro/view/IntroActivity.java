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
import android.widget.Button;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.ActivityIntroBinding;
import com.iceteaviet.englishnow.ui.auth.view.LoginActivity;
import com.iceteaviet.englishnow.ui.base.BaseActivity;
import com.iceteaviet.englishnow.ui.intro.IntroNavigator;
import com.iceteaviet.englishnow.ui.intro.IntroPagerAdapter;
import com.iceteaviet.englishnow.ui.intro.viewmodel.IntroViewModel;
import com.iceteaviet.englishnow.utils.OsUtils;

import javax.inject.Inject;

public class IntroActivity extends BaseActivity<ActivityIntroBinding, IntroViewModel> implements IntroNavigator {
    private final int[] mIntroBgColorIds = new int[]{
            R.color.intro_welcome_background_color,
            R.color.intro_informative_background_color,
            R.color.intro_social_networking_background_color
    };

    @Inject
    protected IntroViewModel introViewModel;

    private ActivityIntroBinding activityIntroBinding;

    private ViewPager viewPager;
    private IntroIndicator introIndicator;
    private Button nextButton;

    private ArgbEvaluator mArgbEvaluator;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private IntroPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        introViewModel.setNavigator(this);
        activityIntroBinding = getViewDataBinding();
        mArgbEvaluator = new ArgbEvaluator();

        bindViews();

        mPagerAdapter = new IntroPagerAdapter(this, viewPager);
        viewPager.setAdapter(mPagerAdapter);
        introIndicator.setViewPager(viewPager);

        ViewCompat.setBackgroundTintList(nextButton, ColorStateList.valueOf(Color.WHITE));

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
                viewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mPagerAdapter.getCount() - 1) {
                    nextButton.setText(R.string.intro_get_started);
                } else {
                    nextButton.setText(R.string.intro_next);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    private void bindViews() {
        viewPager = activityIntroBinding.introPager;
        introIndicator = activityIntroBinding.introIndicator;
        nextButton = activityIntroBinding.nextBtn;
    }

    @Override
    protected void onDestroy() {
        viewPager.removeOnPageChangeListener(onPageChangeListener);
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
    public void navigateToNextScreen() {
        final int currentPos = viewPager.getCurrentItem();
        if (currentPos == mPagerAdapter.getCount() - 1) {
            onIntroFinished();
        } else {
            viewPager.setCurrentItem(currentPos + 1);
        }
    }

    @Override
    public void skip() {
        viewPager.setCurrentItem(mPagerAdapter.getCount() - 1, true);
    }

    private void onIntroFinished() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
