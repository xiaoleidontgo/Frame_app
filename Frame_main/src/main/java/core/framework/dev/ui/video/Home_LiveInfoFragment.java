package core.framework.dev.ui.video;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import core.framework.dev.adapter.provider.BiliHomeLiveBannerProvider;
import core.framework.dev.adapter.provider.BiliHomeLiveContentProvider;
import core.framework.dev.adapter.provider.BiliHomeLiveEntryProvider;
import core.framework.dev.adapter.provider.BiliHomeLiveFooterProvider;
import core.framework.dev.adapter.provider.BiliHomeLiveRecommedProvider;
import core.framework.dev.adapter.provider.BiliHomeLiveTitleProvider;
import core.framework.dev.adapter.provider.BiliHomeMoreLiveProvider;
import core.framework.dev.bean.LiveInfo;
import core.framework.dev.data.http.HttpManager;
import core.framework.dev.data.http.rx.XSubscriber;
import core.framework.dev.utils.ArrayUtils;
import lyh.library.cm.adapter.ItemData;
import lyh.library.cm.basic.fragment.MultiItemSwipeRefreshListFragment;
import lyh.library.cm.component.ptr.PtrFrameLayout;

/**
 * Created by lyh on 2016/11/8.
 * 首页直播
 */

public class Home_LiveInfoFragment extends MultiItemSwipeRefreshListFragment {

    public static Home_LiveInfoFragment newInstance() {
        Bundle args = new Bundle();
        Home_LiveInfoFragment fragment = new Home_LiveInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void configLayoutManager(RecyclerView recyclerView) {
        super.configLayoutManager(recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if ((getAdapterData().get(position).data instanceof ArrayList)
                        || (getAdapterData().get(position).data instanceof LiveInfo.DataBean.RecommendDataBean.PartitionBean)
                        || (getAdapterData().get(position).data instanceof LiveInfo.DataBean.RecommendDataBean.BannerDataBean)
                        || (getAdapterData().get(position).data instanceof String)
                        || (getAdapterData().get(position).data instanceof Double)
                        || (getAdapterData().get(position).data instanceof Integer)) {
                    return 2;
                }
                return 1;
            }
        });
    }

    @Override
    public void onRegisterItems() {
        registerItem(String.class, new BiliHomeLiveFooterProvider());
        registerItem(Double.class, new BiliHomeMoreLiveProvider());
        registerItem(Integer.class, new BiliHomeLiveEntryProvider());
        registerItem(ArrayList.class, new BiliHomeLiveBannerProvider());
        registerItem(LiveInfo.DataBean.RecommendDataBean.LivesBean.class, new BiliHomeLiveContentProvider());
        registerItem(LiveInfo.DataBean.RecommendDataBean.BannerDataBean.class, new BiliHomeLiveRecommedProvider());
        registerItem(LiveInfo.DataBean.RecommendDataBean.PartitionBean.class, new BiliHomeLiveTitleProvider());
    }

    @Override
    public void lazyRequest() {
        startAutoRefreshDelay(100);
    }

    @Override
    public void initAdapterData(List<ItemData> adapterData) {
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        super.onRefreshBegin(frame);

        HttpManager.getBilibiliLive(new XSubscriber<LiveInfo>() {

            @Override
            public void onError(Throwable e) {
                showToast("加载数据失败..." + e.getMessage());
                if (getAdapterData().size() == 0)
                    showFailureView();
                getRefreshView().refreshComplete();
            }

            @Override
            public void onNext(LiveInfo liveInfo) {
                parseBeanAndNotify(liveInfo);
                showContentLayout();
                getRefreshView().refreshComplete();
            }
        });

    }

    /**
     * 首页直播数据解析
     *
     * @param liveInfo
     */

    private void parseBeanAndNotify(LiveInfo liveInfo) {
        getAdapterData().clear();

        //入口标题
        List<LiveInfo.DataBean.EntranceIconsBean> entranceIcons = liveInfo.getData().getEntranceIcons();
        // 轮播图  0
        ArrayList<LiveInfo.DataBean.BannerBean> banner = (ArrayList<LiveInfo.DataBean.BannerBean>) liveInfo.getData().getBanner();
        addItemData(banner);

        //直播入口
        addItemData(new Integer("0"));

        // 推荐条目数据  11 --  banner -- 11
        LiveInfo.DataBean.RecommendDataBean recommend_data = liveInfo.getData().getRecommend_data();
        //推荐轮播数据
        List<LiveInfo.DataBean.RecommendDataBean.BannerDataBean> banner_data = recommend_data.getBanner_data();

        //推荐标题
        addItemData(recommend_data.getPartition());

        //将推荐数据切割成两份，中间插入一份推荐banner数据
        List<LiveInfo.DataBean.RecommendDataBean.LivesBean> lives1 = recommend_data.getLives();
        List<List<LiveInfo.DataBean.RecommendDataBean.LivesBean>> lists = ArrayUtils.splitListByIndex(lives1, lives1.size() / 2 - 1);

        if (banner_data.size() > 1) {
            addItemData(banner_data.get(0));
        }

        addItemDatas(lists.get(0));

        if (banner_data.size() > 1) {
            addItemData(banner_data.get(1));
        } else {
            addItemData(banner_data.get(0));
        }

        addItemDatas(lists.get(1));

        if (banner_data.size() > 2) {
            addItemData(banner_data.get(2));
        }

        //推荐脚
        addItemData(new String("9条新动态，点击刷新"));

        // 直播数据--分区  包含 标题 内容
        List<LiveInfo.DataBean.PartitionsBean> partitions = liveInfo.getData().getPartitions();
        for (int i = 0; i < partitions.size(); i++) {
            // 一个直播标题等信息
            LiveInfo.DataBean.RecommendDataBean.PartitionBean partition = partitions.get(i).getPartition();
            addItemData(partition);
            //一组直播
            List<LiveInfo.DataBean.RecommendDataBean.LivesBean> lives = partitions.get(i).getLives();
            addItemDatas(lives);

            //脚
            addItemData(new String("9条新动态，点击刷新"));
        }
        addItemData(new Double("0"));
        getAdapter().notifyDataSetChanged();

    }

    @Override
    public void onReload() {
        super.onReload();
        startAutoRefreshDelay(100);
        showLoadingView();
    }


}
