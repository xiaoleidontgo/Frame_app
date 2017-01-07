package lyh.library.cm.widget.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.AbsListView;

import lyh.library.ultra.R;

/**
 * Created by lyh on 2016/11/26.
 * 自定义行为，实现在CoordinatorLayout 机制下的快速返回实现
 */
public class SwipeShowHideBehavior extends CoordinatorLayout.Behavior<View> {

    public static final String TAG = "SwipeShowHideBehavior";

    /**
     * 依赖触发行为的view
     */
    private int dependencyId;

    /**
     * 表示是否是显示状态
     */
    private boolean isShowing = true;

    public SwipeShowHideBehavior() {
    }

    public SwipeShowHideBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "SwipeShowHideBehavior counstruct");
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.My_Behavior);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            if (ta.getIndex(i) == R.styleable.My_Behavior_dependency_id) {
                dependencyId = ta.getResourceId(attr, -1);
            }
        }
        ta.recycle();

    }

    /**
     * 确定依赖的View
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        Log.d(TAG, "layoutDependsOn");

        return dependency instanceof NestedScrollView ||
                dependency instanceof RecyclerView ||
                dependency instanceof AbsListView ||
                dependency instanceof ViewPager ||
                dependency instanceof WebView;
    }

    /**
     * 当依赖的View发生变化的时候
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.d(TAG, "onDependentViewChanged");
        return true;
    }

    /**
     * 当自己依附的行为触发时，依附view发生变化时触发
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.d(TAG, "onNestedScroll ->" + dyConsumed + "...." + dyUnconsumed);
    }

    /**
     * 当进行快速滑动
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX
     * @param velocityY
     * @param consumed
     * @return
     */
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, "onNestedFling");
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    /**
     * 判断滑动的方向 我们需要垂直滑动
     * 这里返回true，才会接受到后续滑动事件。
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    private int sinceDirectionChange;

    /**
     * 根据滑动的距离显示和隐藏view
     * <p>
     * 依附的View滑动时会回调此方法
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        Log.d(TAG, "onNestedPreScroll->" + dy);
        if (dy > 0 && sinceDirectionChange < 0 || dy < 0 && sinceDirectionChange > 0) {
            child.animate().cancel();
            sinceDirectionChange = 0;
        }
        sinceDirectionChange += dy;
        if (sinceDirectionChange > child.getHeight() && isShowing) {
            show(child);
        } else if (sinceDirectionChange < 0 && !isShowing) {
            hide(child);
        }
    }

    private void show(View v) {
        Animation animation = generateCloseAnim(v);
        v.startAnimation(animation);
        isShowing = !isShowing;
    }

    private void hide(View v) {
        Animation animation = generateOpenAnim(v);
        v.startAnimation(animation);
        isShowing = !isShowing;
    }

    /**
     * 生成页面打开时的动画
     */
    private Animation generateOpenAnim(View view) {
        TranslateAnimation a = new TranslateAnimation(0f, 0f, -view.getHeight(), 0);
        a.setDuration(400);
        a.setFillAfter(true);
        return a;
    }

    /**
     * 生成页面关闭时的动画
     * TODO: 改成属性动画
     */
    private Animation generateCloseAnim(View view) {
        TranslateAnimation a = new TranslateAnimation(0f, 0f, 0f, -view.getHeight());
        a.setDuration(400);
        a.setFillAfter(true);
        return a;
    }
}

