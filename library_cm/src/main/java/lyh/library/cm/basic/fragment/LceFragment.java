package lyh.library.cm.basic.fragment;

import android.view.View;

import lyh.library.cm.component.lce.LCEHandler;
import lyh.library.cm.component.lce.LCELayout;
import lyh.library.cm.component.lce.LCEUiHandler;
import lyh.library.cm.component.lce.ReloadCallback;
import lyh.library.cm.component.lce.view.DefaultLoadLayout;
import lyh.library.ultra.R;

/**
 * Created by lyh on 2016/10/26.
 * LCE means loading...content...error
 * <p>
 * 当单独继承此Fragment时需要注意需要加LCELayout->xml
 */

public abstract class LceFragment extends LazyFragment implements ReloadCallback {

    /**
     * 页面加载状态的容器,
     * 内部包含加载视图与内容视图
     */
    private LCELayout mLceLayout;

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);
        mLceLayout = $(R.id.lcelayout);
    }

    @Override
    protected void initData(View rootView) {
        super.initData(rootView);
        configLCE();
    }

    /**
     * 子类配置加载视图
     *
     * @return
     */
    private void configLCE() {
        DefaultLoadLayout defaultLoadLayout = (DefaultLoadLayout) mLceLayout.getLCEUiView();
        defaultLoadLayout.setReloadDataCallback(this);
    }


    /**
     * 获取加载视图
     *
     * @return
     */
    private LCEHandler getLCELayout() {
        return mLceLayout;
    }

    /**
     * 获取加载视图内部的状态view
     *
     * @return
     */
    private LCEUiHandler getLoadLayout() {
        return mLceLayout.getLCEUiView();
    }

    /**
     * 显示加载数据为空视图
     */
    public void showEmptyView() {
        getLCELayout().hideContentLayout();
        getLoadLayout().showEmpty();
    }

    /**
     * 显示正在加载视图
     */
    public void showLoadingView() {
        getLCELayout().hideContentLayout();
        getLoadLayout().showLoading();
    }

    /**
     * 显示加载视图视图
     */
    public void showFailureView() {
        getLCELayout().hideContentLayout();
        getLoadLayout().showFailure();
    }

    /**
     * 显示没有网络视图
     */
    public void showNoNetView() {
        getLCELayout().hideContentLayout();
        getLoadLayout().showNoNet();
    }

    /**
     * 显示内容页
     */
    public void showContentLayout() {
        getLCELayout().hideLCELayout();
        getLCELayout().showContentLayout();
    }

    /**
     * 自定义界面的icon 与现实标题
     *
     * @param drawableId
     * @param labelString
     */
    public void showCustomIconAndLabel(int drawableId, String labelString) {
        getLCELayout().hideContentLayout();
        ((DefaultLoadLayout) getLoadLayout()).setCustomIconAndLabel(drawableId, labelString);
    }


    /**
     * 显示加载视图
     */
    public void showLCELayout() {
        getLCELayout().showLoadLayout();
    }

    /**
     * 点击加载视图重新请求数据
     */
    @Override
    public void onReload() {

    }

    /**
     * 填充无数据时的空页面
     */
    @Override
    public void beforeLazyRequest() {
//        showLoadingView();
    }


    //TODO: 应该改写成下面这种形式，方面自定义与重写

    /**
     * 空界面
     *
     * @return
     */
    public int getEmptyLayoutId() {
        return -1;
    }

    /**
     * 错误界面
     *
     * @return
     */
    public int getErrorLayoutId() {
        return -1;
    }

    /**
     * 失败界面
     *
     * @return
     */
    public int getFailureLayoutId() {
        return -1;
    }

    /**
     * 没有网络界面
     *
     * @return
     */
    public int getNoNetLayoutId() {
        return -1;
    }
}
