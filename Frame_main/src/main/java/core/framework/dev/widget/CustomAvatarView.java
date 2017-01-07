package core.framework.dev.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

/**
 * Created by lyh on 2017/1/1.
 */

public class CustomAvatarView extends View {

    public static final String TAG = CustomAvatarView.class.getSimpleName();
    private Paint mPaint;
    private Path mWavePath;
    private int mWidth, mHeight;
    /**
     * 波峰或波谷的高度
     */
    private int waveHieght;
    /**
     * 表示几个波峰或者几个波谷
     */
    private int mWaveNum = 2;
    int waveWidth;
    int centerPointY;
    private int speed = 5;
    private WaveAnimator waveAnimator;

    private int top;
    int bottom;

    public CustomAvatarView(Context context) {
        this(context, null);
    }

    public CustomAvatarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomAvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);

        mWavePath = new Path();
        waveAnimator = new WaveAnimator();
        waveAnimator.setDuration(1000);
        waveAnimator.setRepeatCount(-1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpec = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpec = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMeasureSpec == MeasureSpec.EXACTLY) {

        } else {

        }

        mWidth = width;
        mHeight = height;
        waveHieght = mHeight / 2 - 10;
        waveWidth = mWidth / mWaveNum;
        centerPointY = mHeight / 2;
        top = centerPointY - waveHieght;
        bottom = centerPointY + waveHieght;


        Log.d(TAG, "onMeasure-->" + getLeft() + "...." + mHeight / 2 + "..." + waveWidth);

        this.startAnimation(waveAnimator);

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawWave(canvas);

        Log.d(TAG, "onDraw");
    }

    /**
     * 绘制波浪
     *
     * @param canvas
     */
    private void drawWave(Canvas canvas) {
        //波峰渐变
        if (top < centerPointY + waveHieght) {
            top += speed;
        } else {
            top -= speed;
        }
        //波谷渐变
        if (bottom > centerPointY - waveHieght) {
            bottom -= speed;
        } else {
            bottom += speed;
        }

        mWavePath.moveTo(getLeft(), centerPointY);//start point
        mWavePath.cubicTo(
                getLeft(), centerPointY,
                getLeft() + (waveWidth / 2), top,
                getLeft() + (waveWidth), centerPointY);

        mWavePath.cubicTo(
                getLeft() + waveWidth, centerPointY,
                getLeft() + waveWidth + (waveWidth / 2), bottom,
                getLeft() + waveWidth + waveWidth, centerPointY);

        canvas.drawPath(mWavePath, mPaint);
    }

    class WaveAnimator extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            postInvalidate();
            Log.d(TAG, "动画执行过程：" + waveHieght + "..." + interpolatedTime);
        }
    }

}
