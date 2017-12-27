package com.iceteaviet.englishnow.ui.intro.view;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iceteaviet.englishnow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Genius Doan on 12/27/2017.
 */

public class IntroPageFragment extends android.app.Fragment {

    private static final String ARG_KEY_TITLE = "arg_title";
    private static final String ARG_KEY_DESCRIPTION = "arg_description";
    private static final String ARG_KEY_DRAWABLE_ID = "arg_drawable_id";
    private static final String ARG_KEY_COLOR = "arg_color";

    @BindView(R.id.intro_title)
    TextView tvIntroTitle;
    @BindView(R.id.intro_description)
    TextView tvIntroDes;
    @BindView(R.id.intro_image)
    ImageView imageView;

    public IntroPageFragment() {

    }

    public static IntroPageFragment newInstance(CharSequence title,
                                                CharSequence description,
                                                @DrawableRes int drawableId,
                                                @ColorInt int color) {
        Bundle args = new Bundle();
        args.putCharSequence(ARG_KEY_TITLE, title);
        args.putCharSequence(ARG_KEY_DESCRIPTION, description);
        args.putInt(ARG_KEY_DRAWABLE_ID, drawableId);
        args.putInt(ARG_KEY_COLOR, color);

        IntroPageFragment fragment = new IntroPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.intro_page_fragment, container, false);
        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        tvIntroTitle.setText(args.getCharSequence(ARG_KEY_TITLE));
        tvIntroDes.setText(args.getCharSequence(ARG_KEY_DESCRIPTION));
        Glide.with(imageView.getContext())
                .load(args.getInt(ARG_KEY_DRAWABLE_ID))
                .into(imageView);

        return view;
    }
}