package com.iceteaviet.englishnow.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iceteaviet.englishnow.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Genius Doan on 03/01/2018.
 */

public class SkillRatingBar extends RelativeLayout {

    @BindView(R.id.tv_skill_name)
    protected TextView tvSkillName;

    @BindView(R.id.rating_bar)
    protected RatingBar ratingBar;

    private String skillName;
    private float rating = 0f;

    private Unbinder unbinder;

    public SkillRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public SkillRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    public SkillRatingBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(attrs);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // View is now detached, and about to be destroyed
        unbinder.unbind();
    }

    private void initialize(AttributeSet attrs) {
        View rootView = inflate(getContext(), R.layout.skill_rating_bar, this);
        unbinder = ButterKnife.bind(this, rootView);
        initStyles(attrs);
        initViews();
    }

    private void initViews() {
        tvSkillName.setText(skillName);
        ratingBar.setRating(rating);
    }

    private void initStyles(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.SkillRatingBar);
        skillName = attributes.getString(R.styleable.SkillRatingBar_skillName);
        rating = attributes.getFloat(R.styleable.SkillRatingBar_rating, 0);
        attributes.recycle();
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
