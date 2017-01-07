package lyh.library.cm.widget.animLayout;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyh on 2016/12/5.
 * 功能：将子View打开，收缩至ControlView的位置
 */
public class AnimLayout extends RelativeLayout {

    public static final String TAG = "AnimLayout";

    /**
     * 目标控制View
     */
    private View mTargetView;
    /**
     * 内部菜单集合
     */
    private List<IMenu> mMenuList;
    /**
     * 初始化菜单不可见
     */
    private boolean mMenuDefaultVisible = false;

    /**
     * 一开始默认位置为关闭状态
     */
    private boolean isMenuOpen = false;

    /**
     * 决定当菜单打开时是否聚焦至menu（事件）
     */
    private boolean disableWhenMenuOpen = false;

    /**
     * 如果以后有多状态则切换到枚举类型
     */
    public enum Status {
        OPEN, CLOSE
    }

    /**
     * 内部菜单事件监听
     */
    private OnMenuItemClickListener mMenuClickListener;
    private OnMenuStatusChangeListener mMenuStatusChangeListener;

    public AnimLayout(Context context) {
        this(context, null);
    }

    public AnimLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // TypedArray

        //ta.recycle();

        mMenuList = new ArrayList();

    }

    /**
     * 展示菜单
     */
    public void showMenu() {
        if (isMenuOpen) return;
        ((ITargetView) mTargetView).onStartOpen();
        for (IMenu menu : mMenuList) {
            menu.startOpen();
            makeMenuMove(menu);
        }
        setMenuStatus(true);
    }

    /**
     * 隐藏菜单
     */
    public void hideMenu() {
        if (!isMenuOpen) return;
        ((ITargetView) mTargetView).onStartClose();
        for (IMenu menu : mMenuList) {
            menu.startClose();
            makeMenuMove(menu);
        }
//        mCurrentStatus = Status.CLOSE;
        setMenuStatus(false);
    }

    /**
     * 当菜单显示时，禁掉ViewGroup里其它控件的事件
     *
     * @param disable
     */
    public void disableWhenMenuOpen(boolean disable) {
        this.disableWhenMenuOpen = disable;
    }

    /**
     * 获取菜单当前的状态
     *
     * @return true 打开  false 关闭
     */
    public boolean getMenuStatus() {
        return isMenuOpen;
    }

    /**
     * menu状态设置
     *
     * @param status
     */
    public void setMenuStatus(boolean status) {
        if (isMenuOpen == status) return;
        isMenuOpen = status;
        if (mMenuStatusChangeListener != null)
            mMenuStatusChangeListener.onStatusChange(isMenuOpen);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (disableWhenMenuOpen &&
                isMenuOpen &&
                !isOnMenuRect(ev)) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (disableWhenMenuOpen)
                    hideMenu();
                break;
        }
        return true;
//        return super.onTouchEvent(event);
    }

    /**
     * 点击的位置是否在menu,target view上面
     *
     * @param ev
     * @return
     */
    private boolean isOnMenuRect(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        View touchTarget = getViewAtViewGroup((int) x, (int) y);
        if (touchTarget instanceof IMenu || touchTarget instanceof ITargetView) {
            return true;
        }
        return false;
    }

    /**
     * 根据坐标获取相对应的子控件<br>
     * 在Activity使用
     *
     * @param x坐标
     * @param y坐标
     * @return 目标View
     */
//    public View getViewAtActivity(int x, int y) {
//        // 从Activity里获取容器
//        View root = getWindow().getDecorView();
//        return findViewByXY(root, x, y);
//    }

    /**
     * 根据坐标获取相对应的子控件<br>
     * 在重写ViewGroup使用
     *
     * @return 目标View
     */
    public View getViewAtViewGroup(int x, int y) {
        return findViewByXY(this, x, y);
    }

    private View findViewByXY(View view, int x, int y) {
        View targetView = null;
        if (view instanceof ViewGroup) {
            // 父容器,遍历子控件
            ViewGroup v = (ViewGroup) view;
            for (int i = 0; i < v.getChildCount(); i++) {
                targetView = findViewByXY(v.getChildAt(i), x, y);
                if (targetView != null) {
                    break;
                }
            }
        } else {
            targetView = getTouchTarget(view, x, y);
        }
        return targetView;

    }

    private View getTouchTarget(View view, int x, int y) {
        View targetView = null;
        // 判断view是否可以聚焦
        ArrayList<View> TouchableViews = view.getTouchables();
        for (View child : TouchableViews) {
            if (isTouchPointInView(child, x, y)) {
                targetView = child;
                break;
            }
        }
        return targetView;
    }

    private boolean isTouchPointInView(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (view.isClickable() && y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }

    /**
     * Menu菜单运动的实现
     * 随时通知每个View，其当前在此容器中的位置
     */
    private void makeMenuMove(IMenu menu) {
        if (isMenuOpen) {
            onMenuOpen(menu);
        } else {
            onMenuClose(menu);
        }
    }

    /**
     * 根据控制view，每个menu的位置，并让每个menu确定自身的运动路径
     * 确定menuView在Layout当中的坐标
     * 确定TargetView在Layout当中的坐标
     * 每次Layout 改变时会执行此方法一次，确保位置正确
     */
    private void calcMenuMovePosition() {

        int eT = mTargetView.getTop();
        int eB = mTargetView.getBottom();
        int eL = mTargetView.getLeft();
        int eR = mTargetView.getRight();

        int eX = (eR - eL) / 2 + eL;
        int eY = (eB - eT) / 2 + eT;

        int[] locationInWindow = getLocationInWindow(mTargetView);
        eX = locationInWindow[0];
        eY = locationInWindow[1];

        for (int i = 0; i < mMenuList.size(); i++) {
            View menuView = (View) mMenuList.get(i);

            int sT = menuView.getTop();
            int sB = menuView.getBottom();
            int sL = menuView.getLeft();
            int sR = menuView.getRight();

            int sX = (sR - sL) / 2 + sL;
            int sY = (sB - sT) / 2 + sT;

            locationInWindow = getLocationInWindow(mTargetView);
            eX = locationInWindow[0];
            eY = locationInWindow[1];

            //按照Menu位置，ControlView位置，让子View确定自身路径
            mMenuList.get(i).onInitMovePosition(this, sX, sY, eX, eY);

            initMenuListener(menuView, i);

            Log.d(TAG, ",起始位置 ：" + sX + "," + sY + "结束位置：" + eX + "," + eY);
        }

    }

    /**
     * 获取view相对于屏幕的坐标
     *
     * @param view
     * @return
     */
    private int[] getLocationInWindow(View view) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        int t = rect.top;
        int b = rect.bottom;
        int l = rect.left;
        int r = rect.right;

        Log.d(TAG, "Rect：" + t + "," + b + "," + l + "," + r);

        int X = (r - l) / 2 + l;
        int Y = (b - t) / 2 + t;

        return new int[]{X, Y};
    }

    /**_____________________________________________________________________________
     * Path动画，让View随着Path移动。
     * 可以通过贝塞尔曲线实现很复杂的效果
     * 棒棒哒
     */

    /**
     * 菜单开启的实现
     *
     * @param menu
     */
    private void onMenuOpen(IMenu menu) {
        Path openPath = menu.openPath();
        //开启动画，时刻告诉子View自己的位置
        menu.onMenuMove(isMenuOpen, 0, 0);
    }

    /**
     * 菜单关闭的实现
     *
     * @param menu
     */
    private void onMenuClose(IMenu menu) {
        Path closePath = menu.closePath();
        menu.onMenuMove(isMenuOpen, 0, 0);
    }

    /**
     * Path动画，让View随着Path移动。
     * 可以通过贝塞尔曲线实现很复杂的效果
     * 棒棒哒
     * _____________________________________________________________________________
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate");
        mMenuList.clear();
        initViews(this);
        if (mTargetView == null) {
            throw new IllegalArgumentException("the AnimLayout must has one controlView");
        }
    }

    /**
     * 递归遍历view树节点，确定需要的子View
     * 注意：没有返回条件限制，但是在实际情况下(View树层级不会太深)，不需要
     *
     * @param viewGroup
     */
    private void initViews(ViewGroup viewGroup) {
        Log.d(TAG, "initViews");
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof ViewGroup &&
                    !(childView instanceof IMenu) &&
                    !(childView instanceof ITargetView)) {
                initViews((ViewGroup) childView);
            } else {
                if (childView instanceof IMenu) {
                    IMenu menu = (IMenu) childView;
                    mMenuList.add(menu);
                    if (!mMenuDefaultVisible) {
                        childView.setVisibility(INVISIBLE);
                    }
                } else if (childView instanceof ITargetView) {
                    mTargetView = childView;
                    initControlViewListener(childView);
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //make the layout math_parent or calc the child

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG, "onLayout");
        if (changed) {
            calcMenuMovePosition();
        }
    }

    /**
     * 初始化每个Menu菜单的事件
     *
     * @param childView
     * @param position
     */
    private void initMenuListener(final View childView, final int position) {
        childView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuList.get(position).onMenuClick(true);
                if (mMenuClickListener != null) {
                    mMenuClickListener.onMenuItemClick(v, position);
                }
                for (int i = 0; i < mMenuList.size(); i++) {
                    if (i != position) {
                        mMenuList.get(i).onMenuClick(false);
                    }
                }
            }
        });
    }

    /**
     * 初始化ControlView事件
     *
     * @param controlView
     */
    private void initControlViewListener(View controlView) {
//        controlView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mCurrentStatus == Status.OPEN) {
//                    mCurrentStatus = Status.CLOSE;
//                    hideMenu();
//                } else {
//                    showMenu();
//                    mCurrentStatus = Status.OPEN;
//                }
//            }
//        });
    }

    /**
     * 设置控制View
     * <p>
     * 内部子View根据此controlView进行绘制动画
     *
     * @param controlView
     */
    public void setControlView(View controlView) {
        if (controlView == null)
            throw new NullPointerException("the controlView can't be null");
    }

    /**
     * 菜单条目事件回调注册
     *
     * @param l
     */
    public void setOnMenuItemClickListener(OnMenuItemClickListener l) {
        mMenuClickListener = l;
    }

    /**
     * 菜单状态改变回调
     *
     * @param l
     */
    public void setOnMenuStatusChangeListener(OnMenuStatusChangeListener l) {
        mMenuStatusChangeListener = l;
    }

    /**
     * 菜单条目事件回调
     */
    public interface OnMenuItemClickListener {
        void onMenuItemClick(View view, int item);
    }

    /**
     * 菜单状态改变
     */
    public interface OnMenuStatusChangeListener {
        void onStatusChange(boolean isOpen);
    }

}
