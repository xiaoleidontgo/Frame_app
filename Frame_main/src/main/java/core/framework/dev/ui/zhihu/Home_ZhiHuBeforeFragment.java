package core.framework.dev.ui.zhihu;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import core.framework.dev.adapter.provider.ZhihuBannerProvider;
import core.framework.dev.adapter.provider.ZhihuContentProvider;
import core.framework.dev.adapter.provider.ZhihuTitleProvider;
import core.framework.dev.bean.ZhiHuBefore;
import core.framework.dev.bean.ZhihuLatest;
import core.framework.dev.data.http.HttpManager;
import core.framework.dev.data.http.rx.XSubscriber;
import lyh.library.cm.adapter.ItemData;
import lyh.library.cm.basic.fragment.MultiItemSwipeRefreshListFragment;
import lyh.library.cm.component.ptr.PtrFrameLayout;

/**
 * Created by lyh on 2016/11/21.
 * 首页首页-过往消息
 */
public class Home_ZhiHuBeforeFragment extends MultiItemSwipeRefreshListFragment {

    private int page = 0;

    public static Home_ZhiHuBeforeFragment newInstance() {
        Bundle args = new Bundle();
        Home_ZhiHuBeforeFragment fragment = new Home_ZhiHuBeforeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onRegisterItems() {
        registerItem(ArrayList.class, new ZhihuBannerProvider());
        registerItem(String.class, new ZhihuTitleProvider());
        registerItem(ZhiHuBefore.StoriesBean.class, new ZhihuContentProvider());
    }

    @Override
    public void initAdapterData(List<ItemData> adapterData) {
        getBanner();
    }

    @Override
    public void beforeLazyRequest() {
        super.beforeLazyRequest();
        showLoadingView();
    }

    @Override
    public void lazyRequest() {
        startAutoRefreshDelay(100);
    }

    @Override
    protected boolean checkCanDoLoadMore() {
        return true;
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        super.onRefreshBegin(frame);
        doRequest();
    }

    @Override
    public void onLoadMoreRequested() {
        super.onLoadMoreRequested();
        doRequest();
    }

    /**
     * 数据请求执行
     */
    private void doRequest() {

        XSubscriber<ZhiHuBefore> subscriber = new XSubscriber<ZhiHuBefore>() {

            @Override
            public void onError(Throwable e) {
                if (getAdapterData().size() == 0)
                    showEmptyView();
                getLoadMoreView().onLoadFailure();
            }

            @Override
            public void onNext(ZhiHuBefore zhiHuBefore) {
                page++;
                if (zhiHuBefore != null) {
                    addMultiItemData(new String(zhiHuBefore.getDate()));
                    addMultiItemDatas(zhiHuBefore.getStories());
                    if (getRefreshView().isRefreshing())
                        getRefreshView().refreshComplete();
                    showContentLayout();
                } else
                    showToast("请求数据为空");
            }
        };
        HttpManager.getZhiHuBefore(page, subscriber);
    }

    private void getBanner() {
        XSubscriber<ZhihuLatest> subscriber = new XSubscriber<ZhihuLatest>() {
            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ZhihuLatest zhihuLatest) {
                List<ZhihuLatest.TopStoriesBean> top_stories = zhihuLatest.getTop_stories();
                addItemData(top_stories);
            }
        };
        HttpManager.getZhiHuLatest(subscriber);
    }

}
