package lyh.library.cm.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lyh.library.ultra.R;

import static android.widget.RelativeLayout.ALIGN_PARENT_LEFT;
import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;
import static android.widget.RelativeLayout.CENTER_IN_PARENT;
import static android.widget.RelativeLayout.CENTER_VERTICAL;
import static lyh.library.ultra.R.id.end;
import static lyh.library.ultra.R.id.toolbar;

/**
 * Created by lyh on 2016/10/31.
 * 自定义Toolbar
 * <p>
 * ------对应返回按钮，可以使用Toolbar 自带的方法进行实现----------
 * public void setNavigationIcon(@DrawableRes int resId)
 * public void setNavigationIcon(@Nullable Drawable icon)
 * public void setNavigationIcon(@DrawableRes int resId)
 * public void setNavigationIcon(@Nullable Drawable icon)
 * <p>
 * actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back);
 * <p>
 * ------自带返回按钮事件监听
 * toolbar.setNavigationOnClickListener((v)-> { onBackPressed() || finish()});
 */

public class UiToolbar extends Toolbar {


    /**
     * 模式
     */
    public static final short MODE_ALL = 0X01;
    public static final short MODE_TITLE = 0X01 << 1;
    public static final short MODE_TITLE_BACK = 0X01 << 2;
    public static final short MODE_TITLE_MORE = 0X01 << 3;

    private short mCurrentMode = MODE_ALL;

    //三段式容器
    private RelativeLayout rlLeftContainer, rlCenterContainer, rlRightContainer;
    //自定义Toolbar默认内容控件
    private ImageView ivBack;
    private TextView tvLeftText, tvCenterTitle, tvRightText;

    private String mTitle;


    public UiToolbar(Context context) {
        this(context, null);
    }

    public UiToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UiToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_toolbar, this);
        //自定义三个区域容器
        rlLeftContainer = (RelativeLayout) findViewById(R.id.rl_title_bar_left);
        rlCenterContainer = (RelativeLayout) findViewById(R.id.rl_title_bar_center);
        rlRightContainer = (RelativeLayout) findViewById(R.id.rl_title_bar_right);
        //初始化自定义的默认控件
        ivBack = (ImageView) findViewById(R.id.iv_title_bar_left);
        tvLeftText = (TextView) findViewById(R.id.tv_title_bar_left);
        tvCenterTitle = (TextView) findViewById(R.id.tv_title_bar_title);
        tvRightText = (TextView) findViewById(R.id.tv_title_bar_right);

        //默认全部隐藏，在使用对应的方法时才显示
//        rlLeftContainer.setVisibility(GONE);
//        rlCenterContainer.setVisibility(GONE);
//        rlRightContainer.setVisibility(GONE);

//        TypedArray ta = context.obtainStyledAttributes(attrs,R)
//        ta.recycle();
    }


    protected int getHeaderViewLayoutId() {
        return R.layout.layout_toolbar;
    }

    public ImageView getLeftImageView() {
        return ivBack;
    }

    public TextView getCenterTextView() {
        return tvCenterTitle;
    }

    public TextView getLeftTextView() {
        return tvLeftText;
    }

    public TextView getRightTextView() {
        return tvRightText;
    }

    public void setCenterTitle(String title) {
        mTitle = title;
        tvCenterTitle.setText(title);
    }

    public void setCenterTitle(String title, int color) {
        mTitle = title;
        tvCenterTitle.setText(title);
        tvCenterTitle.setTextColor(color);
    }

    public String getCenterTitle() {
        return mTitle;
    }

    private RelativeLayout.LayoutParams makeLayoutParams(View view) {
        ViewGroup.LayoutParams lpOld = view.getLayoutParams();
        RelativeLayout.LayoutParams lp = null;
        if (lpOld == null) {
            lp = new RelativeLayout.LayoutParams(-2, -1);
        } else {
            lp = new RelativeLayout.LayoutParams(lpOld.width, lpOld.height);
        }
        return lp;
    }

    /**
     * set customized view to left side
     *
     * @param view the view to be added to left side
     */
    public void setCustomizedLeftView(View view) {
        ivBack.setVisibility(GONE);
        tvLeftText.setVisibility(GONE);
        RelativeLayout.LayoutParams lp = makeLayoutParams(view);
        lp.addRule(CENTER_VERTICAL);
        lp.addRule(ALIGN_PARENT_LEFT);
        getLeftViewContainer().addView(view, lp);
    }

    /**
     * set customized view to left side
     *
     * @param layoutId the xml layout file id
     */
    public void setCustomizedLeftView(int layoutId) {
        View view = inflate(getContext(), layoutId, null);
        setCustomizedLeftView(view);
    }

    /**
     * set customized view to center
     *
     * @param view the view to be added to center
     */
    public void setCustomizedCenterView(View view) {
        tvCenterTitle.setVisibility(GONE);
        RelativeLayout.LayoutParams lp = makeLayoutParams(view);
        lp.addRule(CENTER_IN_PARENT);
        getCenterViewContainer().addView(view, lp);
    }

    /**
     * set customized view to center
     *
     * @param layoutId the xml layout file id
     */
    public void setCustomizedCenterView(int layoutId) {
        View view = inflate(getContext(), layoutId, null);
        setCustomizedCenterView(view);
    }

    /**
     * set customized view to right side
     *
     * @param view the view to be added to right side
     */
    public void setCustomizedRightView(View view) {
        RelativeLayout.LayoutParams lp = makeLayoutParams(view);
        lp.addRule(CENTER_VERTICAL);
        lp.addRule(ALIGN_PARENT_RIGHT);
        getRightViewContainer().addView(view, lp);
    }

    public void setCustomizedRightView(int layoutId) {
        View view = inflate(getContext(), layoutId, null);
        setCustomizedRightView(view);
    }

    public RelativeLayout getLeftViewContainer() {
        return rlLeftContainer;
    }

    public RelativeLayout getCenterViewContainer() {
        return rlCenterContainer;
    }

    public RelativeLayout getRightViewContainer() {
        return rlRightContainer;
    }

    public void setLeftOnClickListener(OnClickListener l) {
        rlLeftContainer.setOnClickListener(l);
    }

    public void setCenterOnClickListener(OnClickListener l) {
        rlCenterContainer.setOnClickListener(l);
    }

    public void setRightOnClickListener(OnClickListener l) {
        rlRightContainer.setOnClickListener(l);
    }

    /**
     * Change toolbar mode
     *
     * @param mode
     */
    public void setTitleMode(short mode) {
        mCurrentMode = mode;
        if (mCurrentMode == MODE_ALL) {
            //do nothing
        } else if (mCurrentMode == MODE_TITLE) {
            getCenterViewContainer().setVisibility(VISIBLE);
            getLeftViewContainer().setVisibility(GONE);
            getRightViewContainer().setVisibility(GONE);
        } else if (mCurrentMode == MODE_TITLE_BACK) {
            getCenterViewContainer().setVisibility(VISIBLE);
            getLeftViewContainer().setVisibility(VISIBLE);
            getRightViewContainer().setVisibility(GONE);
        } else if (mCurrentMode == MODE_TITLE_MORE) {
            getLeftViewContainer().setVisibility(GONE);
            getCenterViewContainer().setVisibility(VISIBLE);
            getRightViewContainer().setVisibility(VISIBLE);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    private int mDistanceY = 0;

    /**
     * Toolbar渐变的实现
     * 375BF1
     */
    public void setToolbarGradient(View dependView, final int startPos, final int endPos, final GradientListener gradientListener) {
        if (dependView instanceof RecyclerView && dependView != null) {
            final int toolbarHeight = UiToolbar.this.getHeight();
            final float alphaHeight = endPos - startPos - toolbarHeight;
            ((RecyclerView) dependView).addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    mDistanceY += dy;
                    if (mDistanceY <= alphaHeight) {
                        float scale = (float) mDistanceY / alphaHeight;
                        float alpha = scale * 255;
                        UiToolbar.this.setBackgroundColor(Color.argb((int) alpha, 37, 0X5B, 0xF1));
                        if (gradientListener != null) {
                            gradientListener.onBgChange((int) alpha, alpha == (255 / 2));
                        }
                    } else {
                        UiToolbar.this.setBackgroundColor(Color.argb(255, 37, 0X5B, 0xF1));
                    }

                }
            });
        }
    }

    /**
     * Toolbar渐变时回调
     */
    public interface GradientListener {
        /**
         * @param alpha    渐变时标题栏的alpha值
         * @param isCenter 是否alpha中间值
         */
        void onBgChange(int alpha, boolean isCenter);
    }


}
