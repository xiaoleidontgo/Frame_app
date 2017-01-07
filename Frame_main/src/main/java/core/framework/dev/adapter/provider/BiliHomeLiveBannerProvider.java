package core.framework.dev.adapter.provider;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import core.framework.dev.R;
import core.framework.dev.bean.LiveInfo;
import core.framework.dev.ui.common.WebActivity;
import lyh.library.cm.adapter.AbsItemViewProvider;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;
import lyh.library.cm.component.banner.PageIndicator;
import lyh.library.cm.component.banner.SlideBanner;

/**
 * Created by lyh on 2017/1/4.
 */

public class BiliHomeLiveBannerProvider extends AbsItemViewProvider<ArrayList<LiveInfo.DataBean.BannerBean>> {

    @Override
    public int itemLayoutResId() {
        return R.layout.item_banner;
    }

    @Override
    public void bindItemData(ViewHolder holder, ArrayList<LiveInfo.DataBean.BannerBean> itemData, int position) {
        SlideBanner banner = holder.getView(R.id.banner);
        ArrayList<String> urls = new ArrayList<>();
        for (LiveInfo.DataBean.BannerBean bean : itemData) {
            urls.add(bean.getImg());
        }
        banner.setUrls(urls);
        banner.setIndicatorMode(PageIndicator.MODE.RIGHT);
        banner.setBannerClickListener((v, item, url) -> {
            if (itemData.size() == 0) return;
            WebActivity.launch((Activity) holder.getContext(), itemData.get(item).getLink());
        });
    }

}
