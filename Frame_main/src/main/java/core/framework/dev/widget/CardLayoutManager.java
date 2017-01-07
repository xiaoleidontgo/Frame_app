package core.framework.dev.widget;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by lyh on 2016/12/30.
 * 自定义RecyclerView 布局管理器，实现卡片滑动效果
 */
public class CardLayoutManager extends RecyclerView.LayoutManager {

    public CardLayoutManager() {
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    //对子view进行布局
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }
}
