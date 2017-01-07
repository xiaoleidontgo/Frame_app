package lyh.library.cm.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyh on 2016/11/2.
 * 流式布局
 */

public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        TypedArray ta = context.obtainStyledAttributes(attrs, id)
//        ta.recycle();

    }

    /**
     * 根据子控件的测量模式，测量出所有的子view 并确定自身的大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //宽高模式
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //宽高
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        //不是精确确定宽高的情况下自己测量出所有的子View，确定自己的大小
        int layoutWidth = 0;
        int layoutHeight = 0;

        //行宽高
        int lineWidth = 0;
        int lineHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {

            View childView = getChildAt(i);
            //进行测量子View
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();

            //获取这个子View的宽高
            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //换行
            if (lineWidth + childWidth > sizeWidth) {
                layoutWidth = Math.max(layoutWidth, lineWidth);
                layoutHeight += lineHeight;
                lineWidth = 0;
                lineHeight = 0;
            }

            lineWidth += childWidth;
            lineHeight = Math.max(lineHeight, childHeight);

            //最后剩余的一行
            if (i == getChildCount() - 1) {
                layoutWidth = Math.max(layoutWidth, lineWidth);
                layoutHeight += lineHeight;
            }

        }

        //确定宽高
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ?
                        sizeWidth : layoutWidth,
                modeHeight == MeasureSpec.EXACTLY ?
                        sizeHeight : layoutHeight);

    }

    /**
     * 确定每个子View的位置
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    List<List<View>> allViews = new ArrayList<>();
    List<Integer> lineMaxHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        allViews.clear();
        lineMaxHeight.clear();

        int layoutWidth = getWidth();

        /**
         * setp one : measure child and save all of the child by sort
         */
        int lineWidth = 0;
        int lineHeight = 0;
        int childCount = getChildCount();
        List<View> lineViews = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //换行
            if (lineWidth + childWidth > layoutWidth) {
                allViews.add(lineViews);
                lineMaxHeight.add(lineHeight);
                lineViews = new ArrayList<>();
                lineHeight = 0;
                lineWidth = 0;
            }

            lineWidth += childWidth;
            lineHeight = Math.max(lineHeight, childHeight);

            lineViews.add(childView);

            //一行未满
            if (i == childCount - 1) {
                allViews.add(lineViews);
                lineMaxHeight.add(lineHeight);
            }

        }// for end


        /**
         *进行子View布局
         */
        int left = 0;
        int top = 0;
        for (int i = 0; i < allViews.size(); i++) {

            List<View> lineAllViews = allViews.get(i);
            Integer lineMaxHeight = this.lineMaxHeight.get(i);

            //一行子View进行布局
            for (int j = 0; j < lineAllViews.size(); j++) {
                View childView = lineAllViews.get(j);
                if (childView.getVisibility() == GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();

                int cl = left + lp.leftMargin;
                int ct = top + lp.topMargin;
                int cr = left + childView.getMeasuredWidth() + lp.rightMargin;
                int cb = top + childView.getMeasuredHeight() + lp.bottomMargin;

                childView.layout(cl, ct, cr, cb);

                left += childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }// inner for end

            left = 0;
            top += lineMaxHeight;

        }//outer for end

    }

    /**
     * 生成此容器对应的布局参数对象
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }
}
