package lyh.library.cm.widget.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

import lyh.library.cm.basic._Application;
import lyh.library.cm.utils.LocalDisplay;
import lyh.library.cm.widget.UiToolbar;
import lyh.library.ultra.R;

import static lyh.library.ultra.R.id.toolbar;

/**
 * Created by lyh on 2016/11/27.
 */

public class SwipeGradientBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    public static final String TAG = SwipeGradientBehavior.class.getSimpleName();

    public SwipeGradientBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {

        return dependency instanceof NestedScrollView ||
                dependency instanceof RecyclerView ||
                dependency instanceof AbsListView ||
                dependency instanceof ViewPager;
    }

    private float alphaHeight = 0f;//渐变区间
    private int mDistanceY = 0;

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

        Log.d(TAG, "onNestedPreScroll -------" + mDistanceY);

        if (alphaHeight == 0) {
            alphaHeight = 250 - 0 - child.getHeight();
        }

        mDistanceY += dy;
        if (mDistanceY <= alphaHeight) {
            float scale = (float) mDistanceY / alphaHeight;
            float alpha = scale * 255;
            child.setBackgroundColor(Color.argb((int) alpha, 37, 0X5B, 0xF1));
        } else {
            child.setBackgroundColor(Color.argb(255, 37, 0X5B, 0xF1));
        }

    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        Log.d(TAG, "onStartNestedScroll -------" + nestedScrollAxes);
        return true;
    }

}
