package core.framework.dev.adapter.provider;

import android.app.Activity;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import core.framework.dev.R;
import core.framework.dev.ui.entry.FollowAnchorActivity;
import lyh.library.cm.adapter.AbsItemViewProvider;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;

/**
 * Created by lyh on 2017/1/4.
 */

public class BiliHomeLiveFooterProvider extends AbsItemViewProvider<String> {
    @Override
    public int itemLayoutResId() {
        return R.layout.item_bilibili_footer;
    }

    @Override
    public void bindItemData(ViewHolder holder, String itemData, int position) {
        holder.setText(R.id.tv_refresh, itemData);

        final ImageView ivRefresh = holder.getView(R.id.iv_refresh);
        holder.setOnClickListener(new int[]{R.id.iv_refresh, R.id.tv_refresh}, (v) -> {
            RotateAnimation rotateAnimation = new RotateAnimation(0f, 720f, ivRefresh.getPivotX(), ivRefresh.getPivotY());
            rotateAnimation.setFillAfter(false);
            rotateAnimation.setInterpolator(new FastOutSlowInInterpolator());
            rotateAnimation.setDuration(1000);
            ivRefresh.setAnimation(rotateAnimation);
            ivRefresh.startAnimation(rotateAnimation);
        });


        holder.setOnClickListener(R.id.btn_check_more, v ->
                FollowAnchorActivity.launch((Activity) holder.getContext(), "更多相关直播")
        );

    }

}
