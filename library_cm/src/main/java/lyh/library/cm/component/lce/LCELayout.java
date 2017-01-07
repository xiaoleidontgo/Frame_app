package lyh.library.cm.component.lce;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import lyh.library.cm.component.lce.view.DefaultLoadLayout;

/**
 * Created by lyh on 2016/7/11.
 * 抽象出加载，与加载成功两个动作
 * 加载动作抽象成LoadingUiHandler接口，实现可定制效果
 */
public class LCELayout extends FrameLayout implements LCEHandler {

    /**
     * 目前加载视图默认为DefaultLoadLayout
     */
    private DefaultLoadLayout mDefaultLoadLayout;
    private View mContentView;

    public LCELayout(Context context) {
        this(context, null);
    }

    public LCELayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LCELayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void showLoadLayout() {
        if (mDefaultLoadLayout == null
                || mContentView == null
                || mDefaultLoadLayout.getVisibility() == VISIBLE) return;

        mDefaultLoadLayout.setVisibility(VISIBLE);
        mContentView.setVisibility(GONE);
    }

    @Override
    public void showContentLayout() {
        if (mDefaultLoadLayout == null
                || mContentView == null
                || mContentView.getVisibility() == VISIBLE) return;

        mContentView.setVisibility(VISIBLE);
        mDefaultLoadLayout.setVisibility(GONE);
    }

    @Override
    public void hideLCELayout() {
        if (mDefaultLoadLayout == null
                || mContentView == null
                || mDefaultLoadLayout.getVisibility() == GONE) return;

        mDefaultLoadLayout.setVisibility(GONE);
        mContentView.setVisibility(VISIBLE);
    }

    @Override
    public void hideContentLayout() {
        if (mDefaultLoadLayout == null
                || mContentView == null
                || mContentView.getVisibility() == GONE) return;

        mContentView.setVisibility(GONE);
        mDefaultLoadLayout.setVisibility(VISIBLE);
    }

    @Override
    public LCEUiHandler getLCEUiView() {
        return mDefaultLoadLayout;
    }

    @Override
    public View getContentView() {
        return mContentView;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount < 1 || childCount > 2) {
            throw new IllegalArgumentException("the LCELayout child can't host " +
                    getChildCount() + " child number");
        }

        if (childCount == 1) {
            mContentView = getChildAt(0);
            mDefaultLoadLayout = new DefaultLoadLayout(getContext());
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            this.addView(mDefaultLoadLayout, lp);
        } else {

            View view0 = getChildAt(0);
            View view1 = getChildAt(1);

            if (view0 instanceof LCEUiHandler) {
                mDefaultLoadLayout = (DefaultLoadLayout) view0;
                mContentView = view1;
            } else {
                mDefaultLoadLayout = (DefaultLoadLayout) view1;
                mContentView = view0;
            }

        }

        showContentLayout();

    }

    //***********************Flag,待实现，请不要偷懒

    /**
     * 子类复写，实现自定义空布局界面
     *
     * @return
     */
    protected int emptyLayoutResId() {
        return 0;
    }

    /**
     * 子类复写，实现自定义失败布局界面
     *
     * @return
     */
    protected int failureLayoutResId() {
        return 0;
    }

    /**
     * 子类复写，实现自定义空无网络布局界面
     *
     * @return
     */
    protected int noNetLayoutResId() {
        return 0;
    }

}
