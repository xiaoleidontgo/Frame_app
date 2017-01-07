package core.framework.dev.adapter.provider;

import android.app.Activity;

import java.lang.ref.WeakReference;

import core.framework.dev.R;
import core.framework.dev.ui.entry.FollowAnchorActivity;
import lyh.library.cm.adapter.AbsItemViewProvider;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;

/**
 * Created by lyh on 2017/1/4.
 */

public class BiliHomeLiveEntryProvider extends AbsItemViewProvider<Integer> {

    @Override
    public int itemLayoutResId() {
        return R.layout.item_bilibili_video_entry;
    }

    @Override
    public void bindItemData(ViewHolder holder, Integer itemData, int position) {
        holder.setOnClickListener(
                new int[]{
                        R.id.ll_follow_anchor,
                        R.id.ll_live_center,
                        R.id.ll_search_room,
                        R.id.ll_all_category},
                v -> {
                    switch (v.getId()) {
                        case R.id.ll_follow_anchor:
                            FollowAnchorActivity.launch((Activity) holder.getContext(), "关注主播");
                            break;
                        case R.id.ll_live_center:
                            FollowAnchorActivity.launch((Activity) holder.getContext(), "直播中心");
                            break;
                        case R.id.ll_search_room:
                            FollowAnchorActivity.launch((Activity) holder.getContext(), "搜素直播");
                            break;
                        case R.id.ll_all_category:
                            FollowAnchorActivity.launch((Activity) holder.getContext(), "全部分类");
                            break;
                    }
                });
    }

}
