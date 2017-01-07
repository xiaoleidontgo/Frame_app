package lyh.library.cm.widget.dropdown;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by lyh on 2016/11/2.
 * 下拉容器
 */

public class DropDownLayout extends FrameLayout {

    /**
     * 菜单容器
     */
    private FrameLayout mMenuLayout;
    /**
     * 内容菜单
     */
    private View mContentView;

    /**
     * 展示的界面内容  View 或者 Fragment
     */
    private List<View> mPages;
    private int mPageNum;

    public DropDownLayout(Context context) {
        this(context, null);
    }

    public DropDownLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mMenuLayout = new FrameLayout(context);
        //防止事件穿透
        mMenuLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    /**
     * 设置要显示的view的数目
     *
     * @param num
     */
    public void setPageNum(int num) {
        mPageNum = num;
        initContainer();
    }

    /**
     * 设置进来要展示的页面内容View
     */
    public void setPages(List<View> pages) {
        if (pages == null) return;
        mPages = pages;
        setPageNum(mPages.size());
    }

    /**
     * 添加页面
     *
     * @param page
     */
    public void addPage(View page) {

    }

    /**
     * 删除页面
     *
     * @param index
     */
    public void removePage(int index) {
        checkBounds(index);
        this.removeViewAt(index);
    }

    /**
     * 根据数据初始化下拉容器
     */
    private void initContainer() {
        for (int i = 0; i < mPageNum; i++) {
            FrameLayout childContainer = makeChildPageContainer();
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            if (mPages.get(i) != null)
                childContainer.addView(mPages.get(i), lp);

            mMenuLayout.addView(childContainer, lp);
            childContainer.setVisibility(GONE);
        }

    }

    /**
     * 一个页面对应一个子FrameLayout容器
     *
     * @return
     */
    private FrameLayout makeChildPageContainer() {
        FrameLayout childContainer = new FrameLayout(getContext());
        int childContainerId = View.generateViewId();
        childContainer.setId(childContainerId);
        return childContainer;
    }

    /**
     * 越界检查
     */
    private void checkBounds(int index) {
        if (index >= getChildCount())
            throw new ArrayIndexOutOfBoundsException(index + "----out of bounds");
    }

    /**
     * 打开对应的菜单页面
     *
     * @param index
     */
    public void openPageAt(int index) {
        checkBounds(index);

        if (isOpenPage(index)) {
            return;
        }

        if (mMenuLayout.getVisibility() == GONE) mMenuLayout.setVisibility(VISIBLE);

        FrameLayout childFrame = (FrameLayout) mMenuLayout.getChildAt(index);
        Animation animation = generateOpenAnim();
        childFrame.startAnimation(animation);
        childFrame.setVisibility(VISIBLE);
    }

    /**
     * 关闭对应的下拉菜单
     *
     * @param index
     */
    public void closePageAt(final int index) {
        checkBounds(index);

        if (!isOpenPage(index)) return;

        FrameLayout childView = (FrameLayout) mMenuLayout.getChildAt(index);

        Animation animation = generateCloseAnim();
        childView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mMenuLayout.getChildAt(index).setVisibility(GONE);
                if (!hasOpenPage()) {
                    mMenuLayout.setVisibility(GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    /**
     * 关闭所有的菜单
     */
    public void closeAllPage() {
        for (int i = 0; i < mMenuLayout.getChildCount(); i++) {
            if (mMenuLayout.getChildAt(i).getVisibility() != GONE)
                closePageAt(i);
        }

    }

    /**
     * 获取对应页面的id，可以在外界进行碎片替换，简化逻辑
     *
     * @param index
     */
    public int getPageContainerIdAt(int index) {
        checkBounds(index);
        return this.getChildAt(index).getId();
    }

    /**
     * 获取当前打开的页面
     *
     * @return
     */
    public int getOpenPageIndex() {
        for (int i = 0; i < mMenuLayout.getChildCount(); i++) {
            FrameLayout childFrame = (FrameLayout) mMenuLayout.getChildAt(i);
            if (childFrame.getVisibility() == VISIBLE) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 判断是否存在打开的页面
     *
     * @return
     */
    public boolean hasOpenPage() {
        return getOpenPageIndex() != -1;
    }

    /**
     * 关闭打开的页面
     */
    public void closeOpenPage() {
        closePageAt(getOpenPageIndex());
    }

    /**
     * 返回对应索引页面是否打开
     *
     * @param index
     * @return
     */
    public boolean isOpenPage(int index) {
        checkBounds(index);
        return getOpenPageIndex() == index;
    }

    /**
     * 控制内容视图是否响应事件
     *
     * @param enable
     */
    private void enabelContentView(boolean enable) {

        if (enable) {

        } else {

        }

    }


    /**
     * 生成页面打开时的动画
     */
    private Animation generateOpenAnim() {
        TranslateAnimation a = new TranslateAnimation(0f, 0f, -mMenuLayout.getHeight(), 0);
        a.setDuration(400);
        return a;
    }

    /**
     * 生成页面关闭时的动画
     */
    private Animation generateCloseAnim() {
        TranslateAnimation a = new TranslateAnimation(0f, 0f, 0f, -getHeight());
        a.setDuration(300);
        return a;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() < 1) {
            throw new IllegalArgumentException("the dropDownLayout must contains a contentView");
        }
        mContentView = getChildAt(0);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.addView(mMenuLayout, lp);
        mMenuLayout.setVisibility(GONE);

        mContentView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (hasOpenPage()) {
                    closeOpenPage();
                    return true;
                }
                return false;
            }
        });

    }
}
