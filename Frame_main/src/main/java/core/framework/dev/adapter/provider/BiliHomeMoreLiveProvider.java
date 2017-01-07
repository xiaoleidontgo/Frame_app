package core.framework.dev.adapter.provider;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import core.framework.dev.R;
import core.framework.dev.bean.LiveInfo;
import core.framework.dev.ui.entry.FollowAnchorActivity;
import lyh.library.cm.adapter.AbsItemViewProvider;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;
import lyh.library.cm.utils.SpannableStringUtils;
import lyh.library.cm.utils.ToastUtils;

/**
 * Created by lyh on 2017/1/4.
 */

public class BiliHomeMoreLiveProvider extends AbsItemViewProvider<Double> {

    @Override
    public int itemLayoutResId() {
        return R.layout.item_bili_more_live;
    }

    @Override
    public void bindItemData(ViewHolder holder, Double itemData, int position) {

        holder.setOnClickListener(R.id.btn_more_live, v ->
                FollowAnchorActivity.launch((Activity) holder.getContext(), "更多直播")
        );

    }


}
