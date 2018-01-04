package com.iceteaviet.englishnow.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.utils.ScreenUtils;

/**
 * Created by Genius Doan on 05/01/2018.
 */

public final class ProgressImageView extends AppCompatImageView {

    private int progress = -1;
    private RectF progressRect = new RectF();
    private RectF biggerRect = new RectF();
    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint clearPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public ProgressImageView(Context context) {
        super(context);
        init();
    }

    public ProgressImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        circlePaint.setStrokeWidth(ScreenUtils.dpToPx(getContext(), 4));
        circlePaint.setStyle(Paint.Style.STROKE);

        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if (progress != -1) {
            setColorFilter(Color.rgb(123, 123, 123), PorterDuff.Mode.MULTIPLY);
        } else {
            clearColorFilter();
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (progress == -1) {
            return;
        }

        float angle = (progress / 100f) * 360 - 90;
        float halfWidth = canvas.getWidth() / 2;
        float halfHeight = canvas.getHeight() / 2;
        progressRect.set(halfWidth * 0.75f, halfHeight * 0.75f, halfWidth * 1.25f, halfHeight * 1.25f);
        biggerRect.set(progressRect);
        int margin = 8;
        biggerRect.set(progressRect.left - margin, progressRect.top - margin, progressRect.right + margin, progressRect.bottom + margin);
        canvas.saveLayer(biggerRect, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawOval(progressRect, circlePaint);
        canvas.drawArc(biggerRect, angle, 360 - angle - 90, true, clearPaint);
        canvas.restore();
    }
}