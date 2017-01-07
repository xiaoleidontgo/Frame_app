package lyh.library.cm.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import lyh.library.ultra.R;

/**
 * Created by lyh on 2016/7/27.
 * 实现toolbar的行为控制-->颜色渐变
 */
public class GradientBehavior extends CoordinatorLayout.Behavior<View> {


    float y = 0;
    Toolbar toolbar;
    boolean isFirst = true;

    public GradientBehavior() {
    }

    //一定要重写的构造函数，系统会通过反射调用此方法
    public GradientBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 确定依赖的view
     * child  使用这个Behavior的view
     * dependy.getid()==xxx  担任触发此behavior的view
     * <p/>注意：这个方法会多次调用
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (toolbar == null) {
            toolbar = (Toolbar) child;
            toolbar.getBackground().setAlpha(0);
        }
        if (isFirst) {
            ((RecyclerView) dependency).addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    changeToolBarBg();
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
            isFirst = false;
        }
        return dependency.getId() == -1;
    }

    //当依赖的view变化时回调此方法
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return true;
    }

    private void changeToolBarBg() {
        if (y > 255) {
            y = 255;
        }
        if (y < 0) {
            y = 0;
        }
        toolbar.getBackground().setAlpha((int) y);
    }

    /**
     * 所依赖view的事件发生变化
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed        like a speed?
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        return true;
    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }

}
