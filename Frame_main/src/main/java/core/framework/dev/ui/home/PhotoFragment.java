package core.framework.dev.ui.home;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.framework.dev.R;
import core.framework.dev.adapter.PhotoWallAdapter;
import lyh.library.cm.adapter.recycleview.wrapper.HeaderAndFooterWrapper;
import lyh.library.cm.basic.fragment.SwipeRefreshListFragment;
import lyh.library.cm.component.banner.SlideBanner;
import lyh.library.cm.utils.LocalDisplay;
import lyh.library.cm.widget.UiToolbar;

/**
 * Created by lyh on 2016/10/25.
 * banner + banner + grid + filterMenu
 * <p>
 * 滑动时 filterMenu 会悬停在顶部
 */
public class PhotoFragment extends SwipeRefreshListFragment<String> {

    private UiToolbar uiToolbar;
    private HeaderAndFooterWrapper headerWrapper;
    private AppBarLayout appBarLayout;

    public static PhotoFragment newInstance() {
        PhotoFragment photoFragment = new PhotoFragment();
        Bundle bundle = new Bundle();
        photoFragment.setArguments(bundle);
        return photoFragment;
    }

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);
        uiToolbar = $(R.id.toolbar);
        appBarLayout = $(R.id.app_bar);
        uiToolbar.setTitleMode(UiToolbar.MODE_TITLE_MORE);
        uiToolbar.setCenterTitle("图片墙");
        uiToolbar.setBackgroundColor(Color.argb(0, 37, 0x5B, 0xF1));
        appBarLayout.setBackgroundColor(Color.argb(0, 37, 0x5B, 0xF1));
        uiToolbar.setToolbarGradient(getRecyclerView(), 0, 250, (alpha, isCenter) -> {
            appBarLayout.setBackgroundColor(Color.argb(alpha, 37, 0x5B, 0xF1));
            if (alpha > 255 / 2) {
                uiToolbar.setCenterTitle("滑动查看更多图片");
            } else {
                uiToolbar.setCenterTitle("图片墙");
            }
        });
    }

    @Override
    protected void initData(View rootView) {
        super.initData(rootView);
    }

    @Override
    public void configLayoutManager(RecyclerView recyclerView) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void initAdapterData(List<String> adapterData) {
        for (int i = 0; i < 30; i++) {
            adapterData.add("MEIZHI...?GANHUO...?" + i);
        }
    }

    @Override
    protected boolean checkCanDoLoadMore() {
        return true;
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<String> data) {
        PhotoWallAdapter photoWallAdapter = new PhotoWallAdapter(mActivity, data);
        headerWrapper = new HeaderAndFooterWrapper(photoWallAdapter);
        SlideBanner banner = (SlideBanner) LayoutInflater.from(mActivity).inflate(R.layout.item_banner, null);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LocalDisplay.dp2px(150));
        banner.setLayoutParams(lp);
        String[] urls = {
                "http://pic35.nipic.com/20131121/2531170_145358633000_2.jpg",
                "http://img.taopic.com/uploads/allimg/121017/234940-12101FR22825.jpg",
                "http://pic47.nipic.com/20140901/6608733_145238341000_2.jpg",
                "http://img1.qq.com/gamezone/pics/14806/14806970.jpg"
        };
        banner.setUrls(Arrays.asList(urls));
        headerWrapper.addHeaderView(banner);
        return headerWrapper;
    }

    @Override
    public void onLoadMoreRequested() {
        super.onLoadMoreRequested();
        Log.d(TAG, "onLoadMoreRequest...");
        List<String> loadMoreList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            loadMoreList.add("MEIZHI...?LOAD..." + i);
        }
        addAllDataAndNotify(loadMoreList);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_photo;
    }

    @Override
    public void lazyRequest() {
        showContentLayout();
    }
}
