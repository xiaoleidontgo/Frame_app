package core.framework.dev.adapter.provider;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.framework.dev.R;
import core.framework.dev.bean.ZhihuLatest;
import core.framework.dev.data.http.api.ZhiHuService;
import core.framework.dev.ui.common.WebActivity;
import lyh.library.cm.adapter.AbsItemViewProvider;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;
import lyh.library.cm.component.banner.SlideBanner;
import lyh.library.cm.utils.LocalDisplay;

/**
 * Created by lyh on 2017/1/3.
 */

public class ZhihuBannerProvider extends AbsItemViewProvider<List<ZhihuLatest.TopStoriesBean>> {

    @Override
    public int itemLayoutResId() {
        return R.layout.item_banner;
    }

    @Override
    public void bindItemData(ViewHolder holder, List<ZhihuLatest.TopStoriesBean> itemData, int position) {
        SlideBanner banner = holder.getView(R.id.banner);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LocalDisplay.dp2px(150));
        banner.setLayoutParams(lp);

        List<String> urls = new ArrayList<>(itemData.size());
        for (ZhihuLatest.TopStoriesBean b : itemData) {
            urls.add(b.getImage());
        }
        banner.setUrls(urls);
        banner.startPlay();

        if (banner.getBannerClickListener() == null) {
            banner.setBannerClickListener((v, item, url) ->
                    WebActivity.launch((Activity) holder.getContext(),
                            ZhiHuService.ZHIHU_WEB_URL + itemData.get(item).getId())
            );
        }

    }

}
