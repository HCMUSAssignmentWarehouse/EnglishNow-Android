package com.iceteaviet.englishnow.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.utils.UiUtils;

/**
 * Created by Genius Doan on 05/01/2018.
 */

public final class ProgressImageView extends AppCompatImageView {

    private final int PROGRESS_DEFAULT_RADIUS = 40;
    private int currentProgress = -1;
    private RectF progressRect = new RectF();
    private RectF biggerRect = new RectF();
    private boolean smoothProgressEnabled = false;
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
        circlePaint.setStrokeWidth(UiUtils.dpToPx(getContext(), 4));
        circlePaint.setStyle(Paint.Style.STROKE);

        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    public void setSmoothProgressEnabled(boolean enabled) {
        smoothProgressEnabled = enabled;
    }

    //Only use this method if your download method do not emit 0 for progress percentage
    public void startLoading() {
        setProgress(0);
    }

    public void setProgress(int progress) {
        if (progress > 100 || progress == currentProgress)
            return;

        currentProgress = progress;

        if (smoothProgressEnabled && progress == 0) {
            //Start download
            Handler handler = new Handler();
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    if (currentProgress < 100 && currentProgress != -1) {
                        setProgress(currentProgress + 1);
                        postDelayed(this, 150);
                    }
                }
            };

            handler.postDelayed(r, 150);
        }

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
        if (currentProgress == -1) {
            return;
        }

        float angle = (currentProgress / 100f) * 360 - 90;
        float halfWidth = canvas.getWidth() / 2;
        float halfHeight = canvas.getHeight() / 2;
        progressRect.set(halfWidth - PROGRESS_DEFAULT_RADIUS,
                halfHeight - PROGRESS_DEFAULT_RADIUS,
                halfWidth + PROGRESS_DEFAULT_RADIUS,
                halfHeight + PROGRESS_DEFAULT_RADIUS);
        biggerRect.set(progressRect);
        int margin = 8;
        biggerRect.set(progressRect.left - margin, progressRect.top - margin, progressRect.right + margin, progressRect.bottom + margin);
        canvas.saveLayer(biggerRect, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawOval(progressRect, circlePaint);
        canvas.drawArc(biggerRect, angle, 360 - angle - 90, true, clearPaint);
        canvas.restore();
    }
}