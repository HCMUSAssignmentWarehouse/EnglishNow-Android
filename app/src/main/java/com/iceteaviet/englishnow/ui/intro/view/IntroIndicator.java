package com.iceteaviet.englishnow.ui.intro.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.iceteaviet.englishnow.R;

import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;

/**
 * Created by Genius Doan on 26/12/2017.
 */

public class IntroIndicator extends FrameLayout {

    private final SlidingIndicatorStrip mIndicatorStrip;
    private ViewPager mPager;
    private introPagerOnPageChangeListener mPageChangeListener;
    private int mIndicatorPaddingStart;
    private int mIndicatorPaddingTop;
    private int mIndicatorPaddingEnd;
    private int mIndicatorPaddingBottom;

    private int mIndicatorColor;
    private int mIndicatorRadius;

    public IntroIndicator(Context context) {
        this(context, null);
    }

    public IntroIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IntroIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);

        mIndicatorStrip = new SlidingIndicatorStrip(context);
        super.addView(mIndicatorStrip, 0, new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

        final Resources res = context.getResources();
        mIndicatorColor = res.getColor(R.color.intro_normal_indicator_color);
        mIndicatorRadius = res.getDimensionPixelSize(R.dimen.intro_normal_indicator_radius);
        int selectedIndicatorRadius = res.getDimensionPixelSize(R.dimen.intro_selected_indicator_radius);
        mIndicatorStrip.setSelectedIndicatorRadius(selectedIndicatorRadius);
        int selectedIndicatorColor = res.getColor(R.color.intro_selected_indicator_color);
        mIndicatorStrip.setSelectedIndicatorColor(selectedIndicatorColor);

        mIndicatorPaddingStart = mIndicatorPaddingTop
                = mIndicatorPaddingEnd = mIndicatorPaddingBottom
                = res.getDimensionPixelSize(R.dimen.intro_indicator_padding);
    }

    public void setScrollPosition(int position, float positionOffset) {
        setScrollPosition(position, positionOffset, true);
    }

    void setScrollPosition(int position, float positionOffset,
                           boolean updateIndicatorPosition) {
        final int roundedPosition = Math.round(position + positionOffset);
        if (roundedPosition < 0 || roundedPosition >= mIndicatorStrip.getChildCount()) {
            return;
        }

        // Set the indicator position, if enabled
        if (updateIndicatorPosition) {
            mIndicatorStrip.onPageScrolled(position, positionOffset);
        }
    }

    public void setViewPager(ViewPager viewPager) {
        mPager = viewPager;
        if (mPageChangeListener == null) {
            mPageChangeListener = new introPagerOnPageChangeListener();
        }
        mPageChangeListener.reset();
        viewPager.addOnPageChangeListener(mPageChangeListener);
        addIndicators(mPager.getAdapter());
        setScrollPosition(viewPager.getCurrentItem(), 0f);
    }

    private void addIndicators(PagerAdapter adapter) {
        mIndicatorStrip.removeAllViews();
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            addIndicator(i);
        }
    }

    private void addIndicator(final int position) {
        final IndicatorView view = new IndicatorView(getContext());
        view.setIndicatorRadius(mIndicatorRadius);
        view.setIndicatorColor(mIndicatorColor);
        mIndicatorStrip.addView(view, position);
    }

    class IndicatorView extends View {
        private final Paint mPaint;
        private int mIndicatorRadius;

        public IndicatorView(Context context) {
            super(context);
            ViewCompat.setPaddingRelative(this, mIndicatorPaddingStart, mIndicatorPaddingTop,
                    mIndicatorPaddingEnd, mIndicatorPaddingBottom);
            setClickable(false);
            setFocusable(false);
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
        }

        void setIndicatorRadius(int radius) {
            if (mIndicatorRadius != radius) {
                mIndicatorRadius = radius;
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        void setIndicatorColor(int color) {
            if (mPaint.getColor() != color) {
                mPaint.setColor(color);
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            final int size = MeasureSpec.makeMeasureSpec(mIndicatorRadius * 2, MeasureSpec.AT_MOST);
            final int width = size + getPaddingStart() + getPaddingEnd();
            final int height = size + getPaddingTop() + getPaddingBottom();
            super.onMeasure(width, height);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            final int halfWidth = getWidth() / 2;
            final int halfHeight = getHeight() / 2;
            canvas.drawCircle(halfWidth, halfHeight, mIndicatorRadius, mPaint);
        }
    }

    private class SlidingIndicatorStrip extends LinearLayout {
        private final Paint mSelectedIndicatorPaint;
        int mSelectedPosition = -1;
        float mSelectionOffset;
        private int mSelectedIndicatorSize;
        private int mIndicatorLeft = -1;
        private int mIndicatorRight = -1;

        private ValueAnimator mIndicatorAnimator;

        public SlidingIndicatorStrip(Context context) {
            super(context);
            setWillNotDraw(false);
            mSelectedIndicatorPaint = new Paint();
            mSelectedIndicatorPaint.setAntiAlias(true);
        }

        void setSelectedIndicatorColor(int color) {
            if (mSelectedIndicatorPaint.getColor() != color) {
                mSelectedIndicatorPaint.setColor(color);
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        void setSelectedIndicatorRadius(int size) {
            if (mSelectedIndicatorSize != size) {
                mSelectedIndicatorSize = size;
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        void onPageScrolled(int position, float positionOffset) {
            if (mIndicatorAnimator != null && mIndicatorAnimator.isRunning()) {
                mIndicatorAnimator.cancel();
            }

            mSelectedPosition = position;
            mSelectionOffset = positionOffset;
            updateIndicatorPosition();
        }

        private void updateIndicatorPosition() {
            final View selectedTitle = getChildAt(mSelectedPosition);
            int left, right;

            if (selectedTitle != null) {
                left = selectedTitle.getLeft();
                right = selectedTitle.getRight();

                if (mSelectionOffset > 0f && mSelectedPosition < getChildCount() - 1) {
                    // Draw the selection partway between the tabs
                    View nextTitle = getChildAt(mSelectedPosition + 1);
                    left = (int) (mSelectionOffset * nextTitle.getLeft() +
                            (1.0f - mSelectionOffset) * left);
                    right = (int) (mSelectionOffset * nextTitle.getRight() +
                            (1.0f - mSelectionOffset) * right);
                }
            } else {
                left = right = -1;
            }

            setIndicatorPosition(left, right);
        }

        void setIndicatorPosition(int left, int right) {
            if (left != mIndicatorLeft || right != mIndicatorRight) {
                // If the indicator's left/right has changed, invalidate
                mIndicatorLeft = left;
                mIndicatorRight = right;
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        void animateIndicatorToPosition(final int position, int duration) {

        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);

            // Thick colored underline below the current selection
            if (mIndicatorLeft >= 0 && mIndicatorRight > mIndicatorLeft) {
                final int paddingStart = getChildAt(mSelectedPosition).getPaddingStart();
                final int paddingEnd = getChildAt(mSelectedPosition).getPaddingEnd();
                canvas.drawCircle(mIndicatorLeft + paddingStart + paddingEnd, getHeight() / 2,
                        mSelectedIndicatorSize, mSelectedIndicatorPaint);
            }
        }
    }

    private final class introPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private int mPreviousScrollState;
        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            setScrollPosition(position, 0f);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mPreviousScrollState = mScrollState;
            mScrollState = state;
        }

        void reset() {
            mPreviousScrollState = mScrollState = SCROLL_STATE_IDLE;
        }
    }
}
