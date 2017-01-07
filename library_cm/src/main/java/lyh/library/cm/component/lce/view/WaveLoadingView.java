package lyh.library.cm.component.lce.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lyh on 2016/12/19.
 * <p>
 * 中心圆->周围4小圆->转动->水滴黏贴效果
 */
public class WaveLoadingView extends View {

    /**
     * 圆的半径
     */
    private int mCircleRadius;
    /**
     * 显示的颜色集合
     */
    private int[] mColors;

    public WaveLoadingView(Context context) {
        this(context, null);
    }

    public WaveLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        TypedArray a = context.obtainStyledAttributes(attrs, R.)
//        mCircleRadius = a.
//        mColors=a.
//        a.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCenterCircle(canvas);
        drawAroundCircle(canvas);
    }

    /**
     * 绘制四周4个小圆
     *
     * @param canvas
     */
    private void drawAroundCircle(Canvas canvas) {
    }

    /**
     * 绘制中心圆
     *
     * @param canvas
     */
    private void drawCenterCircle(Canvas canvas) {
    }
}
