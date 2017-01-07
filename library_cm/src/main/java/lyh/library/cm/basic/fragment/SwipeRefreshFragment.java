package lyh.library.cm.basic.fragment;

import android.view.View;

import lyh.library.cm.component.ptr.PtrFrameLayout;
import lyh.library.cm.component.ptr.PtrHandler;
import lyh.library.cm.component.ptr.PtrUIHandler;
import lyh.library.cm.component.ptr.header.MaterialHeader;
import lyh.library.cm.component.ptr.indicator.PtrIndicator;
import lyh.library.cm.utils.LocalDisplay;
import lyh.library.ultra.R;

/**
 * Created by lyh on 2016/10/24.
 * 可刷新Fragment
 */
public abstract class SwipeRefreshFragment extends LceFragment implements PtrHandler {

    /**
     * 刷新控件
     */
    private PtrFrameLayout mPtrFrameLayout;

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);
        mPtrFrameLayout = $(R.id.ptr_framelayout);
    }

    @Override
    protected void initData(View rootView) {
        super.initData(rootView);
        configPtrFrameLayout(mPtrFrameLayout);
        initPtrHeader();
    }


    /**
     * 配置下拉刷新
     */
    public void configPtrFrameLayout(PtrFrameLayout ptrFrameLayout) {
        // the following are default settings
        mPtrFrameLayout.setResistance(1.9f);
        mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrameLayout.setDurationToClose(200);
        mPtrFrameLayout.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrameLayout.setPullToRefresh(false);
        // default is true
        mPtrFrameLayout.setKeepHeaderWhenRefresh(true);
        mPtrFrameLayout.disableWhenHorizontalMove(true);
        mPtrFrameLayout.setPinContent(true);
        mPtrFrameLayout.setLoadingMinTime(800);
        mPtrFrameLayout.setPtrHandler(this);

    }

    /**
     * 初始化下拉刷新头
     */
    public void initPtrHeader() {
        // header
        final MaterialHeader header = new MaterialHeader(getContext());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrameLayout);

        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);

    }

    /**
     * 是否可下拉刷新
     * <p>
     * 子类需要覆盖这个方法以确定刷新控件何时可以进行刷新操作
     * <p></p>
     * 刷新容器下拉行为会询问此方法，以判断自身是否是在合适的时机可以进行刷新操作
     * 提供了默认的实现
     */
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return false;
    }

    /**
     * 开始刷新操作回调
     *
     * @param frame
     */
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {

    }

    /**
     * 刷新完成
     * 子类在此回调进行ui操作
     * 默认实现会让PtrFrameLayout隐藏刷新头
     */
    public void letRefreshComplete() {
        if (getRefreshView().isRefreshing())
            getRefreshView().refreshComplete();
    }

    /**
     * 子类获取刷新控件
     *
     * @return
     */
    public PtrFrameLayout getRefreshView() {
        return mPtrFrameLayout;
    }

    /**
     * 让刷新控件在延迟一段时间后自动刷新的实现
     *
     * @param delayTime
     */
    public void startAutoRefreshDelay(int delayTime) {
        if (!getRefreshView().isAutoRefresh())
            getRefreshView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getRefreshView().autoRefresh(true);
                }
            }, delayTime);
    }


}
