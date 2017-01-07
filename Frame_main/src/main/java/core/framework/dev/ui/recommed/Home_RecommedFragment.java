package core.framework.dev.ui.recommed;

import android.os.Bundle;

import java.util.List;

import core.framework.dev.R;
import core.framework.dev.bean.RecommedBannerInfo;
import core.framework.dev.bean.RecommedInfo;
import core.framework.dev.data.http.HttpManager;
import core.framework.dev.data.http.rx.XSubscriber;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;
import lyh.library.cm.basic.fragment.SingleSwipeRefreshListFragment;

/**
 * Created by lyh on 2016/12/11.
 * 主页——推荐页
 */
public class Home_RecommedFragment extends SingleSwipeRefreshListFragment<RecommedInfo> {

    public static Home_RecommedFragment newInstance() {
        Bundle args = new Bundle();
        Home_RecommedFragment fragment = new Home_RecommedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.layout_item;
    }

    @Override
    protected void bindItemViewData(ViewHolder holder, RecommedInfo recommedInfo, int position) {

    }

    @Override
    public void initAdapterData(List<RecommedInfo> adapterData) {

    }

    @Override
    public void lazyRequest() {

        XSubscriber subscriber = new XSubscriber<Object>() {
            @Override
            public void onError(Throwable e) {
                if (getAdapterData().size() == 0)
                    showEmptyView();
                showToast("onError : " + e.getMessage());
            }

            @Override
            public void onNext(Object object) {
                //轮播数据
                if (object instanceof RecommedBannerInfo) {
                    showToast("轮播数据获取到");
                }
                //内容数据
                else if (object instanceof RecommedInfo) {
                    showToast("内容数据获取到");
                }
                showContentLayout();
            }
        };

        HttpManager.getBilibiliHomeRecommedInfo(subscriber);
    }
}
