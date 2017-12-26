package com.iceteaviet.englishnow.ui.intro.view;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.utils.OsUtils;

public class IntroActivity extends AppCompatActivity {

    private final int[] mIntroTitleIds = new int[]{
            R.string.intro_title_welcome,
            R.string.intro_title_informative,
            R.string.intro_title_social_networking
    };
    private final int[] mIntroDescriptionIds = new int[]{
            R.string.intro_description_welcome,
            R.string.intro_description_informative,
            R.string.intro_description_social_networking
    };
    private final int[] mIntroDrawableIds = new int[]{
            R.drawable.avatar_placeholder,
            R.drawable.avatar_placeholder,
            R.drawable.avatar_placeholder
    };
    private final int[] mIntroBgColorIds = new int[]{
            R.color.intro_welcome_background_color,
            R.color.intro_informative_background_color,
            R.color.intro_social_networking_background_color
    };
    private ViewPager mIntroPager;
    private IntroPagerAdapter mPagerAdapter;
    private IntroIndicator mIntroIndicator;
    private AppCompatButton mNextBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mIntroPager = (ViewPager) findViewById(R.id.intro_pager);
        mPagerAdapter = new IntroPagerAdapter(this, mIntroPager);
        mIntroPager.setAdapter(mPagerAdapter);
        mIntroIndicator = (IntroIndicator) findViewById(R.id.intro_indicator);
        mIntroIndicator.setViewPager(mIntroPager);
        mNextBtn = (AppCompatButton) findViewById(R.id.next_btn);
        ViewCompat.setBackgroundTintList(mNextBtn, ColorStateList.valueOf(Color.WHITE));

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int currentPos = mIntroPager.getCurrentItem();
                if (currentPos == mPagerAdapter.getCount() - 1) {
                    onIntroFinished();
                } else {
                    mIntroPager.setCurrentItem(currentPos + 1);
                }
            }
        });


        findViewById(R.id.skip_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroPager.setCurrentItem(mPagerAdapter.getCount() - 1, true);
            }
        });

        if (OsUtils.isAtLeastL()) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    private void onIntroFinished() {

    }

    public static class IntroPage extends android.app.Fragment {

        private static final String ARG_KEY_TITLE = "arg_title";
        private static final String ARG_KEY_DESCRIPTION = "arg_description";
        private static final String ARG_KEY_DRAWABLE_ID = "arg_drawable_id";
        private static final String ARG_KEY_COLOR = "arg_color";

        public IntroPage() {

        }

        public static IntroPage newInstance(CharSequence title,
                                            CharSequence description,
                                            @DrawableRes int drawableId,
                                            @ColorInt int color) {
            Bundle args = new Bundle();
            args.putCharSequence(ARG_KEY_TITLE, title);
            args.putCharSequence(ARG_KEY_DESCRIPTION, description);
            args.putInt(ARG_KEY_DRAWABLE_ID, drawableId);
            args.putInt(ARG_KEY_COLOR, color);

            IntroPage fragment = new IntroPage();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.intro_page_fragment, container, false);
            Bundle args = getArguments();
            ((TextView) view.findViewById(R.id.intro_title))
                    .setText(args.getCharSequence(ARG_KEY_TITLE));
            ((TextView) view.findViewById(R.id.intro_description))
                    .setText(args.getCharSequence(ARG_KEY_DESCRIPTION));
            ((ImageView) view.findViewById(R.id.intro_image))
                    .setImageResource(args.getInt(ARG_KEY_DRAWABLE_ID));
            return view;
        }
    }

    private final class IntroPagerAdapter extends FragmentPagerAdapter
            implements ViewPager.OnPageChangeListener {

        Context mContext;
        ViewPager mPager;

        ArgbEvaluator mArgbEvaluator;

        public IntroPagerAdapter(AppCompatActivity activity, ViewPager pager) {
            super(activity.getFragmentManager());
            mContext = activity;
            mPager = pager;
            mPager.addOnPageChangeListener(this);
            mArgbEvaluator = new ArgbEvaluator();
        }

        @Override
        public IntroPage getItem(int position) {
            // Because this public method is called outside many times,
            // check if it exits first before creating a new one.
            final String name = makeFragmentName(mPager.getId(), position);
            IntroPage fragment = (IntroPage) getFragmentManager().findFragmentByTag(name);
            if (fragment == null) {
                fragment = IntroPage.newInstance(getString(mIntroTitleIds[position]),
                        getString(mIntroDescriptionIds[position]),
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

        /**
         * This method will be invoked when the current page is scrolled, either as part
         * of a programmatically initiated smooth scroll or a user initiated touch scroll.
         *
         * @param position             Position index of the first page currently being displayed.
         *                             Page position+1 will be visible if positionOffset is nonzero.
         * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
         * @param positionOffsetPixels Value in pixels indicating the offset from position.
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int bgColor = mContext.getResources().getColor(mIntroBgColorIds[position]);
            int nextBgColor = mContext.getResources().getColor(mIntroBgColorIds[position == getCount() - 1 ? position : position + 1]);
            int colorUpdate = (Integer) mArgbEvaluator.evaluate(positionOffset, bgColor, nextBgColor);
            mPager.setBackgroundColor(colorUpdate);
        }

        /**
         * This method will be invoked when a new page becomes selected. Animation is not
         * necessarily complete.
         *
         * @param position Position index of the new selected page.
         */
        @Override
        public void onPageSelected(int position) {
            if (position == getCount() - 1) {
                mNextBtn.setText(R.string.intro_get_started);
            } else {
                mNextBtn.setText(R.string.intro_next);
            }
        }

        /**
         * Called when the scroll state changes. Useful for discovering when the user
         * begins dragging, when the pager is automatically settling to the current page,
         * or when it is fully stopped/idle.
         *
         * @param state The new scroll state.
         * @see ViewPager#SCROLL_STATE_IDLE
         * @see ViewPager#SCROLL_STATE_DRAGGING
         * @see ViewPager#SCROLL_STATE_SETTLING
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
