package core.framework.dev.adapter.provider;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import core.framework.dev.R;
import core.framework.dev.bean.LiveInfo;
import core.framework.dev.ui.entry.FollowAnchorActivity;
import core.framework.dev.ui.video.VideoPlayerCenterActivity;
import lyh.library.cm.adapter.ItemViewProvider;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;
import lyh.library.cm.utils.SpannableStringUtils;
import lyh.library.cm.utils.ToastUtils;

/**
 * Created by lyh on 2017/1/4.
 */

public class BiliHomeLiveContentProvider implements ItemViewProvider<LiveInfo.DataBean.RecommendDataBean.LivesBean> {

    @Override
    public int itemLayoutResId() {
        return R.layout.item_bilibili_content;
    }

    @Override
    public void bindItemData(ViewHolder holder, LiveInfo.DataBean.RecommendDataBean.LivesBean itemData, int position) {
        holder.setText(R.id.tv_user_name, itemData.getOwner().getName())
                .setText(R.id.tv_watch_num, String.valueOf(itemData.getOnline()))
                .setImageViewUrl(holder.getContext(), R.id.iv_content, itemData.getCover().getSrc());

        TextView tvStyle = holder.getView(R.id.tv_bilibili_style);
        tvStyle.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder stringBuilder = SpannableStringUtils.getBuilder("")
                .append("#" + itemData.getArea() + "# ")
                .setForegroundColor(holder.getContext().getResources().getColor(R.color.b_color_theme))
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        FollowAnchorActivity.launch((Activity) holder.getContext(), itemData.getArea());
                    }
                })
                .append(itemData.getTitle()).create();
        tvStyle.setText(stringBuilder);

    }

    @Override
    public void onClick(View view, RecyclerView.ViewHolder holder, LiveInfo.DataBean.RecommendDataBean.LivesBean itemData, int position) {
        VideoPlayerCenterActivity.launch((Activity) view.getContext(), itemData.getPlayurl(), itemData.getArea_id());
    }

    @Override
    public boolean onLongClick(View view, RecyclerView.ViewHolder holder, LiveInfo.DataBean.RecommendDataBean.LivesBean itemData, int position) {
        return false;
    }
}
