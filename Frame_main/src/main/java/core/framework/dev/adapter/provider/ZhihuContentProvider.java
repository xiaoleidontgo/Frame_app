package core.framework.dev.adapter.provider;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.WeakReference;

import core.framework.dev.FrameApp;
import core.framework.dev.R;
import core.framework.dev.bean.ZhiHuBefore;
import core.framework.dev.data.http.api.ZhiHuService;
import core.framework.dev.ui.common.WebActivity;
import lyh.library.cm.adapter.ItemViewProvider;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;

/**
 * Created by lyh on 2017/1/3.
 */

public class ZhihuContentProvider implements ItemViewProvider<ZhiHuBefore.StoriesBean> {

    @Override
    public int itemLayoutResId() {
        return R.layout.item_zhihu_before_content;
    }

    @Override
    public void bindItemData(ViewHolder holder, ZhiHuBefore.StoriesBean itemData, int position) {
        holder.setText(R.id.tv_zhihu_before_content, itemData.getTitle())
                .setImageViewUrl(FrameApp.getContext(), R.id.iv_zhihu_before_content, itemData.getImages().get(0));
    }

    @Override
    public void onClick(View view, RecyclerView.ViewHolder holder, ZhiHuBefore.StoriesBean itemData, int position) {
        WebActivity.launch((Activity) view.getContext(), ZhiHuService.ZHIHU_WEB_URL + itemData.getId());
    }

    @Override
    public boolean onLongClick(View view, RecyclerView.ViewHolder holder, ZhiHuBefore.StoriesBean itemData, int position) {
        return false;
    }

}
