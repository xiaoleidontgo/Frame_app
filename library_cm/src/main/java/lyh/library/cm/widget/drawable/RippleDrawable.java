package lyh.library.cm.widget.drawable;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

/**
 * Created by lyh on 2016/8/26.
 */
public class RippleDrawable extends Drawable implements Animatable {

    //需要的画笔
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //颜色
    private int bg_draw_color = Color.parseColor("#0099CC");
    private int bg_normal_color = Color.parseColor("#EBEBEB");


    //目标view的宽度
    private int mViewWidth;
    //目标view的高度
    private int mViewHeight;
    //扩散的距离
    private int mFullSpace;
    //控制的动画
    private ValueAnimator mValueAnimator;
    //半径
    private int mRadius;

    //是否需要绘制波纹背景
    private boolean isNeedRipple = false;

    /**
     * 必须重写画drawable的方法
     *
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        if (isNeedRipple) {
            drawRipple(canvas);
        } else {
            drawNothing(canvas);
        }

    }

    private void drawRipple(Canvas canvas) {
        mPaint.setColor(bg_draw_color);
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(100);
        canvas.drawCircle(mViewWidth / 2, mViewHeight / 2, mRadius, mPaint);
    }

    private void drawNothing(Canvas canvas) {
        //
        mPaint.setColor(bg_normal_color);
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(100);
    }

    /**
     * 设置透明度
     */
    @Override
    public void setAlpha(int alpha) {

    }

    /**
     * 设置颜色过滤
     */
    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    /**
     * 设置精度
     */
    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mViewWidth = Math.abs(bounds.width());
        mViewHeight = Math.abs(bounds.height());
        mFullSpace = (int) Math.sqrt((mViewWidth * mViewWidth + mViewHeight * mViewHeight));

        mValueAnimator = ValueAnimator.ofInt(0, mFullSpace);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取不断改变的半径并重绘drawable
                mRadius = (int) animation.getAnimatedValue();
                //重绘,会再次调用draw
                invalidateSelf();
            }
        });
        //设置动画持续的时间
        mValueAnimator.setDuration(800);
        start();
    }

    /**
     * 动画开始
     */
    @Override
    public void start() {
        if (mValueAnimator != null) {
            isNeedRipple = true;
            mValueAnimator.start();
        }
    }

    /**
     * 动画结束
     */
    @Override
    public void stop() {
        if (mValueAnimator != null) {
            isNeedRipple = false;
            mValueAnimator.end();
            invalidateSelf();
        }
    }

    /**
     * 动画是否运行中
     */
    @Override
    public boolean isRunning() {
        return mValueAnimator != null && mValueAnimator.isRunning();
    }

}
