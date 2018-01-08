package com.iceteaviet.englishnow.ui.base;

import android.animation.Animator;

import com.iceteaviet.englishnow.utils.ui.EspressoIdlingResource;

/**
 * Created by Genius Doan on 08/01/2018.
 */

public class AnimationAdapter implements Animator.AnimatorListener {

    @Override
    public void onAnimationStart(Animator animation) {
        EspressoIdlingResource.increment();
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        EspressoIdlingResource.decrement();
    }

    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }
}
