package lyh.library.cm.component.lce.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lyh.library.cm.component.lce.LCEUiHandler;
import lyh.library.cm.component.lce.ReloadCallback;
import lyh.library.ultra.R;

/**
 * Created by lyh on 2016/7/11.
 */
public class DefaultLoadLayout extends RelativeLayout
        implements LCEUiHandler, View.OnClickListener {

    private RelativeLayout mRlContainer;
    private ReloadCallback callback;

    private ImageView ivIcon;
    private TextView tvLabel;
    private SunLoadingView sunLoadingView;

    public DefaultLoadLayout(Context context) {
        this(context, null);
    }

    public DefaultLoadLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultLoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_lce_common, this);
        mRlContainer = (RelativeLayout) findViewById(R.id.rl_load_container);

        ivIcon = (ImageView) findViewById(R.id.iv_lce);
        tvLabel = (TextView) findViewById(R.id.tv_lce);
        sunLoadingView = (SunLoadingView) findViewById(R.id.sun_loading);

        ivIcon.setOnClickListener(this);

    }

    private void resetPage() {
        sunLoadingView.setVisibility(GONE);
        mRlContainer.setVisibility(GONE);
        sunLoadingView.stopAnim();
    }

    /**
     * 显示加载界面
     */
    @Override
    public void showLoading() {
        resetPage();
        sunLoadingView.startAnim();
        sunLoadingView.setVisibility(VISIBLE);
    }

    /**
     * 显示加载失败界面
     */
    @Override
    public void showFailure() {
        resetPage();
        mRlContainer.setVisibility(VISIBLE);
        ivIcon.setImageDrawable(getResources().getDrawable(R.drawable.failure));
        tvLabel.setText(getResources().getString(R.string.loading_failure));
    }

    /**
     * 显示没有网络界面
     */
    @Override
    public void showNoNet() {
        resetPage();
        mRlContainer.setVisibility(VISIBLE);
        ivIcon.setImageDrawable(getResources().getDrawable(R.drawable.nonet));
        tvLabel.setText(getResources().getString(R.string.loading_nonet));
    }

    /**
     * 显示空数据界面
     */
    @Override
    public void showEmpty() {
        resetPage();
        mRlContainer.setVisibility(VISIBLE);
        ivIcon.setImageDrawable(getResources().getDrawable(R.drawable.empty));
        tvLabel.setText(getResources().getString(R.string.loading_empty));
    }

    /**
     * 自定义显示的图标与文字
     *
     * @param drawableId
     * @param label
     */
    public void setCustomIconAndLabel(int drawableId, int label) {
        resetPage();
        mRlContainer.setVisibility(VISIBLE);
        ivIcon.setImageDrawable(getResources().getDrawable(drawableId));
        tvLabel.setText(getResources().getString(label));
    }

    public void setCustomIconAndLabel(int drawableId, String label) {
        resetPage();
        mRlContainer.setVisibility(VISIBLE);
        ivIcon.setImageDrawable(getResources().getDrawable(drawableId));
        tvLabel.setText(label);
    }

    @Override
    public void onClick(View v) {
        if (callback != null && sunLoadingView.getVisibility() == GONE) {
            callback.onReload();
        }
    }

    /**
     * 页面重新加载数据点击回调
     *
     * @param callback
     */
    public void setReloadDataCallback(ReloadCallback callback) {
        this.callback = callback;
    }


}
