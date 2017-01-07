package lyh.library.cm.component.banner.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import lyh.library.cm.component.banner.PageIndicator;

/**
 * Created by lyh on 2016/7/14.
 * 默认实现的Banner圆形指示器
 */
public class DotIndicator extends LinearLayout implements PageIndicator {
    public DotIndicator(Context context) {
        super(context);
        init();
    }

    public DotIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DotIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DotIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private CircleIndicator[] indicators;
    private PageIndicator.MODE mCurrentMode = MODE.CENTER;
    private int mIndicatorInterver = 5;

    private void init() {

    }

    @Override
    public void setIndicatorNum(int totalNum) {
        this.removeAllViews();
        if (totalNum == 1 || totalNum == 0) return;
        indicators = new CircleIndicator[totalNum];
        this.setPadding(8, 8, 8, 8);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(15, 15);
        if (mCurrentMode == MODE.CENTER) {
            this.setGravity(Gravity.CENTER);
        } else if (mCurrentMode == MODE.LEFT) {
            this.setGravity(Gravity.LEFT);
        } else if (mCurrentMode == MODE.RIGHT) {
            this.setGravity(Gravity.RIGHT);
        }
        for (int i = 0; i < totalNum; i++) {
            CircleIndicator indicator = new CircleIndicator(getContext());
            if (i != 0) {
                lp.leftMargin = mIndicatorInterver;
            }
            this.addView(indicator, lp);
            indicators[i] = indicator;
        }
        onIndicatorSelected(0);
    }

    //page changed
    @Override
    public void onIndicatorSelected(int current) {
        for (int i = 0; i < indicators.length; i++) {
            if (i == current) {
                indicators[i].setSelected();
            } else {
                indicators[i].setUnSelected();
            }
        }
    }

    @Override
    public void onIndicatorScroll(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void setIndicatorMode(MODE mode) {
        mCurrentMode = mode;
    }


    /**
     * 圆形指示器
     */
    static class CircleIndicator extends View {

        public CircleIndicator(Context context) {
            this(context, null);
        }

        public CircleIndicator(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            //init
            mPaint = new Paint();
            mPaint.setColor(Color.WHITE);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
        }


        private int mCx;
        private int mCy;
        private int mRadius;
        private Paint mPaint;

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);

            setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? width : 50
                    , heightMode == MeasureSpec.EXACTLY ? height : 50);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mCx = w / 2;
            mCy = h / 2;
            mRadius = w / 2;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawCircle(canvas);
        }

        private void drawCircle(Canvas canvas) {
            canvas.drawCircle(mCx, mCy, mRadius, mPaint);
        }


        public void setSelected() {
            mPaint.setColor(Color.RED);
            postInvalidate();
        }

        public void setUnSelected() {
            mPaint.setColor(Color.WHITE);
            postInvalidate();
        }

    }

}
