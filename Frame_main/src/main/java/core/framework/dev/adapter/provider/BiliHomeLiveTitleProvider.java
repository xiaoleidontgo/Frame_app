package core.framework.dev.adapter.provider;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import core.framework.dev.R;
import core.framework.dev.bean.LiveInfo;
import core.framework.dev.ui.entry.FollowAnchorActivity;
import lyh.library.cm.adapter.AbsItemViewProvider;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;
import lyh.library.cm.utils.SpannableStringUtils;

/**
 * Created by lyh on 2017/1/4.
 */

public class BiliHomeLiveTitleProvider extends AbsItemViewProvider<LiveInfo.DataBean.RecommendDataBean.PartitionBean> {

    @Override
    public int itemLayoutResId() {
        return R.layout.item_bilibili_title;
    }

    @Override
    public void bindItemData(ViewHolder holder, LiveInfo.DataBean.RecommendDataBean.PartitionBean itemData, int position) {
        holder.setText(R.id.tv_entry_title, itemData.getName())
                .setImageViewUrl(holder.getContext(), R.id.iv_entry_icon, itemData.getSub_icon().getSrc());

        TextView tvTitleEntry = holder.getView(R.id.tv_entry_label);
        tvTitleEntry.setText(
                SpannableStringUtils.getBuilder("当前")
                        .append("" + itemData.getCount())
                        .setForegroundColor(holder.getContext().getResources().getColor(R.color.b_color_theme))
                        .append("个直播").create());

    }


    @Override
    public void onClick(View view, RecyclerView.ViewHolder holder, LiveInfo.DataBean.RecommendDataBean.PartitionBean itemData, int position) {
        super.onClick(view, holder, itemData, position);
        FollowAnchorActivity.launch((Activity) view.getContext(), itemData.getName());
    }
}
