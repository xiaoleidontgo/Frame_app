package lyh.library.cm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

import com.orhanobut.logger.Logger;

import lyh.library.ultra.R;

/**
 * Show progress with animation
 */
public class ShowCircleView extends View {

    public ShowCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(null, null);
    }

    public ShowCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShowCircleView(Context context) {
        super(context);
        init(context, null);
    }

    private Paint mPaint;
    private int mCommonColor = Color.GRAY;
    private int mCheckedColor = Color.RED;

    private int mStrokeWidth = 15;
    private int mCenterX;
    private int mCenterY;
    private int mRadius;
    private RectF mRect;

    private CircleAnimation mAnimation;

    private String mText = getResources().getString(R.string.app_name);
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());

    private void init(Context context, AttributeSet attrs) {

        TypedArray ty = context.obtainStyledAttributes(attrs, R.styleable.ShowCircleView);
//        mRadius = (int) ty.getDimension(R.styleable.ShowCircleView_radius_width, 100);
//        mStrokeWidth = (int) ty.getDimension(R.styleable.ShowCircleView_stroke_width, 20);
//        mTextSize = (int) ty.getDimension(R.styleable.ShowCircleView_text_size, 45);
//        mCommonColor = ty.getColor(R.styleable.ShowCircleView_circle_color, Color.GRAY);
        ty.recycle();

        mPaint = new Paint();
        mPaint.setColor(mCommonColor);
        mPaint.setStyle(Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mStrokeWidth);

        mAnimation = new CircleAnimation();
        mAnimation.setDuration(1600);
        //interpolator for animation
        mAnimation.setInterpolator(new BounceInterpolator(context, attrs));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(withMode == MeasureSpec.EXACTLY ? width : 50,
                heightMode == MeasureSpec.EXACTLY ? height : 50);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w / 2;
        mCenterY = h / 2;
        mRadius = w / 2 - (mStrokeWidth * 2);
        mRect = new RectF(mCenterX - mRadius,
                mCenterY - mRadius,
                mRadius * 2 + (mCenterX - mRadius),
                mRadius * 2 + (mCenterY - mRadius));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);

        drawLabel(canvas);

        //for animation
        drawArc(canvas);

    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            this.startAnimation(mAnimation);
        }
    }

    /**
     * 绘制外部圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        mPaint.setColor(mCommonColor);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
    }

    /**
     * 通过绘制扇形绘制进度
     *
     * @param canvas
     */


    //end angle
    private float mWeepAngle = 280f;

    private void drawArc(Canvas canvas) {
        mPaint.setColor(mCheckedColor);
        canvas.drawArc(mRect, 0f, mWeepAngle, false, mPaint);
    }

    //label for circle
    private void drawLabel(Canvas canvas) {

    }

    /**
     * View的动画
     */
    class CircleAnimation extends Animation {

        //interpolatedTime  ---  0f---1.0f
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);

            mWeepAngle = 280f * interpolatedTime;

            postInvalidate();

        }
    }


}
